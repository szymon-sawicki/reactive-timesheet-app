package net.szymonsawicki.reactivetimesheetapp.application.service.utils;

import org.testcontainers.containers.MongoDBContainer;

public class AppMongoDbContainer extends MongoDBContainer {

    private static final String IMAGE_VERSION = "mongo:4.0.10";

    private static MongoDBContainer container;

    public static synchronized MongoDBContainer getInstance() {
        if (container == null) {
            container = new AppMongoDbContainer()
                    .withExposedPorts(27017);
        }
        return container;
    }

    private AppMongoDbContainer() {
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
