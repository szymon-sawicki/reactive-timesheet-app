package net.szymonsawicki.reactivetimesheetapp.application.service.exception;

public class TimeEntryServiceException extends RuntimeException {
    public TimeEntryServiceException(String message) {
        super(message);
    }
}
