package com.endpoint.author.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class BookAddVo {

    /**
     * 作品方向;0-男频 1-女频
     */

    @NotNull
    private Integer workDirection;

    /**
     * 类别ID
     */
    @NotNull
    private Integer categoryId;

    /**
     * 类别名
     */
    @NotBlank
    private String categoryName;

    /**
     * 小说封面地址
     */
    @NotBlank
    private String picUrl;

    /**
     * 小说名
     */
    @NotBlank
    private String bookName;

    /**
     * 书籍描述
     */
    @NotBlank
    private String bookDesc;

    /**
     * 是否收费;1-收费 0-免费
     */
    @NotNull
    private Integer isVip;
}