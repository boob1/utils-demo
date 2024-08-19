package com.hongda.entity.data;

import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.data.RichTextStringData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.hongda.entity.sheet.CitySheet;
import com.hongda.entity.sheet.CompanySheet;
import com.hongda.entity.sheet.UserSheet;
import org.apache.poi.ss.usermodel.IndexedColors;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @Description TODO
 * @Author lyb
 * @Date 2024/8/19 16:12
 */
public class Mock {

  /**
   * 构建并返回一个包含示例用户信息的列表
   * 该方法用于生成模拟数据，以供测试或演示使用
   *
   * @return 返回包含四个虚拟用户信息的UserSheet列表
   */
  public static List<UserSheet> userList() {
    // 初始化用户列表，预设容量为10
    List<UserSheet> list = new ArrayList<>(10);

    // 构建并添加第一个用户信息
    list.add(UserSheet.builder()
        .userId(001L)
        .userName("张三")
        .userPhone("11112223123")
        .userEmail("zhansan@163.com")
        .userAddress("北京朝阳区")
        .gender(buildCellData("男"))
        .registerTime(Calendar.getInstance().getTime())
        .build());

    // 构建并添加第二个用户信息
    list.add(UserSheet.builder()
        .userId(002L)
        .userName("李四")
        .userPhone("11112223123")
        .userEmail("lisi@qq.com")
        .userAddress("南京玄武门")
        .gender(buildCellData("女"))
        .registerTime(Calendar.getInstance().getTime())
        .build());

    // 构建并添加第三个用户信息
    list.add(UserSheet.builder()
        .userId(003L)
        .userName("王五")
        .userPhone("11112223123")
        .userEmail("wangwu@google.com")
        .userAddress("杭州未来科技城")
        .gender(buildCellData("男"))
        .registerTime(Calendar.getInstance().getTime())
        .build());

    // 构建并添加第四个用户信息
    list.add(UserSheet.builder()
        .userId(004L)
        .userName("赵六")
        .userPhone("11112223123")
        .userEmail("zhaoliu@baidu.com")
        .userAddress("上海徐家汇")
        .gender(buildCellData("女"))
        .registerTime(Calendar.getInstance().getTime())
        .build());

    // 返回构建好的用户列表
    return list;
  }


  /**
   * 构建带有性别的单元格数据，根据性别赋予单元格不同的字体颜色
   *
   * @param gender 性别，用于判断设置字体颜色的依据
   * @return 返回设置好样式的单元格数据对象
   */
  private static WriteCellData<String> buildCellData(String gender) {
    // 创建单元格数据对象
    WriteCellData<String> cellData = new WriteCellData<>();
    // 设置单元格数据类型为富文本
    cellData.setType(CellDataTypeEnum.RICH_TEXT_STRING);

    // 创建富文本对象
    RichTextStringData richTextStringData = new RichTextStringData();
    // 将富文本对象设置到单元格数据中
    cellData.setRichTextStringDataValue(richTextStringData);
    // 设置富文本内容为传入的性别信息
    richTextStringData.setTextString(gender);
    // 创建字体对象
    WriteFont writeFont = new WriteFont();
    // 根据性别设置字体颜色
    if ("男".equalsIgnoreCase(gender)) {
      // 男性设为红色
      writeFont.setColor(IndexedColors.RED.getIndex());
    } else if ("女".equalsIgnoreCase(gender)) {
      // 女性设为绿色
      writeFont.setColor(IndexedColors.GREEN.getIndex());
    }
    // 将设置好的字体应用到富文本中
    richTextStringData.applyFont(writeFont);
    // 返回设置好样式的单元格数据
    return cellData;
  }


  /**
   * 构建并返回一个包含多个城市的列表
   * 每个城市都通过CitySheet对象描述，包括城市的名称和简介
   * 这个方法展示了如何通过静态方法创建对象，并将对象添加到列表中
   *
   * @return 返回一个List类型的列表，包含多个CitySheet对象
   */
  public static List<CitySheet> cityList() {
    // 初始化城市列表，预计添加10个城市
    List<CitySheet> list = new ArrayList<>(10);

    // 添加杭州市及其简介
    list.add(CitySheet.builder()
        .cityName("杭州市")
        .cityDesc("杭州市一般指杭州。 杭州，简称“杭”，古称临安、钱塘，浙江省辖地级市、省会、副省级市、特大城市、国务院批复确定的浙江省经济、文化、科教中心，长江三角洲中心城市之一，环杭州湾大湾区核心城市、G60科创走廊中心城市。")
        .build());

    // 添加合肥市及其简介
    list.add(CitySheet.builder()
        .cityName("合肥市")
        .cityDesc("合肥市一般指合肥。 合肥，简称“庐”或“合”，古称庐州、庐阳、合淝，安徽省辖地级市、省会，是合肥都市圈中心城市，国务院批复确定的中国长三角城市群副中心城市，全国四大科教基地、现代制造业基地和综合交通枢纽。")
        .build());

    // 添加武汉市及其简介
    list.add(CitySheet.builder()
        .cityName("武汉市")
        .cityDesc("武汉市一般指武汉。 武汉，简称“汉”，别称江城，是湖北省省会，中部六省唯一的副省级市，超大城市，中国中部地区的中心城市，全国重要的工业基地、科教基地和综合交通枢纽，联勤保障部队机关驻地。")
        .build());

    // 添加深圳市及其简介
    list.add(CitySheet.builder()
        .cityName("深圳市")
        .cityDesc("深圳市一般指深圳。 深圳，简称“深”，别称鹏城，广东省辖地级市，是广东省副省级市，国家计划单列市，超大城市，国务院批复确定的中国经济特区、全国性经济中心城市、国际化城市、科技创新中心、区域金融中心、商贸物流中心。")
        .build());

    // 返回城市列表
    return list;
  }


  /**
   * 构建并返回一个包含若干公司信息的列表
   *
   * @return 包含多个CompanySheet对象的列表，每个对象代表一个公司的相关信息
   */
  public static List<CompanySheet> companyList() {
    // 初始化公司列表，预设容量为10
    List<CompanySheet> list = new ArrayList<>(10);

    // 构建并添加阿里巴巴公司信息
    list.add(CompanySheet.builder()
        .companyName("阿里巴巴")
        .companyBoss("马云")
        .companyBase("杭州市")
        .companyDesc("阿里巴巴集团经营多项业务，另外也从关联公司的业务和服务中取得经营商业生态系统上的支援。业务和关联公司的业务包括：淘宝网、天猫、聚划算、全球速卖通、阿里巴巴国际交易市场、1688、阿里妈妈、阿里云、蚂蚁集团 [408]  、菜鸟网络等。")
        .build());

    // 构建并添加字节跳动公司信息
    list.add(CompanySheet.builder()
        .companyName("字节跳动")
        .companyBoss("张一鸣")
        .companyBase("北京市")
        .companyDesc("字节跳动的全球化布局始于2015年 [3]  ，“技术出海”是字节跳动全球化发展的核心战略 [4]  ，其旗下产品有今日头条、西瓜视频、抖音、头条百科、皮皮虾、懂车帝、悟空问答等。")
        .build());

    // 构建并添加腾讯公司信息
    list.add(CompanySheet.builder()
        .companyName("腾讯")
        .companyBoss("马化腾")
        .companyBase("深圳市")
        .companyDesc("社交和通信服务QQ及微信/WeChat、社交网络平台QQ空间、腾讯游戏旗下QQ游戏平台、门户网站腾讯网、腾讯新闻客户端和网络视频服务腾讯视频等。")
        .build());

    // 构建并添加百度公司信息
    list.add(CompanySheet.builder()
        .companyName("百度")
        .companyBoss("李彦宏")
        .companyBase("北京市")
        .companyDesc("百度（Baidu）是拥有强大互联网基础的领先AI公司。百度愿景是：成为最懂用户，并能帮助人们成长的全球顶级高科技公司。")
        .build());

    // 返回构建好的公司列表
    return list;
  }

}

