package com.endpoint.home.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author cr
 * @date 2022/10/27
 * @description
 */
@Data
@ApiModel(description = "小说首页推荐to")
public class BookCommendRespDto implements Serializable {
    //类型 0-轮播图 1-顶部栏 2-本周强推 3-热门推荐 4-精品推荐
    @ApiModelProperty(value = "推荐类型")
    private Integer type;
    //推荐小说ID
    @ApiModelProperty(value = "小说id")
    private Long bookId;

    @ApiModelProperty(value = "小说封面地址")
    private String picUrl;

    @ApiModelProperty(value = "小说名")
    private String bookName;

    @ApiModelProperty(value = "作家名")
    private String authorName;

    @ApiModelProperty(value = "书籍描述")
    private String bookDesc;
}
