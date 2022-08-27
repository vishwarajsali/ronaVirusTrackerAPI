package net.vishwaraj.ronavirustracker.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import net.vishwaraj.ronavirustracker.models.CaseData;
import net.vishwaraj.ronavirustracker.models.Countries;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

@Slf4j
@Service
public class CoronaVirusDataService {

    @Value("${COVID19_MATHDRO}")
    private String MATH_DRO_URL;

    private static final String VALUE = "value";

    public List<Countries> callToMathDroToGetCountries() throws IOException, InterruptedException {
        log.info("/api/countries: getting all countries and their ISO codes");
        HttpResponse response = makeServiceToServiceGetCall(MATH_DRO_URL +"/countries");
        log.info("{}", response.body());
        JsonObject allCountries = convertResponseToJSON(response);
        ArrayList<Countries> countries = new ObjectMapper().readValue(allCountries.get("countries").toString(), new TypeReference<ArrayList<Countries>>(){});
        log.info("caching all the countries: {}",allCountries);
       return countries;
    }

    public CaseData callToMathDro() throws IOException, InterruptedException {
        log.info("/api: getting global summary");
        HttpResponse response = makeServiceToServiceGetCall(MATH_DRO_URL);
        log.info("{}", response.body());
        JsonObject getGlobalData = convertResponseToJSON(response);
        log.info("caching all the countries: {}",getGlobalData);
        CaseData global = new CaseData();
        global.setConfirmed(getGlobalData.get("confirmed").getAsJsonObject().get(VALUE).getAsBigDecimal());
        global.setRecovered(getGlobalData.get("recovered").getAsJsonObject().get(VALUE).getAsBigDecimal());
        global.setDeaths(getGlobalData.get("deaths").getAsJsonObject().get(VALUE).getAsBigDecimal());
        global.setLastUpdate(getGlobalData.get("lastUpdate").getAsString());
        log.info("Global data : {}", global);
        return global;
    }

    public CaseData callCountryData(String country) throws IOException, InterruptedException {
        log.info("/api: getting {} summary", country);
        HttpResponse response = makeServiceToServiceGetCall(MATH_DRO_URL +"/countries/"+country);
        log.info("{}", response.body());
        JsonObject jsonResponse = convertResponseToJSON(response);
        log.info("caching data for {} the countries: {}",country, jsonResponse);
        CaseData countryData = new CaseData();
        countryData.setConfirmed(jsonResponse.get("confirmed").getAsJsonObject().get(VALUE).getAsBigDecimal());
        countryData.setRecovered(jsonResponse.get("recovered").getAsJsonObject().get(VALUE).getAsBigDecimal());
        countryData.setDeaths(jsonResponse.get("deaths").getAsJsonObject().get(VALUE).getAsBigDecimal());
        countryData.setLastUpdate(jsonResponse.get("lastUpdate").getAsString());
        log.info("Country data : {}", countryData);
        return countryData;
    }

    private HttpResponse makeServiceToServiceGetCall(String url) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request =HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .build();

        HttpResponse response =  client.send(request, HttpResponse.BodyHandlers.ofString());
        log.info("Response status is {}, and Response is: {}",response.statusCode(), response.body());
        return response;
    }

    private JsonObject convertResponseToJSON(HttpResponse response){
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(response.body().toString(), JsonObject.class);
    }


}
