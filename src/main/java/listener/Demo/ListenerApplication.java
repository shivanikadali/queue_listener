package listener.Demo;

import java.util.Date;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.cbor.MappingJackson2CborHttpMessageConverter;
import org.springframework.jms.JmsException;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.util.ErrorHandler;

import jakarta.jms.ConnectionFactory;

@SpringBootApplication
@EnableJms
public class ListenerApplication {

	public static void main(String[] args) throws JmsException {
		// to launch the spring boot app //
		ConfigurableApplicationContext context = SpringApplication.run(ListenerApplication.class, args);

		// Get JMS template bean reference
		JmsTemplate jmsTemplate = context.getBean(JmsTemplate.class);

		// send a message
		System.out.println("sending a message");

		Message msg = new Message(10l, "sending msg to activemq", new Date());
		jmsTemplate.convertAndSend("OBC.Q.ASSET.JMS", msg);

		// try {
		// jmsTemplate.send("MESSAGE.Q.", "this msg is sent using jmsTemplate");
		// } catch (JmsException e) {

		// }
		// System.out.println("message sent");
	}

	@Bean
	public JmsListenerContainerFactory<?> myFactory(ConnectionFactory connectionFactory,
			DefaultJmsListenerContainerFactoryConfigurer configurer) {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();

		// anonymous class
		factory.setErrorHandler(new ErrorHandler() {
			@Override
			public void handleError(Throwable t) {
			 System.out.println("an error has occured in the transaction");
			}
		});

		//will provides all boot's default to this factory, including the message converter
		configurer.configure(factory, connectionFactory);
		//we can still override sa=ome of boots default if necessary.

		return factory;
	}
	@Bean
	public MessageConverter jacksonJmsMessageConverter()
	{
		MappingJackson2MessageConverter converter=new MappingJackson2MessageConverter();
		converter.setTargetType(MessageType.TEXT);
		converter.setTypeIdPropertyName("_type");
		return converter;
	}


}
