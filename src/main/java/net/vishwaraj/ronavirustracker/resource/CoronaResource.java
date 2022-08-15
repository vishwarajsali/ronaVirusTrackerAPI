package net.vishwaraj.ronavirustracker.resource;

import net.vishwaraj.ronavirustracker.models.CaseData;

import net.vishwaraj.ronavirustracker.models.Countries;
import net.vishwaraj.ronavirustracker.services.CachingDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class CoronaResource {

    @Autowired
    private CachingDataService cachingDataService;

    @GetMapping("/countries")
    public List<Countries> getAllCountries(){
        return cachingDataService.getCountries();
    }

    @GetMapping
    public CaseData getGlobalData(){
        return cachingDataService.getGlobalData();
    }
}
