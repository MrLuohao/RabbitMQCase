package direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import util.ConnectionUtil;

/**
 * @Title: Sender
 * @Author Mr.罗
 * @Package simplest
 * @Date 2023/11/27 17:53
 * @description: 消息生产者，路由模式
 */
public class Sender {
    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        //声明路由（路由名，路由类型）
        //direct:根据路由键进行定向分发消息
        channel.exchangeDeclare("test_exchange_direct", "direct");
        String msg = "用户注册,【userID=S101】";
        channel.basicPublish("test_exchange_direct", "select", null, msg.getBytes());//insert为消息键
        System.out.println("【用户系统】:" + msg);
        //5.释放资源
        channel.close();
        connection.close();
    }
}
