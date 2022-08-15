package net.vishwaraj.ronavirustracker.models;

import lombok.Data;

@Data
public class LocationStats {
    private String state;
    private String country;
    private int recordedCases;
    private int diffFromPrevDay;
    private String date;
}
