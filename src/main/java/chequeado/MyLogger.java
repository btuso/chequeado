package chequeado;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.UUID;

public class MyLogger {

    public static final String REQUEST_ID = "request_id";

    private final Logger logger;

    private MyLogger(Class classToLog){
        this.logger = LoggerFactory.getLogger(classToLog.getSimpleName());
    }

    public static MyLogger logger(Class classToLog){
        return new MyLogger(classToLog);
    }

    public void setRequestTag(){
        String uuid = String.valueOf(UUID.randomUUID());
        String shortId = uuid.split("-")[0];
        MDC.put(REQUEST_ID, shortId);
    }

    public void clearRequestTag(){
        MDC.remove(REQUEST_ID);
    }

    public void debug(String message, Object ...args){
        this.logger.debug(getMdcTag() + message, args);
    }

    public void info(String message, Object ...args){
        this.logger.info(getMdcTag() + message, args);
    }

    public void error(String message, Object... args) {
        this.logger.error(getMdcTag() + message, args);
    }

    private String getMdcTag(){
        return MDC.get(REQUEST_ID) + " | ";
    }


}
