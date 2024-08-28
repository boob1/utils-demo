package test;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hongda.domain.entity.User;
import com.hongda.domain.entity.UserInfo;
import com.hongda.service.UserService;
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
  @Resource
  private UserService userService;

  @Test
  public void insert(){
    User user = new User();
    user.setUsername("张三");
    user.setPassword("123456");
    user.setPhone("12345678901");
    user.setCreateTime(new Date());
    user.setInfo(UserInfo.of(18,"男","我是一个测试用户"));
    user.setBalance("333");
    userMapper.insert(user);

  }


  @Test
  public void findByPage(){
   int pageNum = 1, pageSize = 10;
   // 分页条件
    Page<User> page = Page.of(pageNum, pageSize);

    //排序
    OrderItem orderItem = new OrderItem("balance", true);
    page.addOrder(orderItem);
    Page<User> userPage = userService.page(page);
    // 总个数
    System.out.println(userPage.getTotal());
    //总页数
    System.out.println(userPage.getPages());
    // 当前页数据
    System.out.println(userPage.getRecords());
  }
}
