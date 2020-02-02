package cn.net.hlk.data.mapper;

import cn.net.hlk.data.pojo.Page;
import cn.net.hlk.data.pojo.PageData;

import java.util.List;

/**
 * @Title
 * @Description 疫情mapper
 * @author 张泽恒
 * @date 2020/2/2 14:47
 * @param
 * @return
 */
public interface YqcheckMapper {

	/**
	 * @Title insertOnsitePunishment
	 * @Description 疫情核查添加
	 * @author 张泽恒
	 * @date 2020/2/2 14:47
	 * @param [pd]
	 * @return void
	 */
	void insertYqcheck(PageData pd);

	/**
	 * @Title getpByT
	 * @Description 疫情导出
	 * @author 张泽恒
	 * @date 2020/2/2 17:49
	 * @param [pd]
	 * @return java.util.List<cn.net.hlk.data.pojo.PageData>
	 */
	List<PageData> getpByT(PageData pd);
}
