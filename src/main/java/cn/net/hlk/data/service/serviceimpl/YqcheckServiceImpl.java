package cn.net.hlk.data.service.serviceimpl;

import cn.net.hlk.data.config.FileUploadProperteis;
import cn.net.hlk.data.mapper.YqcheckMapper;
import cn.net.hlk.data.pojo.PageData;
import cn.net.hlk.data.pojo.ReasonBean;
import cn.net.hlk.data.pojo.ResponseBodyBean;
import cn.net.hlk.data.service.YqcheckService;
import cn.net.hlk.util.FileUtil;
import cn.net.hlk.util.PoiExcelDownLoad;
import cn.net.hlk.util.ResponseUtil;
import cn.net.hlk.util.StringUtil2;
import cn.net.hlk.util.UuidUtil;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
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
	@Autowired
	private FileUploadProperteis fileUploadProperteis;


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

	/**
	 * @Title PersonalExport
	 * @Description 人员导出
	 * @author 张泽恒
	 * @date 2020/2/2 17:29
	 * @param [pd]
	 * @param response
	 * @return cn.net.hlk.data.pojo.PageData
	 */
	@Override
	public PageData PersonalExport(PageData pd, HttpServletResponse response) {

		int status = HttpStatus.INTERNAL_SERVER_ERROR.value();
		ReasonBean reasonBean = null;
		PageData resData = new PageData();
		PageData resultPd = new PageData();
		try {
			String starttime = pd.getString("starttime");
			String endtime = pd.getString("endtime");

			//保存时的文件名
			DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
			Calendar calendar = Calendar.getInstance();
			String dateName = df.format(calendar.getTime())+"Export.xls";

			if(StringUtil2.isNotEmpty(starttime) || StringUtil2.isNotEmpty(endtime) ){
				dateName = starttime+"-"+endtime+"Export.xls";
			}

			//虚拟路径存储
			String realPath = fileUploadProperteis.getUploadFolder();
			String filePath = realPath + File.separator+ "ExcelFile" + File.separator+ "Export"+ File.separator+dateName;
			FileUtil.createDir(filePath);
			//文件地址
			OutputStream out = new FileOutputStream(filePath);
			response.setContentType("application/vnd.ms-excel;chartset=utf-8");
			response.setHeader("Content-Disposition", "attachment;filename="+dateName + ".xls");
			out = response.getOutputStream();

			//获取管控人员信息利用poiModel
			List<PageData> pdList = new ArrayList<PageData>();
			pdList = yqcheckMapper.getpByT(pd);

			PoiExcelDownLoad<PageData> poid = new PoiExcelDownLoad<PageData>();
			//标题list
			List<String> headersList = new ArrayList<String>();
			headersList.add("id");//标题
			// headersList.add("id");//注释
			headersList.add("档案id");
			// headersList.add("档案id");
			headersList.add("核查是否异常");
			// headersList.add("核查是否异常");
			headersList.add("警员姓名");
			// headersList.add("警员姓名");
			headersList.add("警员警号");
			// headersList.add("警员警号");
			headersList.add("警员身份证号");
			// headersList.add("警员身份证号");
			headersList.add("卡口编号");
			// headersList.add("卡口编号");
			headersList.add("卡口名称");
			// headersList.add("卡口名称");
			headersList.add("设备id");
			// headersList.add("设备id");

			headersList.add("姓名");
			// headersList.add("姓名");
			headersList.add("性别");
			// headersList.add("性别");
			headersList.add("身份证号");
			// headersList.add("身份证号");
			headersList.add("地址");
			// headersList.add("地址");
			headersList.add("二代证比对结果");
			// headersList.add("二代证比对结果");
			headersList.add("车牌号码");
			// headersList.add("车牌号码");
			headersList.add("车牌");
			// headersList.add("车牌");
			headersList.add("电话");
			// headersList.add("电话");
			headersList.add("从何处来");
			// headersList.add("从何处来");
			headersList.add("入城/出城");
			// headersList.add("入城/出城");
			headersList.add("体温");
			// headersList.add("体温");
			headersList.add("同行人数");
			// headersList.add("同行人数");
			headersList.add("时间");
			// headersList.add("时间");

			//读取顺序list
			List<String> readList = new ArrayList<String>();
			readList.add("check_id");
			readList.add("optargetId");
			readList.add("checkException");
			readList.add("policeName");
			readList.add("policeCode");
			readList.add("policeIdcard");
			readList.add("locationId");
			readList.add("locationName");
			readList.add("imei");
			readList.add("name");
			readList.add("sex");
			readList.add("idcard");
			readList.add("address");
			readList.add("cardCompareResults");
			readList.add("licensePlateNo");
			readList.add("tel");
			readList.add("fromAddr");
			readList.add("toAddr");
			readList.add("inorout");
			readList.add("tiwen");
			readList.add("txrs");
			readList.add("updatetime");


			Collection<PageData> dataset = pdList;
			try {
				poid.exportExcel("导出",headersList, dataset, out,readList);
				out.close();
				resData.put("path","/upload"+ File.separator+ "ExcelFile"+ File.separator+ "Export"+File.separator+dateName);
				status = HttpStatus.OK.value();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				reasonBean = ResponseUtil.getReasonBean("Exception", e.getClass().getSimpleName());
			} catch (IOException e) {
				e.printStackTrace();
				reasonBean = ResponseUtil.getReasonBean("Exception", e.getClass().getSimpleName());
			}
		} catch (Exception e) {
			e.printStackTrace();
			reasonBean = ResponseUtil.getReasonBean("Exception", e.getClass().getSimpleName());
		}
		resultPd.put("resData", resData);
		resultPd.put("status", status);
		resultPd.put("reasonBean", reasonBean);
		return resultPd;
	}

}
