<template>
<Top />
<Navbar />
<div class="main box_center cf">
    <div class="userBox cf">
        <div class="my_l">

            <ul class="log_list">
                <li><router-link  class="link_1 " to="/membercenter" >个人中心</router-link ></li>
                <li><router-link class="link_4 on" to="/favorites">我的书架</router-link></li>
                <li><router-link class="link_6 " to="/comment">我的书评</router-link></li>
                <li><router-link class="link_7 " to="/feedbacklist">我的反馈</router-link></li>
                <li><router-link  class="link_8 " to="/setup">账号设置</router-link></li>
            </ul>

        </div>
        <div class="my_r">
            <div class="my_info cf">
                <img id="imgLogo" class="user_big_head" src="@/assets/images/man.png" />
                <div class="my_info_txt">
                    <p class="my_name" id="my_name">
                        </p>
                    <ul class="my_list">
                        <li class="my_gold"><i>账户余额：</i><em class="red" id="accountBalance">0</em>终点币<!--<em class="red">+</em><em class="red">0</em>代金券--><router-link to="/recharge" class="btn_link">立即充值</router-link></li>
                        <li class="my_baonian"></li>

                    </ul>
                </div>
            </div>
            <div class="my_bookshelf">
                <div class="title cf">
                    <h4 class="fl">
                        我的书架</h4>
                    <a href="/user/favorites.html" class="fr">全部收藏 &gt;</a>
                </div>
                <div class="updateTable">
                    <table cellpadding="0" cellspacing="0">
                        <thead>
                        <tr>
                            <th class="style">
                                类别
                            </th>
                            <th class="name">
                                书名
                            </th>
                            <th class="chapter">
                                最新章节
                            </th>
                            <th class="time">
                                更新时间
                            </th>
                            <th class="goread">
                                书签
                            </th>
                        </tr>
                        </thead>
                        <tbody id="bookShelfList">
                            <tr class="book_list" vals="291"> 
                                <td class="style bookclass">
                                 <a href="/book/bookclass.html?c=book.catId" >武侠仙侠</a>
                                </td>
                                <td class="name">
                                    <a href="/book/book.bookId.html">遮天</a> 
                                </td>
                                <td class="chapter"> 
                                    <a href='/book/"+book.bookId+"/"+book.lastIndexId+".html'>第二章 素问</a>
                                </td>  
                                <td class="time">2022/1/12 11:20:39 </td> 
                                <td class="goread"> 
                                    <a href='/book/"+book.bookId+"/"+book.preContentId+".html'>继续阅读</a>
                                </td>
                             </tr>

                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<Footer/>
</template>
<script setup>
import "@/assets/styles/user.css";
import { reactive, onMounted, ref } from "vue";
import Top from "@/components/common/TopView";
import Navbar from "@/components/common/NavbarView";
import Footer from "@/components/common/FooterView";
import { ElLoading } from "element-plus";
import { useRouter } from "vue-router";
    //查询用户信息
    
    //查询书架列表
    $.ajax({
        type: "get",
        url: "/user/listBookShelfByPage",
        data: {'limit':2},
        dataType: "json",
        success: function (data) {
            if (data.code == 200) {
                var bookShelfList = data.data.list;
                if (bookShelfList.length > 0) {
                    var bookShelfListHtml = "";
                    for (var i = 0; i < bookShelfList.length; i++) {
                        var book = bookShelfList[i];
                        bookShelfListHtml += (" <tr class=\"book_list\" vals=\"291\">\n" +
                            "                            <td class=\"style bookclass\">\n" +
                            "                                <a href=\"/book/bookclass.html?c="+book.catId+"\" >[" + book.catName + "]</a>\n" +
                            "                            </td>\n" +
                            "                            <td class=\"name\">\n" +
                            "                                <a href=\"/book/" + book.bookId + ".html\">\n" +
                            "                                    " + book.bookName + "</a>\n" +
                            "                            </td>\n" +
                            "                            <td class=\"chapter\" valsc=\"291|2037554|1\">\n" +
                            "<a href='/book/" + book.bookId + "/" + book.lastIndexId + ".html'>" + book.lastIndexName + "</a>" +
                            "                            </td>\n" +
                            "                            <td class=\"time\">\n" +
                            "                                " + book.lastIndexUpdateTime + "\n" +
                            "                            </td>\n" +
                            "                            <td class=\"goread\">\n" +
                            "<a href='/book/" + book.bookId + "/" + book.preContentId + ".html'>继续阅读</a>" +
                            "                            </td>\n" +
                            "                        </tr>");
                    }
                    $("#bookShelfList").html(bookShelfListHtml);
                }

            } else {
                layer.alert(data.msg);
            }

        },
        error: function () {
            layer.alert('网络异常');
        }
    })

</script>

