package edu.codespring.bibliospring.backend.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyProvider {
    private static Properties properties = new Properties();
    private static Logger LOG = LoggerFactory.getLogger(PropertyProvider.class);

    static{
        try(InputStream is = PropertyProvider.class.getResourceAsStream("/bibliospring.properties")){
            properties.load(is);
        } catch (IOException e){
            LOG.error("Connot read configuration file",e);
            throw new RuntimeException(e);
        }
    }

    public static String getProperty(String key){
        return properties.getProperty(key);
    }
}
