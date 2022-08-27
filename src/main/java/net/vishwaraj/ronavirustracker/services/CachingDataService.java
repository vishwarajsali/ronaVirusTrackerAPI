package net.vishwaraj.ronavirustracker.services;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.vishwaraj.ronavirustracker.models.CaseData;
import net.vishwaraj.ronavirustracker.models.Countries;
import net.vishwaraj.ronavirustracker.repository.CachingDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.Clock;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class CachingDataService implements CachingDataRepository {

    @Autowired
    private CoronaVirusDataService coronaVirusDataService;

    @Getter
    private List<Countries> countries;

    @Getter
    private CaseData globalData;

    private Map<String, CaseData> countriesData;

    @PostConstruct
    @Scheduled(cron = "0 0 0 * * *")
    public void cachingData() throws IOException, InterruptedException {
        log.info("clearing cache and getting new data for {}", LocalDate.now(Clock.systemUTC()));
        countries = coronaVirusDataService.callToMathDroToGetCountries();
        globalData = coronaVirusDataService.callToMathDro();
        countriesData = new HashMap<>();
    }


    @Override
    public CaseData getCountry(String country) {
        CaseData caseData = new CaseData();
        try {
            if (!countriesData.containsKey(country)) {
                log.info("Data for {} not present", country);
                caseData = coronaVirusDataService.callCountryData(country);
                countriesData.put(country, caseData);
            }
            log.info("Getting Data for {} from cache", country);
            caseData = countriesData.get(country);

        } catch (InterruptedException | IOException  e) {
            log.error("problem getting data");
        }
        return caseData;

    }
}
