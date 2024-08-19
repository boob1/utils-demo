package com.hongda.config;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.util.ListUtils;
import com.hongda.data.UserData;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @Description EasyExcel有一个AnalysisEventListener，可以自定义一个Listener继承AnalysisEventListener，
 * 里面有一个invoke方法，每条数据都会进入这个方法。我们可以在这里做校验、存储、抛异常等动作，EasyExcel将这些都流程化了，
 * 写起代码来非常舒服。当然也有一些点需要注意下，比如自定义Listener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去。
 *
 * @Author lyb
 * @Date 2024/8/19 17:10
 */
@Slf4j
public class UserExcelReadListener extends AnalysisEventListener<UserData> {

  /**
   * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
   */
  private static final int BATCH_COUNT = 100;

  /**
   * 创建一个Pattern对象，使用正则表达式校验手机号格式
   */
  private static final Pattern PHONE_REGEX = Pattern.compile("^1[0-9]{10}$");

  /**
   * 缓存的数据
   */
  private List<UserData> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

  /**
   * 错误信息列表
   */
  private final List<String> errorMsgList = new ArrayList<>(BATCH_COUNT);

  @Override
  public void invoke(UserData userData, AnalysisContext analysisContext) {
    log.info("解析到一条数据:{}", userData);
    int rowIndex = analysisContext.readRowHolder().getRowIndex();
    String name = userData.getUserName();
    String phone = userData.getUserPhone();
    String gender = userData.getGender();
    String email = userData.getUserEmail();
    Integer age = userData.getAge();
    String address = userData.getUserAddress();
    // 只有全部校验通过的对象才能被添加到下一步
    if (nameValid(rowIndex, name) && phoneValid(rowIndex, phone) && genderValid(rowIndex, gender) &&
        emailValid(rowIndex, email) && ageValid(rowIndex, age) && addressValid(rowIndex, address)) {
      cachedDataList.add(userData);
    }
    // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
    if (cachedDataList.size() >= BATCH_COUNT) {
      // to saveData();
      // 存储完成清理 list
      cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
    }
  }

  @Override
  public void doAfterAllAnalysed(AnalysisContext analysisContext) {
    log.info("所有数据解析完成！全部校验通过的数据有{}条", cachedDataList.size());
    // 这里也要保存数据，确保最后遗留的数据也存储到数据库saveData();
    // todo saveData();

  }

  @Override
  public void onException(Exception exception, AnalysisContext context) throws Exception {
    if (exception instanceof RuntimeException) {
      throw exception;
    }
    int index = context.readRowHolder().getRowIndex() + 1;
    errorMsgList.add("第" + index + "行解析错误");
  }

  @Override
  public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
    int totalRows = context.readSheetHolder().getApproximateTotalRowNumber() - 1;
    int maxNum = 2000;
    if (totalRows > maxNum) {
      errorMsgList.add("数据量过大,单次最多上传2000条");
      throw new RuntimeException("数据量过大,单次最多上传2000条");
    }
  }

  public List<String> getErrorMsgList() {
    return errorMsgList;
  }

  /**
   * 名称的校验
   *
   * @param rowIndex 行数
   * @param name     名称
   */
  private Boolean nameValid(Integer rowIndex, String name) {
    if (StringUtils.isBlank(name)) {
      errorMsgList.add("第" + rowIndex + "行,'姓名'不能为空");
      return Boolean.FALSE;
    }
    return Boolean.TRUE;
  }

  private Boolean phoneValid(int rowIndex, String phone) {
    if (StringUtils.isBlank(phone)) {
      errorMsgList.add("第" + rowIndex + "行,'手机号'不能为空");
      return Boolean.FALSE;
    }
    return Boolean.TRUE;
  }

  /**
   * 性别的校验
   *
   * @param rowIndex 行数
   * @param gender   性别
   */
  private Boolean genderValid(int rowIndex, String gender) {
    if (StringUtils.isBlank(gender)) {
      errorMsgList.add("第" + rowIndex + "行,'性别'不能为空");
      return Boolean.FALSE;
    }
    return Boolean.TRUE;
  }

  /**
   * 地址校验
   *
   * @param rowIndex 行数
   * @param address  地址
   */
  private Boolean addressValid(int rowIndex, String address) {
    // 校验地址是否为空
    if (StringUtils.isBlank(address)) {
      errorMsgList.add("第 " + rowIndex + " 行,'地址'不能为空");
      return Boolean.FALSE;
    }
    return Boolean.TRUE;
  }

  /**
   * 年龄的校验
   *
   * @param rowIndex 行数
   * @param age      年龄
   */
  private Boolean ageValid(int rowIndex, Integer age) {
    // 校验年龄是否为空
    if (Objects.isNull(age)) {
      errorMsgList.add("第 " + rowIndex + " 行'年龄'不能为空");
      return Boolean.FALSE;
    }
    return Boolean.TRUE;
  }

  /**
   * 邮箱的校验
   *
   * @param rowIndex 行数
   * @param email    邮箱
   */
  private Boolean emailValid(int rowIndex, String email) {
    // 校验邮箱是否为空
    if (StringUtils.isBlank(email)) {
      errorMsgList.add("第 " + rowIndex + " 行'邮箱'不能为空");
      return Boolean.FALSE;
    }
    return Boolean.TRUE;
  }
}

