SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for alarm_rule
-- ----------------------------
DROP TABLE IF EXISTS `alarm_rule`;
CREATE TABLE `alarm_rule` (
  `rule_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `app_id` varchar(45) DEFAULT NULL,
  `uid` varchar(45) NOT NULL,
  `is_global` varchar(2) NOT NULL,
  `todo_type` varchar(4) NOT NULL,
  `config_args` varchar(2000) DEFAULT NULL,
  `create_time` datetime NOT NULL,
  `sts` varchar(2) DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`rule_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of alarm_rule
-- ----------------------------
INSERT INTO `alarm_rule` VALUES ('7', null, 'zhouyaliang', '0', '0', '{\"mailInfo\":{\"mailCc\":[\"zhouyaliang@yolo24.com\"],\"mailTo\":[\"zhouyaliang@yolo24.com\"]},\"period\":1}', '2016-07-25 17:53:31', 'A', '2016-07-25 17:53:32');
INSERT INTO `alarm_rule` VALUES ('12', 'Test', 'zhouyaliang', '0', '0', '{\"mailInfo\":{\"mailCc\":[\"zhouyaliang@yolo24.com\"],\"mailTo\":[\"zhouyaliang@yolo24.com\"]},\"period\":10}', '2016-07-26 16:39:06', 'D', '2016-07-26 16:39:06');


SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for application_info
-- ----------------------------
DROP TABLE IF EXISTS `application_info`;
CREATE TABLE `application_info` (
  `app_id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `uid` varchar(50) NOT NULL,
  `app_code` varchar(200) NOT NULL,
  `create_time` datetime DEFAULT NULL,
  `sts` varchar(2) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `app_desc` varchar(1024) DEFAULT NULL,
  `business_line` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`app_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of application_info
-- ----------------------------
INSERT INTO `application_info` VALUES ('1', '11', 'oms-order-view', '2016-06-27 15:49:49', 'A', '2016-06-27 15:49:49', 'oms系统', 'oms');
INSERT INTO `application_info` VALUES ('5', '11', 'oms-search-orders', '2016-06-27 19:30:15', 'A', '2016-06-27 19:30:15', 'oms-search-orders', 'oms');

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for auth_file_config
-- ----------------------------
DROP TABLE IF EXISTS `auth_file_config`;
CREATE TABLE `auth_file_config` (
  `config_id` int(11) NOT NULL,
  `config_key` varchar(200) NOT NULL,
  `value0` varchar(200) DEFAULT NULL,
  `value1` varchar(200) DEFAULT NULL,
  `value2` varchar(200) DEFAULT NULL,
  `value3` varchar(200) DEFAULT NULL,
  `value4` varchar(200) DEFAULT NULL,
  `key_desc` varchar(500) DEFAULT NULL,
  `sts` varchar(2) DEFAULT NULL,
  PRIMARY KEY (`config_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of auth_file_config
-- ----------------------------
INSERT INTO `auth_file_config` VALUES ('1', 'buriedpoint.printf', 'false', 'false', null, null, null, '是否打印数据', 'A');
INSERT INTO `auth_file_config` VALUES ('2', 'buriedpoint.max_exception_stack_length', '4000', '4000', null, null, null, '埋点异常的最大长度', 'A');
INSERT INTO `auth_file_config` VALUES ('3', 'buriedpoint.businesskey_max_length', '300', '300', null, null, null, '业务字段的最大长度', 'A');
INSERT INTO `auth_file_config` VALUES ('4', 'buriedpoint.exclusive_exceptions', '$exclusiveException', '$exclusiveException', null, null, null, '过滤异常', 'A');
INSERT INTO `auth_file_config` VALUES ('5', 'sender.connect_percent', '100', '100', null, null, null, '最大发送者的连接数阀比例', 'A');
INSERT INTO `auth_file_config` VALUES ('6', 'sender.servers_addr', '#servers_addr', '#servers_addr_1', null, null, null, '发送服务端配置', 'A');
INSERT INTO `auth_file_config` VALUES ('7', 'sender.max_copy_num', '2', '2', null, null, null, '最大发送的副本数量', 'A');
INSERT INTO `auth_file_config` VALUES ('8', 'sender.max_send_length', '20000', '20000', null, null, null, '发送的最大长度', 'A');
INSERT INTO `auth_file_config` VALUES ('9', 'sender.retry_get_sender_wait_interval', '2000', '2000', null, null, null, '当没有Sender时,尝试获取sender的等待周期', 'A');
INSERT INTO `auth_file_config` VALUES ('10', 'sender.is_off', 'false', 'false', null, null, null, '是否开启发送消息', 'A');
INSERT INTO `auth_file_config` VALUES ('11', 'consumer.max_consumer', '2', '2', null, null, null, '最大消费线程数', 'A');
INSERT INTO `auth_file_config` VALUES ('12', 'consumer.max_wait_time', '5', '5', null, null, null, '消费者最大等待时间', 'A');
INSERT INTO `auth_file_config` VALUES ('13', 'consumer.consumer_fail_retry_wait_interval', '50', '50', null, null, null, '发送失败等待时间  ', 'A');
INSERT INTO `auth_file_config` VALUES ('14', 'buffer.buffer_max_size', '18000', '18000', null, null, null, '每个Buffer的最大个数', 'A');
INSERT INTO `auth_file_config` VALUES ('15', 'buffer.pool_size', '5', '5', null, null, null, 'Buffer池的最大长度', 'A');
INSERT INTO `auth_file_config` VALUES ('16', 'senderchecker.check_polling_time', '200', '200', null, null, null, '发送检查线程检查周期', 'A');
INSERT INTO `auth_file_config` VALUES ('18', 'gtrace.charset', 'UTF-8', 'UTF-8', null, null, null, 'GTrace数据编码', 'A');
INSERT INTO `auth_file_config` VALUES ('19', 'gtrace.sdk_version', '1.0Final', '1.0Final', null, null, null, 'GTrace SDK的版本', 'A');
INSERT INTO `auth_file_config` VALUES ('20', 'buriedpoint.include_tablename', '%includeTablename', '%includeTablename', null, null, null, '过滤表名', 'A');

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for business_line
-- ----------------------------
DROP TABLE IF EXISTS `business_line`;
CREATE TABLE `business_line` (
  `bcode` varchar(200) NOT NULL,
  `bname` varchar(250) NOT NULL,
  `data_order` bigint(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `bdesc` varchar(1024) DEFAULT NULL,
  `operator` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`bcode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for system_config
-- ----------------------------
DROP TABLE IF EXISTS `system_config`;
CREATE TABLE `system_config` (
  `config_id` int(10) unsigned NOT NULL,
  `conf_key` varchar(200) NOT NULL,
  `conf_value` varchar(500) NOT NULL,
  `val_type` varchar(45) NOT NULL,
  `val_desc` varchar(200) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `sts` varchar(2) NOT NULL,
  `modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`config_id`),
  UNIQUE KEY `conf_key_UNIQUE` (`conf_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of system_config
-- ----------------------------
INSERT INTO `system_config` VALUES ('1000', 'mail_info', '{\"mail.host\":\"mail.asiainfo.com\",\"mail.transport.protocol\":\"smtp\",\"mail.smtp.auth\":\"true\",\"mail.smtp.starttls.enable\":\"false\",\"mail.username\":\"testA\",\"mail.password\":\"******\",\"mail.sender\":\"yushuqiang@yolo24.com\"}', 'json', '默认邮件发送人信息', '2015-12-10 11:54:06', 'A', '2015-12-10 11:54:06');
INSERT INTO `system_config` VALUES ('1001', 'portal_addr', 'http://10.58.56.158:8090/GTrace/', 'string', '默认门户地址', '2015-12-10 15:23:53', 'A', '2015-12-10 15:23:53');
INSERT INTO `system_config` VALUES ('1002', 'servers_addr', '10.58.56.158:35000;10.58.72.45:35000;', 'string', '日志采集地址', '2015-12-10 15:23:53', 'A', '2015-12-10 15:23:53');
INSERT INTO `system_config` VALUES ('1003', 'servers_addr_1', 'XXX.XXX.XXX.XXX:34000;', 'string', '日志采集地址-外网', '2015-12-10 15:23:53', 'A', '2015-12-10 15:23:53');
INSERT INTO `system_config` VALUES ('1004', 'alarm_type_info', '[{\"type\":\"default\",\"label\":\"exception\",\"desc\":\"System Exception\"},{\"type\":\"ExecuteTime-PossibleError\",\"label\":\"remark\",\"desc\":\"Excution Time > 5s\"},{\"type\":\"ExecuteTime-Warning\",\"label\":\"remark\",\"desc\":\"Excution Time > 500ms\"}]', 'json', '告警类型', '2016-04-18 16:04:51', 'A', '2016-04-18 16:04:53');
