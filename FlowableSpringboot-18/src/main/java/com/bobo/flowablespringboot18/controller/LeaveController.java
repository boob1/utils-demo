package com.bobo.flowablespringboot18.controller;

import com.bobo.flowablespringboot18.entity.Result;
import com.bobo.flowablespringboot18.entity.VO.TaskVO;
import lombok.extern.slf4j.Slf4j;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.*;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.image.ProcessDiagramGenerator;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/leave")
@Slf4j
public class LeaveController {
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private ProcessEngine processEngine;

    /**
     * 学生提交请假申请
     * 该方法用于学生提交请假申请，通过Flowable启动流程实例并自动完成首个任务，返回包含流程ID的成功结果。
     *
     * @param day         请假天数
     * @param studentUser 学生用户名
     * @return 包含流程ID的执行结果
     */
    @GetMapping("add/{day}/{studentUser}")
    public Result sub(@PathVariable("day") Integer day, @PathVariable("studentUser") String studentUser) {
        // 学生提交请假申请
        Map<String, Object> map = new HashMap<>();
        map.put("day", day);
        map.put("studentName", studentUser);
        // stuLeave为学生请假流程xml文件中的id，即后面bpmn20.xml文件的process id="stuLeave"
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("stuLeave", map);
        System.out.println("流程实例ID：" + processInstance.getId());
        //完成申请任务
        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
        taskService.complete(task.getId());
        //此处id为流程id
        return new Result(true, "提交成功.流程Id为：" + processInstance.getId());
    }

    /**
     * 获取教师任务列表
     * 该方法用于查询辅导员审批节点的任务列表。
     * 首先通过taskService查询候选组为"a"的任务，然后将每个任务的相关变量封装成TaskVO对象，最后根据任务是否存在返回成功或失败的结果。
     *
     * @return Result 包含任务列表的统一返回结果对象
     * - 当查询成功且有任务时，返回true状态码和任务列表数据
     * - 当没有任务时，返回false状态码和提示信息
     */
    @GetMapping("teacherList")
    public Result teacherList() {
        //此处.taskCandidateGroup("a")的值“a”即是画流程图时辅导员审批节点"分配用户-候选组"中填写的值
        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("a").list();
        List<TaskVO> taskVOList = tasks.stream().map(task -> {
            Map<String, Object> variables = taskService.getVariables(task.getId());
            return new TaskVO(task.getId(), variables.get("day").toString(), variables.get("studentName").toString());
        }).collect(Collectors.toList());
        System.out.println("任务列表：" + tasks);
        if (tasks == null || tasks.size() == 0) {
            return new Result(false,"没有任务");
        }
        return new Result(true,"获取成功",taskVOList);
    }

    /**
     * 辅导员批准
     * 该方法用于审批教师申请，通过任务ID查找并完成指定任务。首先查询候选组为"a"的任务列表，再根据taskId精确查找任务，
     * 若任务不存在则返回失败结果；若存在，则设置审批结果为“通过”，并调用`taskService.complete()`完成该任务，最后返回成功结果。
     *
     * @param taskId 任务ID，非流程id
     */
    @GetMapping("teacherApply/{taskId}")
    public Result teacherApply(@PathVariable("taskId") String taskId) {
        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("a").list();
        Task task = taskService.createTaskQuery().taskCandidateGroup("a").taskId(taskId).singleResult();
        if (task == null) {
            return new Result(false, "没有任务");
        }
        //通过审核
        HashMap<String, Object> map = new HashMap<>();
        map.put("outcome", "通过");
//        for (Task task : tasks) {
//            taskService.complete(task.getId(), map);
//        }
        taskService.complete(task.getId(), map);
        return new Result(true, "审批成功");
    }

    /**
     * 辅导员拒绝
     * 该方法用于处理辅导员拒绝请假申请的流程操作。
     * 首先根据任务ID和候选组查询任务，若任务不存在则返回错误信息；若存在，则设置流程变量`outcome`为"驳回"，并完成该任务，最后返回操作结果。
     *
     * @param taskId 任务ID，非流程id
     */
    @GetMapping("teacherReject/{taskId}")
    public Result teacherReject(@PathVariable("taskId") String taskId) {
        Task task1 = taskService.createTaskQuery().taskCandidateGroup("a").taskId(taskId).singleResult();
        //Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task1 == null) {
            return new Result(false, "没有任务");
        }
        //通过审核
        HashMap<String, Object> map = new HashMap<>();
        map.put("outcome", "驳回");
        taskService.complete(task1.getId(), map);
        return new Result(true, "审批失败");
    }

    /**
     * 院长获取审批管理列表
     * 该方法用于获取院长审批任务列表。首先查询候选组为“b”的任务，
     * 然后将每个任务的相关变量（如天数和学生姓名）封装成TaskVO对象，最后返回结果。如果无任务则返回失败信息。
     */
    @GetMapping("deanList")
    public Result deanList() {
        //此处.taskCandidateGroup("b")的值“b”即是画流程图时辅导员审批节点"分配用户-候选组"中填写的值
        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("b").list();
        List<TaskVO> taskVOList = tasks.stream().map(task -> {
            Map<String, Object> variables = taskService.getVariables(task.getId());
            return new TaskVO(task.getId(), variables.get("day").toString(), variables.get("studentName").toString());
        }).collect(Collectors.toList());
        if (tasks == null || tasks.size() == 0) {
            return new Result(false, "没有任务");
        }
        return new Result(true, "获取成功", taskVOList);
    }

    /**
     * 院长批准
     * 该方法用于院长审批任务。首先查询候选组为“b”的任务列表，
     * 若无任务则返回失败信息；否则，遍历任务列表，将每个任务的审核结果设为“通过”，并完成任务，最后返回审批成功的结果
     * @param taskId 任务ID，非流程id
     * @return
     */
    @GetMapping("deanApply/{taskId}")
    public Result apply(@PathVariable("taskId") String taskId) {
        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("b").list();
        //Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (tasks == null) {
            return new Result(false, "没有任务");
        }
        //通过审核
        HashMap<String, Object> map = new HashMap<>();
        map.put("outcome", "通过");
        for (Task task : tasks) {
            taskService.complete(task.getId(), map);
        }
        return new Result(true,"审批成功");
    }

    /**
     * 院长拒绝
     * @param taskId 任务ID，非流程id
     * @return
     */
    @GetMapping("deanReject/{taskId}")
    public Result deanReject(@PathVariable("taskId") String taskId) {
//        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("b").list();
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task == null) {
            return new Result(false, "没有任务");
        }
        //通过审核
        HashMap<String, Object> map = new HashMap<>();
        map.put("outcome", "驳回");
//        for (Task task : tasks) {
//            taskService.complete(task.getId(), map);
//        }
        taskService.complete(task.getId(), map);
        return new Result(true,"审批成功");
    }

    /**
     * 再次申请
     * @param piId 流程id
     * @param day
     * @return
     */
    @GetMapping("subAgain/{piId}/{day}")
    public Result subAgain(@PathVariable("piId") String piId, @PathVariable("day") Integer day){
        Task task = taskService.createTaskQuery().processInstanceId(piId).singleResult();
        if(Objects.isNull(task)){
            return new Result(false, "没有任务");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("day", day);
        taskService.complete(task.getId(), map);
        return new Result(true,"申请成功");
    }

    /**
     * 生成流程图
     *
     * @param taskId 流程ID
     */
    @GetMapping( "processDiagram/{taskId}")
    public void genProcessDiagram(HttpServletResponse httpServletResponse,@PathVariable("taskId") String taskId) throws Exception {
        ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(taskId).singleResult();

        //流程走完的不显示图
        if (pi == null) {
            return;
        }
        Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();
        //使用流程实例ID，查询正在执行的执行对象表，返回流程实例对象
        String InstanceId = task.getProcessInstanceId();
        List<Execution> executions = runtimeService
                .createExecutionQuery()
                .processInstanceId(InstanceId)
                .list();

        //得到正在执行的Activity的Id
        List<String> activityIds = new ArrayList<>();
        List<String> flows = new ArrayList<>();
        for (Execution exe : executions) {
            List<String> ids = runtimeService.getActiveActivityIds(exe.getId());
            activityIds.addAll(ids);
        }

        //获取流程图
        BpmnModel bpmnModel = repositoryService.getBpmnModel(pi.getProcessDefinitionId());
        ProcessEngineConfiguration engconf = processEngine.getProcessEngineConfiguration();
        ProcessDiagramGenerator diagramGenerator = engconf.getProcessDiagramGenerator();
        InputStream in = diagramGenerator.generateDiagram(bpmnModel,
                "png", activityIds, flows, engconf.getActivityFontName(),
                engconf.getLabelFontName(), engconf.getAnnotationFontName(), engconf.getClassLoader(),
                1.0 ,true);
        OutputStream out = null;
        byte[] buf = new byte[1024];
        int legth = 0;
        try {
            //此处设置resp的header诸多文章都没写，但是我不写出不来流程图，诸位可去掉试试
            httpServletResponse.setHeader("Content-Type",
                    "image/png;charset=utf-8");
            out = httpServletResponse.getOutputStream();
            while ((legth = in.read(buf)) != -1) {
                out.write(buf, 0, legth);
            }
        } finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }
}