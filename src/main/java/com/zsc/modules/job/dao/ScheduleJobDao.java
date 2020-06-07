package com.zsc.modules.job.dao;

import com.zsc.modules.job.entity.ScheduleJobEntity;
import com.zsc.modules.job.utils.ScheduleJob;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ScheduleJobDao {

    /**
     * 根据id查找定时任务
     * @param jobId
     * @return
     */
    ScheduleJobEntity selectById(Long jobId);

    /**
     * 动态sql，根据
     * @param params
     * @return
     */
    List<ScheduleJobEntity> selectByParams(Map<String, Object> params);

    List<ScheduleJobEntity> list();

    long insert(ScheduleJobEntity scheduleJob);

    int update(ScheduleJobEntity scheduleJob);

    int updateDeleteById(Long jobId);

    int updateDeleteBatch(List<Long> list);

    int updateBatch(Map<String, Object> params);

}
