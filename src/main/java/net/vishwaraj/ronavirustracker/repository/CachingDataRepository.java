package net.vishwaraj.ronavirustracker.repository;

import net.vishwaraj.ronavirustracker.models.CaseData;
import net.vishwaraj.ronavirustracker.models.Countries;

import java.util.List;

public interface CachingDataRepository {
    List<Countries> getCountries();
    CaseData getGlobalData();
    CaseData getCountry(String country);
}
