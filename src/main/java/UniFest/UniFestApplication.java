package UniFest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableCaching
@EnableJpaAuditing
@SpringBootApplication
@EnableAspectJAutoProxy
public class UniFestApplication {


	public static void main(String[] args) {
		SpringApplication.run(UniFestApplication.class, args);
	}

}
