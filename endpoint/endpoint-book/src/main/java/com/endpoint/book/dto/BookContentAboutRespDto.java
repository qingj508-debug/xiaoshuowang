package com.endpoint.book.dto;

import lombok.Data;

/**
 * @author cr
 * @date 2022/11/25
 * @description  章节内容信息响应对象
 */
@Data
public class BookContentAboutRespDto {


    //1.小说信息
    private BookRespDto bookInfo;
    //2.章节信息
    private BookChapterAboutRespDto chapterInfo;
    //3.章节内容
    private String bookContent;
    //4.是否需要购买 true 已购买 false 需要购买
    private Boolean needBuy;

}
