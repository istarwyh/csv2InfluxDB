package com.metis.paas;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CrawlTextTest {
    @Test
    void test1() throws Exception{
        CrawlText.getText(true, true, "http://www.biquge.com/");
    }

}