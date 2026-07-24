package com.endpoint.member.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户书架表
 * </p>
 *
 * @author cr
 * @since 2022-10-21
 */
@Data
public class MemberBookshelfDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    //小说ID
    private Long bookId;
    //分类名
    private String catName;
    //分类ID
    private Integer catId;
    //小说名
    private String bookName;
    //最新目录ID
    private Long lastIndexId;
    //最新目录名
    private String lastIndexName;
    //最新目录更新时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastIndexUpdateTime;
    //上一次阅读的章节内容表ID
    private Long preIndexId;
}
