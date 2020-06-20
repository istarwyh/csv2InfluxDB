package com.example.metis.metis.Model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: zhaxinchi
 * @Date: 2020/6/9
 */

//@Data
public class InfluxModel implements Serializable {

    String timeStamp;
    String p;

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getP() {
        return p;
    }

    public void setP(String p) {
        this.p = p;
    }


    @Override
//    public String toString() {
//        return p+" ";
//    }
    public String toString() {
        return p+" "+timeStamp;
    }

}
