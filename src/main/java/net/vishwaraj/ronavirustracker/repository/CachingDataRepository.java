package net.vishwaraj.ronavirustracker.repository;

import net.vishwaraj.ronavirustracker.models.CaseData;
import net.vishwaraj.ronavirustracker.models.Countries;

import java.util.ArrayList;

public interface CachingDataRepository {
    ArrayList<Countries> getCountries();
    CaseData getGlobalData();
}
