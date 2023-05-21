package com.example.javaex.stravaAPI;

public class Athlete {

    private String all_run_totals;

    private String created_at;



    public Athlete(String all_run_totals, String created_at) {
        this.all_run_totals = all_run_totals;
        this.created_at = created_at;
    }

    public Athlete(){}

    public String getAll_run_totals() {
        return all_run_totals;
    }

    public void setAll_run_totals(String all_run_totals) {
        this.all_run_totals = all_run_totals;
 }


    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}

