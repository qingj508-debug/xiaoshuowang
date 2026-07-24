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
                <div class="my_info_txt">
                    <ul class="mytab_list">
                        <li ><i class="tit">我的头像</i>
                            <a style="position: relative" >
                                <img id="imgLogo" class="user_img" alt="我的头像" src="@/assets/images/man.png" />
                                <input class="opacity" onchange="picChange()" type="file" id="file0" name="file" title="点击上传图片" style="z-index: 100;cursor: pointer;left: 0px; top: -25px; width: 60px; height: 80px; opacity: 0; position: absolute;  "/>

                            </a>

                        </li>
                        <li><i class="tit">我的昵称</i><a href="/user/set_name.html" id="my_name">张三 <router-link class="ml10" to="/setname" >[修改]</router-link></a></li>
                        <li style="display:none"><i class="tit">电子邮箱</i><a href="javascript:void(0);"></a></li>
                        <li><i class="tit">我的性别</i><a href="/user/set_sex.html" id="my_sex">男 <router-link class="ml10" to="/setsex">[修改]</router-link></a></li>
                        <li><i class="tit">我的密码</i><router-link  to="/setpassword">修改密码</router-link></li>
                        <li><i class="tit">当前状态</i><a href="javascript:logout()">退出登录</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>

<Footer />

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
