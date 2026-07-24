package com.endpoint.member.service;

import com.endpoint.common.utils.ResultBean;
import com.endpoint.member.dto.MemberBookshelfDto;
import com.endpoint.member.entity.MemberBookshelf;
import com.baomidou.mybatisplus.extension.service.IService;
import com.endpoint.member.vo.MemberBookshelfVo;

import java.util.List;

/**
 * <p>
 * 用户书架表 服务类
 * </p>
 *
 * @author cr
 * @since 2022-10-21
 */
public interface IMemberBookshelfService extends IService<MemberBookshelf> {

    ResultBean isAddToBookshelf(Long bookId, Long useId);

    ResultBean addToBookshelf(MemberBookshelfVo memberBookshelfVo);

    ResultBean<List<MemberBookshelfDto>> getBookShelfList(Long memberId);
}
