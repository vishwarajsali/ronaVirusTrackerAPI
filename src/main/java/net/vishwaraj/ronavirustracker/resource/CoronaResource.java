package net.vishwaraj.ronavirustracker.resource;

import net.vishwaraj.ronavirustracker.models.CaseData;

import net.vishwaraj.ronavirustracker.models.Countries;
import net.vishwaraj.ronavirustracker.repository.CachingDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class CoronaResource {

    @Autowired
    private CachingDataRepository cachingDataRepository;

    @GetMapping("/countries")
    public List<Countries> getAllCountries(){
        return cachingDataRepository.getCountries();
    }

    @GetMapping("/getGlobal")
    public CaseData getGlobalData(){
        return cachingDataRepository.getGlobalData();
    }

    @GetMapping("/country")
    public CaseData getCountry(@RequestParam(value = "country") String country){
        return cachingDataRepository.getCountry(country);
    }
}
