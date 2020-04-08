package org.trident.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class TridentLogger {

    private static final TridentLogger SINGLETON = new TridentLogger();

    public static TridentLogger getInstance(){
        return SINGLETON;
    }

    private Map<String, Logger> cacheLoggers = new HashMap<>();

    private TridentLogger() { }

    public void debug(Class clazz, String message){
        if(doCheckValue(clazz, message)){
            Logger logger = this.getLogger(clazz);
            logger.debug(message);
        }
    }

    public void error(Class clazz, String message){
        if(doCheckValue(clazz, message)){
            Logger logger = this.getLogger(clazz);
            logger.error(message);
        }
    }

    public void info(Class clazz, String message){
        if(doCheckValue(clazz, message)){
            Logger logger = this.getLogger(clazz);
            logger.info(message);
        }
    }

    private Logger getLogger(Class clazz) {
        if(clazz == null){
            throw new IllegalArgumentException("The class is null");
        }
        String key = clazz.getCanonicalName();
        if(cacheLoggers.containsKey(key)){
            return cacheLoggers.get(key);
        }
        Logger logger = LoggerFactory.getLogger(key);
        cacheLoggers.put(key, logger);
        return logger;
    }

    private boolean doCheckValue(Class clazz, String message){
        if(clazz == null || (message == null || message.isEmpty())){
            String errorMessage = "TODO";
            throw new IllegalArgumentException(errorMessage);
        }
        return true;
    }
}
