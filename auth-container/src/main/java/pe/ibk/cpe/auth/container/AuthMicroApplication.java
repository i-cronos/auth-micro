package pe.ibk.cpe.auth.container;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(
        basePackages = {
                "pe.ibk.cpe.auth.domain.core",
                "pe.ibk.cpe.auth.domain.service",
                "pe.ibk.cpe.auth.infrastructure.database",
                "pe.ibk.cpe.auth.infrastructure.security",
                "pe.ibk.cpe.auth.application.rest",
                "pe.ibk.cpe.dependencies.global.config"
        }
)
public class AuthMicroApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(AuthMicroApplication.class, args);

        print(applicationContext);
    }

    private static void print(ApplicationContext applicationContext) {
        String[] beans = applicationContext.getBeanDefinitionNames();
        for (int i = 0; i < beans.length; i++) {
            System.out.println((1 + i) + " ::: " + beans[i]);
        }
    }
}
