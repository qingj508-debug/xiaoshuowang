package com.endpoint.search.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author cr
 * @date 2022/11/10
 * @description
 */
@Data
@ToString
public class SearchParam {
    @ApiModelProperty("搜索关键字")
    private String keyword;

    @ApiModelProperty("作品方向")
    private Integer workDirection;

    @ApiModelProperty("分类ID")
    private Integer catId;

    @ApiModelProperty("是否收费，1：收费，0：免费")
    private Integer isVip;

    @ApiModelProperty("小说更新状态，0：连载中，1：已完结")
    private Integer bookStatus;

    @ApiModelProperty("字数最小值")
    private Integer wordCountMin;

    @ApiModelProperty("字数最大值")
    private Integer wordCountMax;

    @ApiModelProperty(hidden = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updateTimeMin;

    @ApiModelProperty("排序字段")
    private String sort;
}
