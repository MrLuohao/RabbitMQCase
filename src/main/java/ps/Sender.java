package ps;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import util.ConnectionUtil;

/**
 * @Title: Sender
 * @Author Mr.罗
 * @Package simplest
 * @Date 2023/11/27 17:53
 * @description: 消息生产者, 发布订阅模式 Publish/Subscribe
 */
public class Sender {
    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        //声明路由（路由名，路由类型）
        //fanout:不处理路由键（只需要将队列绑定到路由上，发送到路由的消息都会被转发到与该路由绑定的所有队列上）
        channel.exchangeDeclare("test_exchange_fanout", "fanout");
        String msg = "hello,大家好！";
        channel.basicPublish("test_exchange_fanout", "", null, msg.getBytes());//因为不知道谁会关注该路由，所以第二个参数为""
        System.out.println("生产者:" + msg);
        //5.释放资源
        channel.close();
        connection.close();
    }
}
