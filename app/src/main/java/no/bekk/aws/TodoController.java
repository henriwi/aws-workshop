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

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.joining;

@Controller
@RequestMapping("/api/")
public class TodoController {

    @RequestMapping(value = "/todo", method = RequestMethod.GET)
    @ResponseBody
    public List<Todo> getTodos() {
        return emptyList();
    }

    @RequestMapping(value = "/todo", method = RequestMethod.POST)
    @ResponseBody
    public void addTodo(@RequestBody Todo todo) {
    }

    @RequestMapping(value = "/todo/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public void updateTodo(@PathVariable("id") int id, @RequestBody Todo todo) {
    }

    @RequestMapping(value = "/todo/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public void deleteTodo(@PathVariable("id") int id) {
        
    }

}
