package com.endpoint.member.service;

import com.endpoint.common.utils.ResultBean;
import com.endpoint.member.entity.MemberBuyRecord;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户消费记录表 服务类
 * </p>
 *
 * @author cr
 * @since 2022-10-21
 */
public interface IMemberBuyRecordService extends IService<MemberBuyRecord> {
    ResultBean buyBookIndex(Long bookIndexId, Long userId);
}
