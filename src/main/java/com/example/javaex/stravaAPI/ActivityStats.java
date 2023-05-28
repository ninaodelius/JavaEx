package com.example.javaex.stravaAPI;

public class ActivityStats {

    private AllRunTotals all_run_totals;
    private String biggest_climb_elevation_gain;

    public String getBiggest_climb_elevation_gain() {
        return biggest_climb_elevation_gain;
    }

    public void setBiggest_climb_elevation_gain(String biggest_climb_elevation_gain) {
        this.biggest_climb_elevation_gain = biggest_climb_elevation_gain;
    }

    public ActivityStats(AllRunTotals all_run_totals, String biggest_climb_elevation_gain) {
        this.all_run_totals = all_run_totals;
        this.biggest_climb_elevation_gain = biggest_climb_elevation_gain;
    }

    public ActivityStats() {

    }

    public AllRunTotals getAll_run_totals() {
        return all_run_totals;
    }

    public void setAll_run_totals(AllRunTotals all_run_totals) {
        this.all_run_totals = all_run_totals;
    }


}
