// package cn.net.hlk.data.mqtt.producer;
//
// import cn.net.hlk.data.mqtt.MqttAppConfiguration.MyGateway;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.ApplicationContext;
// import org.springframework.integration.mqtt.support.MqttHeaders;
// import org.springframework.integration.support.MessageBuilder;
// import org.springframework.messaging.Message;
// import org.springframework.stereotype.Component;
//
// @Component
// public class MqttProducer {
// 	// 发送
// 	@Autowired
// 	ApplicationContext context;
//
// 	/**
// 	 * 【描 述】：Mqtt 消息发送
// 	 * @param topic 主题
// 	 * @param text 正文
// 	 */
// 	public void send(String topic, String text) {
// 		// 发送的消息
// 		Message<String> message = MessageBuilder.withPayload(text)
// 				// 发送的主题
// 				.setHeader(MqttHeaders.TOPIC, topic).build();
// 		MyGateway gateway = context.getBean(MyGateway.class);
// 		gateway.sendToMqtt(message);
// 	}
// }
