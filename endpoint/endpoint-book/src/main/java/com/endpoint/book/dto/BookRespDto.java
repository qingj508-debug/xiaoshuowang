package com.endpoint.book.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author cr
 * @date 2022/11/23
 * @description
 */
@Data
public class BookRespDto {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 作品方向，0：男频，1：女频'
     */
    private Integer workDirection;

    /**
     * 分类ID
     */
    private Integer catId;

    /**
     * 分类名
     */
    private String catName;

    /**
     * 小说封面
     */
    private String picUrl;

    /**
     * 小说名
     */
    private String bookName;

    /**
     * 作者id
     */
    private Long authorId;

    /**
     * 作者名
     */
    private String authorName;

    /**
     * 书籍描述
     */
    private String bookDesc;

    /**
     * 评分，预留字段
     */
    private Float score;

    /**
     * 书籍状态，0：连载中，1：已完结
     */
    private Integer bookStatus;

    /**
     * 点击量
     */
    private Long visitCount;

    /**
     * 总字数
     */
    private Integer wordCount;

    /**
     * 评论数
     */
    private Integer commentCount;

    /**
     * 昨日订阅数
     */
    private Integer yesterdayBuy;

    /**
     * 最新目录ID
     */
    private Long lastIndexId;

    /**
     * 最新目录名
     */
    private String lastIndexName;

    /**
     * 最新目录更新时间
     */

    private LocalDateTime lastIndexUpdateTime;

    /**
     * 是否收费，1：收费，0：免费
     */
    private Integer isVip;

    /**
     * 状态，0：入库，1：上架
     */
    private Integer status;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    //第一章ID
    private Long firstChapterId;
}
