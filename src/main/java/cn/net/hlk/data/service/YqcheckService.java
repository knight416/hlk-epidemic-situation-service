package cn.net.hlk.data.service;

import cn.net.hlk.data.pojo.PageData;
import cn.net.hlk.data.pojo.ResponseBodyBean;

import javax.servlet.http.HttpServletResponse;

/**
 * @Title
 * @Description 疫情核查
 * @author 张泽恒
 * @date 2020/2/2 14:44
 * @param
 * @return
 */
public interface YqcheckService {


	/**
	 * @Title insertYqcheck
	 * @Description 疫情核查添加
	 * @author 张泽恒
	 * @date 2020/2/2 14:44
	 * @param [pd]
	 * @return cn.net.hlk.data.pojo.ResponseBodyBean
	 */
	ResponseBodyBean insertYqcheck(PageData pd);

	/**
	 * @Title PersonalExport
	 * @Description 人员导出
	 * @author 张泽恒
	 * @date 2020/2/2 17:28
	 * @param [pd]
	 * @param response
	 * @return cn.net.hlk.data.pojo.PageData
	 */
	PageData PersonalExport(PageData pd, HttpServletResponse response);
}
