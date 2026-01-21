package microMQ;

import org.apache.activemq.ActiveMQConnectionFactory;


import javax.jms.*;

/**
 * @author 观自在
 * @description
 * @date 2026-01-21 21:20
 */
public class Producer {
    public static void main(String[] args) {
        ActiveMQConnectionFactory connectionFactory;
        Connection conn=null;
        Session session=null;

        try {
            //1.创建连接工厂
            connectionFactory = new ActiveMQConnectionFactory("admin","admin","tcp://localhost:61616");
            //2.创建连接对象
            conn=connectionFactory.createConnection();
            conn.start();

            //3.创建会话
            session=conn.createSession(false,Session.AUTO_ACKNOWLEDGE);

            //4.创建点对点发送的目标
            Destination destination = session.createQueue("queue");

            //5.创建生产者消息
            MessageProducer producer = session.createProducer(destination);
            //设置生产者模式
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            //6.创建一条消息
            String text = "Hello World";
            TextMessage message = session.createTextMessage(text);

            //7.发送消息
            producer.send(message);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(conn!=null){
                try {
                    conn.close();
                }catch (JMSException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
