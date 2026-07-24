package com.endpoint.book.service;

import com.endpoint.book.entity.BookComment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.endpoint.book.vo.MemberCommentVo;
import com.endpoint.common.utils.ResultBean;

/**
 * <p>
 * 小说评论表 服务类
 * </p>
 *
 * @author cr
 * @since 2022-10-21
 */
public interface IBookCommentService extends IService<BookComment> {

    ResultBean saveComment(MemberCommentVo memberCommentVo);
}
