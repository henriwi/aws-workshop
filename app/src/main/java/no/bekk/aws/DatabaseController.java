package no.bekk.aws;

import no.bekk.aws.domain.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
        String query = "INSERT INTO todo (text) VALUES (?)";
        jdbcTemplate.execute(query, (PreparedStatementCallback<Boolean>) ps -> {
            ps.setString(1, todo.text);
            return ps.execute();
        });
    }

    @RequestMapping(value = "/todo/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public void updateTodo(@PathVariable("id") int id, @RequestBody Todo todo) {
        String query = "UPDATE todo SET text=?, done=? WHERE id=?";

        jdbcTemplate.execute(query, (PreparedStatementCallback<Object>) ps -> {
            ps.setString(1, todo.text);
            ps.setBoolean(2, todo.isDone);
            ps.setInt(3, todo.id);
            return ps.execute();
        });

        jdbcTemplate.execute("UPDATE todo SET text='" + todo.text + "', done=" + todo.isDone + " WHERE id=" + id);
    }

    @RequestMapping(value = "/todo/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteTodo(@PathVariable("id") int id) {
        String query = "DELETE FROM todo WHERE id=?";
        jdbcTemplate.execute(query, (PreparedStatementCallback<Boolean>) ps -> {
            ps.setInt(1, id);
            return ps.execute();
        });
    }

}
