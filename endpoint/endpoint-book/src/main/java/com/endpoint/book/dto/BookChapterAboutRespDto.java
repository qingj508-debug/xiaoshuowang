package com.endpoint.book.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author cr
 * @date 2022/11/24
 * @description  章节相关信息对象
 */
@Data
public class BookChapterAboutRespDto {

    //章节ID 主键
    private Long id;
    //小说ID
    private Long bookId;
    //目录号
    private Integer indexNum;
    //目录名
    private String indexName;
    //章节字数
    private Integer wordCount;
    //是否收费，1：收费，0：免费
    private Integer isVip;
    //章节费用（屋币）
    private Integer bookPrice;
    //更新时间
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private LocalDateTime updateTime;

    //章节总数
    private Long chapterTotal;
    //内容概要（30字）
    private String contentSummary;
}
