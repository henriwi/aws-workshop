package no.bekk.aws.domain;

public class Todo {
    public int id;
    public String text;
    public boolean isDone;

    public Todo withId(int id) {
        this.id = id;
        return this;
    }

    public Todo withText(String text) {
        this.text = text;
        return this;
    }

    public Todo withIsDone(boolean isDone) {
        this.isDone = isDone;
        return this;
    }
}
