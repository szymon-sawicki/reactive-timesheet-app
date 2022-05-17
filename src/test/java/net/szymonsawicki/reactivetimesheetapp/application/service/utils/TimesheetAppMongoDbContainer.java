package net.szymonsawicki.reactivetimesheetapp.application.service.utils;

import org.testcontainers.containers.MongoDBContainer;

public class TimesheetAppMongoDbContainer extends MongoDBContainer {

    private static final String IMAGE_VERSION = "mongo:4.0.10";

    private static MongoDBContainer container;

    public static synchronized MongoDBContainer getInstance() {
        if (container == null) {
            container = new TimesheetAppMongoDbContainer();
        }
        return container;
    }

    private TimesheetAppMongoDbContainer() {
        super(IMAGE_VERSION);
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void stop() {
        // do nothing, JVM handles shut down
    }
}
