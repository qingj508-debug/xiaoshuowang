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
            <div class="my_bookshelf">
                <div class="title cf">
                    <h2 class="fl">我的反馈</h2>
                    <div class="fr"><a href="/user/feedback.html" class="btn_red">写反馈</a></div>
                </div>

                <dl class="feedback_list cf">
                    <dd id="feedbackList">
                        <div class=" comment_list clear" >
                            <ul>
                                <li class="li_1"><span class="user_name fl"></span><span class="time fl" >2022-12-12 12:12:12</span></li>
                                <li class="li_2">我充值怎么充不上?</li>
                            </ul>
                        </div>



                    </dd>
                </dl>
                <div class="cf">
                    <div class="help_bar fl hidden">
                        <a href=""><span class="txt">读者常见问题(18)</span></a>
                    </div>
                    <div class="pageBox cf" id="feedbackPage">
                    </div>
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

    function search(curr, limit) {

        $.ajax({
            type: "get",
            url: "/user/listUserFeedBackByPage",
            data: {'curr':curr,'limit':limit},
            dataType: "json",
            success: function (data) {
                if (data.code == 200) {
                    var feedbackList = data.data.list;
                    if (feedbackList.length > 0) {
                        var feedbackListHtml = "";
                        for (var i = 0; i < feedbackList.length; i++) {
                            var feedback = feedbackList[i];
                            feedbackListHtml += (" <div class=\"comment_list clear\" ><ul>\n" +
                                "                                <li class=\"li_1\"><span class=\"user_name fl\"></span><span class=\"time fl\">"+feedback.createTime+"</span></li>\n" +
                                "                                <li class=\"li_2\">"+feedback.content+"</li>\n" +
                                "                            </ul></div>");
                        }
                        $("#feedbackList").html(feedbackListHtml);

                        layui.use('laypage', function () {
                            var laypage = layui.laypage;

                            //执行一个laypage实例
                            laypage.render({
                                elem: 'feedbackPage' //注意，这里的 test1 是 ID，不用加 # 号
                                , count: data.data.total //数据总数，从服务端得到,
                                , curr: data.data.pageNum
                                , limit: data.data.pageSize
                                , jump: function (obj, first) {


                                    //obj包含了当前分页的所有参数，比如：
                                    console.log(obj.curr); //得到当前页，以便向服务端请求对应页的数据。
                                    console.log(obj.limit); //得到每页显示的条数


                                    //首次不执行
                                    if (!first) {
                                        search(obj.curr, obj.limit);
                                    } else {

                                    }
                                }
                            });
                        });

                    }


                } else if (data.code == 1001) {
                    //未登录
                    location.href = '/user/login.html?originUrl=' + decodeURIComponent(location.href);

                }else {
                    layer.alert(data.msg);
                }

            },
            error: function () {
                layer.alert('网络异常');
            }
        })

    }
</script>

