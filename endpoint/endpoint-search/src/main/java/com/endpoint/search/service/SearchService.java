package com.endpoint.search.service;

import com.endpoint.common.utils.ResultBean;
import com.endpoint.search.to.BookEsModel;
import com.endpoint.search.vo.SearchParam;
import com.endpoint.search.vo.SearchResult;

/**
 * @author cr
 * @date 2022/11/9
 * @description
 */
public interface SearchService {
    Boolean  booksImportEs();
    SearchResult searchBookByPage(SearchParam searchParam, int pageNum, int pageSize);

    void importToEs(BookEsModel bookEsModel);
}
