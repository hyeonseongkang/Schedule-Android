package com.mirror.schedule_android.chat;

public class ChatData {
    private String user;
    private String message;
    private String time;

    public ChatData() {}

    public ChatData(String user, String message, String time) {
        this.user = user;
        this.message = message;
        this.time = time;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String username) {
        this.user = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String date) {
        this.time = date;
    }
}
