package com.epam.demo.service;

import com.epam.demo.model.MidBean;
import com.epam.demo.repository.CassandraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.UUID;

/**
 * Created by zmazula on 08/11/16.
 */

@RestController
@RequestMapping("/midBeans")
public class MidController {

    @Autowired
    public CassandraRepository cassandraRepository;

    @RequestMapping(method=RequestMethod.POST)
    public MidBean create(@RequestBody @Valid MidBean midBean) {
        MidBean newMidBean = cassandraRepository.save(midBean);
        return newMidBean;
    }

    @RequestMapping(method=RequestMethod.GET)
    public Iterable<MidBean> list() {
        return cassandraRepository.findAll();
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public MidBean get(@PathVariable("id") String id) {
        return cassandraRepository.findOne(UUID.fromString(id));
    }

    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    public MidBean update(@PathVariable("id") String id, @RequestBody @Valid MidBean midBean) {
        midBean.setMidId(UUID.fromString(id));
        return cassandraRepository.save(midBean);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<Boolean> delete(@PathVariable("id") String id) {
        this.cassandraRepository.delete(UUID.fromString(id));
        return new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.OK);
    }

}
