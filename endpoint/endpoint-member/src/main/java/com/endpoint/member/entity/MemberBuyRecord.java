package com.endpoint.member.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户消费记录表
 * </p>
 *
 * @author cr
 * @since 2022-10-21
 */
@TableName("member_buy_record")
public class MemberBuyRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 会员ID
     */
    private Long memberId;

    /**
     * 购买的小说ID
     */
    private Long bookId;

    /**
     * 购买的小说名
     */
    private String bookName;

    /**
     * 购买的章节ID
     */
    private Long bookIndexId;

    /**
     * 购买的章节名
     */
    private String bookIndexName;

    /**
     * 购买使用的终点币数量
     */
    private Integer buyAmount;

    /**
     * 购买时间
     */
    private LocalDateTime createTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Long getBookIndexId() {
        return bookIndexId;
    }

    public void setBookIndexId(Long bookIndexId) {
        this.bookIndexId = bookIndexId;
    }

    public String getBookIndexName() {
        return bookIndexName;
    }

    public void setBookIndexName(String bookIndexName) {
        this.bookIndexName = bookIndexName;
    }

    public Integer getBuyAmount() {
        return buyAmount;
    }

    public void setBuyAmount(Integer buyAmount) {
        this.buyAmount = buyAmount;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "MemberBuyRecord{" +
        "id=" + id +
        ", memberId=" + memberId +
        ", bookId=" + bookId +
        ", bookName=" + bookName +
        ", bookIndexId=" + bookIndexId +
        ", bookIndexName=" + bookIndexName +
        ", buyAmount=" + buyAmount +
        ", createTime=" + createTime +
        "}";
    }
}
