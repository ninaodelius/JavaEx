package com.example.javaex.stravaAPI;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class WorkoutWebClient {

    private WebClient webClient;
    private String authBearer;
    private String athleteId;

    public void setAthleteId(String athleteId){
        this.athleteId = athleteId;
        initializeWebClient();
    }
    public void setAuthBearer(String authBearer){
        this.authBearer = authBearer;
        initializeWebClient();
    }

    private void initializeWebClient() {
        this.webClient = WebClient.builder()
                .baseUrl("https://www.strava.com/api/v3")
                .defaultHeader("Authorization", "Bearer " + authBearer)
                .build();
    }

    //Bearer token must be updated every 6h
    public WorkoutWebClient() {
        this.webClient = WebClient.builder()
                .baseUrl("https://www.strava.com/api/v3")
                .defaultHeader("Authorization", "Bearer "+authBearer)
                .build();
    }



    public Mono<Athlete> getAthleteInfo() {
        return webClient.get()
                .uri("/athlete")
                .retrieve()
                .bodyToMono(Athlete.class)
                .map(athleteRequest -> {
                    System.out.println(athleteRequest);
                    return athleteRequest;
                });
    }

    //to get athlete info
    public List<Athlete> monoToList(){
        return getAthleteInfo()
                .flux()
                .collectList()
                .block();
    }

    /*public Flux<ActivityStats> getActivityStats() {
        //athlete id must be updated
        String athleteId = "107540269";
        return webClient.get()
                .uri("/athletes/"+athleteId+"/stats")
               // .header("Authorization", "Bearer 45f11d78c592d16f2382f8a68f3b5fdb66b42298")
                .retrieve()
                .bodyToFlux(ActivityStats.class);
    }*/
    public Mono<ActivityStats> getActivityStats() {
        //get athleteId from https://www.strava.com/api/v3/athlete + Authorization : Bearer xxxxxxxxx
        //String athleteId = "107540269";
        return webClient.get()
                .uri("/athletes/"+athleteId+"/stats")
                .retrieve()
                .bodyToMono(ActivityStats.class)
                .map(activityRequest -> {
                    System.out.println(activityRequest);
                    return activityRequest;
                });
    }
    public List<ActivityStats> monoActivitiesToList(){
        return getActivityStats()
                .flux()
                .collectList()
                .block();
    }

    //to get activitystats
    /*public List<ActivityStats> fluxToList(){
        WorkoutWebClient client = new WorkoutWebClient();

        Flux<ActivityStats> activityStatsFlux = client.getActivityStats();
    return activityStatsFlux.collectList().block();
    }*/

/*
    public List<AthleteStats> monoStatToList(){
        return getAthleteStats()
                .flux()
                .collectList()
                .block();
    }*/


    public Flux<Athlete> getAllAthletes() {
        return webClient.get()
                .uri("/athletes")
                .retrieve()
                .bodyToFlux(Athlete.class);
    }


}
