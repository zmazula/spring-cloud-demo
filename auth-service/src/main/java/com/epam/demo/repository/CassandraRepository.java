package com.epam.demo.repository;

import com.epam.demo.model.AuthToken;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by zmazula on 09/11/16.
 */
public interface CassandraRepository extends CrudRepository<AuthToken, String> {

}
