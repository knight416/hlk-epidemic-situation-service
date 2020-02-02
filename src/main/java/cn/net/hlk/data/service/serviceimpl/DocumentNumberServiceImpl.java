package cn.net.hlk.data.service.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.net.hlk.data.mapper.DocumentNumberMapper;
import cn.net.hlk.data.pojo.Page;
import cn.net.hlk.data.pojo.PageData;
import cn.net.hlk.data.pojo.ReasonBean;
import cn.net.hlk.data.pojo.ResponseBodyBean;
import cn.net.hlk.data.service.DocumentNumberService;
import cn.net.hlk.util.ResponseUtil;

import com.alibaba.fastjson.JSON;

/**
 * @package: cn.net.hlk.data.service.serviceimpl   
 * @Title: DocumentNumberServiceImpl   
 * @Description:文书编号管理service
 * @Company: hylink 
 * @author 张泽恒  
 * @date 2019年2月15日 上午11:38:18
 */
@Service
public class DocumentNumberServiceImpl extends BaseServiceImple implements DocumentNumberService {

	@Autowired
	private DocumentNumberMapper  documentNumberMapper;

	/**
	 * @Title: insertContinuousDocumentNumber
	 * @discription 文书编号添加
	 * @author 张泽恒       
	 * @created 2019年2月15日 下午1:57:17      
	 * @param pd
	 * @return     
	 * @see cn.net.hlk.data.service.DocumentNumberService#insertContinuousDocumentNumber(cn.net.hlk.data.pojo.PageData)
	 */
	@Override
//	@TargetDataSource(dataSource = "baqgl_yyjg")
	public ResponseBodyBean insertContinuousDocumentNumber(PageData pd) {
		ResponseBodyBean responseBodyBean = new ResponseBodyBean();//返回值
		ReasonBean reasonBean = new ReasonBean();//返回参数
		PageData resData = new PageData();//返回数据
		try {
			//进行编号区间验证
//			pd.put("region_coding", pd.get("region_coding")+"-");//区域头
			String doc_number_start1 = pd.get("region_coding").toString()+pd.get("doc_number_start");
			char[] doc_number_start1n = doc_number_start1.toCharArray();
			if(doc_number_start1n.length > 0){
				int sum = 0;
				for(char n : doc_number_start1n){
					sum = sum + Integer.parseInt(String.valueOf(n));;
				}
				doc_number_start1 = doc_number_start1+ sum%7;
			}
			pd.put("doc_number_start1", doc_number_start1);
			String doc_number_end1 = pd.get("region_coding").toString()+pd.get("doc_number_end");
			char[] doc_number_end1n = doc_number_start1.toCharArray();
			if(doc_number_end1n.length > 0){
				int sum = 0;
				for(char n : doc_number_end1n){
					sum = sum + Integer.parseInt(String.valueOf(n));;
				}
				doc_number_end1 = doc_number_end1+ sum%7;
			}
			pd.put("doc_number_end1", doc_number_end1);
			
			System.out.println("pd "+pd);
			List<String> docNumberList  = documentNumberMapper.verificationOnly(pd);
			
			if(docNumberList == null ||docNumberList.size() < 1 ){
				//区间内没有文书编号 进行添加
				//分解操警员信息
				PageData operator  = JSON.parseObject(JSON.toJSONString(pd.get("operator")),PageData.class);
				pd.put("police_unit_code", operator.get("police_unit_code"));//所属组织机构
				pd.put("police_name", operator.get("police_name"));//警员姓名
				pd.put("police_idcard", operator.get("police_idcard"));//身份证号
				pd.put("police_number", operator.get("police_number"));//警号
				//创建添加数组
				List<PageData> documentNumberList = new ArrayList<PageData>();
				for(int i = Integer.valueOf(pd.get("doc_number_start").toString());i<= Integer.valueOf(pd.get("doc_number_end").toString());i++){
					PageData documentNumber = new PageData();
					String doc_number = pd.get("region_coding").toString()+i;
					doc_number = doc_number+ (Long.valueOf(doc_number)%7);
					documentNumber.put("doc_number", doc_number);
					documentNumber.put("type", pd.get("type"));
					documentNumber.put("police_unit_code", pd.getString("police_unit_code"));
					documentNumber.put("police_name", pd.getString("police_name"));
					documentNumber.put("police_idcard", pd.getString("police_idcard"));
					documentNumber.put("police_number", pd.getString("police_number"));
					documentNumber.put("state", 0);
					documentNumberList.add(documentNumber);
				}
				pd.put("documentNumberList", documentNumberList);
				System.out.println("documentNumberList"+documentNumberList);
				if(documentNumberList != null && documentNumberList.size() > 0){
					documentNumberMapper.insertContinuousDocumentNumber(pd);
				}
				resData.put("code","200");
				responseBodyBean.setResult(resData);
			}else{
				//文书编号区间存在文书编号
				resData.put("code","400");
				resData.put("text","文书编号区间已存在文书编号");
				responseBodyBean.setResult(resData);
			}
		}catch (Exception e) {
			e.printStackTrace();
			reasonBean = ResponseUtil.getReasonBean("Exception", e.getClass().getSimpleName());
			responseBodyBean.setReason(reasonBean);
		}
		return responseBodyBean;
	}

	/***
	 * @Title: getOnsitePunishmentListSearch
	 * @discription 文书编号条件查询
	 * @author 张泽恒       
	 * @created 2019年2月19日 下午2:03:37      
	 * @param page
	 * @return     
	 * @see cn.net.hlk.data.service.DocumentNumberService#getOnsitePunishmentListSearch(cn.net.hlk.data.pojo.Page)
	 */
	@Override
	public ResponseBodyBean getOnsitePunishmentListSearch(Page page) {
		ResponseBodyBean responseBodyBean = new ResponseBodyBean();//返回值
		ReasonBean reasonBean = new ReasonBean();//返回参数
		PageData resData = new PageData();//返回数据
		try {
			List<PageData> pdList = new ArrayList<PageData>();
			pdList = documentNumberMapper.getDocumentNumberSearchPgListPage(page);
			
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
	
	
	
}
