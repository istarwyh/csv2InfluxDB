package com.metis.config.exception;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class ExceptionMsgEnumTest {
    private static final Logger logger = LoggerFactory.getLogger(ExceptionMsgEnumTest.class);

    @Test
    void getException() {
        try {
            ExceptionMsgEnum.NULL_VALUE.assertNotNull(null);
        } catch (NonBusinessRuntimeException ex) {
            logger.info(ex.getMessage());
        }
    }
}
