package com.epam.demo.repository;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.java.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

/**
 * Created by zmazula on 09/11/16.
 */
@Configuration
@EnableCassandraRepositories(basePackages = "com.epam.demo.repository")
public class CassandraConfig extends AbstractCassandraConfiguration {

    @Override
    public String getKeyspaceName() {
        return "test_mid";
    }

    @Override
    protected String getContactPoints() {
        return "cassandra";
    }
}