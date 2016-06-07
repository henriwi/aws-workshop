
var TodoBox = React.createClass({
    loadTodosFromServer: function() {
        $.ajax({
            url: this.props.url,
            contentType: 'application/json',
            cache: false,
            success: function(data) {
                this.setState({data: data});
            }.bind(this),
            error: function(xhr, status, err) {
                console.error(this.props.url, status, err.toString());
            }.bind(this)
        });
    },
    handleTodoSubmit: function(todo) {
        var todos = this.state.data;
        var newTodos = todos.concat([todo]);
        this.setState({data: newTodos});

        $.ajax({
            url: this.props.url,
            contentType: 'application/json',
            type: 'POST',
            data: JSON.stringify(todo),
            success: function() {
                console.log("success");
            }.bind(this),
            error: function(xhr, status, err) {
                this.setState({data: todos});
                console.error(this.props.url, status, err.toString());
            }.bind(this)
        });

    },
    getInitialState: function() {
        return {data: []};
    },
    componentDidMount: function() {
        this.loadTodosFromServer();
    },
    render: function() {
        return (
            <div className="todoBox">
                <TodoForm onTodoSubmit={this.handleTodoSubmit} />
                <TodoList data={this.state.data} />
        </div>
        );
    }
});

var TodoList = React.createClass({
    render: function() {
        var todoNodes = this.props.data.map(function(todo) {
            return (
                <Todo text={todo.text}/>
            );
        });
        return (
            <div className="todoList">
                {todoNodes}
            </div>
        );
    }
});

var Todo = React.createClass({
    render: function() {
        return (
            <p className="todoText">
                {this.props.text}
            </p>
        );
    }
});

var TodoForm = React.createClass({
    getInitialState: function() {
        return {text: ''};
    },
    handleTextChange: function(e) {
        this.setState({text: e.target.value});
    },
    handleSubmit: function(e) {
        e.preventDefault();
        var text = this.state.text.trim();
        if (!text) {
            return;
        }
        this.props.onTodoSubmit({text: text});
        this.setState({text: ''});
    },
    render: function() {
        return (
            <form className="todoForm" onSubmit={this.handleSubmit}>
                <input className="todoInput"
                    type="text"
                    placeholder="Legg til todo..."
                    value={this.state.text}
                    onChange={this.handleTextChange}
                />
            </form>
        );
    }
});

ReactDOM.render(
<TodoBox url="/api/todo" />,
    document.getElementById('content')
);