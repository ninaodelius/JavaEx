package com.example.javaex.weatherAPI;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class WeatherWebClient {

    WebClient webClient = WebClient.create("https://goweather.herokuapp.com/weather/stockholm");

    public Mono<WeatherModel> getWeatherInfo(){
        Mono<WeatherModel> weather = webClient
                .get()
                .retrieve()
                .bodyToMono(WeatherModel.class)
                .map(weatherRequest -> {
                    System.out.println(weatherRequest);
                    return weatherRequest;
                });
        return weather;
    }

    public Flux<WeatherModel> monoToFlux(){
        return getWeatherInfo().flux();
    }

    public List<WeatherModel> fluxToList(){
        return monoToFlux().collectList().block();
    }

}
