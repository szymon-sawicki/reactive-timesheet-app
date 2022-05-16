package net.szymonsawicki.reactivetimesheetapp.application.service.utils;

import org.testcontainers.containers.MongoDBContainer;

public class AppMongoDbContainer extends MongoDBContainer {

    private static final String IMAGE_VERSION = "mongo:4.0.10";

    private static MongoDBContainer container;

    public static synchronized MongoDBContainer getInstance() {
        if (container == null) {
            container = new AppMongoDbContainer()
                    .withEnv("MONGO_DB_HOST", "mongodb")
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
        System.setProperty("spring.data.mongodb.host", container.getHost());
        System.setProperty("spring.data.mongodb.port", String.valueOf(container.getBoundPortNumbers()));
        System.setProperty("spring.data.mongodb.database", "db_1");
    }

    @Override
    public void stop() {
        // do nothing, JVM handles shut down
    }
}
