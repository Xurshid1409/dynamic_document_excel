package uz.jurayev.dynamic_document_excel.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlywayConfig {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Bean
    public Flyway flyway() {
        Flyway flyway = Flyway.configure()
                .dataSource(url, username, password)
                .baselineOnMigrate(true)
                .validateOnMigrate(true)
                .defaultSchema("documents")
                .load();
        flyway.migrate();
        return flyway;
    }
}
