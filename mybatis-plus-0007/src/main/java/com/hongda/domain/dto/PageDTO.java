package com.hongda.domain.dto;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.Data;

/**
 * @Description
 * @Author lyb
 * @Date 2024/8/27 18:08
 */
@Data
@ApiModel("分页返回结果")
public class PageDTO<T> {

  @ApiModelProperty("总条数")
  private Long total;

  @ApiModelProperty("总页数")
  private Long totalPage;

  @ApiModelProperty("数据列表")
  private List<T> list;

  /**
    * @Author lyb
    * @Description 把分页的p类型转化Vo类型
    * @Date 21:55 2024/8/28
    * @Param [p:源数据, voClass：转换后的类型]
    * @return com.hongda.domain.dto.PageDTO
    **/
  public static <PO, VO> PageDTO of(Page<PO> p, Class<VO> voClass) {
    PageDTO<VO> pageDTO = new PageDTO<>();
    pageDTO.setTotal(p.getTotal());
    pageDTO.setTotalPage(p.getPages());

    List<PO> records = p.getRecords();
    if (CollUtil.isEmpty(records)) {
      pageDTO.setList(Collections.EMPTY_LIST);
      return pageDTO;
    }
    pageDTO.setList(BeanUtil.copyToList(records, voClass));
    return pageDTO;
  }


  /**
   * @Author lyb
   * @Description 把分页的p类型转化Vo类型;是利用自己写好的转化器
   * @Date 21:55 2024/8/28
   * @Param [p:源数据, voClass：转换后的类型]
   * @return com.hongda.domain.dto.PageDTO
   **/
  public static <PO, VO> PageDTO of(Page<PO> p, Function<PO,VO> convertor) {
    PageDTO<VO> pageDTO = new PageDTO<>();
    pageDTO.setTotal(p.getTotal());
    pageDTO.setTotalPage(p.getPages());

    List<PO> records = p.getRecords();
    if (CollUtil.isEmpty(records)) {
      pageDTO.setList(Collections.EMPTY_LIST);
      return pageDTO;
    }
    List<VO> pageVo = records.stream().map(convertor).collect(Collectors.toList());
    pageDTO.setList(pageVo);
    return pageDTO;
  }


}
