1.启动类上添加注解：  
@EnableRetry  

2.需要重试的方法上添加注解：  
@Retryable(retryFor = Exception.class,maxAttempts = 3,backoff = @Backoff(delay = 5000,multiplier = 2))   
1.重试的异常；2.重试的次数;3重试等待的时间第一次为5秒，第二次以后是上次的2倍；  


3.兜底的方法，三次仍是失败；走这个注解的方法：
@Recover  

4.jdk:必须版本要求为17

