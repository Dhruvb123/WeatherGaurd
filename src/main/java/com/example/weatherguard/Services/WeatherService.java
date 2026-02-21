package com.example.weatherguard.Services;

import com.example.weatherguard.DTO.ResponseDTO;
import com.example.weatherguard.Entity.EventRequest;
import com.example.weatherguard.Entity.EventWindow;
import com.example.weatherguard.Entity.WeatherResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
public class WeatherService implements IWeatherService{

    private final WebClient webClient;

    public WeatherService(@Value("${weather.api.base-url}") String baseUrl) {
        this.webClient = WebClient.builder()
                                  .baseUrl(baseUrl)
                                  .build();
    }


    @Override
    public Mono<WeatherResponse> getForecast(EventRequest req) {

        double lat = req.getLocation().getLatitude();
        double lon = req.getLocation().getLongitude();
        String start = req.getStart_time().substring(0,10);
        String end = req.getEnd_time().substring(0,10);

        return webClient.get()
                                                .uri(uriBuilder -> uriBuilder
                                                .queryParam("latitude", lat)
                                                .queryParam("longitude", lon)
                                                .queryParam("start_date", start)
                                                .queryParam("end_date", end)
                                                .queryParam("hourly", "precipitation_probability,windspeed_10m,weathercode")
                                                .build())
                                        .retrieve()
                                        .bodyToMono(WeatherResponse.class);

    }

    @Override
    public ResponseDTO modifyResponse(EventRequest req, WeatherResponse response) {
        String start = req.getStart_time().substring(0,16);
        String end = req.getEnd_time().substring(0,16);

        int startIndx = 0;
        int endIndx = 0;

        List<String> time = response.getHourly().getTime();

        for(int i=0;i<time.size();i++){
            if(start.equals(time.get(i))){
                startIndx = i;
            }
            if(end.equals(time.get(i))){
                endIndx = i;
            }
        }

        List<Integer> rainProb = response.getHourly().getPrecipitation_probability();
        List<Float> windSpeed = response.getHourly().getWindspeed_10m();
        List<Integer> weatherCode = response.getHourly().getWeathercode();

        ResponseDTO res = new ResponseDTO();
        List<EventWindow> eventWindows = new ArrayList<>();

        int code = 0, indx = startIndx;

        for(int i = startIndx;i<endIndx;i++){
            EventWindow window = new EventWindow();
            window.setTime(time.get(i).substring(11));
            window.setRain_prob(rainProb.get(i));
            window.setWind_kmh(windSpeed.get(i));

            eventWindows.add(window);

            int curCode = weatherCode.get(i);
            if(curCode > code){
                code = curCode;
                indx = i;
            }
        }

        List<String> details = getDetails(indx, code, time.get(indx),rainProb.get(indx),windSpeed.get(indx));
        res.setClassification(details.get(0));
        res.setSummary(details.get(1));
        res.setReason(details.subList(2, details.size()));
        res.setEvent_window_forecast(eventWindows);

        return res;
    }

    private static List<String> getDetails(int index, int code, String time, Integer rainProb , Float windSpeed){
        List<String> res = new ArrayList();

        String peakTime = time.substring(11);

        String classification, summary;
        List<String> reason = new ArrayList<>();

        long sevScore = Math.round((double)rainProb);

        if(code == 9 || code == 17){
            classification = "Unsafe, Severity Score: " + sevScore;
            if(code==9) {
                summary = "High chance of Duststorm/Sandstorm";
            }
            else {
                summary = "High chance of Thunderstorm";
            }
        }
        else if(code>=0 && code<=20){
            classification = "Safe, Severity Score: "+sevScore;
            summary = "Clear Sky, Good Day for Event";

        }
        else if((code>=30 && code <=39) || (code>=80)){
            classification = "Unsafe, Severity Score: "+sevScore;
            if(code>=80){
                summary = "High chance of Rain and Thunderstorm";
            }
            else{
                summary = "High chance of Duststorm/Sandstorm";
            }
        }
        else{
            classification = "Risky, Severity Score: "+ sevScore;
            summary = "High chance of Rain";
        }

        res.add(classification);
        res.add(summary);
        res.add("Rain probability is "+ rainProb + "% at "+peakTime);
        res.add("Wind Speed is "+ windSpeed +" km/h");

        return res;
    }


}
