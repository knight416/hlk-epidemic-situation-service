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
import cn.net.hlk.data.service.DocumentNumberService;
import cn.net.hlk.util.ResponseUtil;
import cn.net.hlk.util.StringUtil2;

/**
 * @package: cn.net.hlk.data.controller   
 * @Title: DocumentNumberController   
 * @Description:文书编号管理
 * @Company: hylink 
 * @author 张泽恒  
 * @date 2019年2月15日 上午11:35:24
 */
@Api(description = "文书编号管理", value = "/")
@RestController
@RequestMapping(value="/DocumentNumber")
@EnableAutoConfiguration
public class DocumentNumberController extends BaseController{
	
	@Autowired
	private DocumentNumberService documentNumberService;
	
	/**
	 * @Title: insertContinuousDocumentNumber
	 * @discription 文书编号添加
	 * @author 张泽恒       
	 * @created 2019年2月15日 下午5:23:42     
	 * @param pd
	 * @param Authorization
	 * @return
	 */
	@SuppressWarnings("all")
	@ApiOperation(value = "文书编号添加", notes = "文书编号添加")
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
	@SysLog("文书编号添加")
	@RequestMapping(value="/insertContinuousDocumentNumber", method=RequestMethod.POST)
	public  @ResponseBody ResponseBodyBean insertContinuousDocumentNumber( @RequestBody PageData pd,  @RequestHeader String Authorization) { 
		int status = HttpStatus.INTERNAL_SERVER_ERROR.value();//状态码
		response.setStatus(status);//状态码存入
		ResponseBodyBean responseBodyBean = new ResponseBodyBean();//返回值
		ReasonBean reasonBean = new ReasonBean();//返回参数
		PageData resData = new PageData();//返回数据
		try{
			if(pd != null && StringUtil2.isNotEmpty(pd.get("type"))//处罚类型,1-非现场执法,2-现场简易处罚,3-现场强制措施
					&& StringUtil2.isNotEmpty(pd.get("operator")) //核查警员信息
					&& StringUtil2.isNotEmpty(pd.get("doc_number_start")) //编号起始
					&& StringUtil2.isNotEmpty(pd.get("doc_number_end")) //编号结束
					&& StringUtil2.isNotEmpty(pd.get("region_coding")) //区域编码
					){
				responseBodyBean = documentNumberService.insertContinuousDocumentNumber(pd);
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
	 * @Title: getDocumentNumberListSearch
	 * @discription 文书编号列表查询
	 * @author 张泽恒       
	 * @created 2019年2月19日 下午2:02:16     
	 * @param page
	 * @param Authorization
	 * @return
	 */
	@SuppressWarnings("all")
	@ApiOperation(value = "文书编号列表查询", notes = "文书编号列表查询")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "body", name = "page", dataType = "Page", required = true, value = "客户端传入JSON字符串", defaultValue = "") ,
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
	@RequestMapping(value="/getDocumentNumberListSearch", method=RequestMethod.POST)
	public  @ResponseBody ResponseBodyBean getDocumentNumberListSearch( @RequestBody Page page,  @RequestHeader String Authorization) { 
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
					responseBodyBean = documentNumberService.getOnsitePunishmentListSearch(page);
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
	
	
}
