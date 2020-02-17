/*
 Navicat Premium Data Transfer

 Source Server         : 本地数据库
 Source Server Type    : MySQL
 Source Server Version : 50553
 Source Host           : localhost:3306
 Source Schema         : bl_forum

 Target Server Type    : MySQL
 Target Server Version : 50553
 File Encoding         : 65001

 Date: 17/02/2020 19:13:48
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for forum_article
-- ----------------------------
DROP TABLE IF EXISTS `forum_article`;
CREATE TABLE `forum_article`  (
  `article_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '文章id',
  `user_id` int(11) NOT NULL COMMENT '归属用户id',
  `article_title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文章标题',
  `article_content` varchar(5000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文章内容',
  `article_add_time` datetime NULL DEFAULT NULL COMMENT '文章发布时间',
  `article_update_time` datetime NULL DEFAULT NULL COMMENT '文章更新时间',
  `article_status` int(1) NULL DEFAULT 1 COMMENT '文章状态(1正常 0删除)',
  `article_view` int(11) NULL DEFAULT 0 COMMENT '文章阅览次数',
  `article_top` int(11) NULL DEFAULT 0 COMMENT '文章置顶,越大的越在前面',
  PRIMARY KEY (`article_id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 88 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;



-- ----------------------------
-- Table structure for forum_article_comment
-- ----------------------------
DROP TABLE IF EXISTS `forum_article_comment`;
CREATE TABLE `forum_article_comment`  (
  `article_comment_id` int(11) NOT NULL AUTO_INCREMENT,
  `parent_article_comment_id` int(11) NULL DEFAULT 0 COMMENT '父文章评论id',
  `article_id` int(11) NULL DEFAULT NULL COMMENT '关联的文章id',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '评论用户id',
  `article_comment` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '评论内容',
  `article_comment_time` datetime NULL DEFAULT NULL COMMENT '评论时间',
  `article_comment_status` int(1) NULL DEFAULT 1 COMMENT '评论状态 1正常 0删除',
  PRIMARY KEY (`article_comment_id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 36 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '评论表' ROW_FORMAT = Dynamic;



-- ----------------------------
-- Table structure for forum_article_tag
-- ----------------------------
DROP TABLE IF EXISTS `forum_article_tag`;
CREATE TABLE `forum_article_tag`  (
  `article_tag_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '文章tagid',
  `article_id` int(11) NULL DEFAULT NULL COMMENT '文章id',
  `tag_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`article_tag_id`) USING BTREE,
  INDEX `article_id`(`article_id`, `tag_id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '文章标签关系表\r\n' ROW_FORMAT = Fixed;


-- ----------------------------
-- Table structure for forum_article_topic
-- ----------------------------
DROP TABLE IF EXISTS `forum_article_topic`;
CREATE TABLE `forum_article_topic`  (
  `article_topic_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '文章话题id',
  `article_id` int(11) NULL DEFAULT NULL COMMENT '文章id',
  `topic_id` int(11) NULL DEFAULT NULL COMMENT '话题名称',
  `add_time` datetime NULL DEFAULT NULL COMMENT '添加时间',
  PRIMARY KEY (`article_topic_id`) USING BTREE,
  INDEX `article_id`(`article_id`, `topic_id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '文章话题关系表' ROW_FORMAT = Fixed;


-- ----------------------------
-- Table structure for forum_article_view
-- ----------------------------
DROP TABLE IF EXISTS `forum_article_view`;
CREATE TABLE `forum_article_view`  (
  `article_view_id` int(11) NOT NULL AUTO_INCREMENT,
  `article_id` int(11) NULL DEFAULT NULL,
  `add_time` datetime NULL DEFAULT NULL COMMENT '添加时间',
  PRIMARY KEY (`article_view_id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 712 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Fixed;


-- ----------------------------
-- Table structure for forum_config
-- ----------------------------
DROP TABLE IF EXISTS `forum_config`;
CREATE TABLE `forum_config`  (
  `config_key` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'key',
  `config_value` varchar(3000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'value',
  `config_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分类类型',
  `config_input_tip` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '输入提示',
  `config_input_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '输入类型,text,number,textarea,boolean',
  PRIMARY KEY (`config_key`) USING BTREE
) ENGINE = MyISAM CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of forum_config
-- ----------------------------
INSERT INTO `forum_config` VALUES ('mail_host', 'smtp.exmail.qq.com', '邮箱', '邮箱地址', 'text');
INSERT INTO `forum_config` VALUES ('mail_port', '465', '邮箱', '邮箱端口', 'text');
INSERT INTO `forum_config` VALUES ('mail_ssl', 'true', '邮箱', '是否使用ssl', 'checkbox');
INSERT INTO `forum_config` VALUES ('mail_from', 'bl@bload.cn', '邮箱', '发件人名', 'text');
INSERT INTO `forum_config` VALUES ('mail_user', 'bl@bload.cn', '邮箱', '邮箱账号', 'text');
INSERT INTO `forum_config` VALUES ('mail_pass', 'Asdw112233', '邮箱', '邮箱密码', 'password');
INSERT INTO `forum_config` VALUES ('mail_template_register', '\n<head>\n    <base target=\"_blank\" />\n    <style type=\"text/css\">::-webkit-scrollbar{ display: none; }</style>\n    <style id=\"cloudAttachStyle\" type=\"text/css\">#divNeteaseBigAttach, #divNeteaseBigAttach_bak{display:none;}</style>\n    <style id=\"blockquoteStyle\" type=\"text/css\">blockquote{display:none;}</style>\n    <style type=\"text/css\">\n        body{font-size:14px;font-family:arial,verdana,sans-serif;line-height:1.666;padding:0;margin:0;overflow:auto;white-space:normal;word-wrap:break-word;min-height:100px}\n        td, input, button, select, body{font-family:Helvetica, \'Microsoft Yahei\', verdana}\n        pre {white-space:pre-wrap;white-space:-moz-pre-wrap;white-space:-pre-wrap;white-space:-o-pre-wrap;word-wrap:break-word;width:95%}\n        th,td{font-family:arial,verdana,sans-serif;line-height:1.666}\n        img{ border:0}\n        header,footer,section,aside,article,nav,hgroup,figure,figcaption{display:block}\n        blockquote{margin-right:0px}\n    </style>\n</head>\n<body tabindex=\"0\" role=\"listitem\">\n<table width=\"700\" border=\"0\" align=\"center\" cellspacing=\"0\" style=\"width:700px;\">\n    <tbody>\n    <tr>\n        <td>\n            <div style=\"width:700px;margin:0 auto;border-bottom:1px solid #ccc;margin-bottom:30px;\">\n                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"700\" height=\"39\" style=\"font:12px Tahoma, Arial, 宋体;\">\n                    <tbody><tr><td width=\"210\"></td></tr></tbody>\n                </table>\n            </div>\n            <div style=\"width:680px;padding:0 10px;margin:0 auto;\">\n                <div style=\"line-height:1.5;font-size:14px;margin-bottom:25px;color:#4d4d4d;\">\n                    <strong style=\"display:block;margin-bottom:15px;\">尊敬的用户：<span style=\"color:#f60;font-size: 16px;\"></span>您好！</strong>\n                    <strong style=\"display:block;margin-bottom:15px;\">\n                        您正在进行<span style=\"color: red\">注册账号</span>操作，请在验证码输入框中输入：<span style=\"color:#f60;font-size: 24px;text-decoration: underline;\">[code]</span>，以完成操作。\n                    </strong>\n                </div>\n                <div style=\"margin-bottom:30px;\">\n                    <small style=\"display:block;margin-bottom:20px;font-size:12px;\">\n                        <p style=\"color:#747474;\">\n                            注意：此操作可能会修改您的密码、登录邮箱或绑定手机。如非本人操作，请及时登录并修改密码以保证帐户安全\n                            <br>（工作人员不会向你索取此验证码，请勿泄漏！)\n                        </p>\n                    </small>\n                </div>\n            </div>\n            <div style=\"width:700px;margin:0 auto;\">\n                <div style=\"padding:10px 10px 0;border-top:1px solid #ccc;color:#747474;margin-bottom:20px;line-height:1.3em;font-size:12px;\">\n                    <p>此为系统邮件，请勿回复<br>\n                        请保管好您的邮箱，避免账号被他人盗用\n                    </p>\n                </div>\n       ', '邮箱', '注册模板 变量：[code],[email]', 'editor');
INSERT INTO `forum_config` VALUES ('mail_template_login', '<head>\n    <base target=\"_blank\" />\n    <style type=\"text/css\">::-webkit-scrollbar{ display: none; }</style>\n    <style id=\"cloudAttachStyle\" type=\"text/css\">#divNeteaseBigAttach, #divNeteaseBigAttach_bak{display:none;}</style>\n    <style id=\"blockquoteStyle\" type=\"text/css\">blockquote{display:none;}</style>\n    <style type=\"text/css\">\n        body{font-size:14px;font-family:arial,verdana,sans-serif;line-height:1.666;padding:0;margin:0;overflow:auto;white-space:normal;word-wrap:break-word;min-height:100px}\n        td, input, button, select, body{font-family:Helvetica, \'Microsoft Yahei\', verdana}\n        pre {white-space:pre-wrap;white-space:-moz-pre-wrap;white-space:-pre-wrap;white-space:-o-pre-wrap;word-wrap:break-word;width:95%}\n        th,td{font-family:arial,verdana,sans-serif;line-height:1.666}\n        img{ border:0}\n        header,footer,section,aside,article,nav,hgroup,figure,figcaption{display:block}\n        blockquote{margin-right:0px}\n    </style>\n</head>\n<body tabindex=\"0\" role=\"listitem\">\n<table width=\"700\" border=\"0\" align=\"center\" cellspacing=\"0\" style=\"width:700px;\">\n    <tbody>\n    <tr>\n        <td>\n            <div style=\"width:700px;margin:0 auto;border-bottom:1px solid #ccc;margin-bottom:30px;\">\n                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"700\" height=\"39\" style=\"font:12px Tahoma, Arial, 宋体;\">\n                    <tbody><tr><td width=\"210\"></td></tr></tbody>\n                </table>\n            </div>\n            <div style=\"width:680px;padding:0 10px;margin:0 auto;\">\n                <div style=\"line-height:1.5;font-size:14px;margin-bottom:25px;color:#4d4d4d;\">\n                    <strong style=\"display:block;margin-bottom:15px;\">尊敬的用户：<span style=\"color:#f60;font-size: 16px;\">[userNick]</span>您好！</strong>\n                    <strong style=\"display:block;margin-bottom:15px;\">\n                        您正在进行<span style=\"color: red\">登录账号</span>操作。\n                    </strong>\n                </div>\n                <div style=\"margin-bottom:30px;\">\n                    <small style=\"display:block;margin-bottom:20px;font-size:12px;\">\n                        <p style=\"color:#747474;\">\n                            注意：如非本人操作，请及时登录并修改密码以保证帐户安全\n                        </p>\n                    </small>\n                </div>\n            </div>\n            <div style=\"width:700px;margin:0 auto;\">\n                <div style=\"padding:10px 10px 0;border-top:1px solid #ccc;color:#747474;margin-bottom:20px;line-height:1.3em;font-size:12px;\">\n                    <p>此为系统邮件，请勿回复<br>\n                        请保管好您的邮箱，避免账号被他人盗用\n                    </p>\n                </div>\n            </div>\n        </td>\n    </tr>\n    </tbody>\n</table>\n</body>', '邮箱', '登录模板 变量：[email],[userNick]', 'editor');
INSERT INTO `forum_config` VALUES ('mail_template_edit', '<head>\n    <base target=\"_blank\" />\n    <style type=\"text/css\">::-webkit-scrollbar{ display: none; }</style>\n    <style id=\"cloudAttachStyle\" type=\"text/css\">#divNeteaseBigAttach, #divNeteaseBigAttach_bak{display:none;}</style>\n    <style id=\"blockquoteStyle\" type=\"text/css\">blockquote{display:none;}</style>\n    <style type=\"text/css\">\n        body{font-size:14px;font-family:arial,verdana,sans-serif;line-height:1.666;padding:0;margin:0;overflow:auto;white-space:normal;word-wrap:break-word;min-height:100px}\n        td, input, button, select, body{font-family:Helvetica, \'Microsoft Yahei\', verdana}\n        pre {white-space:pre-wrap;white-space:-moz-pre-wrap;white-space:-pre-wrap;white-space:-o-pre-wrap;word-wrap:break-word;width:95%}\n        th,td{font-family:arial,verdana,sans-serif;line-height:1.666}\n        img{ border:0}\n        header,footer,section,aside,article,nav,hgroup,figure,figcaption{display:block}\n        blockquote{margin-right:0px}\n    </style>\n</head>\n<body tabindex=\"0\" role=\"listitem\">\n<table width=\"700\" border=\"0\" align=\"center\" cellspacing=\"0\" style=\"width:700px;\">\n    <tbody>\n    <tr>\n        <td>\n            <div style=\"width:700px;margin:0 auto;border-bottom:1px solid #ccc;margin-bottom:30px;\">\n                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"700\" height=\"39\" style=\"font:12px Tahoma, Arial, 宋体;\">\n                    <tbody><tr><td width=\"210\"></td></tr></tbody>\n                </table>\n            </div>\n            <div style=\"width:680px;padding:0 10px;margin:0 auto;\">\n                <div style=\"line-height:1.5;font-size:14px;margin-bottom:25px;color:#4d4d4d;\">\n                    <strong style=\"display:block;margin-bottom:15px;\">尊敬的用户：<span style=\"color:#f60;font-size: 16px;\"></span>您好！</strong>\n                    <strong style=\"display:block;margin-bottom:15px;\">\n                        您正在进行<span style=\"color: red\">修改邮箱号</span>操作，请在验证码输入框中输入：<span style=\"color:#f60;font-size: 24px;text-decoration: underline;\">[code]</span>，以完成操作。\n                    </strong>\n                </div>\n                <div style=\"margin-bottom:30px;\">\n                    <small style=\"display:block;margin-bottom:20px;font-size:12px;\">\n                        <p style=\"color:#747474;\">\n                            注意：此操作可能会修改您的密码、登录邮箱或绑定手机。如非本人操作，请及时登录并修改密码以保证帐户安全\n                            <br>（工作人员不会向你索取此验证码，请勿泄漏！)\n                        </p>\n                    </small>\n                </div>\n            </div>\n            <div style=\"width:700px;margin:0 auto;\">\n                <div style=\"padding:10px 10px 0;border-top:1px solid #ccc;color:#747474;margin-bottom:20px;line-height:1.3em;font-size:12px;\">\n                    <p>此为系统邮件，请勿回复<br>\n                        请保管好您的邮箱，避免账号被他人盗用\n                    </p>\n                </div>', '邮箱', '修改模板 变量：[code],[email]', 'editor');
INSERT INTO `forum_config` VALUES ('mail_template_find', '<head>\n    <base target=\"_blank\" />\n    <style type=\"text/css\">::-webkit-scrollbar{ display: none; }</style>\n    <style id=\"cloudAttachStyle\" type=\"text/css\">#divNeteaseBigAttach, #divNeteaseBigAttach_bak{display:none;}</style>\n    <style id=\"blockquoteStyle\" type=\"text/css\">blockquote{display:none;}</style>\n    <style type=\"text/css\">\n        body{font-size:14px;font-family:arial,verdana,sans-serif;line-height:1.666;padding:0;margin:0;overflow:auto;white-space:normal;word-wrap:break-word;min-height:100px}\n        td, input, button, select, body{font-family:Helvetica, \'Microsoft Yahei\', verdana}\n        pre {white-space:pre-wrap;white-space:-moz-pre-wrap;white-space:-pre-wrap;white-space:-o-pre-wrap;word-wrap:break-word;width:95%}\n        th,td{font-family:arial,verdana,sans-serif;line-height:1.666}\n        img{ border:0}\n        header,footer,section,aside,article,nav,hgroup,figure,figcaption{display:block}\n        blockquote{margin-right:0px}\n    </style>\n</head>\n<body tabindex=\"0\" role=\"listitem\">\n<table width=\"700\" border=\"0\" align=\"center\" cellspacing=\"0\" style=\"width:700px;\">\n    <tbody>\n    <tr>\n        <td>\n            <div style=\"width:700px;margin:0 auto;border-bottom:1px solid #ccc;margin-bottom:30px;\">\n                <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"700\" height=\"39\" style=\"font:12px Tahoma, Arial, 宋体;\">\n                    <tbody><tr><td width=\"210\"></td></tr></tbody>\n                </table>\n            </div>\n            <div style=\"width:680px;padding:0 10px;margin:0 auto;\">\n                <div style=\"line-height:1.5;font-size:14px;margin-bottom:25px;color:#4d4d4d;\">\n                    <strong style=\"display:block;margin-bottom:15px;\">尊敬的用户：<span style=\"color:#f60;font-size: 16px;\"></span>您好！</strong>\n                    <strong style=\"display:block;margin-bottom:15px;\">\n                        您正在进行<span style=\"color: red\">找回密码</span>操作，请在验证码输入框中输入：<span style=\"color:#f60;font-size: 24px;text-decoration: underline;\">[code]</span>，以完成操作。\n                    </strong>\n                </div>\n                <div style=\"margin-bottom:30px;\">\n                    <small style=\"display:block;margin-bottom:20px;font-size:12px;\">\n                        <p style=\"color:#747474;\">\n                            注意：此操作可能会修改您的密码。如非本人操作，请及时登录并修改密码以保证帐户安全\n                            <br>（工作人员不会向你索取此验证码，请勿泄漏！)\n                        </p>\n                    </small>\n                </div>\n            </div>\n            <div style=\"width:700px;margin:0 auto;\">\n                <div style=\"padding:10px 10px 0;border-top:1px solid #ccc;color:#747474;margin-bottom:20px;line-height:1.3em;font-size:12px;\">\n                    <p>此为系统邮件，请勿回复<br>\n                        请保管好您的邮箱，避免账号被他人盗用\n                    </p>\n                </div>', '邮箱', '找回模板 变量：[code],[email]', 'editor');
INSERT INTO `forum_config` VALUES ('mail_template_register_success', NULL, '邮箱', '注册成功模板 变量：[email]', 'editor');

-- ----------------------------
-- Table structure for forum_tag
-- ----------------------------
DROP TABLE IF EXISTS `forum_tag`;
CREATE TABLE `forum_tag`  (
  `tag_id` int(11) NOT NULL AUTO_INCREMENT,
  `tag_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标签名',
  `tag_add_time` datetime NULL DEFAULT NULL COMMENT '标签添加时间',
  PRIMARY KEY (`tag_id`) USING BTREE,
  UNIQUE INDEX `tag_name`(`tag_name`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '标签表' ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for forum_topic
-- ----------------------------
DROP TABLE IF EXISTS `forum_topic`;
CREATE TABLE `forum_topic`  (
  `topic_id` int(11) NOT NULL AUTO_INCREMENT,
  `topic_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '话题名称',
  `topic_desc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '话题描述',
  `topic_add_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`topic_id`) USING BTREE,
  UNIQUE INDEX `topic_name`(`topic_name`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 15 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '话题表' ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for forum_user
-- ----------------------------
DROP TABLE IF EXISTS `forum_user`;
CREATE TABLE `forum_user`  (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户账号',
  `user_password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户密码(md5加密)',
  `user_email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户邮箱',
  `user_face` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '/assets/face.png' COMMENT '用户头像',
  `user_nick` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户昵称',
  `user_desc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户签名/描述',
  `user_status` int(1) NULL DEFAULT 1 COMMENT '用户状态 1正常 0禁言 -1删除了',
  `user_add_time` datetime NULL DEFAULT NULL COMMENT '用户注册时间',
  `user_op` int(1) NULL DEFAULT 0 COMMENT '是否是管理员 1是 0不是',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `user_name`(`user_name`) USING BTREE COMMENT '唯一的用户名'
) ENGINE = MyISAM AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of forum_user
-- ----------------------------
INSERT INTO `forum_user` VALUES (8, 'admin', '5881c9a8ef5781ae18835c6d40e0a320', '2426477553@qq.com', 'http://localhost:7000/upload/0369fece-6cb4-459a-9af3-0a6c5484cc9f.png', 'admin', '签名22222222222', 1, '2020-02-11 18:41:14', 1);

-- ----------------------------
-- Table structure for forum_user_notify
-- ----------------------------
DROP TABLE IF EXISTS `forum_user_notify`;
CREATE TABLE `forum_user_notify`  (
  `user_notify_id` int(11) NOT NULL AUTO_INCREMENT,
  `target` int(11) NULL DEFAULT NULL COMMENT '目标id(文章id/评论id)',
  `target_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '目标类型(article/comment/notice)',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户id(0全体用户，否则指定用户)',
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '通知内容',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`user_notify_id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 27 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for forum_user_notify_config
-- ----------------------------
DROP TABLE IF EXISTS `forum_user_notify_config`;
CREATE TABLE `forum_user_notify_config`  (
  `user_notify_config_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL COMMENT '0表示默认全局的',
  `user_notify_config_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '通知名称',
  `user_notify_config_value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '通知key值',
  `user_notify_config_notify` int(1) NULL DEFAULT 0 COMMENT '是否通知 1通知 0不通知',
  `user_notify_config_email` int(1) NULL DEFAULT 0 COMMENT '是否邮件通知 1通知 0不通知',
  PRIMARY KEY (`user_notify_config_id`) USING BTREE,
  INDEX `user_id`(`user_id`, `user_notify_config_value`) USING BTREE,
  INDEX `user_id_2`(`user_id`, `user_notify_config_name`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 19 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for forum_user_notify_read
-- ----------------------------
DROP TABLE IF EXISTS `forum_user_notify_read`;
CREATE TABLE `forum_user_notify_read`  (
  `user_notify_read_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_notify_id` int(11) NOT NULL COMMENT '提醒id',
  `user_id` int(11) NOT NULL COMMENT '阅读者id',
  `read_time` datetime NULL DEFAULT NULL COMMENT '阅读时间',
  PRIMARY KEY (`user_notify_read_id`) USING BTREE,
  UNIQUE INDEX `user_notify_id`(`user_notify_id`, `user_id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 34 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Fixed;


SET FOREIGN_KEY_CHECKS = 1;
