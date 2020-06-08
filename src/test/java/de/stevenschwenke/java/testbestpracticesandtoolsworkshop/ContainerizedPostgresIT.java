package de.stevenschwenke.java.testbestpracticesandtoolsworkshop;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.DriverManager;

import static org.junit.jupiter.api.Assertions.assertTrue;

/*
 Code from https://www.hascode.com/2019/01/using-throwaway-containers-for-integration-testing-with-java-junit-5-and-testcontainers/
 */
@Testcontainers
public class ContainerizedPostgresIT {

    @Container
    private PostgreSQLContainer postgresqlContainer = new PostgreSQLContainer()
            .withDatabaseName("mydb")
            .withUsername("user")
            .withPassword("secret");

    @Test
    @DisplayName("postgres should be running")
    void shouldBeRunningPostgres() throws Exception {
        System.out.printf("postgres db running, db-name: '%s', user: '%s', jdbc-url: '%s'%n ",
                postgresqlContainer.getDatabaseName(),
                postgresqlContainer.getUsername(),
                postgresqlContainer.getJdbcUrl());
        assertTrue(postgresqlContainer.isRunning());
    }

    @Test
    @DisplayName("should write and read from database")
    void shouldReadFromDatabase() throws Exception {
        Class.forName("org.postgresql.Driver");
        var connection = DriverManager
                .getConnection(postgresqlContainer.getJdbcUrl(), postgresqlContainer.getUsername(),
                        postgresqlContainer.getPassword());
        connection.prepareStatement("CREATE DATABASE article_db").execute();
        connection.prepareStatement("CREATE table articles (title VARCHAR , url VARCHAR)").execute();
        connection.prepareStatement("INSERT INTO articles VALUES('Implementing Reactive Client-Server Communication over TCP or Websockets with RSocket and Java','https://www.hascode.com/2018/11/implementing-reactive-client-server-communication-over-tcp-or-websockets-with-rsocket-and-java/')").execute();
        connection.prepareStatement("INSERT INTO articles VALUES('Setting up Kafka Brokers for Testing with Kafka-Unit','https://www.hascode.com/2018/03/setting-up-kafka-brokers-for-testing-with-kafka-unit/')").execute();
        connection.prepareStatement("INSERT INTO articles VALUES('Managing Architecture Decision Records with ADR-Tools','https://www.hascode.com/2018/05/managing-architecture-decision-records-with-adr-tools/')").execute();

        var result = connection
                .prepareStatement("SELECT title,url FROM articles ORDER BY title ASC").executeQuery();
        result.next();
        assertTrue(result.getString("title").equals("Implementing Reactive Client-Server Communication over TCP or Websockets with RSocket and Java"));
        result.next();
        assertTrue(result.getString("title").equals("Managing Architecture Decision Records with ADR-Tools"));
        result.next();
        assertTrue(result.isLast() && result.getRow() == 3);
    }

}
