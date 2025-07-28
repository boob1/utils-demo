package com.hongda.flowableutils17;

import org.flowable.engine.ProcessEngine;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class FlowableUtils17ApplicationTests {
    @Autowired
    private ProcessEngine processEngine;

    // 可以流程定义和部署
    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Test
    void contextLoads() {
    }

    /**
     * 1.流程部署操作：act_re_procdef
     * 创建表
     */
    @Test
    void deployFlow() {
        // RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deploy = repositoryService.createDeployment()
                .addClasspathResource("process/FirstFlow.bpmn20.xml")
                .name("第一个流程图")
                .deploy(); // 部署的方法
        System.out.println("deploy.getId() = " + deploy.getId());
    }

    /**
     * 发起流程
     * act_ru_task：待审批任务
     * act_ru_execution：会记录流程分支
     * act_hi_procinst：每启动一个流程实例就会记录一条
     */
    @Test
    void startProcess() {
        // 发起流程需要通过runtimeService来实现
        // RuntimeService runtimeService = processEngine.getRuntimeService();
        // act_re_procdef 表中的id

        String processId = "FirstFlow:1:5b59353b-64b0-11f0-9554-102017003d2f";
        // 根据流程定义id启动 返回的是当前启动的流程实例 ProcessInstance
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processId);
        System.out.println("processInstance.getId() = " + processInstance.getId());

        // 根据流程定义的key启动
       /* String processKey = "FirstFlow";
        runtimeService.startProcessInstanceByKey(processKey);*/
    }

    /**
     * 根据用户查询待办信息
     */
    @Test
    void findFlow() {
        // act_ru_task 表的ASSIGNEE_字段查询
        // 任务实例操作我们都是通过 TaskService 来实现的
        // TaskService taskService = processEngine.getTaskService();
        // 获取到 act_ru_task 中 assignee 字段是 zhangsan 的记录
        List<Task> list = taskService.createTaskQuery()
                .taskAssignee("lisi") // 指定查询的条件
                .list();
        for (Task task : list) {
            System.out.println(task.getId());
        }
    }

    /**
     * 任务的审批
     * 审批完act_ru_task：无数据了
     */
    @Test
    void completeTask() {
        // act_ru_task表的ID
        // 完成任务的审批，根据任务ID
        TaskService taskService = processEngine.getTaskService();
        //taskService.complete("9669fd52-64b2-11f0-9897-102017003d2f");
        taskService.complete("0f78ad31-6519-11f0-95b1-102017003d2f");
    }

    /**
     * 流程的挂起和激活
     */
    @Test
    void suspendedActivity() {
        // act_re_procdef表的流程定义 ID
        String processDefinitionId = "FirstFlow:1:5b59353b-64b0-11f0-9554-102017003d2f";

        // 查询流程定义
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionId(processDefinitionId)
                .singleResult();

        // 获取流程定义的状态
        boolean suspended = processDefinition.isSuspended();
        // 修改：SUSPENSION_STATE_字段；1：激活；2：挂起
        if (suspended) {
            // 如果流程定义处于挂起状态，则激活它
            System.out.println("激活流程");
            repositoryService.activateProcessDefinitionById(processDefinitionId);
        } else {
            // 如果流程定义处于激活状态，则挂起它
            System.out.println("挂起流程");
            repositoryService.suspendProcessDefinitionById(processDefinitionId);
        }
    }

    /**
     * 挂起流程实例
     */
    @Test
    void suspendedInstance() {
        // act_ru_task这个表的PROC_INST_ID_字段；
        // act_ru_task这个表的SUSPENSION_STATE_就由1改成2
        // 挂起流程实例
        runtimeService.suspendProcessInstanceById("0f71cf5c-6519-11f0-95b1-102017003d2f");

        // 激活流程实例（当前被注释掉）
        // runtimeService.activateProcessInstanceById("a7ae5680-7ba3-11ee-809a-c035ad224018");
    }


}
