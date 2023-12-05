package work;

import com.rabbitmq.client.*;
import util.ConnectionUtil;

import java.io.IOException;

/**
 * @Title: Recer
 * @Author Mr.罗
 * @Package simplest
 * @Date 2023/11/27 18:04
 * @description: 消息接收者1, 通过ACK确认机制
 */
public class Recer1 {
    static int i = 1;//统计吃掉羊肉串的数量

    public static void main(String[] args) throws Exception {
        //1.获取连接
        Connection connection = ConnectionUtil.getConnection();
        //2.获得通道（信道）
        final Channel channel = connection.createChannel();
        //queueDeclare()此方法有双重作用,如果队列不存在，就创建，如果存在，就获取
        channel.queueDeclare("test_work_queue", false, false, false, null);
        channel.basicQos(1);//添加此代码表示当消费者确认消息消费过后rabbitMQ再分配消息给该消费者而不是盲目分配，从而实现了能者多劳
        //3.从信道中获得消息
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override//交付处理(收件人信息,包裹上的快递标签,协议的配置,消息)
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                //body就是从队列中获取的消息
                String s = new String(body);
                System.out.println("【顾客1】吃掉 " + s + "！总共吃掉【" + i++ + "】串！");
                try {
                    //模拟网络延迟
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                //手动确认（收件人信息，是否同时确认多个信息）
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        //4.监听队列 false:手动消息确认
        channel.basicConsume("test_work_queue", false, consumer);
    }
}
