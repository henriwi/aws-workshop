package no.bekk.aws;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class Config {

    @Bean
    public DataSource dataSource() {
        if (System.getenv("RDS_DB_NAME") != null) {
            String dbName = System.getenv("RDS_DB_NAME");
            String userName = System.getenv("RDS_USERNAME");
            String password = System.getenv("RDS_PASSWORD");
            String hostname = System.getenv("RDS_HOSTNAME");
            String port = System.getenv("RDS_PORT");

            HikariDataSource dataSource = new HikariDataSource();
            dataSource.setJdbcUrl("jdbc:mysql://" + hostname + ":" + port + "/" + dbName);
            dataSource.setUsername(userName);
            dataSource.setPassword(password);            
            
            return dataSource;
        }
        else {
            return null;
        }
    }
}
