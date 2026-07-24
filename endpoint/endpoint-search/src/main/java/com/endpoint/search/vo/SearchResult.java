package com.endpoint.search.vo;

import com.endpoint.search.to.BookEsModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.List;


@Api(tags = "小说检索结果")
@Data
@ToString
public class SearchResult {

    @ApiModelProperty(value = "页码")
    private Integer pageNum;
    @ApiModelProperty(value = "每页大小")
    private Integer pageSize;
    @ApiModelProperty(value = "总记录数")
    private Long total;
    @ApiModelProperty(value = "分页数据集合")
    private List<BookEsModel> list;
}
