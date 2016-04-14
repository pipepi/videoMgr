show variables like '%sche%';
set global event_scheduler = 1;
CREATE EVENT if not exists  `deleteMoreMonthFlowLog` 
	ON SCHEDULE
		EVERY 1 DAY STARTS '2016-04-01 23:53:37'
	ON COMPLETION PRESERVE
	ENABLE
	COMMENT '删除超过一个月的流量日志数据'
	DO BEGIN
delete from t_sysmgr_flow_log where create_time<date_sub(now(),interval 1 month);
END