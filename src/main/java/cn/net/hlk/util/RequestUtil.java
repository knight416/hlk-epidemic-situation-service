package cn.net.hlk.util;

import cn.net.hlk.data.pojo.PageData;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Description: 请求服务
 * Created by gaoxipeng on 2019/4/15 9:03
 */
@Component
public class RequestUtil {

    private Logger logger = LoggerFactory.getLogger(RequestUtil.class);

    @Autowired
    RestTemplate rest;

    /**
     * @param data
     * @param url
     * @return
     * @Title: sendOutUrl
     * @discription 请求服务
     * @author gaoxipeng
     * @created 2019年4月115日 上午9:04:09
     * @see cn.net.hlk.data.service.OnsitePunishmentService#sendOutUrl(java.lang.String, java.lang.String)
     */
    //@SuppressWarnings("all")
    public PageData sendOutUrl(String data, String url) {
        PageData companypd = new PageData();
        try {
            String result = "";
            Object object = null;
            HttpHeaders headers = new HttpHeaders();
            String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIzNTM3Y2RmYS0zMjc0LTQ4MjUtYTc1Mi02MGM3ZmEyM2QwYTMiLCJpYXQiOjE1MTc2MzA0MzksInN1YiI6IjMiLCJpc3MiOiJTZWN1cml0eSBDZW50ZXIiLCJkZXBhcnRtZW50Ijp7ImRlcHRoIjoxLCJuYW1lIjoi6buR6b6Z5rGf55yBIiwiY29kZSI6IjIzMDAwMDAwMDAwMCJ9LCJnb3Zlcm5tZW50IjpbXSwiaWQiOjMsImlkQ2FyZCI6IjIzMDEwMjE5OTIwMTExMDAwMSIsInBjYXJkIjoiMjMwMDAxIiwibmFtZSI6Inh1IiwiaXNBZG1pbiI6MCwiZXhwIjoxNTE5NzA0MDM5fQ.Kas4PlaCjnwIMCbl1Dkkfe5zAvQo9plwjI1otfl0Y2A";
            MediaType mediaType = MediaType.parseMediaType("application/json; charset=UTF-8");
            headers.setContentType(mediaType);
            headers.add("Accept", MediaType.APPLICATION_JSON.toString());
            headers.add("Authorization", token);
            logger.info("url>>" + url);
            HttpEntity<String> formEntity = new HttpEntity<String>(data, headers);
            ResponseEntity<String> responseEntity = rest.exchange(url,
                    HttpMethod.POST, formEntity, String.class);
            HttpStatus status = responseEntity.getStatusCode();
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
        } finally {
            return companypd;
        }
    }
}
