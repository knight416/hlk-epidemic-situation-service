package cn.net.hlk.data.service;

import cn.net.hlk.data.pojo.Page;
import cn.net.hlk.data.pojo.PageData;
import cn.net.hlk.data.pojo.ResponseBodyBean;

/**
 * @package: cn.net.hlk.data.service   
 * @Title: OnsitePunishmentService   
 * @Description:现场处罚管理
 * @Company: hylink 
 * @author 张泽恒  
 * @date 2019年2月15日 下午3:38:25
 */
public interface OnsitePunishmentService {

	/**
	 * @Title: getOnsitePunishmentListSearch
	 * @discription 现场处罚列表查询
	 * @author 张泽恒       
	 * @created 2019年2月15日 下午5:08:01     
	 * @param page
	 * @return
	 */
	ResponseBodyBean getOnsitePunishmentListSearch(Page page);

	/**
	 * @Title: deleteOnsitePunishment
	 * @discription 现场处罚删除
	 * @author 张泽恒       
	 * @created 2019年2月16日 上午8:52:23     
	 * @param pd
	 * @return
	 */
	ResponseBodyBean deleteOnsitePunishment(PageData pd);

	/**
	 * @Title: insertOnsitePunishment
	 * @discription 现场处罚添加
	 * @author 张泽恒       
	 * @created 2019年2月16日 下午2:04:29     
	 * @param pd
	 * @return
	 */
	ResponseBodyBean insertOnsitePunishment(PageData pd);

	/**
	 * @Title: uploadOnsitePunishment
	 * @discription 现场处罚上传
	 * @author 张泽恒       
	 * @created 2019年2月18日 上午8:36:18     
	 * @param pd
	 * @return
	 */
	ResponseBodyBean uploadOnsitePunishment(PageData pd);
	
	/**
	 * @Title: sendOutUrl
	 * @discription 服务请求
	 * @author 张泽恒       
	 * @created 2019年1月26日 上午10:16:54     
	 * @param pd
	 * @return
	 */
	PageData sendOutUrl(String data,String url);

	/**
	 * @Title: onsitePunishmentDetails
	 * @discription 现场处罚详情
	 * @author 张泽恒       
	 * @created 2019年2月18日 上午10:46:53     
	 * @param pd
	 * @return
	 */
	ResponseBodyBean onsitePunishmentDetails(PageData pd);

}
