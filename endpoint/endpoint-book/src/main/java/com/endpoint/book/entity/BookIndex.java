package com.endpoint.book.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 小说目录表
 * </p>
 *
 * @author cr
 * @since 2022-10-21
 */
@TableName("book_index")
public class BookIndex implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 小说ID
     */
    private Long bookId;

    /**
     * 目录号
     */
    private Integer indexNum;

    /**
     * 目录名
     */
    private String indexName;

    /**
     * 字数
     */
    private Integer wordCount;

    /**
     * 是否收费，1：收费，0：免费
     */
    private Integer isVip;

    /**
     * 章节费用（屋币）
     */
    private Integer bookPrice;

    /**
     * 存储方式
     */
    private String storageType;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Integer getIndexNum() {
        return indexNum;
    }

    public void setIndexNum(Integer indexNum) {
        this.indexNum = indexNum;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public Integer getWordCount() {
        return wordCount;
    }

    public void setWordCount(Integer wordCount) {
        this.wordCount = wordCount;
    }

    public Integer getIsVip() {
        return isVip;
    }

    public void setIsVip(Integer isVip) {
        this.isVip = isVip;
    }

    public Integer getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(Integer bookPrice) {
        this.bookPrice = bookPrice;
    }

    public String getStorageType() {
        return storageType;
    }

    public void setStorageType(String storageType) {
        this.storageType = storageType;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "BookIndex{" +
        "id=" + id +
        ", bookId=" + bookId +
        ", indexNum=" + indexNum +
        ", indexName=" + indexName +
        ", wordCount=" + wordCount +
        ", isVip=" + isVip +
        ", bookPrice=" + bookPrice +
        ", storageType=" + storageType +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        "}";
    }
}
