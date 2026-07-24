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
                            <li><i class="tit">我的密码</i><input name="oldPass" type="password" id="txtOldPass" class="s_input" placeholder="请输入原密码" /></li>
                            <li><i class="tit">&nbsp;</i><input name="txtNewPass1" type="password" id="txtNewPass1" class="s_input" placeholder="请输入新密码" /></li>
                            <li><i class="tit">&nbsp;</i><input name="txtNewPass2" type="password" id="txtNewPass2" class="s_input" placeholder="请确认新密码" /></li>
                            <li><i class="tit">&nbsp;</i><input type="submit" name="btnExchangePassword" value="修改" id="btnExchangePassword" onclick="updatePassword()" class="s_btn btn_red" /></li>
                            <li><i class="tit">&nbsp;</i><span id="LabErr"></span></li>
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

//1.判断旧密码、新密码、确认密码不能为空   两次输入的新密码不匹配

</script>


