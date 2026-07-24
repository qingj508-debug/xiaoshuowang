package com.endpoint.search.service.impl;

import com.endpoint.common.utils.ResultBean;
import com.endpoint.search.constant.EsConstant;
import com.endpoint.search.feign.BookFeignService;
import com.endpoint.search.service.SearchService;
import com.endpoint.search.to.BookEsModel;
import com.endpoint.search.vo.SearchParam;
import com.endpoint.search.vo.SearchResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.util.internal.StringUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author cr
 * @date 2022/11/9
 * @description
 */
@Slf4j
@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private RestHighLevelClient highLevelClient;

    @Autowired
    private BookFeignService bookFeignService;

    @SneakyThrows
    @Override
    public Boolean booksImportEs() {
        ResultBean<List<BookEsModel>> result = bookFeignService.getBookList();
        Boolean flag = false;
        if(result!=null&&result.getCode()==200){
            //将数据批量保存到es中
            BulkRequest bulkRequest = new BulkRequest();
            for (BookEsModel bookEsModel : result.getData()) {
                //构造保存请求
                IndexRequest indexRequest = new IndexRequest(EsConstant.BOOK_INDEX);
                //设置索引的id
                indexRequest.id(bookEsModel.getId().toString());
                //设置具体要保存的内容
                String json = new ObjectMapper().writeValueAsString(bookEsModel);
                indexRequest.source(json, XContentType.JSON);
                //添加保存请求
                bulkRequest.add(indexRequest);
            }
            BulkResponse bulk = highLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
            flag = bulk.hasFailures();
        }
        return flag;
    }

    @Override
    public SearchResult searchBookByPage(SearchParam searchParam, int pageNum, int pageSize) {

        //构建DSL语句
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //一.模糊匹配,过滤(keyword 作品方向 作品分类 作品是否完结  作品字数区间 时间)
        //1.构建bool查询
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        //1.1 多字段查询关键字 "authorName","bookName","bookDesc"
        if(!StringUtils.isEmpty(searchParam.getKeyword())){
            boolQueryBuilder.must(QueryBuilders.multiMatchQuery(searchParam.getKeyword(),"authorName","bookName","bookDesc"));
        }
        //1.2 filter 过滤 作品方向
        if(searchParam.getWorkDirection() != null){
            boolQueryBuilder.filter(QueryBuilders.termQuery("workDirection", searchParam.getWorkDirection()));
        }

        //1.3 filter 过滤 作品分类
        if (searchParam.getCatId() != null) {
            boolQueryBuilder.filter(QueryBuilders.termQuery("catId", searchParam.getCatId()));
        }
        //1.4 filter 过滤 作品是否完结
        if (searchParam.getBookStatus() != null) {
            boolQueryBuilder.filter(QueryBuilders.termQuery("bookStatus", searchParam.getBookStatus()));
        }

        //1.5 filter 过滤 作品字数区间
        //设置最小值
        if (searchParam.getWordCountMin() == null) {
            searchParam.setWordCountMin(0);
        }
        // 为空设置字数最大值
        if (searchParam.getWordCountMax() == null) {
            searchParam.setWordCountMax(Integer.MAX_VALUE);
        }
        boolQueryBuilder.filter(QueryBuilders.rangeQuery("wordCount").gt(searchParam.getWordCountMin()).lte(searchParam.getWordCountMax()));
        //1.6 filter 过滤 大于传递过来的时间
        if (searchParam.getUpdateTimeMin() != null) {
            boolQueryBuilder.filter(QueryBuilders.rangeQuery("lastIndexUpdateTime").gte(searchParam.getUpdateTimeMin()).lte(new Date()).timeZone("GMT+8"));
        }
        // //封装了上述查询
        searchSourceBuilder.query(boolQueryBuilder);

        // 二、排序 分页 高亮

        //2.1 设置排序
        if ( !StringUtils.isEmpty(searchParam.getSort())) {
            String[] s = searchParam.getSort().split("_");
            SortOrder sortOrder=s[1].equalsIgnoreCase("asc")?SortOrder.ASC:SortOrder.DESC;
            searchSourceBuilder.sort(s[0], SortOrder.DESC);
        }

        // 设置分页
        searchSourceBuilder.from((pageNum - 1) * pageSize);
        searchSourceBuilder.size(pageSize);

        // 高亮字段
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("authorName");
        highlightBuilder.field("bookName");
        highlightBuilder.field("lastIndexName");
        highlightBuilder.preTags("<span style='color:red'>").postTags("</span>");
        highlightBuilder.requireFieldMatch(false); //如果要多个字段高亮,这项要为false
        //高亮如文字内容等有很多字的字段,必须配置,不然会导致高亮不全,文章内容缺失等
        highlightBuilder.fragmentSize(20000);
        searchSourceBuilder.highlighter(highlightBuilder);

        SearchRequest searchRequest = new SearchRequest(new String[]{EsConstant.BOOK_INDEX}, searchSourceBuilder);

        System.out.println(searchRequest.source().toString());

        //创建响应对象
        SearchResult searchResult = new SearchResult();
        try {
            //1.执行检索
            SearchResponse searchResponse = highLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            //2.分析相应数据
            SearchHits hits = searchResponse.getHits();
            //2.1 封装bookEsModels
            List<BookEsModel> bookEsModels = new ArrayList<>();

            for (SearchHit hit : hits) {
                String json = hit.getSourceAsString();
                BookEsModel bookEsModel = new ObjectMapper().readValue(json, BookEsModel.class);
                if(!StringUtils.isEmpty(searchParam.getKeyword())){
                    Map<String, HighlightField> highlightFields = hit.getHighlightFields();
                    HighlightField bookName = highlightFields.get("bookName");
                    HighlightField authorName = highlightFields.get("authorName");
                    HighlightField lastIndexName = highlightFields.get("lastIndexName");
                    if(bookName!=null&& !StringUtils.isEmpty(bookName.getName())){
                        bookEsModel.setBookName(bookName.fragments()[0].toString());
                    }
                    if(authorName!=null&& !StringUtils.isEmpty(authorName.getName())){
                        bookEsModel.setAuthorName(authorName.fragments()[0].toString());
                    }
                    if(lastIndexName!=null&& !StringUtils.isEmpty(lastIndexName.getName())){
                        bookEsModel.setLastIndexName(lastIndexName.fragments()[0].toString());
                    }
                }
                bookEsModels.add(bookEsModel);
            }
            searchResult.setList(bookEsModels);
            //3 当前页面
            searchResult.setPageNum(pageNum);
            //4. 命中总条数
            long total =  hits.getTotalHits().value;
            searchResult.setTotal(total);
            searchResult.setPageSize(pageSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return searchResult;
    }
    @SneakyThrows
    @Override
    public void importToEs(BookEsModel bookEsModel) {
        //构造保存请求
        IndexRequest indexRequest = new IndexRequest(EsConstant.BOOK_INDEX);
        //设置索引的id
        indexRequest.id(bookEsModel.getId().toString());
        //设置具体要保存的内容
        String json = new ObjectMapper().writeValueAsString(bookEsModel);
        indexRequest.source(json, XContentType.JSON);
        IndexResponse index = highLevelClient.index(indexRequest, RequestOptions.DEFAULT);

        log.debug(index.getResult().toString());
    }
}
