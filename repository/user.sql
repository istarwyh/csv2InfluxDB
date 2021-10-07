/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 50719
Source Host           : localhost:3306
Source Database       : tmp

Target Server Type    : MYSQL
Target Server Version : 50719
File Encoding         : 65001

Date: 2020-11-08 13:05:11
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) DEFAULT NULL COMMENT '姓名',
  `nick_name` varchar(255) DEFAULT NULL COMMENT '昵称',
  `passwd` varchar(255) DEFAULT NULL COMMENT '姓名',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `money` double DEFAULT NULL COMMENT '账户余额',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'yihui', 'yihui','88888888','23', '5000');
INSERT INTO `user` VALUES ('2', 'SnailClimb', '','', '22', '3000');
INSERT INTO `user` VALUES ('4', 'SnailClimb', '','','22', '3000');
INSERT INTO `user` VALUES ('5', 'Daisy', '19','','', '3000');
INSERT INTO `user` VALUES ('6', 'SnailClimb','','', '22', '3000');
INSERT INTO `user` VALUES ('7', 'Daisy', '19','','', '3000');
INSERT INTO `user` VALUES ('8', 'SnailClimb', '','','22', '3000');
INSERT INTO `user` VALUES ('9', 'Daisy', '19', '','','3000');
