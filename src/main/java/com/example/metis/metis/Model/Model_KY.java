package com.example.metis.metis.Model;

import lombok.Data;

import java.io.Serializable;


/**
 * @Author: zhaxinchi
 * @Date: 2020/6/2
 */

//@Data
public class Model_KY implements Serializable {
    String key;
    String value;

    @Override
    public String toString() {
        return "Model_KY{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

    public Model_KY() {
    }

    public Model_KY(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
