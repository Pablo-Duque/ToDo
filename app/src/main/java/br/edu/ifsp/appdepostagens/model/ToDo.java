package br.edu.ifsp.appdepostagens.model;

public class ToDo {

    private String userId;
    private String id;
    private String title;
    private boolean completed;

    public ToDo(String userId, String title, boolean body) {
        this.userId = userId;
        this.title = title;
        this.completed = body;
    }

    public String getUserId () {
        return userId;
    }

    public void setUserId (String userId) {
        this.userId = userId;
    }

    public String getId () {
        return id;
    }

    public void setId (String id) {
        this.id = id;
    }

    public String getTitle () {
        return title;
    }

    public void setTitle (String title) {
        this.title = title;
    }

    public boolean getCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
