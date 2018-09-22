package com.example.root.gogetitserver.model;

public class Sender {
    public String to;
    public Notifications notification;

    public Sender(String to, Notifications notification) {
        this.to = to;
        this.notification = notification;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Notifications getNotification() {
        return notification;
    }

    public void setNotification(Notifications notification) {
        this.notification = notification;
    }
}
