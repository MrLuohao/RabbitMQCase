package util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @Title: ConnectionUtil
 * @Author Mr.罗
 * @Package util
 * @Date 2023/11/27 17:21
 * @description: 专门与RabbitMQ获得连接
 */
public class ConnectionUtil {
    public static Connection getConnection() throws Exception {
        //1.创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //2.在工厂对象中设置MQ连接信息（ip,port,vhost,username,password）
        factory.setHost("192.168.247.128");
        factory.setPort(5672);
        factory.setVirtualHost("/RabbitMQInduction");
        factory.setUsername("luohao");
        factory.setPassword("123456");
        Connection connection = factory.newConnection();
        return connection;
    }

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtil.getConnection();
        System.out.println("Connection=" + connection);//Connection=amqp://luohao@192.168.247.128:5672//RabbitMQInduction连接成功
        connection.close();
    }
}
