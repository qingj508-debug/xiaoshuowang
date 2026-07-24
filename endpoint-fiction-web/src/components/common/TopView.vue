<template>
  <div class="topMain">
    <div class="box_center cf">
      <router-link :to="{ name: 'home' }" class="logo fl"
        ><img :src="logo" alt="终点小说网"
      /></router-link>
      <div class="searchBar fl">
        <div class="search cf">
          <input
            v-model="state.keyword"
            type="text" 
            placeholder="书名、作者、关键字"
            class="s_int"
            v-on:keyup.enter="searchByKw"
          />
          <label class="search_btn" id="btnSearch" @click="searchByKw"  ><i class="icon"></i></label>
        </div>
      </div>
      
      <div class="bookShelf fr" id="headerUserInfo">
        <router-link to="/favorites" class="sj_link" >我的书架</router-link>
        <span v-if="!state.token" class="user_link">
          <router-link to="/login" class="mr15">登录</router-link>
          <router-link to="/register" class="mr15">注册</router-link>
        </span>

        <span v-if="state.token" class="user_link">
          <router-link class="mr15" to="/membercenter" >{{ state.nickname }}</router-link>
          <a @click="logout" href="javascript:void(0)">退出</a>
        </span>

      </div>
    </div>
  </div>
</template>
  
<script setup>
import logo from "@/assets/images/logo.png";
import { getToken, getNickName, removeToken, removeNickName,removeUid } from "@/utils/auth";
import { reactive } from "vue";
import { useRouter, useRoute } from "vue-router";
const state = reactive({
  keyword: "",
  token: getToken(),
  nickname: getNickName()
});

console.log("topview:",state.token)

const route = useRoute();
const router = useRouter();

state.keyword = route.query.key;
const emit = defineEmits(['eventSerch']);

const logout = () => {
      removeToken();
      removeNickName();
      removeUid()
      state.nickName = "";
      state.token = "";
    };
const searchByKw = () => {
  //通过关键字查询并跳转页面
  router.push({ path: "/booksearch", query: { key: state.keyword } });
  //context.emit("eventSerch", state.keyword);
  emit("eventSerch",state.keyword);
};
</script>
