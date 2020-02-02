package cn.net.hlk.data.controller;

import java.util.ArrayList;
import java.util.List;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.net.hlk.data.annotation.SysLog;
import cn.net.hlk.data.pojo.Page;
import cn.net.hlk.data.pojo.PageData;
import cn.net.hlk.data.pojo.ReasonBean;
import cn.net.hlk.data.pojo.ResponseBodyBean;
import cn.net.hlk.data.service.OnsitePunishmentService;
import cn.net.hlk.util.ResponseUtil;
import cn.net.hlk.util.StringUtil2;

@Api(description = "现场处罚管理", value = "/")
@RestController
@RequestMapping(value="/OnsitePunishment")
@EnableAutoConfiguration
public class OnsitePunishmentController extends BaseController{

	@Autowired
	private OnsitePunishmentService onsitePunishmentService;
	
	/**
	 * @Title: getOnsitePunishmentListSearch
	 * @discription 现场处罚列表查询
	 * @author 张泽恒       
	 * @created 2019年2月15日 下午5:05:42     
	 * @param page
	 * @param Authorization
	 * @return
	 */
	@SuppressWarnings("all")
	@ApiOperation(value = "现场处罚列表查询", notes = "现场处罚列表查询")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "body", name = "page", dataType = "Page", required = true, value = "客户端传入JSON字符串", defaultValue = "")
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
	@RequestMapping(value="/getOnsitePunishmentListSearch", method=RequestMethod.POST)
	public  @ResponseBody ResponseBodyBean getOnsitePunishmentListSearch( @RequestBody Page page) { 
		int status = HttpStatus.INTERNAL_SERVER_ERROR.value();//状态码
		response.setStatus(status);//状态码存入
		ResponseBodyBean responseBodyBean = new ResponseBodyBean();//返回值
		ReasonBean reasonBean = new ReasonBean();//返回参数
		PageData resData = new PageData();//返回数据
		try{
			List<PageData> pdList = new ArrayList<PageData>();
			PageData pd = page.getPd();
			if(page != null ){
				if(pd != null 
						){
					responseBodyBean = onsitePunishmentService.getOnsitePunishmentListSearch(page);
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
	
	/**
	 * @Title: deleteOnsitePunishment
	 * @discription 现场处罚删除
	 * @author 张泽恒       
	 * @created 2019年2月16日 上午8:51:31     
	 * @param pd
	 * @param Authorization
	 * @return
	 */
	@SuppressWarnings("all")
	@ApiOperation(value = "现场处罚删除", notes = "现场处罚删除")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = true, value = "客户端传入JSON字符串", defaultValue = "") ,
		@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "安全中心颁发token验证信息", defaultValue = "eyJqdGkiOiIzYzdhN2UzNy0yMWUyLTRhMTYtOTUwNS0zMjVlMjI0NGMxZjAiLCJpYXQiOjE1MDM4ODc5MDcsInN1YiI6IjEiLCJpc3MiOiJTZWN1cml0eSBDZW50ZXIiLCJncm91cGNvZGUiOiIyMzAwMDAwMDAwMDAiLCJ1dHlwZSI6IjEifQ"),
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
	@SysLog("现场处罚删除")
	@RequestMapping(value="/deleteOnsitePunishment", method=RequestMethod.POST)
	public  @ResponseBody ResponseBodyBean deleteOnsitePunishment( @RequestBody PageData pd,  @RequestHeader String Authorization) { 
		int status = HttpStatus.INTERNAL_SERVER_ERROR.value();//状态码
		response.setStatus(status);//状态码存入
		ResponseBodyBean responseBodyBean = new ResponseBodyBean();//返回值
		ReasonBean reasonBean = new ReasonBean();//返回参数
		PageData resData = new PageData();//返回数据
		try{
			if(pd != null && StringUtil2.isNotEmpty(pd.get("onsite_punishment_id"))//现场处罚id
					){
				responseBodyBean = onsitePunishmentService.deleteOnsitePunishment(pd);
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
	
	/**
	 * @Title: insertOnsitePunishment
	 * @discription 简易处罚添加
	 * @author 张泽恒       
	 * @created 2019年2月16日 下午1:59:07     
	 * @param pd
	 * @param Authorization
	 * @return
	 */
	@SuppressWarnings("all")
	@ApiOperation(value = "现场处罚添加", notes = "现场处罚添加")
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
	@SysLog("现场处罚添加")
	@RequestMapping(value="/insertOnsitePunishment", method=RequestMethod.POST)
	public  @ResponseBody ResponseBodyBean insertOnsitePunishmentEasy( @RequestBody PageData pd) { 
		int status = HttpStatus.INTERNAL_SERVER_ERROR.value();//状态码
		response.setStatus(status);//状态码存入
		ResponseBodyBean responseBodyBean = new ResponseBodyBean();//返回值
		ReasonBean reasonBean = new ReasonBean();//返回参数
		PageData resData = new PageData();//返回数据
		try{
			if(pd != null 
					){
				responseBodyBean = onsitePunishmentService.insertOnsitePunishment(pd);
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
	
	/**
	 * @Title: uploadOnsitePunishmentEasy
	 * @discription 现场处罚上传
	 * @author 张泽恒       
	 * @created 2019年2月18日 上午8:35:51     
	 * @param pd
	 * @param Authorization
	 * @return
	 */
	@SuppressWarnings("all")
	@ApiOperation(value = "现场处罚上传", notes = "现场处罚上传")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = true, value = "客户端传入JSON字符串", defaultValue = "") ,
		@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "安全中心颁发token验证信息", defaultValue = "eyJqdGkiOiIzYzdhN2UzNy0yMWUyLTRhMTYtOTUwNS0zMjVlMjI0NGMxZjAiLCJpYXQiOjE1MDM4ODc5MDcsInN1YiI6IjEiLCJpc3MiOiJTZWN1cml0eSBDZW50ZXIiLCJncm91cGNvZGUiOiIyMzAwMDAwMDAwMDAiLCJ1dHlwZSI6IjEifQ"),
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
	@SysLog("现场处罚上传")
	@RequestMapping(value="/uploadOnsitePunishment", method=RequestMethod.POST)
	public  @ResponseBody ResponseBodyBean uploadOnsitePunishment( @RequestBody PageData pd,  @RequestHeader String Authorization) { 
		int status = HttpStatus.INTERNAL_SERVER_ERROR.value();//状态码
		response.setStatus(status);//状态码存入
		ResponseBodyBean responseBodyBean = new ResponseBodyBean();//返回值
		ReasonBean reasonBean = new ReasonBean();//返回参数
		PageData resData = new PageData();//返回数据
		try{
			if(pd != null && StringUtil2.isNotEmpty(pd.get("onsitePunishmentIdList")) && pd.get("onsitePunishmentIdList").toString().length()>2
					){
				responseBodyBean = onsitePunishmentService.uploadOnsitePunishment(pd);
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
	
	/**
	 * @Title: onsitePunishmentDetails
	 * @discription 现场处罚详情
	 * @author 张泽恒       
	 * @created 2019年2月18日 上午10:46:21     
	 * @param pd
	 * @param Authorization
	 * @return
	 */
	@SuppressWarnings("all")
	@ApiOperation(value = "现场处罚详情", notes = "现场处罚详情")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "body", name = "pd", dataType = "PageData", required = true, value = "客户端传入JSON字符串", defaultValue = "") ,
		@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "String", required = true, value = "安全中心颁发token验证信息", defaultValue = "eyJqdGkiOiIzYzdhN2UzNy0yMWUyLTRhMTYtOTUwNS0zMjVlMjI0NGMxZjAiLCJpYXQiOjE1MDM4ODc5MDcsInN1YiI6IjEiLCJpc3MiOiJTZWN1cml0eSBDZW50ZXIiLCJncm91cGNvZGUiOiIyMzAwMDAwMDAwMDAiLCJ1dHlwZSI6IjEifQ"),
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
	@SysLog("现场处罚详情")
	@RequestMapping(value="/onsitePunishmentDetails", method=RequestMethod.POST)
	public  @ResponseBody ResponseBodyBean onsitePunishmentDetails( @RequestBody PageData pd,  @RequestHeader String Authorization) { 
		int status = HttpStatus.INTERNAL_SERVER_ERROR.value();//状态码
		response.setStatus(status);//状态码存入
		ResponseBodyBean responseBodyBean = new ResponseBodyBean();//返回值
		ReasonBean reasonBean = new ReasonBean();//返回参数
		PageData resData = new PageData();//返回数据
		try{
			if(pd != null && StringUtil2.isNotEmpty(pd.get("onsite_punishment_id"))//现场处罚id
					){
				responseBodyBean = onsitePunishmentService.onsitePunishmentDetails(pd);
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
	
	
}
