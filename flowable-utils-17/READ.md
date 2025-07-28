1.学习flowable提供哪些接口

![img.png](img.png)
![img_1.png](img_1.png)
![img_6.png](img_6.png) 
1.1部署用：  
@Autowired  
private RepositoryService repositoryService;  
1.2启动用：  
@Autowired  
private RuntimeService runtimeService;  
1.3任务用：  
@Autowired  
private TaskService taskService;

2.表结构的支持
![img_2.png](img_2.png)
![img_3.png](img_3.png)

3.流程实例的启动涉及的表
![img_4.png](img_4.png)
![img_5.png](img_5.png)

