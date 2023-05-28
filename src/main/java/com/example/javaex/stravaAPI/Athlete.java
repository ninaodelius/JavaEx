package com.example.javaex.stravaAPI;

public class Athlete {

    private String username;
    private String firstname;

    private String created_at;
    private String city;
    private String country;
    private AllRunTotals allRunTotals;
    private ActivityStats activityStats;

    public Athlete(String username, String firstname, String created_at, String city, String country) {
        this.username = username;
        this.firstname = firstname;
        this.created_at = created_at;
        this.city = city;
        this.country = country;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Athlete(String created_at) {
        this.created_at = created_at;
    }

    public Athlete() {
    }


    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}


