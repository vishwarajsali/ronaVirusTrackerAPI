package net.vishwaraj.ronavirustracker.services;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.vishwaraj.ronavirustracker.models.LocationStats;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CoronaVirusDataService {

    private static final String VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";

    @Getter
    private List<LocationStats> allRecords = new ArrayList<>();

    @PostConstruct
    @Scheduled(cron = "* * 1 * * *")
    public void fetchdata() throws IOException, InterruptedException {
        List<LocationStats> updatedRecords = new ArrayList<>();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request =HttpRequest.newBuilder()
                .uri(URI.create(VIRUS_DATA_URL))
                .build();
        HttpResponse<String> response =  client.send(request, HttpResponse.BodyHandlers.ofString());
//        log.info("Response is: {}", response.body().toString());
        StringReader stringReader = new StringReader(response.body());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(stringReader);

        for (CSVRecord record: records) {
            LocationStats locationStat = new LocationStats();
            locationStat.setState(record.get("Province/State"));
            locationStat.setCountry(record.get("Country/Region"));
            int latestCases = Integer.parseInt(record.get(record.size() - 1));
            int prevDayCases = Integer.parseInt(record.get(record.size() - 2));
            locationStat.setRecordedCases(latestCases);
            locationStat.setDiffFromPrevDay(latestCases - prevDayCases);
            updatedRecords.add(locationStat);
            log.info("Record {}", locationStat);
        }
        log.info("total recored updated: {}", updatedRecords.size());

        allRecords = updatedRecords;

    }

    private void extractResponse(){

    }
}
