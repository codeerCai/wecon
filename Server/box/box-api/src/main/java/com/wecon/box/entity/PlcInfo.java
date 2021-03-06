package com.wecon.box.entity;

import java.sql.Timestamp;
/**
 * @author lanpenghui
 * 2017年8月1日
 */
public class PlcInfo {
/**
 * `PLC_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `DEVICE_ID` bigint(20) DEFAULT NULL COMMENT '设备ID',
  `TYPE` varchar(64) NOT NULL COMMENT '驱动名称',
  `DRIVER` varchar(64) NOT NULL COMMENT '驱动文件名',
  `BOX_STAT_NO` int(11) NOT NULL COMMENT '盒子站号',
  `PLC_STAT_NO` int(11) NOT NULL COMMENT 'PLC站号',
  `PORT` varchar(12) NOT NULL COMMENT '串口名称，例如：COM1',
  `COMTYPE` int(11) DEFAULT NULL,
  `BAUDRATE` varchar(12) DEFAULT NULL COMMENT '波特率',
  `STOP_BIT` int(11) DEFAULT NULL COMMENT '停止位',
  `DATA_LENGTH` int(11) DEFAULT NULL COMMENT '数据位',
  `CHECK_BIT` varchar(12) DEFAULT NULL COMMENT '校验位',
  `RETRY_TIMES` int(11) DEFAULT NULL COMMENT '重试次数',
  `WAIT_TIMEOUT` int(11) DEFAULT NULL COMMENT '等待超时 ms',
  `REV_TIMEOUT` int(11) DEFAULT NULL COMMENT '接收超时 ms',
  `COM_STEPINTERVAL` int(11) DEFAULT NULL COMMENT '连读长度',
  `COM_IODELAYTIME` int(11) DEFAULT NULL COMMENT '通讯延迟时间 ms',
  `RETRY_TIMEOUT` int(11) DEFAULT NULL COMMENT '重试超时 s',
  `NET_PORT` int(11) DEFAULT NULL COMMENT '端口',
  `NET_TYPE` int(11) DEFAULT NULL COMMENT '网络类型0:tcp_client 1:udp_client2:tcp_client_2n 3:udp_client_2n 4:tcp_server 5:udp_server',
  `NET_ISBROADCAST` int(11) DEFAULT NULL COMMENT '使用广播地址',
  `NET_BROADCASTADDR` int(11) DEFAULT NULL COMMENT '广播地址站号',
  `NET_IPADDR` varchar(64) DEFAULT NULL COMMENT 'IP地址',
  `STATE` int(11) DEFAULT NULL,
  `CREATE_DATE` datetime DEFAULT NULL,
  `UPDATE_DATE` datetime DEFAULT NULL,
 */
	public long plc_id;
	public long device_id;
	public String type;
	public String driver;
	public int box_stat_no;
	public int plc_stat_no;
	public String port;
	public int comtype;
	public String baudrate;
	public int  stop_bir;
	public int  data_length;
	public String  check_bit;
	public int  retry_times;
	public int  wait_timeout;
	public int  dev_timeout;
	public int  com_stepinterval;
	public int  com_iodelaytime;
	public int retry_timeout;
	public int net_port;
	public int net_type;
	public int net_isbroadcast;
	public int net_broadcastaddr;
	public String net_ipaddr;
	public int state;
	public Timestamp create_date;
	public Timestamp update_date;
	
}
