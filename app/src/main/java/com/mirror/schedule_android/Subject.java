package com.mirror.schedule_android;

class Subject {

    String key;
    String title;
    String professor;
    String location;
    String rest;
    String schedule;
    String latitude;
    String longitude;
    public Subject() {}
    public Subject(String key, String title, String professor,
                   String location, String rest, String schedule,
                   String latitude, String longitude) {
        this.key = key;
        this.title = title;
        this.professor = professor;
        this.location = location;
        this.rest = rest;
        this.schedule = schedule;
        this.latitude = latitude;
        this.longitude= longitude;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getRest() {
        return rest;
    }

    public void setRest(String rest) {
        this.rest = rest;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }
}
