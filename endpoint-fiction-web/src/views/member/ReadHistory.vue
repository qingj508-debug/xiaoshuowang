<template>
    <Top />
    <Navbar />
<div class="main box_center cf">
    <div class="userBox cf">
        <div class="viewhistoryBox">
            <div class="my_bookshelf">
                <div class="title cf">
                    <h2 class="fl ml10"><router-link to="/readhistory" class="red">最近阅读</router-link></h2><i class="fl ml20 mr20 font16">|</i><h2 class="fl"><router-link to="/favorites">我的书架</router-link ></h2>
                </div>
                <div id="divData" class="updateTable">
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
                        <tbody id="bookHistoryList">

                        <tr class="book_list" vals="234">
                            <td class="style bookclass">武侠仙侠</td>
                            <td class="name">
                                <a href="/book/234.html">遮天</a></td>
                            <td class="chapter" valsc="234|2005456|1">第二章 素问</td>
                            <td class="time">2022-12-12 12:12:12</td>
                            <td class="goread">继续阅读</td>
                        </tr>

                        </tbody>
                    </table>
                    <div class="pageBox cf" id="historyPage">
                    </div>
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
