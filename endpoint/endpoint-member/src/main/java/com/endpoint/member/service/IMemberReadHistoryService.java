package com.endpoint.member.service;

import com.endpoint.common.utils.ResultBean;
import com.endpoint.member.entity.MemberReadHistory;
import com.baomidou.mybatisplus.extension.service.IService;
import com.endpoint.member.vo.MemberBookshelfVo;

/**
 * <p>
 * 用户阅读记录表 服务类
 * </p>
 *
 * @author cr
 * @since 2022-10-21
 */
public interface IMemberReadHistoryService extends IService<MemberReadHistory> {

    ResultBean addReadHistory(MemberBookshelfVo vo);
}
