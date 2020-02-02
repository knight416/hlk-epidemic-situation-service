package cn.net.hlk.data.controller;

import cn.net.hlk.data.annotation.SysLog;
import cn.net.hlk.data.pojo.Page;
import cn.net.hlk.data.pojo.PageData;
import cn.net.hlk.data.pojo.ReasonBean;
import cn.net.hlk.data.pojo.ResponseBodyBean;
import cn.net.hlk.data.service.OnsitePunishmentService;
import cn.net.hlk.data.service.YqcheckService;
import cn.net.hlk.util.ResponseUtil;
import cn.net.hlk.util.StringUtil2;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Api(description = "疫情核查", value = "/")
@RestController
@RequestMapping(value="/yq")
@EnableAutoConfiguration
public class YqcheckController extends BaseController{

	@Autowired
	private YqcheckService yqcheckService;


	@SuppressWarnings("all")
	@ApiOperation(value = "疫情核查添加", notes = "疫情核查添加")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = true, value = "客户端传入JSON字符串", defaultValue = "")
		 })
	@ApiResponses({
        @ApiResponse(code=200,message="指示客服端的请求已经成功收到，解析，接受"),
        @ApiResponse(code=201,message="资源已被创建"),
        @ApiResponse(code=401,message="未授权"),
        @ApiResponse(code=400,message="请求参数没填好"),
        @ApiResponse(code=403,message="拒绝访问"),
        @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对"),
        @ApiResponse(code=406,message="不是指定的数据类型"),
        @ApiResponse(code=500,message="服务器内部错误")
     })
	@SysLog("疫情核查添加")
	@RequestMapping(value="/insertYqcheck", method=RequestMethod.POST)
	public  @ResponseBody ResponseBodyBean insertYqcheck( @RequestBody PageData pd) {
		int status = HttpStatus.INTERNAL_SERVER_ERROR.value();//状态码
		response.setStatus(status);//状态码存入
		ResponseBodyBean responseBodyBean = new ResponseBodyBean();//返回值
		ReasonBean reasonBean = new ReasonBean();//返回参数
		PageData resData = new PageData();//返回数据
		try{
			if(pd != null 
					){
				responseBodyBean = yqcheckService.insertYqcheck(pd);
				if(responseBodyBean.getReason() == null){
					status = HttpStatus.OK.value();
					response.setStatus(status);
				}
			}else{
				reasonBean.setCode("400");
				reasonBean.setText("请求的参数不正确");
				status = HttpStatus.PRECONDITION_REQUIRED.value();
				response.setStatus(status);
				responseBodyBean.setReason(reasonBean);
			}
		}catch(Exception e){
			e.printStackTrace();
			reasonBean = ResponseUtil.getReasonBean("Exception", e.getClass().getSimpleName());
			responseBodyBean.setReason(reasonBean);
		}finally {
			return responseBodyBean;
		}
	}

	@SuppressWarnings("all")
	@ApiOperation(value = "人员导出", notes = "人员导出")
	@ApiImplicitParams({
	})
	@ApiResponses({
			@ApiResponse(code=200,message="指示客服端的请求已经成功收到，解析，接受"),
			@ApiResponse(code=201,message="资源已被创建"),
			@ApiResponse(code=401,message="未授权"),
			@ApiResponse(code=400,message="请求参数没填好"),
			@ApiResponse(code=403,message="拒绝访问"),
			@ApiResponse(code=404,message="请求路径没有或页面跳转路径不对"),
			@ApiResponse(code=406,message="不是指定的数据类型"),
			@ApiResponse(code=500,message="服务器内部错误")
	})
	@RequestMapping(value="/PersonalExport", method=RequestMethod.GET)
	public  @ResponseBody ResponseBodyBean PersonalExport(@RequestParam String starttime,@RequestParam String endtime,@RequestParam String starttimeh,@RequestParam String endtimeh) {
		int status = HttpStatus.INTERNAL_SERVER_ERROR.value();
		ResponseBodyBean responseBodyBean = new ResponseBodyBean();
		ReasonBean reasonBean = null;
		Object result = null;
		PageData resData = new PageData();
		PageData resultPd = new PageData();
		try{
			PageData pd = new PageData();
			pd.put("starttime",starttime+" "+starttimeh);
			pd.put("endtime",endtime+" "+endtimeh);

			resultPd = yqcheckService.PersonalExport(pd,response);
		}catch(Exception e){
			e.printStackTrace();
			reasonBean = ResponseUtil.getReasonBean("Exception", e.getClass().getSimpleName());
			resultPd.put("resData", resData);
			resultPd.put("status", status);
			resultPd.put("reasonBean", reasonBean);
		}finally {
			result = resultPd.get("resData");
			responseBodyBean.setResult(result);
			response.setStatus(Integer.valueOf(resultPd.get("status").toString()));
			responseBodyBean.setReason((ReasonBean)resultPd.get("reasonBean"));
			return responseBodyBean;
		}
	}


}
