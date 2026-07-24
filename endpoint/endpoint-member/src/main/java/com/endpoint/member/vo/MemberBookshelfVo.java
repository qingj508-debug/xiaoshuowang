package com.endpoint.member.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
public class MemberBookshelfVo implements Serializable {

    //会员ID
    private Long memberId;
    //小说ID
    @NotNull
    private Long bookId;
    //preIndexId
    private Long preIndexId; //上次阅读查看章节id
}
