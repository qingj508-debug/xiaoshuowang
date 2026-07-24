package com.endpoint.member.to;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author cr
 * @date 2022/11/30
 * @description
 */
@Data
public class BookIndexTo implements Serializable {

    private static final long serialVersionUID = 1L;

    //主键
    private Long id;

    //小说ID
    private Long bookId;

    //章节id
    private Integer indexNum;

    //目录名
    private String indexName;

    //字数
    private Integer wordCount;

    //是否收费，1：收费，0：免费
    private Integer isVip;

    //章节费用（屋币）
    private Integer bookPrice;
    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
