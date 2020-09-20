package com.metis.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


/**
 * @author : zhaxinchi
 * @date : 2020/6/2
 */

@Getter
@Setter
@AllArgsConstructor
public class KeyValueModel implements Serializable {
    String key;
    String value;

    @Override
    public String toString() {
        return "Model_KY{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

}
