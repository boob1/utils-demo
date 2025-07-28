package com.hongda.flowableutils17;

import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.engine.repository.Deployment;
import org.junit.jupiter.api.Test;


public class FlowTest01 {
private static final String url = "jdbc:mysql://localhost:3306/flowable?serverTimezone=UTC";
    @Test
    void deployFlow() {
        // 流程引擎的配置对象
        ProcessEngineConfiguration cfg = new StandaloneProcessEngineConfiguration()
                .setJdbcUrl(url)
                .setJdbcDriver("com.mysql.cj.jdbc.Driver")
                .setJdbcUsername("root")
                .setJdbcPassword("123456")
                .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);

        // 构建流程引擎对象
        ProcessEngine processEngine = cfg.buildProcessEngine();

        // 部署流程需要获取 RepositoryService
        RepositoryService repositoryService = processEngine.getRepositoryService();

        // 创建部署
        Deployment deploy = repositoryService.createDeployment()
                .addClasspathResource("process/FirstFlow.bpmn20.xml") // 假设流程定义文件在类路径下的processes目录
                .name("第一个流程图")
                .deploy();//部署的方法

        System.out.println("流程部署ID: " + deploy.getId());
        System.out.println("流程部署名称: " + deploy.getName());
    }
}
