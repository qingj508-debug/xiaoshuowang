package com.endpoint.author.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 作家邀请码表
 * </p>
 *
 * @author cr
 * @since 2022-10-21
 */
@TableName("author_code")
public class AuthorCode implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 邀请码
     */
    private String inviteCode;

    /**
     * 有效时间
     */
    private LocalDateTime validityTime;

    /**
     * 是否使用过，0：未使用，1:使用过
     */
    private Boolean isUse;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 创建人ID
     */
    private Long createUserId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public LocalDateTime getValidityTime() {
        return validityTime;
    }

    public void setValidityTime(LocalDateTime validityTime) {
        this.validityTime = validityTime;
    }

    public Boolean getIsUse() {
        return isUse;
    }

    public void setIsUse(Boolean isUse) {
        this.isUse = isUse;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    @Override
    public String toString() {
        return "AuthorCode{" +
        "id=" + id +
        ", inviteCode=" + inviteCode +
        ", validityTime=" + validityTime +
        ", isUse=" + isUse +
        ", createTime=" + createTime +
        ", createUserId=" + createUserId +
        "}";
    }
}
