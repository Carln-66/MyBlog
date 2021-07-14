import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.listener.Event;
import com.alibaba.nacos.api.naming.listener.EventListener;
import com.alibaba.nacos.api.naming.listener.NamingEvent;

import java.util.Properties;

/**
 * @Auther: Carl
 * @Date: 2021/06/25/21:00
 * @Description:
 */
public class NamingExample {

    public static void main(String[] args) throws NacosException {

        Properties properties = new Properties();
        properties.setProperty("serverAddr", System.getProperty("serverAddr"));
        properties.setProperty("namespace", System.getProperty("namespace"));

        NamingService naming = NamingFactory.createNamingService(properties);

        naming.registerInstance("system-server", "11.11.11.11", 8888, "TEST1");

        naming.registerInstance("system-server", "2.2.2.2", 9999, "DEFAULT");

        System.out.println(naming.getAllInstances("system-server"));

        naming.deregisterInstance("system-server", "2.2.2.2", 9999, "DEFAULT");

        System.out.println(naming.getAllInstances("system-server"));

        naming.subscribe("system-server", new EventListener() {
            @Override
            public void onEvent(Event event) {
                System.out.println(((NamingEvent)event).getServiceName());
                System.out.println(((NamingEvent)event).getInstances());
            }
        });
    }
}
