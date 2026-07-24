package com.endpoint.book.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.endpoint.book.entity.BookComment;
import com.endpoint.book.mapper.BookCommentMapper;
import com.endpoint.book.service.IBookCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.endpoint.book.vo.MemberCommentVo;
import com.endpoint.common.constant.ResponseStatus;
import com.endpoint.common.utils.ResultBean;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 * 小说评论表 服务实现类
 * </p>
 *
 * @author cr
 * @since 2022-10-21
 */
@Service
public class BookCommentServiceImpl extends ServiceImpl<BookCommentMapper, BookComment> implements IBookCommentService {


    @Override
    public ResultBean saveComment(MemberCommentVo memberCommentVo) {
        //校验用户是否已发表评论
        Long count = baseMapper.selectCount(new QueryWrapper<BookComment>()
                .eq("create_user_id", memberCommentVo.getUserId())
                .eq("book_id", memberCommentVo.getBookId()));
        if(count.intValue()>0){
            //用户已经发表评论
            return ResultBean.fail(ResponseStatus.HAS_COMMENTS);
        }
        //用户未发表该书评论 保存评论
        BookComment bookComment = new BookComment();
        bookComment.setBookId(memberCommentVo.getBookId());
        bookComment.setCreateUserId(memberCommentVo.getUserId());
        bookComment.setCreateTime(LocalDateTime.now());
        bookComment.setCommentContent(memberCommentVo.getCommentContent());
        baseMapper.insert(bookComment);
        return ResultBean.ok();
    }
}
