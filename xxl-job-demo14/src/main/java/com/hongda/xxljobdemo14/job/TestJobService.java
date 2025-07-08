package com.hongda.xxljobdemo14.job;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import com.xxl.job.core.util.ShardingUtil;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Slf4j
@Component
public class TestJobService {

    /**
     * 2、分片广播任务
     */
    @XxlJob("sendMessageJobHandler")
    public ReturnT<String> execute(String param) {
        // 任务逻辑
        return ReturnT.SUCCESS;
    }
}
