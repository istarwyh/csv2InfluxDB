package com.example.metis.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class InfluxModel implements Serializable {
    String MeasurementName;
    String TagSet;
    String TimeStamp;
    String FiledSet;

    @Override
    public String toString() {
        return MeasurementName+","+TagSet+" "+FiledSet+" "+TimeStamp;
    }

}
