package com.endpoint.book.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.endpoint.common.utils.UsernameSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author cr
 * @date 2022/11/24
 * @description  评论信息响应对象
 */
@Data
public class BookCommentRespDto {
    //评论总数
    private Long commentTotal;
    //评论列表
    private List<CommentInfo> comments;
    @Data
    public static class CommentInfo {
        //评论ID
        private Long id;
        //评价内容
        private String commentContent;
        //评价人ID
        private Long createUserId;
        //评价时间
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime createTime;

        //用户评论头像
        private String commentUserPhoto;

        @JsonSerialize(using = UsernameSerializer.class)
        private String commentUser;
    }
}
