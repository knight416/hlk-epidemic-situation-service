package cn.net.hlk.data.service;

import cn.net.hlk.data.pojo.Page;
import cn.net.hlk.data.pojo.PageData;
import cn.net.hlk.data.pojo.ResponseBodyBean;

/**
 * @package: cn.net.hlk.data.service   
 * @Title: DocumentNumberService   
 * @Description:文书编号service
 * @Company: hylink 
 * @author 张泽恒  
 * @date 2019年2月15日 上午11:36:15
 */
public interface DocumentNumberService {

	/**
	 * @Title: insertContinuousDocumentNumber
	 * @discription 文书编号添加
	 * @author 张泽恒       
	 * @created 2019年2月15日 下午1:56:54     
	 * @param pd
	 * @return
	 */
	ResponseBodyBean insertContinuousDocumentNumber(PageData pd);

	/**
	 * @Title: getOnsitePunishmentListSearch
	 * @discription 文书编号列表查询
	 * @author 张泽恒       
	 * @created 2019年2月19日 下午2:02:51     
	 * @param page
	 * @return
	 */
	ResponseBodyBean getOnsitePunishmentListSearch(Page page);

	
}
