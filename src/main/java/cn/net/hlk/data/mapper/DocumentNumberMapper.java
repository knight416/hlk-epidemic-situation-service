package cn.net.hlk.data.mapper;

import java.util.List;

import cn.net.hlk.data.pojo.Page;
import cn.net.hlk.data.pojo.PageData;

/**
 * @package: cn.net.hlk.data.mapper   
 * @Title: DocumentNumberMapper   
 * @Description:文书编号核查mapper
 * @Company: hylink 
 * @author 张泽恒  
 * @date 2019年2月15日 上午11:39:26
 */
public interface DocumentNumberMapper {

	/**
	 * @Title: insertVehicle
	 * @discription 车辆信息添加
	 * @author 张泽恒       
	 * @created 2019年1月24日 上午10:16:22     
	 * @param pd
	 */
	void DocumentNumber(PageData pd);

	/**
	 * @Title: updateVehicle
	 * @discription 车辆信息修改
	 * @author 张泽恒       
	 * @created 2019年1月24日 上午10:16:57     
	 * @param pd
	 */
	void updateDocumentNumber(PageData pd);

	/**
	 * @Title: getVehiclePgListPage
	 * @discription 车辆列表查询
	 * @author 张泽恒       
	 * @created 2019年1月24日 上午10:17:52     
	 * @param page
	 * @return
	 */
	List<PageData> getDocumentNumberSearchPgListPage(Page page);

	/**
	 * @Title: verificationOnly
	 * @discription 验证区间是否唯一
	 * @author 张泽恒       
	 * @created 2019年2月15日 下午2:02:41     
	 * @param pd
	 * @return
	 */
	List<String> verificationOnly(PageData pd);

	/**
	 * @Title: insertContinuousDocumentNumber
	 * @discription 区间段文书编号添加
	 * @author 张泽恒       
	 * @created 2019年2月15日 下午2:17:49     
	 * @param pd
	 */
	void insertContinuousDocumentNumber(PageData pd);


}
