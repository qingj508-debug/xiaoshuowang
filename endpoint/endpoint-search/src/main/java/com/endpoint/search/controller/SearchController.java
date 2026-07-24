package com.endpoint.search.controller;

import com.endpoint.common.utils.ResultBean;
import com.endpoint.search.service.SearchService;
import com.endpoint.search.to.BookEsModel;
import com.endpoint.search.vo.SearchParam;
import com.endpoint.search.vo.SearchResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author cr
 * @date 2022/11/8
 * @description
 */
@Api(tags = "书籍小说检索模块")
@RestController
@RequestMapping("search")
public class SearchController {
    @Autowired
    private SearchService searchService;

    @GetMapping("/booksImportEs")
    public ResultBean booksImportEs(){
        Boolean flag = searchService.booksImportEs();
        if(flag){
            return ResultBean.ok();
        }else{
            return ResultBean.error();
        }
    }
    @ApiOperation(value = "分页搜索小说列表接口")
    @GetMapping("/searchByPage")
    public ResultBean<SearchResult> searchByPage(SearchParam searchParam,
                                                 @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                                 @RequestParam(value = "pageSize", defaultValue = "10") int pageSize){
        SearchResult searchResult = searchService.searchBookByPage(searchParam,pageNum,pageSize);
        return ResultBean.ok(searchResult);
    }
}
