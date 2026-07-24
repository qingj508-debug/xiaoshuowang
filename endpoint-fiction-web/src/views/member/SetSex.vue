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
                            <li><i class="tit">我的性别</i>
                                <label><input type="radio" name="sex" value="0"  />&nbsp;男生</label>
                                <label><input class="ml20" type="radio" name="sex" value="1" checked />&nbsp;女生</label>
                            </li>
                            <li class="mt20"><i class="tit">&nbsp;</i><input type="button" onclick="updateSex()" name="btn" value="修改" id="btn" class="s_btn btn_red" /></li>
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

</script>
