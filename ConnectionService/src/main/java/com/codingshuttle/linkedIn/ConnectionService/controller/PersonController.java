package com.codingshuttle.linkedIn.ConnectionService.controller;

import com.codingshuttle.linkedIn.ConnectionService.entity.Person;
import com.codingshuttle.linkedIn.ConnectionService.service.PersonServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/core")
public class PersonController {

    @Autowired
    private PersonServiceImpl personService;

    @GetMapping("/connections/first/{userId}")
    public ResponseEntity<List<Person>> getFirstConnections(@PathVariable Long userId){
        return ResponseEntity.ok(personService.getFirstDegreeConnections(userId));
    }
}
