package com.endpoint.member.service.impl;

import com.endpoint.common.constant.ResponseStatus;
import com.endpoint.common.utils.ResultBean;
import com.endpoint.member.entity.Member;
import com.endpoint.member.entity.MemberBuyRecord;
import com.endpoint.member.feign.BookFeignService;
import com.endpoint.member.mapper.MemberBuyRecordMapper;
import com.endpoint.member.service.IMemberBuyRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.endpoint.member.service.IMemberService;
import com.endpoint.member.to.BookIndexTo;
import com.endpoint.member.to.BookTo;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * <p>
 * 用户消费记录表 服务实现类
 * </p>
 *
 * @author cr
 * @since 2022-10-21
 */
@Slf4j
@Service
public class MemberBuyRecordServiceImpl extends ServiceImpl<MemberBuyRecordMapper, MemberBuyRecord> implements IMemberBuyRecordService {


    @Autowired
    private BookFeignService bookFeignService;
    @Autowired
    private IMemberService memberService;

    @Transactional
    @Override
    public ResultBean buyBookIndex(Long bookIndexId, Long userId) {
        //1.查询章节信息
        ResultBean<BookIndexTo> bookIndexResult = bookFeignService.getBookIndexById(bookIndexId);
        if(bookIndexResult!=null&&bookIndexResult.getCode()==200){
            BookIndexTo bookIndex = bookIndexResult.getData();


            //2.查询书籍信息
            BookTo book = bookFeignService.getBookById(bookIndex.getBookId()).getData();
            //3.判断余额是否足够支付章节解锁费用  查询用户余额
            Member member = memberService.getById(userId);
            if(member.getAccountBalance()<bookIndex.getBookPrice()){
                //如果用户余额小于该章节扣减终点币 说明解锁该章节余额不足 提示及时充值
               return ResultBean.fail(ResponseStatus.USER_NO_BALANCE);
            }
            //4. 扣减账户
            member.setAccountBalance(member.getAccountBalance()-bookIndex.getBookPrice());
            memberService.updateById(member);
            //5. 生成购买记录
            MemberBuyRecord memberBuyRecord = new MemberBuyRecord();
            memberBuyRecord.setMemberId(userId);//会员id
            memberBuyRecord.setBookIndexId(bookIndexId);//章节id
            memberBuyRecord.setBookIndexName(bookIndex.getIndexName());//章节名
            memberBuyRecord.setBookId(bookIndex.getBookId());//小说id
            memberBuyRecord.setBookName(book.getBookName());//小说名
            memberBuyRecord.setBuyAmount(bookIndex.getBookPrice());
            memberBuyRecord.setCreateTime(LocalDateTime.now());
            baseMapper.insert(memberBuyRecord);

            return ResultBean.ok();
        }else{
            log.error("vip章节解锁失败,找不到该章节");
            //找不到该章节 返回未知异常
            return ResultBean.fail(ResponseStatus.ERROR);
        }
    }
}
