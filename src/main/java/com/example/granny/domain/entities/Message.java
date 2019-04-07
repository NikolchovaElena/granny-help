package com.example.granny.domain.entities;

import java.time.LocalDateTime;

public class Message {

    String title;
    String content;
    LocalDateTime date;
//    User from;
//    User to;

    public Message() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

}
