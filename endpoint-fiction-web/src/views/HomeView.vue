<template>
  <Top />
  <Navbar />
  <div class="main box_center cf">
    <div class="channelWrap channelBanner cf">
      <div class="leftBox">
        <!-- 轮播图-->
        <div class="sliderContent">
          <dl class="scBigImg" id="carouseBig">
            <dd
              v-for="(item, index) in state.sliderContent"
              :key="index"
              :class="{ on: index == 0 }"
            >
              <a href="javascript:void(0)">
                <img :src="item.picUrl" :alt="item.bookName" />
              </a>
            </dd>
          </dl>
          <div class="scSmallImg" id="carouseSmall">
            <ul>
              <li
                v-for="(item, index) in state.sliderContent"
                :key="index"
                :class="{ on: index == 0 }"
              >
                <img :src="item.picUrl" :alt="item.bookName" />
              </li>
            </ul>
          </div>
        </div>
        <!--顶部小说栏设置 -->
        <!-- <div class="hot_articles">
          <dl class="hot_recommend" id="topBooks">
            <template v-for="(item, index) in state.topBooks">
            <dt v-if="index==0">
              <a href="javascript:void(0)" >{{ item.bookName}}</a>
            </dt>
            <dd v-if="index!=0&&index!=5">
              <a href="javascript:void(0)" >{{ item.bookName}}</a>
              <a href="javascript:void(0)" >{{ item.bookName}}</a>
            </dd>

          </template>
          </dl> -->
          <div class="hot_articles">
          <dl class="hot_recommend" id="topBooks1">
            <dt>
              <a href="javascript:void(0)" @click="bookDetail(topBooks[0].bookId)" v-if="topBooks[0]">{{
                topBooks[0].bookName
              }}</a>
            </dt>
            <dd>
              <a href="javascript:void(0)" @click="bookDetail(topBooks[1].bookId)" v-if="topBooks[1]">{{
                topBooks[1].bookName
              }}</a
              ><a href="javascript:void(0)" @click="bookDetail(topBooks[2].bookId)" v-if="topBooks[2]">{{
                topBooks[2].bookName
              }}</a>
            </dd>
            <dd>
              <a href="javascript:void(0)" @click="bookDetail(topBooks[3].bookId)" v-if="topBooks[3]">{{
                topBooks[3].bookName
              }}</a
              ><a href="javascript:void(0)" @click="bookDetail(topBooks[4].bookId)" v-if="topBooks[4]">{{
                topBooks[4].bookName
              }}</a>
            </dd>
          </dl>
          <dl class="hot_recommend" id="topBooks2">
            <dt>
              <a href="javascript:void(0)" @click="bookDetail(topBooks[5].bookId)" v-if="topBooks[5]">{{
                topBooks[5].bookName
              }}</a>
            </dt>
            <dd>
              <a href="javascript:void(0)" @click="bookDetail(topBooks[6].bookId)" v-if="topBooks[6]">{{
                topBooks[6].bookName
              }}</a
              ><a href="javascript:void(0)" @click="bookDetail(topBooks[7].bookId)" v-if="topBooks[7]">{{
                topBooks[7].bookName
              }}</a>
            </dd>
            <dd>
              <a href="javascript:void(0)" @click="bookDetail(topBooks[8].bookId)" v-if="topBooks[8]">{{
                topBooks[8].bookName
              }}</a
              ><a href="javascript:void(0)" @click="bookDetail(topBooks[9].bookId)" v-if="topBooks[9]">{{
                topBooks[9].bookName
              }}</a>
            </dd>
          </dl>


          <!-- 最新资讯-->
          <dl class="hot_notice" id="indexNews">
            <dd
              style="text-align: left"
              v-for="(item, index) in state.newsList"
              :key="index"
            >
              <span>[{{ item.catName }}]</span>
              <a href="javascript:void(0)"> {{ item.title }}</a>
            </dd>
          </dl>
        </div>
      </div>
      <!-- 本周强推 -->
      <div class="rightBox">
        <div class="title cf" id="weekcommend">
          <h3>本周强推</h3>
        </div>
        <div class="rightList">
          <ul id="currentWeek">
            <li
              v-for="(item, index) in state.weekcommend"
              :key="index"
              :class="['num' + (Number(`${index}`) + 1), { on: index == 0 }]"
            >
              <div class="book_name">
                <i>{{ index + 1 }}</i
                ><a class="name" href="javascript:void(0)">{{
                  item.bookName
                }}</a>
              </div>
              <div class="book_intro">
                <div class="cover">
                  <a href="javascript:void(0)"
                    ><img
                      :src="item.picUrl"
                      :alt="item.bookName"
                      onerror="this.src='default.gif';this.onerror=null"
                  /></a>
                </div>
                <a
                  class="txt"
                  href="javascript:void(0)"
                  v-html="item.bookDesc"
                ></a>
              </div>
            </li>
          </ul>
        </div>
      </div>
    </div>


    <div class="channelWrap channelPic cf">
      <div class="leftBox">
        <div class="title">
          <h2 class="on">热门推荐</h2>
        </div>
        <div class="picRecommend cf" id="hotRecBooks">
          <div
            class="itemsList"
            v-for="(item, index) in state.hotRecommend"
            :key="index"
          >
            <a class="items_img" href="javascript:void(0)" @click="bookDetail(item.bookId)">
              <img
                :src="item.picUrl"
                onerror="this.src='default.gif';this.onerror=null"
                :alt="item.bookName"
              />
            </a>
            <div class="items_txt">
              <h4>
                <a href="javascript:void(0)" @click="bookDetail(item.bookId)">{{ item.bookName }}</a>
              </h4>
              <p class="author">
                <a href="javascript:void(0)">作者：{{ item.authorName }}</a>
              </p>
              <p class="intro">
                <a
                  href="javascript:void(0)" @click="bookDetail(item.bookId)"
                  v-html="item.bookDesc"
                ></a>
              </p>
            </div>
          </div>
        </div>
      </div>
      <BookVisitRank /> 
    </div>

    <!-- 精品推荐 -->
    <div class="channelWrap channelPic cf">
      <div class="leftBox">
        <div class="title">
          <h2>精品推荐</h2>
        </div>

        <div class="picRecommend cf" id="classicBooks">
          <div
            class="itemsList"
            v-for="(item, index) in state.goodRecommend"
            :key="index"
          >
            <a class="items_img" href="javascript:void(0)" @click="bookDetail(item.bookId)">
              <img
                :src="item.picUrl"
                onerror="this.src='default.gif';this.onerror=null"
              />
            </a>
            <div class="items_txt">
              <h4>
                <a href="javascript:void(0)" @click="bookDetail(item.bookId)">{{ item.bookName }}</a>
              </h4>
              <p class="author">
                <a href="javascript:void(0)">作者：{{ item.authorName }}</a>
              </p>
              <p class="intro">
                <a
                  href="javascript:void(0)" @click="bookDetail(item.bookId)"
                  v-html="item.bookDesc"
                ></a>
              </p>
            </div>
          </div>
        </div>
      </div>
      <BookNewestRank /> 
    </div>
     <BookUpdateRank />
  </div>
  <Footer />
</template>

<script setup>
import { reactive, onMounted, ref } from "vue";
import Top from "../components/common/TopView";
import Navbar from "../components/common/NavbarView";
import Footer from "../components/common/FooterView";
import BookVisitRank from "../components/home/BookVisitRank";
import BookNewestRank from "../components/home/BookNewestRank";
import BookUpdateRank from "../components/home/BookUpdateRank";
import { latestNewsList, bookCommendList} from "@/api/home";
import { ElLoading } from "element-plus";
import { useRouter } from "vue-router";

 
const state = reactive({
  sliderContent: [],// 轮播图
  topBooks: [], // 顶部栏
  weekcommend: [], //本周强推
  newsList: [],  //新闻资讯
  hotRecommend:[], // 热门推荐
  goodRecommend:[]// 精品推荐
});
const router = useRouter();
const topBooks = ref([]);

onMounted(async () => {

  const loadingInstance = ElLoading.service({
        target: "#topBooks2",
        text: "加载中。。。",
      });
  await bookCommendList().then(res =>{
    console.log(res)
    res.data.forEach((book) => {
        if (book.type == 0) {
          // 轮播图
          state.sliderContent[state.sliderContent.length] = book;
        }
        if (book.type == 1) {
          // 顶部栏
          state.topBooks[state.topBooks.length] = book;
        }
        if (book.type == 2) {
          //本周强推
          state.weekcommend[state.weekcommend.length] = book;
        }
        if (book.type == 3) {
          //热门推荐
          state.hotRecommend[state.hotRecommend.length] = book;
        }
        if (book.type == 4) {
          //精品推荐
          state.goodRecommend[state.goodRecommend.length] = book;
        }
      });
  })
  topBooks.value = state.topBooks;
  console.log(":",state.topBooks[0].bookName);
  latestNewsList().then((res) =>{
    state.newsList = res.data;
  });
  console.log(state);
  loadingInstance.close();





  let $div = $(".scBigImg dl"); //放置大图容器
  let $nav = $(".scSmallImg li"); //放置缩略图容器
  let num = -1;
  let open;
 
  function changeKv() {
    console.log("$nav.length:", $nav.length);
    if (num >= $nav.length - 1) {
      num = 0;
    } else {
      num++;
    }
    $nav.eq(num).trigger("mouseenter"); //为缩略图容器添加 鼠标移入事件
    open = setTimeout(changeKv, 3000); //设置定时器3秒执行一次函数
  }

  changeKv();
  $nav.each(function (index) {
    //遍历缩略图 所有元素
    $(this)
      .off("mouseenter") //移除当前元素  鼠标移入事件
      .on("mouseenter", function () {
        //当前元素绑定鼠标移入事件
        clearTimeout(open); //首先清除定时器

        $(this).addClass("on").siblings().removeClass("on"); //当前元素添加 class=on 其他 同辈元素 移除class="on"
        $(".scBigImg dd") //当前大图与缩略图的序号一致. 添加 class on 其他同辈元素 移除class on
          .eq(index)
          .addClass("on")
          .siblings()
          .removeClass("on");
        $(".scSmallImg") //当前 索引的缩略图  移除鼠标移出事件
          .off("mouseleave")
          .on("mouseleave", function () {
            //添加新鼠标移除事件
            num = index; //索引设为num
            setTimeout(function () {
              //重新设置定时器
              changeKv();
            }, 3000);
          });
      });
  });
  $div.each(function () {
    //遍历大图容器元素
    $(this)
      .off("mouseenter") //当前移除 鼠标移入事件
      .on("mouseenter", function () {
        //添加新事件 移入清除定时器
        clearTimeout(open);
      });
  });
});


const bookDetail = (bookId) => {
      router.push({ path: `/book/${bookId}` });
    };



</script>