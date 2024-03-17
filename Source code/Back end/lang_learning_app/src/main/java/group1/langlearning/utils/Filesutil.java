package group1.langlearning.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.stereotype.Component;

// @Component
public class Filesutil {

    private static final String PROP_FILE = "/application.properties";
    private static final Properties props = new Properties();

    static {
        InputStream is = Filesutil.class.getClassLoader().getResourceAsStream(PROP_FILE);
        try {
            props.load(is);
        } catch (IOException e) {
            // Handle exception
        }
    }

    public String getProperty(String key) {
        return props.getProperty(key);
    }
}
