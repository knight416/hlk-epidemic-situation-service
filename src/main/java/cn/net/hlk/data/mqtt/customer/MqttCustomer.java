package cn.net.hlk.data.mqtt.customer;

import cn.net.hlk.data.pojo.PageData;
import cn.net.hlk.util.CustomConfigUtil;
import cn.net.hlk.util.StringUtil2;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;


/**
 * 【描 述】：MQTT Customer 接收端
 * 【环 境】：J2SE 1.8
 * @author   柴志鹏	CHAIZP@GMAIL.COM
 * @version  version 1.0
 * @since    2018年1月22日
 */
@Component
public class MqttCustomer {
	
/*	@Autowired
	IMqttService mqttService;*/
	
	/** 
	 * 【描 述】：消息处理
	 * @param message
	 */
	public void handle(Message<?> message) {
		String topic = (String) message.getHeaders().get(MqttHeaders.TOPIC);
//System.out.println(message.getPayload().toString());
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		mapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
		PageData pd = null;
		try {
			pd = mapper.readValue(message.getPayload().toString(), PageData.class);
			if("1".equals(CustomConfigUtil.getString("mqtt.enable"))) {
				System.out.println("mqtt.topic:"+topic);
				System.out.println("mqtt.message:"+pd);
				if(StringUtil2.isNotEmpty(pd.getString("OptTag"))){
//					if(pd.getString("OptTag").contains("SET_FINGER_TAMPLATE_DB_ADD_RETURN")
//							|| pd.getString("OptTag").contains("SET_FINGER_TAMPLATE_DB_DELETE_RETURN")) {
//						
//					}else if("QRCODE_CREATE_RETURN".equals(pd.getString("OptTag"))) {
//						
//					}
				}
				if("Opt/security/biological/template".equals(topic)) {
					//调用方法传pd
//					custodianDossierService.biosignatureChangeOperater(pd);
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
