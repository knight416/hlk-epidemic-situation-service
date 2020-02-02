package cn.net.hlk.data.service.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import cn.net.hlk.data.mapper.OnsitePunishmentMapper;
import cn.net.hlk.data.pojo.Page;
import cn.net.hlk.data.pojo.PageData;
import cn.net.hlk.data.pojo.ReasonBean;
import cn.net.hlk.data.pojo.ResponseBodyBean;
import cn.net.hlk.data.service.OnsitePunishmentService;
import cn.net.hlk.util.CustomConfigUtil;
import cn.net.hlk.util.ResponseUtil;
import cn.net.hlk.util.StringUtil2;
import cn.net.hlk.util.UuidUtil;

import com.alibaba.fastjson.JSON;

/**
 * @package: cn.net.hlk.data.service.serviceimpl   
 * @Title: OnsitePunishmentServiceImpl   
 * @Description:现场处罚实现类
 * @Company: hylink 
 * @author 张泽恒  
 * @date 2019年2月15日 下午3:39:10
 */
@Service
public class OnsitePunishmentServiceImpl extends BaseServiceImple implements OnsitePunishmentService {

	@Autowired
	private OnsitePunishmentMapper onsitePunishmentMapper;
	@Autowired
	RestTemplate rest;

	/**
	 * @Title: getOnsitePunishmentListSearch
	 * @discription 现场处罚查询
	 * @author 张泽恒       
	 * @created 2019年2月15日 下午5:11:01      
	 * @param page
	 * @return     
	 * @see cn.net.hlk.data.service.OnsitePunishmentService#getOnsitePunishmentListSearch(cn.net.hlk.data.pojo.Page)
	 */
	@Override
	public ResponseBodyBean getOnsitePunishmentListSearch(Page page) {
		ResponseBodyBean responseBodyBean = new ResponseBodyBean();//返回值
		ReasonBean reasonBean = new ReasonBean();//返回参数
		PageData resData = new PageData();//返回数据
		try {
			List<PageData> pdList = new ArrayList<PageData>();
			pdList = onsitePunishmentMapper.getOnsitePunishmentSearchPgListPage(page);
			
			resData.put("list", pdList);
			resData.put("page", page);
			responseBodyBean.setResult(resData);
		} catch (Exception e) {
			e.printStackTrace();
			reasonBean = ResponseUtil.getReasonBean("Exception", e.getClass().getSimpleName());
			responseBodyBean.setReason(reasonBean);
		}
		return responseBodyBean;
	}

	/**
	 * @Title: deleteOnsitePunishment
	 * @discription 现场处罚删除
	 * @author 张泽恒       
	 * @created 2019年2月16日 上午8:52:58      
	 * @param pd
	 * @return     
	 * @see cn.net.hlk.data.service.OnsitePunishmentService#deleteOnsitePunishment(cn.net.hlk.data.pojo.PageData)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
	public ResponseBodyBean deleteOnsitePunishment(PageData pd) {
		ResponseBodyBean responseBodyBean = new ResponseBodyBean();//返回值
		ReasonBean reasonBean = new ReasonBean();//返回参数
		PageData resData = new PageData();//返回数据
		try {
			List<PageData> pdList = new ArrayList<PageData>();
			pd.put("visibale", 0);
			onsitePunishmentMapper.updateOnsitePunishment(pd);
			
			responseBodyBean.setResult(resData);
		} catch (Exception e) {
			e.printStackTrace();
			reasonBean = ResponseUtil.getReasonBean("Exception", e.getClass().getSimpleName());
			responseBodyBean.setReason(reasonBean);
		}
		return responseBodyBean;
	}

	/**
	 * @Title: insertOnsitePunishment
	 * @discription 现场处罚添加
	 * @author 张泽恒       
	 * @created 2019年2月16日 下午2:05:48      
	 * @param pd
	 * @return     
	 * @see cn.net.hlk.data.service.OnsitePunishmentService#insertOnsitePunishmentEasy(cn.net.hlk.data.pojo.PageData)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
	public ResponseBodyBean insertOnsitePunishment(PageData pd) {
		ResponseBodyBean responseBodyBean = new ResponseBodyBean();//返回值
		ReasonBean reasonBean = new ReasonBean();//返回参数
		PageData resData = new PageData();//返回数据
		try {
			PageData onsitePunishmentPd = new PageData();
			//处理参数 拼装现场处罚pojo
			//解析参数
			//违法人信息
			PageData idcardInfo = JSON.parseObject(JSON.toJSONString(pd.get("idcardInfo")),PageData.class);
			//文书编号信息
			PageData titleInfo = JSON.parseObject(JSON.toJSONString(pd.get("titleInfo")),PageData.class);
			//违法信息
			PageData baseInfo = JSON.parseObject(JSON.toJSONString(pd.get("baseInfo")),PageData.class);
			//上传信息
			PageData upload = JSON.parseObject(JSON.toJSONString(baseInfo.get("upload")),PageData.class);
			//处罚类型
			String dataType = pd.get("dataType").toString();
			Integer punishment_type = 0;
			if("40".equals(dataType) || "43".equals(dataType)){
				punishment_type = 1;
			}else{
				punishment_type = 0;
			}
			pd.put("onsite_punishment_id", pd.getString("optargetId"));
			PageData onlyOne = onsitePunishmentMapper.getOnsitePunishmentByID(pd);
			
			if(idcardInfo != null && titleInfo != null && baseInfo != null && upload != null 
					&& StringUtil2.isNotEmpty(dataType) && StringUtil2.isNotEmpty(titleInfo.getString("wsbh"))
					&& StringUtil2.isNotEmpty(upload.getString("zqmj")) && StringUtil2.isNotEmpty(idcardInfo.getString("idcard"))
					&& onlyOne == null
					){
				//拼装二级
				PageData punishment_police = new PageData(); //处罚民警信息
				punishment_police.put("policeName", pd.getString("policeName"));
				punishment_police.put("policeIdcard", pd.getString("policeIdcard"));
				punishment_police.put("policeCode", pd.getString("policeCode"));
				punishment_police.put("policeUnit", pd.getString("policeUnit"));
				punishment_police.put("policeUnitCode", pd.getString("policeUnitCode"));
				
				PageData qzcs = new PageData(); //强制措施信息
				qzcs.put("qzcslx", upload.get("qzcslx"));//强制措施类型
				qzcs.put("klwpcfd", upload.get("klwpcfd"));//扣留物品存放地
				qzcs.put("sjxm", upload.get("sjxm"));//收缴项目
				qzcs.put("sjwpmc", upload.get("sjwpmc"));//收缴物品名称
				qzcs.put("sjwpcfd", upload.get("sjwpcfd"));//收缴物品存放地
				
				PageData wfqy = new PageData(); //违法区域信息
				wfqy.put("xzqh", upload.get("xzqh"));//行政区划
				wfqy.put("wfdd", upload.get("wfdd"));//违法地点
				wfqy.put("lddm", upload.get("lddm"));//路段号码
				wfqy.put("ddms", upload.get("ddms"));//地点米数
				wfqy.put("wfdz", upload.get("wfdz"));//违法地址
				
				PageData wfxw = new PageData(); //违法行为信息
				wfxw.put("wfxw", upload.get("wfxw"));//违法行为
				wfxw.put("scz", upload.get("scz"));//实测值
				wfxw.put("bzz", upload.get("bzz"));//标准值
				wfxw.put("wfxw1", upload.get("wfxw1"));//违法行为
				wfxw.put("scz1", upload.get("scz1"));//实测值
				wfxw.put("bzz1", upload.get("bzz1"));//标准值
				if(StringUtil2.isEmpty(upload.get("wfxw"))){
					wfxw.put("wfxw", upload.get("wfxw1"));//违法行为
					wfxw.put("scz", upload.get("scz1"));//实测值
					wfxw.put("bzz", upload.get("bzz1"));//标准值
				}
				wfxw.put("wfxw2", upload.get("wfxw2"));//违法行为
				wfxw.put("scz2", upload.get("scz2"));//实测值
				wfxw.put("bzz2", upload.get("bzz2"));//标准值
				wfxw.put("wfxw3", upload.get("wfxw3"));//违法行为
				wfxw.put("scz3", upload.get("scz3"));//实测值
				wfxw.put("bzz3", upload.get("bzz3"));//标准值
				wfxw.put("wfxw4", upload.get("wfxw4"));//违法行为
				wfxw.put("scz4", upload.get("scz4"));//实测值
				wfxw.put("bzz4", upload.get("bzz4"));//标准值
				wfxw.put("wfxw5", upload.get("wfxw5"));//违法行为
				wfxw.put("scz5", upload.get("scz5"));//实测值
				wfxw.put("bzz5", upload.get("bzz5"));//标准值
				
				PageData cfxx = new PageData(); //处罚信息
				cfxx.put("cfzl", upload.get("cfzl"));//处罚种类
				cfxx.put("fkje", upload.get("fkje"));//罚款金额
				cfxx.put("jkfs", upload.get("jkfs"));//交款方式
				cfxx.put("jkbj", upload.get("jkbj"));//交款标记
				cfxx.put("jkrq", upload.get("jkrq"));//交款日期
				cfxx.put("clsj", upload.get("clsj"));//处理时间
				
				PageData other_message = new PageData(); //其他信息
				cfxx.put("jd", upload.get("jd"));//经度
				cfxx.put("wd", upload.get("wd"));//纬度
				cfxx.put("Zxbh", upload.get("Zxbh"));//证芯编号后六位
				cfxx.put("Sfzdry", upload.get("Sfzdry"));//是否指导人员
				cfxx.put("optargetId", pd.get("optargetId"));//档案编号
				cfxx.put("titleInfo", titleInfo);//文书编号信息
				
				PageData illegal_vehicle_information = new PageData(); //违法车辆信息
				illegal_vehicle_information.put("hpzl", upload.get("hpzl"));//号牌种类
				illegal_vehicle_information.put("hphm", upload.get("hphm"));//号牌号码
				illegal_vehicle_information.put("jdcsyr", upload.get("jdcsyr"));//机动车所有人
				illegal_vehicle_information.put("fdjh", upload.get("fdjh"));//发动机号
				illegal_vehicle_information.put("clsbdh", upload.get("clsbdh"));//车辆识别代号
				illegal_vehicle_information.put("syxz", upload.get("syxz"));//使用性质
				//额外自定数据
				illegal_vehicle_information.put("wzsm", baseInfo.get("wzsm"));//违章说明
				illegal_vehicle_information.put("hpzlText", baseInfo.get("hpzlText"));//车辆类型
				illegal_vehicle_information.put("wfddText", baseInfo.get("wfddText"));//违法地点text
				illegal_vehicle_information.put("lddmText", baseInfo.get("lddmText"));//路段代码text
				illegal_vehicle_information.put("beizhu", baseInfo.get("beizhu"));//备注
				illegal_vehicle_information.put("phone", baseInfo.get("phone"));//电话
				illegal_vehicle_information.put("address", baseInfo.get("address"));//地址
				illegal_vehicle_information.put("hpys", baseInfo.get("hpys"));//号牌颜色
				
				PageData illegal_driver_information = new PageData(); //违法人员信息
				illegal_driver_information.put("ryfl", upload.get("ryfl"));//人员分类
				upload.put("jszh", idcardInfo.get("idcard"));
				illegal_driver_information.put("jszh", upload.get("jszh"));//驾驶证号
				illegal_driver_information.put("dabh", upload.get("dabh"));//档案编号
				illegal_driver_information.put("fzjg", upload.get("fzjg"));//发证机关
				illegal_driver_information.put("zjcx", upload.get("zjcx"));//准驾车型
				upload.put("dsr", idcardInfo.get("name"));
				illegal_driver_information.put("dsr", upload.get("dsr"));//当事人
				illegal_driver_information.put("zsxzqh", upload.get("zsxzqh"));//住所行政区划
				illegal_driver_information.put("zsxxdz", upload.get("zsxxdz"));//住所详细地址
				illegal_driver_information.put("dh", upload.get("dh"));//电话
				illegal_driver_information.put("lxfs", upload.get("lxfs"));//联系方式
				illegal_driver_information.put("idcardInfo",idcardInfo);//违法人员信息
				
//				String onsite_punishment_id = UuidUtil.get32UUID();//现场处罚id
				String onsite_punishment_id = pd.getString("optargetId");//现场处罚id
				
				//拼装pojo
				onsitePunishmentPd.put("onsite_punishment_id",onsite_punishment_id);//现场处罚id
				onsitePunishmentPd.put("punishment_police",JSON.toJSONString(punishment_police));//处罚民警信息
				onsitePunishmentPd.put("punishment_type",punishment_type);//处罚类型
				if("40".equals(dataType)){
					upload.put("pzbh", titleInfo.getString("wsbh"));
					onsitePunishmentPd.put("jdsbh",upload.get("pzbh"));//决定书编号
					onsitePunishmentPd.put("pzbh",upload.get("pzbh"));//凭证编号
				}else{
					upload.put("jdsbh", titleInfo.getString("wsbh"));
					onsitePunishmentPd.put("jdsbh",upload.get("jdsbh"));//决定书编号
					onsitePunishmentPd.put("pzbh",upload.get("jdsbh"));//凭证编号
					punishment_type = 0;
				}
				
				onsitePunishmentPd.put("wslb",upload.get("wslb"));//文书类别
				onsitePunishmentPd.put("qzcs",JSON.toJSONString(qzcs));//强制措施信息
				onsitePunishmentPd.put("wfqy",JSON.toJSONString(wfqy));//违法区域信息
				onsitePunishmentPd.put("wfxw",JSON.toJSONString(wfxw));//违法行为信息
				onsitePunishmentPd.put("jtfs",upload.get("jtfs"));//交通方式
				onsitePunishmentPd.put("wfsj",upload.get("wfsj"));//违法时间
				onsitePunishmentPd.put("zqmj",upload.get("zqmj"));//执勤民警
				onsitePunishmentPd.put("fxjg",upload.get("fxjg"));//发现机关
				onsitePunishmentPd.put("cfxx",JSON.toJSONString(cfxx));//处罚信息
				onsitePunishmentPd.put("jsjqbj",upload.get("jsjqbj"));//拒收拒签标记
				onsitePunishmentPd.put("sgdj",upload.get("sgdj"));//事故等级
				onsitePunishmentPd.put("other_message",JSON.toJSONString(other_message));//其他信息
				onsitePunishmentPd.put("gps_point","");//位置gps
				onsitePunishmentPd.put("print_jds",1);//是否打印决定书 0否 1是
				onsitePunishmentPd.put("updateuser",pd.getString("policeName"));//修改人
				onsitePunishmentPd.put("illegal_vehicle_information",JSON.toJSONString(illegal_vehicle_information));//违法车辆信息
				onsitePunishmentPd.put("illegal_driver_information",JSON.toJSONString(illegal_driver_information));//违法人员信息
				onsitePunishmentPd.put("upload_message",JSON.toJSONString(upload));//前端上传信息
				
				onsitePunishmentMapper.insertOnsitePunishment(onsitePunishmentPd);
				
				List<String> onsitePunishmentIdList = new ArrayList<String>();
				onsitePunishmentIdList.add(onsite_punishment_id);
				List<PageData> policeOnsitePunishmentList = onsitePunishmentMapper.getPoliceOnsitePunishmentListByIdList(new PageData("onsitePunishmentIdList",onsitePunishmentIdList));
				pd.put("onsitePunishmentIdList", onsitePunishmentIdList);
				//直接进行上传
				if(policeOnsitePunishmentList != null && policeOnsitePunishmentList.size() > 0){
					//访问local服务中间件 上传6合一
					String serverUri = CustomConfigUtil.getString("local.serverUri");
					PageData pdC = new PageData();
					pdC.put("policeOnsitePunishmentList",policeOnsitePunishmentList);//上传信息
					serverUri = serverUri+"/returnXCCFData";
					PageData companypd = sendOutUrl(JSON.toJSONString(pdC),serverUri);
//					resData.put("companypd", companypd);
					//获取请求返回值数据
					if(companypd != null && StringUtil2.isNotEmpty(companypd.get("result"))){
						logger.info("访问local返回结果 ------ "+companypd.toString());
						//获取成功idlist 修改状态
						PageData result = JSON.parseObject(JSON.toJSONString(companypd.get("result")), PageData.class);
						PageData data = JSON.parseObject(JSON.toJSONString(result.get("data")), PageData.class);
						onsitePunishmentIdList = JSON.parseArray(JSON.toJSONString(data.get("onsitePunishmentIdList")), String.class);
						if(onsitePunishmentIdList != null && onsitePunishmentIdList.size() > 0){
							//修改上传失败数据
							pd.put("print_jds",0);
							pd.put("onsitePunishmentIdList", onsitePunishmentIdList);
							onsitePunishmentMapper.updateOnsitePunishmentBatch(pd);
						}
//						resData.put("successCount", data.get("successCount"));//成功数量
//						resData.put("failureCount", data.get("failureCount"));//失败数量
						logger.error("上传错误信息数据"+data.get("onsitePunishmentIdList").toString());
						logger.error("上传错误信息详情 "+data.get("failureMessageList").toString());
					}else{
						//请求结果异常 回滚上传记录改变
						pd.put("print_jds",0);
						onsitePunishmentMapper.updateOnsitePunishmentBatch(pd);
					}
				}
				
				
			}else{
				reasonBean.setCode("400");
				reasonBean.setText("请求的参数不正确");
				responseBodyBean.setReason(reasonBean);
			}
			
			responseBodyBean.setResult(resData);
		} catch (Exception e) {
			e.printStackTrace();
			reasonBean = ResponseUtil.getReasonBean("Exception", e.getClass().getSimpleName());
			responseBodyBean.setReason(reasonBean);
		}
		return responseBodyBean;
	}

	/**
	 * @Title: uploadOnsitePunishment
	 * @discription 现场处罚上传
	 * @author 张泽恒       
	 * @created 2019年2月18日 上午8:36:37      
	 * @param pd
	 * @return     
	 * @see cn.net.hlk.data.service.OnsitePunishmentService#uploadOnsitePunishment(cn.net.hlk.data.pojo.PageData)
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
	public ResponseBodyBean uploadOnsitePunishment(PageData pd) {
		ResponseBodyBean responseBodyBean = new ResponseBodyBean();//返回值
		ReasonBean reasonBean = new ReasonBean();//返回参数
		PageData resData = new PageData();//返回数据
		try {
			//批量修改 状态改为上传
			pd.put("print_jds",1);
			onsitePunishmentMapper.updateOnsitePunishmentBatch(pd);
			List<String> onsitePunishmentIdList = JSON.parseArray(JSON.toJSONString(pd.get("onsitePunishmentIdList")), String.class);
			List<PageData> policeOnsitePunishmentList = onsitePunishmentMapper.getPoliceOnsitePunishmentListByIdList(pd);
			if(policeOnsitePunishmentList != null && policeOnsitePunishmentList.size() > 0){
				//访问local服务中间件 上传6合一
				String serverUri = CustomConfigUtil.getString("local.serverUri");
				PageData pdC = new PageData();
				pdC.put("policeOnsitePunishmentList",policeOnsitePunishmentList);//上传信息
				serverUri = serverUri+"/returnXCCFData";
				PageData companypd = sendOutUrl(JSON.toJSONString(pdC),serverUri);
				resData.put("companypd", companypd);
				//获取请求返回值数据
				if(companypd != null && StringUtil2.isNotEmpty(companypd.get("result"))){
					logger.info("访问local返回结果 ------ "+companypd.toString());
					//获取成功idlist 修改状态
					PageData result = JSON.parseObject(JSON.toJSONString(companypd.get("result")), PageData.class);
					PageData data = JSON.parseObject(JSON.toJSONString(result.get("data")), PageData.class);
					onsitePunishmentIdList = JSON.parseArray(JSON.toJSONString(data.get("onsitePunishmentIdList")), String.class);
					if(onsitePunishmentIdList != null && onsitePunishmentIdList.size() > 0){
						//修改上传失败数据
						pd.put("print_jds",0);
						pd.put("onsitePunishmentIdList", onsitePunishmentIdList);
						onsitePunishmentMapper.updateOnsitePunishmentBatch(pd);
					}
					resData.put("successCount", data.get("successCount"));//成功数量
					resData.put("failureCount", data.get("failureCount"));//失败数量
					logger.error("上传错误信息数据"+data.get("onsitePunishmentIdList").toString());
					logger.error("上传错误信息详情 "+data.get("failureMessageList").toString());
				}else{
					//请求结果异常 回滚上传记录改变
					pd.put("print_jds",0);
					onsitePunishmentMapper.updateOnsitePunishmentBatch(pd);
				}
			}
			
			responseBodyBean.setResult(resData);
		} catch (Exception e) {
			e.printStackTrace();
			reasonBean = ResponseUtil.getReasonBean("Exception", e.getClass().getSimpleName());
			responseBodyBean.setReason(reasonBean);
		}
		return responseBodyBean;
	}
	
	/**
	 * @Title: sendOutUrl
	 * @discription 请求服务
	 * @author 张泽恒       
	 * @created 2019年2月18日 上午9:04:09      
	 * @param data
	 * @param url
	 * @return     
	 * @see cn.net.hlk.data.service.OnsitePunishmentService#sendOutUrl(java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("all")
	@Override
	public PageData sendOutUrl(String data, String url) {
		PageData companypd = new PageData();
		try {
			HttpHeaders headers = new HttpHeaders();
			String result = "";
			Object object = null;
			String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIzNTM3Y2RmYS0zMjc0LTQ4MjUtYTc1Mi02MGM3ZmEyM2QwYTMiLCJpYXQiOjE1MTc2MzA0MzksInN1YiI6IjMiLCJpc3MiOiJTZWN1cml0eSBDZW50ZXIiLCJkZXBhcnRtZW50Ijp7ImRlcHRoIjoxLCJuYW1lIjoi6buR6b6Z5rGf55yBIiwiY29kZSI6IjIzMDAwMDAwMDAwMCJ9LCJnb3Zlcm5tZW50IjpbXSwiaWQiOjMsImlkQ2FyZCI6IjIzMDEwMjE5OTIwMTExMDAwMSIsInBjYXJkIjoiMjMwMDAxIiwibmFtZSI6Inh1IiwiaXNBZG1pbiI6MCwiZXhwIjoxNTE5NzA0MDM5fQ.Kas4PlaCjnwIMCbl1Dkkfe5zAvQo9plwjI1otfl0Y2A";
			MediaType mediaType = MediaType.parseMediaType("application/json; charset=UTF-8");
			headers.setContentType(mediaType);
			headers.add("Accept", MediaType.APPLICATION_JSON.toString());
			headers.add("token", token);
			logger.info("url>>"+url);
//			String data = JSON.toJSONString(page);
			HttpEntity<String> formEntity = new HttpEntity<String>(data, headers);
			 ResponseEntity<String> responseEntity = rest.exchange(url,
		                HttpMethod.POST, formEntity, String.class);
		        HttpStatus status = responseEntity.getStatusCode();
//		        result = rest.postForObject(url, formEntity, String.class);
		        if(status.value()==200){
		        	  result = responseEntity.getBody();
		        	  com.alibaba.fastjson.JSONObject  obj = JSON.parseObject(result);
//			        	  object = obj.get("result");
		        	  object = obj;
//			        	  logger.info("asd:"+JSON.toJSONString(object));
		        }
		        String json = JSON.toJSONString(object);
		        companypd = JSON.parseObject(json, PageData.class);
//				List<JSONObject> company = (List<JSONObject>) companypd.get("data");
//		        PageData policeUnitPd = JSON.parseObject(list.get(i).toJSONString(),PageData.class);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			return companypd;
		}
	}

	/**
	 * @Title: onsitePunishmentDetails
	 * @discription 现场处罚详情
	 * @author 张泽恒       
	 * @created 2019年2月18日 上午10:47:23      
	 * @param pd
	 * @return     
	 * @see cn.net.hlk.data.service.OnsitePunishmentService#onsitePunishmentDetails(cn.net.hlk.data.pojo.PageData)
	 */
	@Override
	public ResponseBodyBean onsitePunishmentDetails(PageData pd) {
		ResponseBodyBean responseBodyBean = new ResponseBodyBean();//返回值
		ReasonBean reasonBean = new ReasonBean();//返回参数
		PageData resData = new PageData();//返回数据
		try {
			List<PageData> pdList = new ArrayList<PageData>();
			pd.put("visibale", 0);
			PageData onsitePunishmentPd =  onsitePunishmentMapper.getOnsitePunishmentByID(pd);
			
			resData.put("data", onsitePunishmentPd);
			responseBodyBean.setResult(resData);
		} catch (Exception e) {
			e.printStackTrace();
			reasonBean = ResponseUtil.getReasonBean("Exception", e.getClass().getSimpleName());
			responseBodyBean.setReason(reasonBean);
		}
		return responseBodyBean;
	}
	
}
