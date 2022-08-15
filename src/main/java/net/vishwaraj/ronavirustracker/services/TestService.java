package net.vishwaraj.ronavirustracker.services;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.vishwaraj.ronavirustracker.models.LocationStats;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Slf4j
public class TestService {

    @Value("${VIRUS_DATA_URL}")
    private String VIRUS_DATA_URL;

    @Value("${COVID19_MATHDRO}")
    private String MATH_DRO_URL;


    @Getter
    private List<LocationStats> allRecords = new ArrayList<>();

    //    @PostConstruct
    @Scheduled(cron = "* * 1 * * *")
    public void fetchData() throws IOException, InterruptedException {
        List<LocationStats> updatedRecords = new ArrayList<>();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request =HttpRequest.newBuilder()
                .uri(URI.create(VIRUS_DATA_URL))
                .build();

        HttpResponse<String> response =  client.send(request, HttpResponse.BodyHandlers.ofString());
        StringReader stringReader = new StringReader(response.body());
        CSVParser records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(stringReader);
//        List<String> headers =  records.getHeaderMap().keySet().stream().toList();

        for (CSVRecord record: records) {
            LocationStats locationStat = new LocationStats();
            locationStat.setState(record.get("Province/State"));
            locationStat.setCountry(record.get("Country/Region"));
            int latestCases = Integer.parseInt(record.get(record.size() - 1));
            int prevDayCases = Integer.parseInt(record.get(record.size() - 2));
            locationStat.setRecordedCases(latestCases);
            locationStat.setDiffFromPrevDay(latestCases - prevDayCases);
            updatedRecords.add(locationStat);
        }
        log.info("total recored updated: {}", updatedRecords.size());

        allRecords = updatedRecords;

    }

    private void test() throws IOException, InterruptedException {
        List<LocationStats> updatedRecords = new ArrayList<>();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request =HttpRequest.newBuilder()
                .uri(URI.create(VIRUS_DATA_URL))
                .build();

        HttpResponse<String> response =  client.send(request, HttpResponse.BodyHandlers.ofString());
        StringReader stringReader = new StringReader(response.body());
        CSVParser records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(stringReader);
//        List<String> headers =  records.getHeaderMap().keySet().stream().toList();

        /*for (CSVRecord record: records) {
            LocationStats locationStat = new LocationStats();
            locationStat.setState(record.get(headers.get(0)));
            locationStat.setCountry(record.get(headers.get(1)));
            HashMap<String ,Integer> count = new HashMap<>();
            for(int i = count.size() + 4; i<headers.size();i++){
                count.put(headers.get(i), Integer.parseInt(record.get(headers.get(i))));
            }
            log.info("{}, {}", locationStat, count);

        }*/
    }


}
