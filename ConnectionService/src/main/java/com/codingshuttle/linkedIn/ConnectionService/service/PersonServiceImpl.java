package com.codingshuttle.linkedIn.ConnectionService.service;

import com.codingshuttle.linkedIn.ConnectionService.entity.Person;
import com.codingshuttle.linkedIn.ConnectionService.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PersonServiceImpl {

    @Autowired
    private PersonRepository personRepository;

    public List<Person> getFirstDegreeConnections(Long userId){
        //log.info("User is ${userId}");
        return personRepository.getFirstDegreeConnections(userId);
    }

}
