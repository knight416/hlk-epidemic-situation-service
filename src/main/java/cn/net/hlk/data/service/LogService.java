package cn.net.hlk.data.service;

import java.util.List;

import cn.net.hlk.data.pojo.Page;
import cn.net.hlk.data.pojo.PageData;

public interface LogService {
	
	/**
	 * @Title: getLogListForMenuPgListPage
	 * @discription 日志数据列表查询
	 * @author mxd     
	 * @param pd
	 * @return
	 */
	List<PageData> queryLogPgListPage(Page page);

}
