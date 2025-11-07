/*
 Navicat Premium Data Transfer

 Source Server         : 101.200.194.32
 Source Server Type    : MySQL
 Source Server Version : 50721
 Source Host           : 101.200.194.32:3306
 Source Schema         : test

 Target Server Type    : MySQL
 Target Server Version : 50721
 File Encoding         : 65001

 Date: 05/11/2025 23:20:21
*/

create database test;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 131 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES (3, 'test', 'mysql测试', 'mysql测试');
INSERT INTO `users` VALUES (10, '张三', '1221', '10');
INSERT INTO `users` VALUES (122, '李四', '12123', '李四');
INSERT INTO `users` VALUES (123, 'root', '2312321', '321313');
INSERT INTO `users` VALUES (125, 'root', '2312321', '321313');
INSERT INTO `users` VALUES (126, '321321', '3213213', '321321');
INSERT INTO `users` VALUES (127, '321321', '77777', '321321');
INSERT INTO `users` VALUES (128, NULL, NULL, NULL);
INSERT INTO `users` VALUES (129, '呃呃问问', '212121', '张三丰');
INSERT INTO `users` VALUES (130, '张三丰的爹', 'dafdaffa', NULL);

SET FOREIGN_KEY_CHECKS = 1;
