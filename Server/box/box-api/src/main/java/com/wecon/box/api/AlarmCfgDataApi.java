package com.wecon.box.api;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.stereotype.Component;
import com.wecon.box.entity.AlarmCfgData;
import com.wecon.box.entity.Page;
import com.wecon.box.filter.AlarmCfgDataFilter;

/**
 * @author lanpenghui 2017年8月1日
 */
@Component
public interface AlarmCfgDataApi {
	/**
	 * 保存报警数据
	 *
	 * @param model
	 * @return
	 */
	public long SaveAlarmCfgData(AlarmCfgData model);

	/**
	 * 批量保存报警数据
	 * 
	 * @param listmodel
	 * @return
	 */
	public void SaveAlarmCfgData(List<AlarmCfgData> listmodel);

	/**
	 * 根据alarm_cfg_id取列表报警数据
	 *
	 * @param alarm_cfg_id
	 * @return
	 */
	public List<AlarmCfgData> getAlarmCfgData(long alarm_cfg_id);

	/**
	 * 根据alarm_cfg_id,monitor_time取某个报警数据配置
	 *
	 * @param alarm_cfg_id
	 * @return
	 */
	public AlarmCfgData getAlarmCfgData(long alarm_cfg_id, Timestamp monitor_time);

	/**
	 * 根据alarm_cfg_id删除某个报警数据
	 * 
	 * @param alarm_cfg_id
	 * 
	 */
	public void delAlarmCfgData(long alarm_cfg_id);

	/**
	 * 分页查询报警数据
	 * 
	 * @param filter
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	Page<AlarmCfgData> getRealHisCfgDataList(AlarmCfgDataFilter filter, int pageIndex, int pageSize);
}
