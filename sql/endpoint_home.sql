USE `endpoint_home`;
/*
 Navicat Premium Data Transfer

 Source Server         : 109
 Source Server Type    : MySQL
 Source Server Version : 80046
 Source Host           : 192.168.10.109:3306
 Source Schema         : endpoint_home

 Target Server Type    : MySQL
 Target Server Version : 80046
 File Encoding         : 65001

 Date: 24/07/2026 08:57:54
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for home_book
-- ----------------------------
DROP TABLE IF EXISTS `home_book`;
CREATE TABLE `home_book`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `book_id` bigint(0) NULL DEFAULT NULL COMMENT '小说ID',
  `sort` tinyint(0) NULL DEFAULT NULL COMMENT '排序号',
  `type` tinyint(1) NULL DEFAULT NULL COMMENT '类型，0：轮播图，1：顶部小说栏设置，2：本周强推，3：热门推荐，4：精品推荐',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user_id` bigint(0) NULL DEFAULT NULL COMMENT '创建人ID',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_user_id` bigint(0) NULL DEFAULT NULL COMMENT '更新人ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 64 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '首页小说设置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of home_book
-- ----------------------------
INSERT INTO `home_book` VALUES (32, 1585217615612182531, 1, 0, '2020-04-27 15:45:58', NULL, '2020-04-27 15:46:03', NULL);
INSERT INTO `home_book` VALUES (33, 1585217304541626368, 2, 0, '2020-04-27 15:46:21', NULL, '2020-04-27 15:46:24', NULL);
INSERT INTO `home_book` VALUES (34, 1585216585885384704, 3, 0, '2020-04-27 15:47:06', NULL, '2020-04-27 15:47:09', NULL);
INSERT INTO `home_book` VALUES (35, 1585216283341848576, 4, 0, '2020-04-27 15:47:24', NULL, '2020-04-27 15:47:27', NULL);
INSERT INTO `home_book` VALUES (36, 1585216182292676608, 1, 1, NULL, NULL, NULL, NULL);
INSERT INTO `home_book` VALUES (37, 1585216019188776960, 2, 1, NULL, NULL, NULL, NULL);
INSERT INTO `home_book` VALUES (38, 1585216012398198784, 3, 1, NULL, NULL, NULL, NULL);
INSERT INTO `home_book` VALUES (39, 1585214620707160064, 4, 1, NULL, NULL, NULL, NULL);
INSERT INTO `home_book` VALUES (40, 1585214236181757952, 5, 1, NULL, NULL, NULL, NULL);
INSERT INTO `home_book` VALUES (41, 1585213481538387968, 1, 2, NULL, NULL, NULL, NULL);
INSERT INTO `home_book` VALUES (42, 1585212934739558400, 2, 2, NULL, NULL, NULL, NULL);
INSERT INTO `home_book` VALUES (43, 1585212906847436800, 3, 2, NULL, NULL, NULL, NULL);
INSERT INTO `home_book` VALUES (44, 1585212667541422080, 4, 2, NULL, NULL, NULL, NULL);
INSERT INTO `home_book` VALUES (45, 1585212629960458240, 5, 2, NULL, NULL, NULL, NULL);
INSERT INTO `home_book` VALUES (46, 1585212488008433664, 6, 2, NULL, NULL, NULL, NULL);
INSERT INTO `home_book` VALUES (47, 1585212166275956736, 1, 3, NULL, NULL, NULL, NULL);
INSERT INTO `home_book` VALUES (48, 1585212133640077312, 2, 3, NULL, NULL, NULL, NULL);
INSERT INTO `home_book` VALUES (49, 1585211823991390208, 3, 3, NULL, NULL, NULL, NULL);
INSERT INTO `home_book` VALUES (50, 1585211742609309696, 4, 3, NULL, NULL, NULL, NULL);
INSERT INTO `home_book` VALUES (51, 1585211589575933952, 5, 3, NULL, NULL, NULL, NULL);
INSERT INTO `home_book` VALUES (52, 1585211486458970112, 6, 3, NULL, NULL, NULL, NULL);
INSERT INTO `home_book` VALUES (53, 1585211190240444416, 1, 4, NULL, NULL, NULL, NULL);
INSERT INTO `home_book` VALUES (54, 1585210966105227264, 2, 4, NULL, NULL, NULL, NULL);
INSERT INTO `home_book` VALUES (55, 1585210367359942657, 3, 4, NULL, NULL, NULL, NULL);
INSERT INTO `home_book` VALUES (56, 1585210367355748354, 4, 4, NULL, NULL, NULL, NULL);
INSERT INTO `home_book` VALUES (57, 1585210367355748353, 5, 4, NULL, NULL, NULL, NULL);
INSERT INTO `home_book` VALUES (58, 1585210367355748352, 6, 4, NULL, NULL, NULL, NULL);
INSERT INTO `home_book` VALUES (59, 1585210366814683136, 6, 1, NULL, NULL, NULL, NULL);
INSERT INTO `home_book` VALUES (60, 1585214427421048832, 7, 1, NULL, NULL, NULL, NULL);
INSERT INTO `home_book` VALUES (61, 1585216834360147968, 8, 1, NULL, NULL, NULL, NULL);
INSERT INTO `home_book` VALUES (62, 1585213401888555008, 9, 1, NULL, NULL, NULL, NULL);
INSERT INTO `home_book` VALUES (63, 1585211661529219072, 10, 1, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for home_friendlink
-- ----------------------------
DROP TABLE IF EXISTS `home_friendlink`;
CREATE TABLE `home_friendlink`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `link_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '链接名',
  `link_url` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '链接url',
  `sort` tinyint(0) NOT NULL DEFAULT 11 COMMENT '排序号',
  `is_open` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否开启，0：不开启，1：开启',
  `create_user_id` bigint(0) NULL DEFAULT NULL COMMENT '创建人id',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user_id` bigint(0) NULL DEFAULT NULL COMMENT '更新者用户id',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of home_friendlink
-- ----------------------------
INSERT INTO `home_friendlink` VALUES (5, '终点小说网', 'https://www.endpoint.com', 11, 1, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for home_news
-- ----------------------------
DROP TABLE IF EXISTS `home_news`;
CREATE TABLE `home_news`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `cat_id` int(0) NULL DEFAULT NULL COMMENT '类别ID',
  `cat_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '分类名',
  `source_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '来源',
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '内容',
  `read_count` bigint(0) NOT NULL DEFAULT 0 COMMENT '阅读量',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '发布时间',
  `create_user_id` bigint(0) NULL DEFAULT NULL COMMENT '发布人ID',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_user_id` bigint(0) NULL DEFAULT NULL COMMENT '更新人ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '新闻表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of home_news
-- ----------------------------
INSERT INTO `home_news` VALUES (1, 1, '行业', '未知', '阅文推“单本可选新合同”：授权分级、免费或付费自选', '阅文推“单本可选新合同”：授权分级、免费或付费自选', 0, '2020-04-27 15:42:21', NULL, '2020-04-27 15:42:26', NULL);
INSERT INTO `home_news` VALUES (2, 3, '资讯', '全媒派公众号', 'AI小说悄然流行：人类特有的创作力，已经被AI复制？', 'AI小说悄然流行：人类特有的创作力，已经被AI复制？', 0, '2020-04-28 15:44:07', NULL, '2020-04-28 15:44:12', NULL);

-- ----------------------------
-- Table structure for home_news_category
-- ----------------------------
DROP TABLE IF EXISTS `home_news_category`;
CREATE TABLE `home_news_category`  (
  `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '分类名',
  `sort` tinyint(0) NOT NULL DEFAULT 10 COMMENT '排序',
  `create_user_id` bigint(0) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_user_id` bigint(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '新闻类别表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of home_news_category
-- ----------------------------
INSERT INTO `home_news_category` VALUES (1, '行业', 10, NULL, NULL, NULL, NULL);
INSERT INTO `home_news_category` VALUES (3, '资讯', 11, NULL, NULL, NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
