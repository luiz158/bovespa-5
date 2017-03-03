import com.dolirio.bovespa.scrapper.infra.StockAnalisysScrapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.dolirio.bovespa.scrapper")
public class Application {

    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(Application.class);

        StockAnalisysScrapper stockAnalisys = ctx.getBean(StockAnalisysScrapper.class);
        stockAnalisys.scrapCompanies();
    }
}