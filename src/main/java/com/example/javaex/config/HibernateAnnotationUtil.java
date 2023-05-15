package com.example.javaex.config;

import com.example.javaex.user.UserModel;
import com.example.javaex.user.workout.WorkoutModel;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.util.HashMap;
import java.util.Map;

public class HibernateAnnotationUtil{

public static SessionFactory getSessionFactory() {

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
        .applySettings(dbSettings())
        .build();

        Metadata metadata = new MetadataSources(serviceRegistry)
        .addAnnotatedClass(UserModel.class)
                .addAnnotatedClass(WorkoutModel.class)
        .buildMetadata();

        return metadata.buildSessionFactory();
        }

private static Map<String, Object> dbSettings() {
    Map<String, Object> dbSettings = new HashMap<>();
    dbSettings.put(Environment.URL, "jdbc:postgresql://localhost:5432/postgres");
    dbSettings.put(Environment.USER, "baeldung");
    dbSettings.put(Environment.PASS, "baeldung");
    dbSettings.put(Environment.DRIVER, "org.hibernate.dialect.PostgreSQLDialect");
    dbSettings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
    dbSettings.put(Environment.SHOW_SQL, "true");
    dbSettings.put(Environment.HBM2DDL_AUTO, "create");
    return dbSettings;
        }
}