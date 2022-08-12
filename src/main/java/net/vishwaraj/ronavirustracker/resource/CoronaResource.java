package net.vishwaraj.ronavirustracker.resource;

import net.vishwaraj.ronavirustracker.models.LocationStats;
import net.vishwaraj.ronavirustracker.services.CoronaVirusDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CoronaResource {

    @Autowired
    private CoronaVirusDataService coronaVirusDataService;

    @GetMapping
    public List<LocationStats> getAllCountries(){
        return coronaVirusDataService.getAllRecords();
    }
}
