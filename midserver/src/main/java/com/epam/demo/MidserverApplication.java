package com.epam.demo;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;


@EnableCassandraRepositories
@SpringBootApplication
@EnableDiscoveryClient
public class MidserverApplication {


    @Autowired
    private CassandraOperations cassandraOperations;

	public static void main(String[] args) {

		SpringApplication.run(MidserverApplication.class, args);

	}
}
