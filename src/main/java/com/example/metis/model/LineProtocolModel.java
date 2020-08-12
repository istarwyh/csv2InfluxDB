package com.example.metis.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
public class InfluxModel implements Serializable {
    private static String MeasurementName;
    private static String TagSet;
    private static String TimeStamp;
    private static String FiledSet;

    public static void setMeasurementName(String measurementName) {
        InfluxModel.MeasurementName = measurementName;
    }

    public static void setTagSet(String tagSet) {
        InfluxModel.TagSet = tagSet;
    }

    public static void setFiledSet(String filedSet) {
        InfluxModel.FiledSet = filedSet;
    }

    public static void setTimeStamp(String timeStamp) {
        InfluxModel.TimeStamp = timeStamp;
    }

    @Override
    public String toString() {
        return MeasurementName+","+TagSet+" "+FiledSet+" "+TimeStamp;
    }

}
