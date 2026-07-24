<template>
    <div class="mainNav" id="mainNav">
      <div class="box_center cf">
        <ul class="nav" id="navModule">
          <li><router-link to="/home">首页</router-link></li>
          <li>
            <router-link to="/booksearch"> 全部作品 </router-link>
          </li>
          <li><router-link to="/home">排行榜</router-link></li>
          <li class=""><a  @click="goRecharge">充值</a></li>
          <li><a @click="goAuthor" href="javascript:void(0)">作家专区</a></li>
        </ul>
      </div>
    </div>
  </template>
<script setup>
import { useRouter, useRoute } from "vue-router";
import { getToken} from "@/utils/auth";
import {getAuthorStatus } from "@/api/author";
import {ElMessage } from "element-plus";



    const route = useRoute();
    const router = useRouter();
    const goAuthor = async () => {
      if (!getToken()) {
        router.push({
          name: "login",
        });
        return;
      }

      const {data} = await getAuthorStatus();
      if(data === null){
        console.log("作家未注册")
          router.push({
          name: "authorRegister",
        });
        return;
      }else{
        let routeUrl = router.resolve({
        name: "authorBookList",
      });
      window.open(routeUrl.href, "_blank");
      }
    }
    const goRecharge = async () => {
      if (!getToken()) {
        ElMessage({
          message: '请登录后再充值',
          type: 'warning',
        })
        router.push({
          name: "login",
        });
        return;
      }else{
        router.push({
          name: "recharge",
        });
      }
    }
</script>   