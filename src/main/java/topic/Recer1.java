package topic;

import com.rabbitmq.client.*;
import util.ConnectionUtil;

import java.io.IOException;

/**
 * @Title: Recer
 * @Author Mr.罗
 * @Package simplest
 * @Date 2023/11/27 18:04
 * @description: 消息接收者1
 */
public class Recer1 {
    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        //声明队列(第二个参数为真表示支持持久化)
        channel.queueDeclare("test_exchange_topic_queue1", true, false, false, null);
        //绑定路由（关注）表示将test_exchange_direct_queue1绑定到test_exchange_topic(绑定用户相关的消息)
        channel.queueBind("test_exchange_topic_queue1", "test_exchange_topic", "user.#");
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override//交付处理(收件人信息,包裹上的快递标签,协议的配置,消息)
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                //body就是从队列中获取的消息
                String s = new String(body);
                System.out.println("【消费者1】 = " + s);
            }
        };
        //4.监听队列 true:自动消息确认
        channel.basicConsume("test_exchange_direct_queue1", true, consumer);
    }
}
