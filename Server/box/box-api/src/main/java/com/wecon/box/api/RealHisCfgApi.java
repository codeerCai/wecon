package com.wecon.box.api;

import java.util.List;

import org.springframework.stereotype.Component;
import com.wecon.box.entity.Page;
import com.wecon.box.entity.RealHisCfg;
import com.wecon.box.filter.RealHisCfgFilter;
/**
 * @author lanpenghui
 * 2017年8月1日
 */
@Component
public interface RealHisCfgApi {

	/**
	 * 保存历史（实时）数据配置信息
	 * 
	 * @param model
	 * @return
	 */
	public long saveRealHisCfg(RealHisCfg model);

	/**
	 * 更新历史（实时）数据配置信息
	 * 
	 * @param model
	 * @return
	 */
	public boolean updateRealHisCfg(RealHisCfg model);

	/**
	 * 获取某条历史（实时）数据配置信息
	 * 
	 * @param id
	 * @return
	 */
	public RealHisCfg getRealHisCfg(long id);
	/**
	 * 通过通讯口id和启用状态获取列表历史（实时）数据配置信息
	 * @param plc_id
	 * @param state
	 * @return
	 */
	public List<RealHisCfg> getRealHisCfg(long plc_id,int state);
	
	
	/**
	 * 删除某个id的历史（实时）数据配置信息
	 * @param id
	 */
	public void delRealHisCfg(long id);
	

	/**
	 * 获取某条历史（实时）数据配置的分页列表
	 * 
	 * @param filter
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	Page<RealHisCfg> getRealHisCfgList(RealHisCfgFilter filter, int pageIndex, int pageSize);

}
