package cn.net.hlk.data.mapper;

import java.util.List;

import cn.net.hlk.data.pojo.Page;
import cn.net.hlk.data.pojo.PageData;

/**
 * @package: cn.net.hlk.data.mapper   
 * @Title: OnsitePunishmentMapper   
 * @Description:现场处罚mapper
 * @Company: hylink 
 * @author 张泽恒  
 * @date 2019年2月15日 下午3:39:51
 */
public interface OnsitePunishmentMapper {

	/**
	 * @Title: insertVehicle
	 * @discription 现场处罚添加
	 * @author 张泽恒       
	 * @created 2019年1月24日 上午10:16:22     
	 * @param pd
	 */
	void insertOnsitePunishment(PageData pd);

	/**
	 * @Title: updateVehicle
	 * @discription 现场处罚修改
	 * @author 张泽恒       
	 * @created 2019年1月24日 上午10:16:57     
	 * @param pd
	 */
	void updateOnsitePunishment(PageData pd);

	/**
	 * @Title: getVehiclePgListPage
	 * @discription 现场处罚列表查询
	 * @author 张泽恒       
	 * @created 2019年1月24日 上午10:17:52     
	 * @param page
	 * @return
	 */
	List<PageData> getOnsitePunishmentSearchPgListPage(Page page);
	
	/**
	 * @Title: getOnsitePunishmentByID
	 * @discription 根据现场处罚id 获取现场处罚信息
	 * @author 张泽恒       
	 * @created 2019年2月15日 下午4:23:10     
	 * @param pd
	 * @return
	 */
	PageData getOnsitePunishmentByID(PageData pd);

	/**
	 * @Title: updateOnsitePunishmentBatch
	 * @discription 现场处罚批量修改
	 * @author 张泽恒       
	 * @created 2019年2月18日 上午8:55:00     
	 * @param pd
	 */
	void updateOnsitePunishmentBatch(PageData pd);

	/**
	 * @Title: updateOnsitePunishmentBatch
	 * @discription 根据现场处罚id数组 获取现场处罚信息
	 * @author 张泽恒       
	 * @created 2019年2月18日 上午8:55:00     
	 * @param pd
	 */
	List<PageData> getPoliceOnsitePunishmentListByIdList(
			PageData pd);

	/**
	 * @Title: getOnsitePunishmentIdListPrint_jds0
	 * @discription 获取未上传任务
	 * @author 张泽恒       
	 * @created 2019年3月23日 上午11:02:33     
	 * @return
	 */
	List<String> getOnsitePunishmentIdListPrint_jds0();
}
