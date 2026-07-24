package com.endpoint.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.endpoint.common.utils.ResultBean;
import com.endpoint.member.entity.MemberBookshelf;
import com.endpoint.member.entity.MemberReadHistory;
import com.endpoint.member.mapper.MemberReadHistoryMapper;
import com.endpoint.member.service.IMemberBookshelfService;
import com.endpoint.member.service.IMemberReadHistoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.endpoint.member.vo.MemberBookshelfVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 * 用户阅读记录表 服务实现类
 * </p>
 *
 * @author cr
 * @since 2022-10-21
 */
@Service
public class MemberReadHistoryServiceImpl extends ServiceImpl<MemberReadHistoryMapper, MemberReadHistory> implements IMemberReadHistoryService {
    @Autowired
    private IMemberBookshelfService memberBookshelfService;

    @Override
    public ResultBean addReadHistory(MemberBookshelfVo vo) {
        //1. 删除该书原有阅读记录
        baseMapper.delete(new QueryWrapper<MemberReadHistory>().eq("member_id",vo.getMemberId()).eq("book_id",vo.getBookId()));

        //2.添加新的阅读记录
        MemberReadHistory memberReadHistory = new MemberReadHistory();
        memberReadHistory.setMemberId(vo.getMemberId());
        memberReadHistory.setBookId(vo.getBookId());
        memberReadHistory.setPreIndexId(vo.getPreIndexId());
        memberReadHistory.setCreateTime(LocalDateTime.now());
        memberReadHistory.setUpdateTime(LocalDateTime.now());
        baseMapper.insert(memberReadHistory);
        //3.更新书架 记录
        //3.1查询书架是否存在该书
        MemberBookshelf memberBookShelf = memberBookshelfService.getOne(new QueryWrapper<MemberBookshelf>().eq("member_id", vo.getMemberId()).eq("book_id", vo.getBookId()));
        if(memberBookShelf!=null){
            memberBookShelf.setPreIndexId(vo.getPreIndexId());
            memberBookShelf.setUpdateTime(LocalDateTime.now());
            //3.2 更新书架
            memberBookshelfService.updateById(memberBookShelf);
        }

        return ResultBean.ok("本章节加入阅读记录成功");
    }
}
