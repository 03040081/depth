package com.zsc.modules.job.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zsc.common.base.PageBean;
import com.zsc.common.base.PageData;
import com.zsc.common.base.ResultCode;
import com.zsc.common.constant.CommonKey;
import com.zsc.common.constant.Constant;
import com.zsc.modules.job.dao.ScheduleJobDao;
import com.zsc.modules.job.entity.ScheduleJobEntity;
import com.zsc.modules.job.service.ScheduleJobService;
import com.zsc.modules.job.utils.ScheduleUtils;
import lombok.extern.slf4j.Slf4j;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ScheduleJobServiceImpl implements ScheduleJobService {

    @Autowired
    private ScheduleJobDao scheduleJobDao;

    @Autowired
    private Scheduler scheduler;

    @PostConstruct
    public void init(){
        List<ScheduleJobEntity> scheduleJobList = scheduleJobDao.list();
        log.info("init ScheduleJob list:{}", scheduleJobList);
        for (ScheduleJobEntity scheduleJob : scheduleJobList) {
            CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, scheduleJob.getJobId());
            if (cronTrigger == null) {
                ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
                log.info("createScheduleJob:{}", scheduleJob);
            } else {
                ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
                log.info("updateScheduleJob:{}", scheduleJob);
            }
        }
    }

    @Override
    public PageBean queryPage(Map<String, Object> params) {
        Integer page = (Integer) params.get(CommonKey.PAGE);
        Integer row = (Integer) params.get(CommonKey.ROW);
        //String beanName = (String)params.get("beanName");
        PageHelper.startPage(page, row);
        List<ScheduleJobEntity> scheduleJobList = scheduleJobDao.selectByParams(params);
        PageInfo<ScheduleJobEntity> pageInfo = new PageInfo<>(scheduleJobList);
        long total = pageInfo.getTotal();

        PageBean<ScheduleJobEntity> pageBean = new PageBean<>(ResultCode.SUCCESS);
        PageData<ScheduleJobEntity> pageData = new PageData<>();

        pageData.setTotal(total);
        pageData.setList(scheduleJobList);
        pageBean.setData(pageData);

        return pageBean;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveJob(ScheduleJobEntity scheduleJob) {
        scheduleJob.setCreateTime(new Date());
        scheduleJob.setStatus(Constant.ScheduleStatus.NORMAL.getValue());
        scheduleJobDao.insert(scheduleJob);

        ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ScheduleJobEntity scheduleJob) {
        scheduleJobDao.update(scheduleJob);
        ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatch(Long[] jobIds) {
        for(Long jobId : jobIds){
            ScheduleUtils.deleteScheduleJob(scheduler, jobId);
        }

        //删除数据
        scheduleJobDao.updateDeleteBatch(Arrays.asList(jobIds));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateBatch(Long[] jobIds, int status) {
        Map<String, Object> map = new HashMap<>(2);
        map.put("list", jobIds);
        map.put("status", status);
        return scheduleJobDao.updateBatch(map);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void run(Long[] jobIds) {
        for(Long jobId : jobIds){
            ScheduleUtils.run(scheduler, scheduleJobDao.selectById(jobId));
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void pause(Long[] jobIds) {
        for(Long jobId : jobIds){
            ScheduleUtils.pauseJob(scheduler, jobId);
        }

        updateBatch(jobIds, Constant.ScheduleStatus.PAUSE.getValue());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resume(Long[] jobIds) {
        for(Long jobId : jobIds){
            ScheduleUtils.resumeJob(scheduler, jobId);
        }

        updateBatch(jobIds, Constant.ScheduleStatus.NORMAL.getValue());
    }
}
