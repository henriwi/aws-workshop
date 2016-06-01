package no.bekk.aws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.sql.DataSource;
import java.util.List;

import static java.util.stream.Collectors.joining;

@Controller
public class DatabaseController {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public DatabaseController(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        
        jdbcTemplate.update("CREATE TABLE IF NOT EXISTS tbl (message TEXT)");
    }

    @RequestMapping("/db")
    public String testDatabase() {
        List<String> query = jdbcTemplate.query("SHOW TABLES", (rs, i) -> rs.getString(0));
        
        return query.stream().collect(joining("\n"));
    }
}
