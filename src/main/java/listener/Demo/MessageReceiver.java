package listener.Demo;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class MessageReceiver {

    @JmsListener(destination ="OBC.Q.ASSET.JMS")
    public void receiveMessage(Message msg)
    {
        System.err.println("received : "+msg);
    }
}
