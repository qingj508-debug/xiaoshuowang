package com.endpoint.book.to;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author cr
 * @date 2022/12/8
 * @description
 */
@Data
public class MemberBuyRecordTo {
        private static final long serialVersionUID = 1L;
        //主键
        private Long id;
        //会员ID
        private Long memberId;
        //购买的小说ID
        private Long bookId;
        //购买的小说名
        private String bookName;
        //购买的章节ID
        private Long bookIndexId;
        //购买的章节名
        private String bookIndexName;
        //购买使用的终点币数量
        private Integer buyAmount;
        //购买时间
        private LocalDateTime createTime;
}
