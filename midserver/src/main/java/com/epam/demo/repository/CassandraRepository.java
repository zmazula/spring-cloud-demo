package com.epam.demo.repository;

import com.epam.demo.model.MidBean;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.UUID;

/**
 * Created by Zoltan_Mazula on 11/3/2016.
 */
@RepositoryRestResource(collectionResourceRel = "mid", path = "mid")
public interface CassandraRepository extends CrudRepository<MidBean, UUID> {
}
