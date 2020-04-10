package org.trident.util;

import java.util.Properties;

/**
 * @author https://github.com/vincenzopalazzo
 */
public class ConfiguratorSingleton {

    //Called pattern Singleton fin on the web
    private static ConfiguratorSingleton SINGLETON;
    //tag for logger
    private static final Class TAG = ConfiguratorSingleton.class;

    public static ConfiguratorSingleton getInstance(){
        if(SINGLETON == null){
            SINGLETON = new ConfiguratorSingleton();
        }
        return SINGLETON;
    }

    private Properties config;

    private ConfiguratorSingleton() {
        loadProperties();
    }

    private void loadProperties() {
        //Do nothing for moment
    }

    public String getPropriety(Constant key){
        //do nothing for moment
        return "";
    }

    public void setPropriety(Constant key, String value){
        if(value == null || value.isEmpty()){
            TridentLogger.getInstance().error(TAG, "TODO build a good message");
            throw new IllegalArgumentException("TODO build a good message");
        }
        TridentLogger.getInstance().debug(TAG, "Added propriety with key: "
                + key.toString() + " and value: " + value);
        config.setProperty(key.toString(), value);
    }


}
