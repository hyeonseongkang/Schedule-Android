package com.mirror.schedule_android.schedule;

public class MySubjects {
    private String key;
    private String title;
    private String[] schedule;
    private String schedules;
    private String location;
    private String professor;
    private String rest;
    private String latitude;
    private String longitude;

    public MySubjects(){}

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public MySubjects(String key, String title, String[] schedule, String schedules, String location, String professor, String rest, String latitude, String longitude) {
        this.key = key;
        this.title = title;
        this.schedule = schedule;
        this.schedules = schedules;
        this.location = location;
        this.professor = professor;
        this.rest = rest;
        this.latitude= latitude;
        this.longitude = longitude;
    }

    public MySubjects(String key, String title, String schedules, String location, String professor, String rest, String latitude, String longitude) {
        this.key = key;
        this.title = title;
        this.schedules = schedules;
        this.location = location;
        this.professor = professor;
        this.rest = rest;
        this.latitude= latitude;
        this.longitude = longitude;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public String getRest() {
        return rest;
    }

    public void setRest(String rest) {
        this.rest = rest;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSchedules() {
        return schedules;
    }

    public void setSchedules(String schedules) {
        this.schedules = schedules;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getSchedule() {
        return schedule;
    }

    public void setSchedule(String[] schedule) {
        this.schedule = schedule;
    }
}
