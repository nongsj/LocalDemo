package com.layui.util;

import com.github.pagehelper.PageInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 结果返回处理工具类
 * @author lgf 2017-09-20
 * @version 1.0
 *
 */
public class ResultUtil {
	/**
	 * 结果装载接口
	 * @param r 枚举返回状体码
	 * @param result 返回的数据结果hashmap
	 * @return 规范化数据结果返回map
	 */
	public static Map<String, Object> combinationResult(ResultStatus r, Object result){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", r.getCode());
		map.put("msg", r.getMsg());
		map.put("result", result);
		SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
		map.put("date", dateFormater.format(new Date()));
		return map;
	}
	
	/**
	 * 图例数据转换接口
	 * @param list 图例查询结果
	 * @return 结果集合
	 */
//	public static List<Map<String, Object>> ConvertLegends(List<Map<String, Object>> list){
//		for (Map<String, Object> map : list) {
//			String legends = (String)map.get("legends");
//			List<Legend> legendsData = JsonHelper.LegedsFromJsonString(legends);
//			map.put("legends", legendsData);
//		}
//		return list;
//	}
	
	/**
	 * 图例数据转换接口
	 * @param list 图例查询结果
	 * @return 结果集合
	 */
//	public static List<Map<String, Object>> ConvertAttrs(List<Map<String, Object>> list){
//		for (Map<String, Object> map : list) {
//			String sublayers = (String)map.get("sublayers");
//			List<SubLayer> attrsData = JsonHelper.AttrsFromJsonString(sublayers);
//			map.put("sublayers", attrsData);
//		}
//		return list;
//	}
	
	/**
	 * 专题数据转换接口
	 * @param list 专题数据查询结果
	 * @return 结果集合
	 */
//	public static List<Map<String, Object>> ConvertSpecial(List<Map<String, Object>> list){
//		for (Map<String, Object> map : list) {
//			String items = (String)map.get("items");
//			List<SpecialItem> SpecialData = JsonHelper.SpecialFromJsonString(items);
//			map.put("items", SpecialData);
//		}
//		return list;
//	}
	
	/**
	 * 根据专题父id进行专题数据筛选
	 * @param pid 专题父id
	 * @param list 专题集合
	 * @return 过滤集合
	 */
	public static List<Map<String, Object>> combinationSpecial(int pid, List<Map<String, Object>> list){
		List<Map<String, Object>> new_list = new ArrayList<Map<String,Object>>();
		for (Map<String, Object> map : list) {
			int spid = (int)map.get("proid");
			if(pid == spid){
				new_list.add(map);
			}
		}
		return new_list;
	}
	
	/**
	 * 统计查询实体类和配置json转换
	 * @param list 查询数据集合
	 * @return 转换结果集合
	 */
//	public static List<ConfigChart> combinationChart(List<Map<String, Object>> list){
//		List<ConfigChart> new_list = new ArrayList<ConfigChart>();
//		new_list.add(StaticAttr.CHART_FIRST_SELECT);
//		for (Map<String, Object> map : list) {
//			ConfigChart chart = new ConfigChart();
//			chart.setName((String)map.get("label"));
//			chart.setKey((String)map.get("key"));
//			chart.setTable((String)map.get("tab"));
//			chart.setFields((String)map.get("chartfield")+","+(String)map.get("chartalias"));
//			new_list.add(chart);
//		}
//		return new_list;
//	}
	/**
	 * 结果装载接口
	 * @param r 枚举返回状体码
	 * @param result 返回的数据结果hashmap
	 * @return 规范化数据结果返回map
	 */
	public static Map<String, Object> combinationRows(ResultStatus r, Object result, PageInfo<?> page){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", r.getCode());
		map.put("msg", r.getMsg());
		map.put("rows", result);
		map.put("page", page==null?"":page);
		map.put("total", page==null?"":page.getTotal());
		SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		map.put("date", dateFormater.format(new Date()));
		return map;
	}
}
