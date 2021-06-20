package com.metis.config.business;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

class BusinessStatusEnumTest {

    @Test
    void getDescByCode() {
        Assertions.assertEquals(BusinessStatusEnum.PARAM_BIG_EXCEPTION.getDesc(),
                BusinessStatusEnum.getDescByCode(40002));
        Assertions.assertEquals(BusinessStatusEnum.UNKNOWN_ERROR.getDesc(), BusinessStatusEnum.getDescByCode(45));
    }
}
