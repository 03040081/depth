package com.zsc.modules.job.task.Impl;

import com.zsc.modules.job.task.ITask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("testTask")
public class TestTask implements ITask {

    @Override
    public void run(String params) {
        log.info("TestTask {}", params);
    }
}
