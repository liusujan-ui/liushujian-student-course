package microMQ;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author 观自在
 * @description
 * @date 2026-01-21 21:19
 */
public class Consumer {
    public static void main(String[] args) {
        ActiveMQConnectionFactory connectionFactory;
        Connection conn = null;
        Session session = null;
        MessageConsumer consumer = null;
        try {
            //1.创建连接工厂
            connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
            //2.创建连接对象
            conn = connectionFactory.createConnection("admin", "admin");
            conn.start();
            //3.创建会话
            session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);

            //4.创建点对点接收的目标
            Destination destination = session.createQueue("queue");

            // ... existing code ...

            //5.创建消费者
            consumer = session.createConsumer(destination);

            //6.设置消息监听器（异步接收）
            consumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    if (message instanceof TextMessage) {
                        try {
                            System.out.println("接收到消息：" + ((TextMessage) message).getText());
                        } catch (JMSException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

            // 保持主线程运行，以便监听器能持续接收消息
            // 如果是测试程序，通常可以使用 System.in.read() 或者 Thread.sleep 来防止程序退出
            System.in.read();

        } catch (JMSException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //7.关闭资源
            if (consumer != null) {
                try {
                    consumer.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
            if (session != null) {
                try {
                    session.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
