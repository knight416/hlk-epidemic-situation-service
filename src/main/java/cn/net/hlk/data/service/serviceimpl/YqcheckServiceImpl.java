package cn.net.hlk.data.service.serviceimpl;

import cn.net.hlk.data.mapper.OnsitePunishmentMapper;
import cn.net.hlk.data.mapper.YqcheckMapper;
import cn.net.hlk.data.pojo.Page;
import cn.net.hlk.data.pojo.PageData;
import cn.net.hlk.data.pojo.ReasonBean;
import cn.net.hlk.data.pojo.ResponseBodyBean;
import cn.net.hlk.data.service.OnsitePunishmentService;
import cn.net.hlk.data.service.YqcheckService;
import cn.net.hlk.util.CustomConfigUtil;
import cn.net.hlk.util.ResponseUtil;
import cn.net.hlk.util.StringUtil2;
import cn.net.hlk.util.UuidUtil;
import com.alibaba.fastjson.JSON;
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

import java.util.ArrayList;
import java.util.List;

/**
 * @Title
 * @Description 疫情核查
 * @author 张泽恒
 * @date 2020/2/2 14:45
 * @param
 * @return
 */
@Service
public class YqcheckServiceImpl extends BaseServiceImple implements YqcheckService {

	@Autowired
	private YqcheckMapper yqcheckMapper;


	/**
	 * @Title insertYqcheck
	 * @Description 疫情核查添加
	 * @author 张泽恒
	 * @date 2020/2/2 14:46
	 * @param [pd]
	 * @return cn.net.hlk.data.pojo.ResponseBodyBean
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
	public ResponseBodyBean insertYqcheck(PageData pd) {
		ResponseBodyBean responseBodyBean = new ResponseBodyBean();//返回值
		ReasonBean reasonBean = new ReasonBean();//返回参数
		PageData resData = new PageData();//返回数据
		try {
			PageData yqcheck = new PageData();
			//处理参数 拼装现场处罚pojo
			//解析参数
			//locationInfo
			PageData locationInfo = JSON.parseObject(JSON.toJSONString(pd.get("locationInfo")),PageData.class);
			//idcardInfo
			PageData idcardInfo = JSON.parseObject(JSON.toJSONString(pd.get("idcardInfo")),PageData.class);
			//faceInfo
			PageData faceInfo = JSON.parseObject(JSON.toJSONString(pd.get("faceInfo")),PageData.class);
			//carInfo
			PageData carInfo = JSON.parseObject(JSON.toJSONString(pd.get("carInfo")),PageData.class);
			//paintRealInfo
			PageData paintRealInfo = JSON.parseObject(JSON.toJSONString(pd.get("paintRealInfo")),PageData.class);
			//yiqingInfo
			PageData yiqingInfo = JSON.parseObject(JSON.toJSONString(pd.get("yiqingInfo")),PageData.class);
			//checkInfo
			PageData checkInfo = JSON.parseObject(JSON.toJSONString(pd.get("checkInfo")),PageData.class);


			// 组装
			String check_id = UuidUtil.get32UUID();
			yqcheck.put("check_id",check_id);
			yqcheck.put("optargetId",pd.get("optargetId"));
			yqcheck.put("dataType",pd.get("dataType"));
			yqcheck.put("relationId",pd.get("relationId"));
			yqcheck.put("checkId",pd.get("checkId"));
			yqcheck.put("checkException",pd.get("checkException"));

			yqcheck.put("locationInfo",JSON.toJSONString(pd.get("locationInfo")));
			if(locationInfo != null){
				yqcheck.put("policeName",locationInfo.get("policeName"));
				yqcheck.put("policeCode",locationInfo.get("policeCode"));
				yqcheck.put("policeIdcard",locationInfo.get("policeIdcard"));
				yqcheck.put("locationId",locationInfo.get("locationId"));
				yqcheck.put("locationName",locationInfo.get("locationName"));
				yqcheck.put("imei",locationInfo.get("imei"));
			}

			yqcheck.put("idcardInfo",JSON.toJSONString(pd.get("idcardInfo")));
			if(idcardInfo != null){
				yqcheck.put("name",idcardInfo.get("name"));
				yqcheck.put("sex",idcardInfo.get("sex"));
				yqcheck.put("idcard",idcardInfo.get("idcard"));
				yqcheck.put("address",idcardInfo.get("address"));
				yqcheck.put("photo",idcardInfo.get("photo"));
				yqcheck.put("imei",idcardInfo.get("imei"));
			}

			yqcheck.put("faceInfo",JSON.toJSONString(pd.get("faceInfo")));
			if(faceInfo != null){
				yqcheck.put("cardCompareResults",faceInfo.get("cardCompareResults"));
			}

			yqcheck.put("carInfo",JSON.toJSONString(pd.get("carInfo")));

			yqcheck.put("paintRealInfo",JSON.toJSONString(pd.get("paintRealInfo")));

			yqcheck.put("yiqingInfo",JSON.toJSONString(pd.get("yiqingInfo")));
			if(yiqingInfo != null){
				yqcheck.put("licensePlateNo",yiqingInfo.get("licensePlateNo"));
				yqcheck.put("tel",yiqingInfo.get("tel"));
				yqcheck.put("fromAddr",yiqingInfo.get("fromAddr"));
				yqcheck.put("toAddr",yiqingInfo.get("toAddr"));
				yqcheck.put("inorout",yiqingInfo.get("inorout"));
				yqcheck.put("tiwen",yiqingInfo.get("tiwen"));
				yqcheck.put("txrs",yiqingInfo.get("txrs"));
			}

			yqcheck.put("checkInfo",JSON.toJSONString(pd.get("checkInfo")));

			yqcheck.put("upladmessage",JSON.toJSONString(pd));

			yqcheckMapper.insertYqcheck(yqcheck);

			responseBodyBean.setResult(resData);
		} catch (Exception e) {
			e.printStackTrace();
			reasonBean = ResponseUtil.getReasonBean("Exception", e.getClass().getSimpleName());
			responseBodyBean.setReason(reasonBean);
		}
		return responseBodyBean;
	}

}
