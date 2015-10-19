import nl.marcenschede.tests.elastic.order.Order;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@EnableAutoConfiguration
@ComponentScan(basePackageClasses = App.class)
public class App {

    private static Class app = App.class;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(app, args);
    }


}
