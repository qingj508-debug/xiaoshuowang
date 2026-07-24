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
                    <h2 class="fl"><router-link  to="/favorites" class="red">我的书架</router-link></h2><i class="fl ml20 mr20 font16">|</i><h2 class="fl"><router-link to="/readhistory" >最近阅读</router-link></h2>
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
                        <tbody id="bookShelfList">
                           
                            <tr   v-for="(item, index) in state.books"   :key="index" class="book_list" vals="291"> 
                                <td class="style bookclass">
                                    <a href=" javascript:void(0)" >{{item.catName}}</a>
                                </td>
                                <td class="name">
                                    <a href=" javascript:void(0)">{{item.bookName}}</a> 
                                </td>
                                 <td class="chapter" > 
                                    <a href='javascript:void(0)'>{{item.lastIndexName}}</a>
                                </td> 
                                <td class="time">{{item.lastIndexUpdateTime}}</td> 
                                <td class="goread"> 
                                    <a @click="readPreChapter(item.bookId,item.preIndexId)" >继续阅读</a>
                                </td>
                             </tr>
                         
                        </tbody>
                    </table>
                    <div class="pageBox cf" id="shellPage">
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
import { getToken, getNickName } from "@/utils/auth";
import { ElLoading } from "element-plus";
import { useRouter } from "vue-router";
import { getBookShelfList } from "@/api/member";

const state = reactive({
 books:[]
});
const router = useRouter();
const topBooks = ref([]);

onMounted(async () => {
    if (!getToken()) {

        router.push({
            name: "login",
        });
        return;
    }
    init();
});

const init= async()=>{
    const {data} = await getBookShelfList();
    console.log("查询出的书架集合信息",data);
    if(data){
        state.books=data;
    }
}

const readPreChapter = async(bookId,preIndexId)=>{
    router.push({ path: `/book/${bookId}/${preIndexId}` });
}

</script>

