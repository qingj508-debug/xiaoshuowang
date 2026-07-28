USE `endpoint_payorder`;
/*
 Navicat Premium Data Transfer

 Source Server         : 109
 Source Server Type    : MySQL
 Source Server Version : 80046
 Source Host           : 192.168.10.109:3306
 Source Schema         : endpoint_payorder

 Target Server Type    : MySQL
 Target Server Version : 80046
 File Encoding         : 65001

 Date: 24/07/2026 08:58:10
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for order_pay
-- ----------------------------
DROP TABLE IF EXISTS `order_pay`;
CREATE TABLE `order_pay`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `out_trade_no` bigint(0) NOT NULL COMMENT '商户订单号',
  `trade_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '支付宝/微信交易号',
  `pay_channel` tinyint(1) NOT NULL DEFAULT 1 COMMENT '支付渠道，1：支付宝，2：微信',
  `total_amount` int(0) NOT NULL COMMENT '交易金额(单位元)',
  `user_id` bigint(0) NOT NULL COMMENT '支付用户ID',
  `pay_status` tinyint(1) NULL DEFAULT 2 COMMENT '支付状态：0：支付失败，1：支付成功，2：待支付',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 70 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '充值订单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_pay
-- ----------------------------
INSERT INTO `order_pay` VALUES (63, 202212121109239677, NULL, 1, 10, 1255664783722586120, 2, '2022-12-12 11:09:24', '2022-12-12 11:09:24');
INSERT INTO `order_pay` VALUES (64, 202212121534493294, NULL, 1, 10, 1255664783722586120, 2, '2022-12-12 15:34:49', '2022-12-12 15:34:49');
INSERT INTO `order_pay` VALUES (65, 202605081859169139, NULL, 1, 10, 1255664783722586118, 2, '2026-05-08 18:59:17', '2026-05-08 18:59:17');
INSERT INTO `order_pay` VALUES (66, 202605081859579196, NULL, 1, 10, 1255664783722586118, 2, '2026-05-08 18:59:58', '2026-05-08 18:59:58');
INSERT INTO `order_pay` VALUES (67, 202605081901497600, NULL, 1, 10, 1255664783722586118, 2, '2026-05-08 19:01:50', '2026-05-08 19:01:50');
INSERT INTO `order_pay` VALUES (68, 202605082018174529, NULL, 1, 10, 1255664783722586118, 2, '2026-05-08 20:18:17', '2026-05-08 20:18:17');
INSERT INTO `order_pay` VALUES (69, 202605082021380463, NULL, 1, 10, 1255664783722586118, 2, '2026-05-08 20:21:38', '2026-05-08 20:21:38');
INSERT INTO `order_pay` VALUES (70, 202605082024522369, NULL, 1, 10, 1255664783722586118, 2, '2026-05-08 20:24:52', '2026-05-08 20:24:52');
INSERT INTO `order_pay` VALUES (71, 202607232136103358, NULL, 1, 10, 1255664783722586122, 2, '2026-07-23 21:36:10', '2026-07-23 21:36:10');
INSERT INTO `order_pay` VALUES (72, 202607232139022221, NULL, 1, 10, 1255664783722586122, 2, '2026-07-23 21:39:02', '2026-07-23 21:39:02');
INSERT INTO `order_pay` VALUES (73, 202607232200437276, NULL, 1, 10, 1255664783722586122, 2, '2026-07-23 22:00:44', '2026-07-23 22:00:44');

SET FOREIGN_KEY_CHECKS = 1;
