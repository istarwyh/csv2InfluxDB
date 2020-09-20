package com.metis;

import com.metis.service.InfluxClient2;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MetisApplicationTests {
    private InfluxClient2 tmpObject2 = new InfluxClient2();

    @Test
    void contextLoads() {
        tmpObject2.csv2InfluxDB();
    }

}
