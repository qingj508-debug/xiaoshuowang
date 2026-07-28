USE `endpoint_author`;
/*
 Navicat Premium Data Transfer

 Source Server         : 109
 Source Server Type    : MySQL
 Source Server Version : 80046
 Source Host           : 192.168.10.109:3306
 Source Schema         : endpoint_author

 Target Server Type    : MySQL
 Target Server Version : 80046
 File Encoding         : 65001

 Date: 24/07/2026 08:57:26
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for author
-- ----------------------------
DROP TABLE IF EXISTS `author`;
CREATE TABLE `author`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `member_id` bigint(0) NULL DEFAULT NULL COMMENT '用户ID',
  `invite_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邀请码',
  `pen_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '笔名',
  `tel_phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '手机号码',
  `chat_account` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'QQ或微信账号',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '电子邮箱',
  `work_direction` tinyint(0) NULL DEFAULT NULL COMMENT '作品方向，0：男频，1：女频',
  `status` tinyint(0) NULL DEFAULT 0 COMMENT '0：正常，1：封禁',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`, `email`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '作者表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of author
-- ----------------------------
INSERT INTO `author` VALUES (1, NULL, 'reerer', 'abc', '13560487656', '23484388', '23484388@qq.com', 0, 0, NULL);
INSERT INTO `author` VALUES (2, 1255060328322027520, 'rwrr445554', '梦入神机', '13560421324', '1179705413', 'reerer@qq.com', 0, 0, '2020-05-13 14:01:31');
INSERT INTO `author` VALUES (4, 1255664783722586118, '0', '千王之王', '18518390496', '1234562', '657224127@qq.com', 0, 0, '2022-11-28 17:10:53');
INSERT INTO `author` VALUES (5, 1255664783722586122, '0', '测试作者007', '13512345678', '1179705413', 'test007@test.com', 0, 0, '2026-07-23 15:27:05');

-- ----------------------------
-- Table structure for author_code
-- ----------------------------
DROP TABLE IF EXISTS `author_code`;
CREATE TABLE `author_code`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `invite_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邀请码',
  `validity_time` datetime(0) NULL DEFAULT NULL COMMENT '有效时间',
  `is_use` tinyint(1) NULL DEFAULT 0 COMMENT '是否使用过，0：未使用，1:使用过',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user_id` bigint(0) NULL DEFAULT NULL COMMENT '创建人ID',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `key_code`(`invite_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '作家邀请码表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of author_code
-- ----------------------------
INSERT INTO `author_code` VALUES (3, 'reerer', '2020-05-27 22:43:45', 1, '2020-05-13 11:40:56', 1);
INSERT INTO `author_code` VALUES (4, '123456', '2020-05-28 00:00:00', 0, '2020-05-13 14:09:55', 1);
INSERT INTO `author_code` VALUES (5, 'ww34343', '2020-05-21 00:00:00', 0, '2020-05-13 14:18:58', 1);

-- ----------------------------
-- Table structure for author_income
-- ----------------------------
DROP TABLE IF EXISTS `author_income`;
CREATE TABLE `author_income`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(0) NOT NULL COMMENT '用户ID',
  `author_id` bigint(0) NOT NULL COMMENT '作家ID',
  `book_id` bigint(0) NOT NULL COMMENT '作品ID',
  `income_month` date NOT NULL COMMENT '收入月份',
  `pre_tax_income` bigint(0) NOT NULL DEFAULT 0 COMMENT '税前收入（分）',
  `after_tax_income` bigint(0) NOT NULL DEFAULT 0 COMMENT '税后收入（分）',
  `pay_status` tinyint(1) NOT NULL DEFAULT 0 COMMENT '支付状态，0：待支付，1：已支付',
  `confirm_status` tinyint(1) NOT NULL DEFAULT 0 COMMENT '稿费确认状态，0：待确认，1：已确认',
  `detail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '详情',
  `create_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '稿费收入统计表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of author_income
-- ----------------------------

-- ----------------------------
-- Table structure for author_income_detail
-- ----------------------------
DROP TABLE IF EXISTS `author_income_detail`;
CREATE TABLE `author_income_detail`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(0) NOT NULL COMMENT '用户ID',
  `author_id` bigint(0) NOT NULL COMMENT '作家ID',
  `book_id` bigint(0) NOT NULL DEFAULT 0 COMMENT '作品ID,0表示全部作品',
  `income_date` date NOT NULL COMMENT '收入日期',
  `income_account` int(0) NOT NULL DEFAULT 0 COMMENT '订阅总额',
  `income_count` int(0) NOT NULL DEFAULT 0 COMMENT '订阅次数',
  `income_number` int(0) NOT NULL DEFAULT 0 COMMENT '订阅人数',
  `create_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '稿费收入明细统计表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of author_income_detail
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
