package com.metis.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @Description: PropertyUtil
 * @Author: https://blog.csdn.net/u011958281/article/details/81531676
 * @Date: 2020-11-08 17:05
 * @Version: ing
 */
public class PropertyUtil {

    private static final Logger logger = LoggerFactory.getLogger(PropertyUtil.class);
    private static Properties props;

    static {
        loadProps();
    }

    synchronized static private void loadProps() {
        logger.info("start to load properties.......");
        props = new Properties();
        InputStream in = null;
        try {
            in = PropertyUtil.class.getClassLoader().
                    getResourceAsStream("application.properties");
            props.load(in);
        } catch (FileNotFoundException e) {
            logger.error("properties not found!");
        } catch (IOException e) {
            logger.error("IOException");
        } finally {
            try {
                if (null != in) {
                    in.close();
                }
            } catch (IOException e) {
                logger.error("properties close Exception!");
            }
        }
        // logger.info(props);
        logger.info("load properties over...........");
    }

    public static String getProperty(String key) {
        if (null == props) {
            loadProps();
        }
        return props.getProperty(key);
    }
}