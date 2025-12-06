package org.bananalaba.teamsports.ingest;

public class DataIngestionException extends RuntimeException {

    public DataIngestionException(String message) {
        super(message);
    }

    public DataIngestionException(String message, Throwable cause) {
        super(message, cause);
    }

}
