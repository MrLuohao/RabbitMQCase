package topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import util.ConnectionUtil;

/**
 * @Title: Sender
 * @Author Mr.罗
 * @Package simplest
 * @Date 2023/11/27 17:53
 * @description: 消息生产者，通配符模式
 */
public class Sender {
    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        //声明路由（路由名，路由类型,持久化）
        //topic:模糊匹配的定向分发
        channel.exchangeDeclare("test_exchange_topic", "topic",true);
        String msg = "商品降价";
        channel.basicPublish("test_exchange_topic", "product.price", MessageProperties.PERSISTENT_TEXT_PLAIN, msg.getBytes());//insert为消息键
        System.out.println("【用户系统】:" + msg);
        //5.释放资源
        channel.close();
        connection.close();
    }
}
