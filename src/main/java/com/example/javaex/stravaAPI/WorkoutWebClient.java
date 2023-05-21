package com.example.javaex.stravaAPI;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class WorkoutWebClient {


    private WebClient webClient;

    public WorkoutWebClient() {
        this.webClient = WebClient.builder()
                .baseUrl("https://www.strava.com/api/v3")
                .defaultHeader("Authorization", "Bearer 72953655900b33f0ffda3c77dc30c25f83f35316")
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

    public Flux<Athlete> getAllAthletes() {
        return webClient.get()
                .uri("/athletes")
                .retrieve()
                .bodyToFlux(Athlete.class);
    }

    public List<Athlete> fluxToList(){
        WorkoutWebClient client = new WorkoutWebClient();

        Flux<Athlete> athleteFlux = client.getAllAthletes();

    return athleteFlux.collectList().block();
    }

    public List<Athlete> monoToList(){
        return getAthleteInfo()
                .flux()
                .collectList()
                .block();
    }


}
