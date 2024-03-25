package listener.Demo;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class MessageListener {
    @JmsListener(destination="MESSAGE_QUEUE")
    public static void receiveMessage(String message)
    {
         System.out.println("Received message : "+message);
    }
}
