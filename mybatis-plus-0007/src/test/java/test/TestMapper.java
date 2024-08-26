package test;

import com.hongda.domain.entity.User;
import java.util.Date;
import javax.annotation.Resource;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Description
 * @Author lyb
 * @Date 2024/8/24 23:40
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = com.hongda.MybatisPlusApplication.class)
public class TestMapper {
  @Resource
  private com.hongda.mapper.UserMapper userMapper;

  @Test
  public void insert(){
    User user = new User();
    user.setUsername("张三");
    user.setPassword("123456");
    user.setPhone("12345678901");
    user.setCreateTime(new Date());
    user.setBalance("333");
    user.setPhone("{}");
    userMapper.insert(user);

  }

}
