// package cn.net.hlk.data.mqtt;
//
// import cn.net.hlk.data.mqtt.customer.MqttCustomer;
// import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.context.annotation.Bean;
// import org.springframework.integration.annotation.IntegrationComponentScan;
// import org.springframework.integration.annotation.MessagingGateway;
// import org.springframework.integration.annotation.ServiceActivator;
// import org.springframework.integration.channel.DirectChannel;
// import org.springframework.integration.core.MessageProducer;
// import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
// import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
// import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
// import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
// import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
// import org.springframework.messaging.Message;
// import org.springframework.messaging.MessageChannel;
// import org.springframework.messaging.MessageHandler;
// import org.springframework.messaging.MessagingException;
//
// @SpringBootApplication
// @IntegrationComponentScan
// public class MqttAppConfiguration {
// 	private final Logger log = LoggerFactory.getLogger(this.getClass());
// 	/** 【描 述】：服务连接 */
// 	@Value("${mqtt.serverUri}")
//     private String serviceUri;
//
//     /** 【描 述】：用户名 */
//     @Value("${mqtt.username:}")
//     private String username;
//
//     /** 【描 述】：密码 */
//     @Value("${mqtt.password:}")
//     private String password;
//
//     /** 【描 述】：主题 */
// //    @Value("${mqtt.topic}")
// //    private String topic;
//
//     /** 【描 述】：customer客户端Id */
// //    @Value("${mqtt.customer.clientId}")
//     private String customerClientId = MqttAsyncClient.generateClientId() + "_customer";
//
//     /** 【描 述】：producer客户端Id */
// //    @Value("${mqtt.producer.clientId}")
//     private String producerClientId = MqttAsyncClient.generateClientId() + "_producer";
//
//     /** 【描 述】：通道 */
//     @Value("${mqtt.qos}")
//     private Integer qos;
//
//     /** 【描 述】：超时时常(毫秒) */
//     @Value("${mqtt.completionTimeout}")
//     private Integer completionTimeout;
//
//     /** 【描 述】：主题 */
//     @Value("${mqtt.topic}")
//     private String topic;
//
//
// 	// 发送
//     /**
//      * 【描 述】：mqtt服务器配置
//      * @return
//      */
//     @Bean
//     public MqttPahoClientFactory mqttClientFactory() {
//         DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
//         factory.setServerURIs(serviceUri);
//         factory.setUserName(username);
//         factory.setPassword(password);
//         return factory;
//     }
//
//     @Bean
//     @ServiceActivator(inputChannel = "mqttOutboundChannel")
//     public MqttPahoMessageHandler mqttOutbound(MqttPahoClientFactory clientFactory) {
//         MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler(customerClientId,
//         		clientFactory);
//         messageHandler.setAsync(true);
//         messageHandler.setDefaultQos(qos);
//         messageHandler.setDefaultRetained(false);
//         messageHandler.setAsyncEvents(false);
//         return messageHandler;
//     }
//
//     @Bean
//     public MessageChannel mqttOutboundChannel() {
//         return new DirectChannel();
//     }
//
//     @MessagingGateway(defaultRequestChannel = "mqttOutboundChannel")
//     public interface MyGateway {
//         void sendToMqtt(Message<?> message);
//     }
//
//
//     // 接收
//     @Bean
//     public MessageChannel mqttInputChannel() {
//         return new DirectChannel();
//     }
//
//     /**
//      * 【描 述】：通道适配器
//      * @return
//      */
//     @Bean
//     public MessageProducer inbound() {
//     	String[] topics = topic.split(",");
//         MqttPahoMessageDrivenChannelAdapter adapter =
//                 new MqttPahoMessageDrivenChannelAdapter(serviceUri, producerClientId,
//                 		topics);
//         adapter.setCompletionTimeout(completionTimeout);
//         adapter.setConverter(new DefaultPahoMessageConverter());
//         adapter.setQos(qos);
//         adapter.setOutputChannel(mqttInputChannel());
//         return adapter;
//     }
//
//     @Autowired
//     MqttCustomer customer;
//
//     /**
//      * 【描 述】：消息处理
//      * @return
//      */
//     @Bean
//     @ServiceActivator(inputChannel = "mqttInputChannel")
//     public MessageHandler handler() {
//         return new MessageHandler() {
//             @Override
//             public void handleMessage(Message<?> message) throws MessagingException {
//             	customer.handle(message);
// //                System.out.println(message.getPayload());
// //                System.out.println(message.getHeaders().containsValue("/baq/mqtt/test_receive"));
// //                String topic = (String) message.getHeaders().get(MqttHeaders.TOPIC);
// //                System.out.println(topic);
// //                this.publish(topic == null ? this.defaultTopic : topic, mqttMessage, message);
//             }
//         };
//     }
//
//
//
//     /*public static void main(String[] args) {
//         ConfigurableApplicationContext context =
//                 new SpringApplicationBuilder(MqttAppConfiguration.class)
//                         .web(false)
//                         .run(args);
// //        发送的消息
//         Message<String> message = MessageBuilder.withPayload("mqtt message")
// //        发送的主题
//                 .setHeader(MqttHeaders.TOPIC, "/baq/mqtt/test_send").build();
//         MyGateway gateway = context.getBean(MyGateway.class);
//         gateway.sendToMqtt(message);
//     }*/
//
// }