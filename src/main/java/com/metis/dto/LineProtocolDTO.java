package com.metis.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class LineProtocolDTO implements Serializable {
    private String measurementName;
    private String tagSet;
    private String timeStamp;
    private String filedSet;

    @Override
    public String toString() {
        return measurementName+","+tagSet+" "+filedSet+" "+timeStamp;
    }

}
