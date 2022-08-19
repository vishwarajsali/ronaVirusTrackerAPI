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
import java.util.ArrayList;

@Service
@Slf4j
public class CachingDataService implements CachingDataRepository {

    @Autowired
    private CoronaVirusDataService coronaVirusDataService;

    @Getter
    private ArrayList<Countries> countries;

    @Getter
    private CaseData globalData;

    @PostConstruct
    @Scheduled(cron = "0 0 0 * * *")
    public void cachingData() throws IOException, InterruptedException {
        countries = coronaVirusDataService.callToMathDroToGetCountries();
        globalData = coronaVirusDataService.callToMathDro();
    }

}
