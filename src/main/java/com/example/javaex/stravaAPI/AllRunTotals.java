package com.example.javaex.stravaAPI;

public class AllRunTotals {
    private int count;
    private float distance;
    private int moving_time;
    private int elapsed_time;
    private float elevation_gain;
    private int achievement_count;

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public int getMoving_time() {
        return moving_time;
    }

    public void setMoving_time(int moving_time) {
        this.moving_time = moving_time;
    }

    public int getElapsed_time() {
        return elapsed_time;
    }

    public void setElapsed_time(int elapsed_time) {
        this.elapsed_time = elapsed_time;
    }

    public float getElevation_gain() {
        return elevation_gain;
    }

    public void setElevation_gain(float elevation_gain) {
        this.elevation_gain = elevation_gain;
    }

    public int getAchievement_count() {
        return achievement_count;
    }

    public void setAchievement_count(int achievement_count) {
        this.achievement_count = achievement_count;
    }

    public AllRunTotals(int count, float distance, int moving_time, int elapsed_time, float elevation_gain, int achievement_count) {
        this.count = count;
        this.distance = distance;
        this.moving_time = moving_time;
        this.elapsed_time = elapsed_time;
        this.elevation_gain = elevation_gain;
        this.achievement_count = achievement_count;
    }

    public AllRunTotals(int count) {
        this.count = count;
    }

    public AllRunTotals() {

    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}