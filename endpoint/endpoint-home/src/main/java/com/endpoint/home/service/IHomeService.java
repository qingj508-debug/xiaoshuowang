package com.endpoint.home.service;

import com.endpoint.common.utils.ResultBean;
import com.endpoint.home.to.BookTo;

import java.util.List;

/**
 * @author cr
 * @date 2022/11/3
 * @description
 */
public interface IHomeService {

    public List<BookTo> updateRankList();

}
