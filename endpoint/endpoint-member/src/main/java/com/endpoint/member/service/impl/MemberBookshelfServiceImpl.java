package com.endpoint.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.endpoint.common.constant.ResponseStatus;
import com.endpoint.common.utils.ResultBean;
import com.endpoint.member.dto.MemberBookshelfDto;
import com.endpoint.member.entity.MemberBookshelf;
import com.endpoint.member.feign.BookFeignService;
import com.endpoint.member.mapper.MemberBookshelfMapper;
import com.endpoint.member.service.IMemberBookshelfService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.endpoint.member.to.BookIndexTo;
import com.endpoint.member.to.BookTo;
import com.endpoint.member.vo.MemberBookshelfVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户书架表 服务实现类
 * </p>
 *
 * @author cr
 * @since 2022-10-21
 */
@Service
public class MemberBookshelfServiceImpl extends ServiceImpl<MemberBookshelfMapper, MemberBookshelf> implements IMemberBookshelfService {


    @Autowired
    private BookFeignService bookFeignService;

    @Override
    public ResultBean isAddToBookshelf(Long bookId, Long useId) {
        Long count = baseMapper.selectCount(new QueryWrapper<MemberBookshelf>().eq("member_id", useId).eq("book_id", bookId));
        if(count>0){
            //用户已经将小说加入书架
            return ResultBean.ok(true);
        }
        // 未加入书架 返回状态码 10010
        return ResultBean.ok(false);
    }

    @Override
    public ResultBean addToBookshelf(MemberBookshelfVo memberBookshelfVo) {
        //1.查询书架是否已经加入过该小说
        MemberBookshelf memberBookshelf1 = baseMapper.selectOne(new QueryWrapper<MemberBookshelf>()
                .eq("member_id", memberBookshelfVo.getMemberId())
                .eq("book_id", memberBookshelfVo.getBookId()));

        if(memberBookshelf1!=null){
            //用户已经将小说加入书架

            return ResultBean.ok("该小说已经加入书架");
        }
        //2.如果未加入书架
        MemberBookshelf memberBookshelf = new MemberBookshelf();
        memberBookshelf.setMemberId(memberBookshelfVo.getMemberId());
        memberBookshelf.setBookId(memberBookshelfVo.getBookId());
        if(memberBookshelfVo.getPreIndexId()==0){//章节id传过来为0 说明在详情页加入书架
            //1.查询该小说的第一章节id
            BookIndexTo bookIndex = bookFeignService.getfirstChapterByBookId(memberBookshelfVo.getBookId()).getData();
            if(bookIndex!=null){
                memberBookshelfVo.setPreIndexId(bookIndex.getId());
            }
        }
        memberBookshelf.setPreIndexId(memberBookshelfVo.getPreIndexId());
        memberBookshelf.setCreateTime(LocalDateTime.now());
        memberBookshelf.setUpdateTime(LocalDateTime.now());
        baseMapper.insert(memberBookshelf);
        return ResultBean.ok("加入书架后可从我的书架查看");
    }

    @Override
    public ResultBean<List<MemberBookshelfDto>> getBookShelfList(Long memberId) {
        //1.查询该用户书架所有记录
        List<MemberBookshelf> mbsList = baseMapper.selectList(new QueryWrapper<MemberBookshelf>().eq("member_id", memberId));

        //2.取出所有小说id
        List<Long> bookIds = mbsList.stream().map(MemberBookshelf::getBookId).collect(Collectors.toList());

        //3.远程调用查询所有 小说信息
        ResultBean<List<BookTo>> res = bookFeignService.listByIds(bookIds);
        if(res!=null&&res.getCode()==200){
            List<BookTo> bookList = res.getData();
            // 变为 key 为小说ID 值为 BookTo的map集合
            Map<Long, BookTo> map = bookList.stream().collect(Collectors.toMap(BookTo::getId, Function.identity()));
            //将数据封装变为集合
            List<MemberBookshelfDto> mbsdList = mbsList.stream().map(item -> {
                MemberBookshelfDto memberBookshelfDto = new MemberBookshelfDto();
                Long bookId = item.getBookId();
                BookTo bookTo = map.get(bookId);
                memberBookshelfDto.setId(item.getId());//设置书架记录主键
                memberBookshelfDto.setBookId(bookId);//书籍ID

                memberBookshelfDto.setBookName(bookTo.getBookName());
                memberBookshelfDto.setCatId(bookTo.getCatId());
                memberBookshelfDto.setCatName(bookTo.getCatName());
                memberBookshelfDto.setLastIndexId(bookTo.getLastIndexId());
                memberBookshelfDto.setLastIndexName(bookTo.getLastIndexName());
                memberBookshelfDto.setLastIndexUpdateTime(bookTo.getLastIndexUpdateTime());
                //设置 上一次查看章节Id
                memberBookshelfDto.setPreIndexId(item.getPreIndexId());
                return memberBookshelfDto;
            }).collect(Collectors.toList());
            return  ResultBean.ok(mbsdList);
        }

        return  ResultBean.ok(Collections.emptyList());
    }
}
