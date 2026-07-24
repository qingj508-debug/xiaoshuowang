<template>
  <div id="bookrank2_ShowBookRank">
    <div class="rightBox">
      <div class="title cf">
        <h3 class="on">新书榜单</h3>
      </div>
      <div class="rightList">
        <ul id="newRankBooks">
          <li
            v-for="(item, index) in state.books"
            :key="index"
            :class="['num' + (Number(`${index}`) + 1), { on: index == 0 }]"
          >
            <div class="book_name">
              <i>{{ index + 1 }}</i
              ><a
                class="name"
                href="javascript:void(0)"
              
                >{{ item.bookName }}</a
              >
            </div>
            <div class="book_intro">
              <div cla  ss="cover">
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
        <div class="more">
          查看更多&gt;
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { reactive,onMounted } from "vue";


const state = reactive({
  books: [
    {
      picUrl: "https://bookcover.yuewen.com/qdbimg/349573/1735921/180",
      bookName: "遮天",
      bookDesc:"冰冷与黑暗并存的宇宙深处，九具庞大的龙尸拉着一口青铜古棺，亘古长存。这是太空探测器在枯寂的宇宙中捕捉到的一幅极其震撼的画面。九龙拉棺，究竟是回到了上古，还是来到了星空的彼岸？一个浩大的仙侠世界，光怪陆离，神秘无尽。热血似火山沸腾，激情若瀚海汹涌，欲望如深渊无止境……登天路，踏歌行，弹指遮天。"
    },
    {
      picUrl: "https://bookcover.yuewen.com/qdbimg/349573/1033448521/180",
      bookName: "重铸2005",
      bookDesc:"大龄单身狗苇庆凡在第N次被催婚后回到高中时代，获得了一次重新筑基的机会。"
    },
    {
      picUrl: "https://bookcover.yuewen.com/qdbimg/349573/1033982482/180",
      bookName: "你老爹我才不是什么莽夫",
      bookDesc:"xxxxxx"
    },
    {
      picUrl: "https://bookcover.yuewen.com/qdbimg/349573/1034342761/180",
      bookName: "从逆练辟邪开始",
      bookDesc:"xxxxxx"
    },
    {
      picUrl: "https://bookcover.yuewen.com/qdbimg/349573/1022576776/180",
      bookName: "叩问仙道",
      bookDesc:"xxxxxx"
    }
  ]
});

onMounted(() => {

});




</script>