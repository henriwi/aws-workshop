package no.bekk.aws;

import no.bekk.aws.domain.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.util.List;

import static java.util.stream.Collectors.joining;

@Controller
@RequestMapping("/api/")
public class DatabaseController {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public DatabaseController(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.update("CREATE TABLE IF NOT EXISTS todo " +
                "(id INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                "text TEXT," +
                "done BOOL)");
    }

    @RequestMapping(value = "/todo", method = RequestMethod.GET)
    @ResponseBody
    public List<Todo> getTodos() {
        List<Todo> todos = jdbcTemplate.query("SELECT * FROM todo", (rs, i) -> new Todo()
                .withId(rs.getInt("id"))
                .withText(rs.getString("text"))
                .withIsDone(rs.getBoolean("done")));

        return todos;
    }

    @RequestMapping(value = "/todo", method = RequestMethod.POST)
    @ResponseBody
    public void addTodo(@RequestBody Todo todo) {
        jdbcTemplate.execute("INSERT INTO todo (text) VALUES ('" + todo.text + "')");
    }

    @RequestMapping(value = "/todo/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public void updateTodo(@PathVariable("id") int id, @RequestBody Todo todo) {
        jdbcTemplate.execute("UPDATE todo SET text='" + todo.text + "', done=" + todo.isDone + " WHERE id=" + id);
    }

    @RequestMapping(value = "/todo/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteTodo(@PathVariable("id") int id) {
        jdbcTemplate.execute("DELETE FROM todo WHERE id=" + id);
    }

}
