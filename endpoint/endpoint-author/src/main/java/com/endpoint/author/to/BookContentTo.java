package com.endpoint.author.to;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * @author cr
 * @date 2022/11/30
 * @description
 */
@Data
public class BookContentTo implements Serializable {
    private static final long serialVersionUID = 1L;
    //主键
    private Long id;

    // 目录ID
    private Long indexId;

    //小说章节内容
    private String content;
}
