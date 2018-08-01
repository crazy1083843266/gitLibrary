package com.jt.test.rabbitmq;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;
import com.rabbitmq.client.ShutdownSignalException;

public class TestRabbitMQ {

	/**
	 * 如何连接rabbitMQ
	 * 1.需要连接的Connection
	 * 2.利用工厂模式获取Connection
	 * 3.需要为工厂模式设定连接参数
	 * 	host/端口/用户名/密码/虚拟主机
	 */
	
	private Connection connection;
	private String queueName="simple";
	//初始化connection 参数
	
	@Before
	public void init() throws IOException{
		ConnectionFactory factory=new ConnectionFactory();
		factory.setHost("192.168.220.139");
		factory.setPort(5672);
		factory.setUsername("jtadmin");
		factory.setPassword("jtadmin");
		factory.setVirtualHost("/jt");
		
		connection=factory.newConnection();
	}
	/**
	 * 定义消息提供者
	 * 步骤：
	 * 1.定义连接通道channel
	 * 2.定义队列
	 * 3.定义发送消息内容
	 * 4.将消息发送到队列中
	 * @throws IOException 
	 */
	@Test
	public void provider() throws IOException{
		Channel channel = connection.createChannel();
		/**
		 * 参数介绍
		 * 	queue：队列名称
		 * 	durable：表示队列是否持久化
		 * 	exclusive：是否有提供者独有，如果该参数为true，那么消费者不能消费
		 * 	autoDelete：当队列中没有消息时，是否自动删除
		 * 	arguments：传递的参数，如果没有参数一般为null
		 */
		channel.queueDeclare(queueName, false, false, false, null);
		String msg="我是一个简单的模式";
		/**
		 * 参数介绍：
		 * 	exchange：交换机  作用:可以将消息发送不同的队列中
		 * 	routingKey：标识消息发送哪个队列
		 * 	props：消息队列中的配置文件 一般为空
		 * 	body：代表消息的二进制文件
		 */
		channel.basicPublish("", queueName, null, msg.getBytes());
		
		channel.close();
		connection.close();
		System.out.println("消息发送成功");
		
	}
	
	//定义消费者
	/**
	 1.定义连接通道channel
	 * 2.定义队列
	 * 3.定义消费者对象
	 * 4.将消息与对象进行绑定
	 * 5.从队列中获取消息
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @throws ConsumerCancelledException 
	 * @throws ShutdownSignalException 
	 */
	@Test
	public void consumer() throws IOException, ShutdownSignalException, ConsumerCancelledException, InterruptedException{
		Channel channel = connection.createChannel();
		channel.queueDeclare(queueName, false, false, false, null);
		QueueingConsumer consumer=new QueueingConsumer(channel);
		//将消息与对象进行绑定
		channel.basicConsume(queueName, true, consumer);
		
		while(true){
			Delivery delivery = consumer.nextDelivery();
			System.out.println("获取消息："+new String(delivery.getBody()));
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
