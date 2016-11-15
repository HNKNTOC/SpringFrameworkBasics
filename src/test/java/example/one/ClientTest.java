package example.one;

import org.junit.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.*;

/**
 * Created by Nikita on 15.11.2016.
 */
public class ClientTest {

    private static ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("main.xml");

    @Test
    public void checkGreeting() throws Exception {
        Client client = context.getBean("client",Client.class);
        System.out.println(client.toString());

    }
}