package net.vishwaraj.ronavirustracker.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Countries {
    private String name;
    private String iso2;
    private String iso3;
}
