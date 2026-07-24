package com.endpoint.book.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 小说弹幕表
 * </p>
 *
 * @author cr
 * @since 2022-10-21
 */
@TableName("book_screen_bullet")
public class BookScreenBullet implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 小说内容ID
     */
    private Long contentId;

    /**
     * 小说弹幕内容
     */
    private String screenBullet;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getContentId() {
        return contentId;
    }

    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }

    public String getScreenBullet() {
        return screenBullet;
    }

    public void setScreenBullet(String screenBullet) {
        this.screenBullet = screenBullet;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "BookScreenBullet{" +
        "id=" + id +
        ", contentId=" + contentId +
        ", screenBullet=" + screenBullet +
        ", createTime=" + createTime +
        "}";
    }
}
