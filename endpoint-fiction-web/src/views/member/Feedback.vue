<template>
    <Top />
    <Navbar />
<div class="main box_center cf">
    <div class="channelWrap channelFeedback cf">

        <div class="userBox cf">
            <div class="viewhistoryBox">
                <div class="my_bookshelf">
                    <div class="title cf">
                        <h2 class="fl ml10">
                            <a href="javascript:void(0);">反馈留言</a></h2>
                    </div>
                    <div class="feedbackBox">

                        <form method="post" action="/" id="Form1"
                              onsubmit="javascript:return checkForm();">
                            <ul class="reg_list">
                                <li>
                                    <textarea name="txtDescription" rows="2" cols="20" id="txtDescription"
                                              class="textarea" placeholder="请说明情况，并留下联系方式，你可以在个人中心【我的反馈】里查看回复~">
</textarea></li>
                                <li class="mb20">
                                    <input type="submit" name="btnSave" value="提交" id="btnSave"
                                           class="btn_red btn_big"/></li>
                                <li>
                                    <span id="LabErr"></span></li>
                            </ul>
                        </form>

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

 
const state = reactive({
});
const router = useRouter();
const topBooks = ref([]);
//反馈内容必须填写 并且不能少于五个字
</script>
