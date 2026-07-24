package com.endpoint.book.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author cr
 * @date 2022/11/25
 * @description
 */
@Data
public class MemberCommentVo {

    private Long userId;

    @NotNull(message="小说ID不能为空！")
    private Long bookId;

    @NotBlank(message="评论不能为空！")
    @Length(min = 10,max = 512)
    private String commentContent;
}
