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
                    <h2 class="fl">我的书评</h2>
                    <div class="fr"></div>
                </div>
                <div class="bookComment">
                    <div class="no_contet no_comment" style="display:none">
                        您还没有发表过评论！
                    </div>
                    <div class="commentBar" id="commentBar">
                        <div class="comment_list cf">
                            <div class="user_heads fl" vals="389"> 
                                <img src="@/assets/images/man.png" class="user_head" >
                                <span class="user_level1" style="display: none;">见习</span></div>
                                <ul class="pl_bar fr">
                                    <li class="name">1983232312312</li>
                                    <li class="dec">神作啊</li>
                                    <li class="other cf"> 
                                        <span class="time fl">2022-12-12 12:12:12</span>
                                        <span class="fr">
                                            <a href="javascript:void(0);" @click="BookDetail.AddAgreeTotal(77,this);" class="zan" >赞<i class="num">(0)</i></a>
                                        </span>
                                    </li>
                                </ul>
                            </div>

                    </div>
                </div>
                <div class="pageBox cf" id="commentPage">
                </div>
            </div>
        </div>
    </div>
</div>
</template>
<script setup>
import "@/assets/styles/user.css";
import { reactive, onMounted, ref } from "vue";
import Top from "@/components/common/TopView";
import Navbar from "@/components/common/NavbarView";
import Footer from "@/components/common/FooterView";
import { ElLoading } from "element-plus";
import { useRouter } from "vue-router";

 
const state = reactive({
});
const router = useRouter();
const topBooks = ref([]);


</script>
