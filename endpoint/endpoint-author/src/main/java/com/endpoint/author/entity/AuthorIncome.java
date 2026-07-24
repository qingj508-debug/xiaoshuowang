package com.endpoint.author.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 稿费收入统计表
 * </p>
 *
 * @author cr
 * @since 2022-10-21
 */
@TableName("author_income")
public class AuthorIncome implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 作家ID
     */
    private Long authorId;

    /**
     * 作品ID
     */
    private Long bookId;

    /**
     * 收入月份
     */
    private LocalDate incomeMonth;

    /**
     * 税前收入（分）
     */
    private Long preTaxIncome;

    /**
     * 税后收入（分）
     */
    private Long afterTaxIncome;

    /**
     * 支付状态，0：待支付，1：已支付
     */
    private Boolean payStatus;

    /**
     * 稿费确认状态，0：待确认，1：已确认
     */
    private Boolean confirmStatus;

    /**
     * 详情
     */
    private String detail;

    private LocalDateTime createTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public LocalDate getIncomeMonth() {
        return incomeMonth;
    }

    public void setIncomeMonth(LocalDate incomeMonth) {
        this.incomeMonth = incomeMonth;
    }

    public Long getPreTaxIncome() {
        return preTaxIncome;
    }

    public void setPreTaxIncome(Long preTaxIncome) {
        this.preTaxIncome = preTaxIncome;
    }

    public Long getAfterTaxIncome() {
        return afterTaxIncome;
    }

    public void setAfterTaxIncome(Long afterTaxIncome) {
        this.afterTaxIncome = afterTaxIncome;
    }

    public Boolean getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Boolean payStatus) {
        this.payStatus = payStatus;
    }

    public Boolean getConfirmStatus() {
        return confirmStatus;
    }

    public void setConfirmStatus(Boolean confirmStatus) {
        this.confirmStatus = confirmStatus;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "AuthorIncome{" +
        "id=" + id +
        ", userId=" + userId +
        ", authorId=" + authorId +
        ", bookId=" + bookId +
        ", incomeMonth=" + incomeMonth +
        ", preTaxIncome=" + preTaxIncome +
        ", afterTaxIncome=" + afterTaxIncome +
        ", payStatus=" + payStatus +
        ", confirmStatus=" + confirmStatus +
        ", detail=" + detail +
        ", createTime=" + createTime +
        "}";
    }
}
