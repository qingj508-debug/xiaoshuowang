package com.endpoint.home.to;

import com.endpoint.common.utils.DateTimeDeserializer;
import com.endpoint.common.utils.DateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author cr
 * @date 2022/11/4
 * @description
 */
@Data
@ApiModel(description="小说书籍TO")
public class BookTo implements Serializable{
        @ApiModelProperty(value = "主键")
        private Long id;

        @ApiModelProperty(value = "作品方向，0：男频，1：女频")
        private Integer workDirection;

        @ApiModelProperty(value = "分类ID")
        private Integer catId;

        @ApiModelProperty(value = "分类名")
        private String catName;

        @ApiModelProperty(value = "小说封面")
        private String picUrl;

        @ApiModelProperty(value = "小说名")
        private String bookName;

        @ApiModelProperty(value = "作者名")
        private String authorName;

        @ApiModelProperty(value = "书籍描述")
        private String bookDesc;

        @ApiModelProperty(value = "评分，预留字段")
        private Float score;

        @ApiModelProperty(value = "点击量")
        private Long visitCount;

        @ApiModelProperty(value = "总字数")
        private Integer wordCount;

        @ApiModelProperty(value = "评论数")
        private Integer commentCount;

        @ApiModelProperty(value = "昨日订阅数")
        private Integer yesterdayBuy;

        @ApiModelProperty(value = "最新目录名")
        private String lastIndexName;

        @ApiModelProperty(value = "最新目录更新时间")


        @JsonDeserialize(using = DateTimeDeserializer.class)
        @JsonSerialize(using = DateTimeSerializer.class)
        private LocalDateTime lastIndexUpdateTime;

        @ApiModelProperty(value = "作者ID")
        private Long authorId;
        @ApiModelProperty(value = "书籍状态，0：连载中，1：已完结")
        private Integer bookStatus;
        @ApiModelProperty(value = "最新目录ID")
        private Long lastIndexId;

        @ApiModelProperty(value = "是否收费，1：收费，0：免费")
        private Integer isVip;

        @ApiModelProperty(value = "状态，0：入库，1：上架")
        private Integer status;

        @ApiModelProperty(value = "更新时间")
        private LocalDateTime updateTime;

        @ApiModelProperty(value = "创建时间")
        private LocalDateTime createTime;
}
