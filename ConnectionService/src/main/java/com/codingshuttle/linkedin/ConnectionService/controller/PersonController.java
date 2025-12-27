package com.codingshuttle.linkedin.ConnectionService.controller;

import com.codingshuttle.linkedin.ConnectionService.entity.Person;
import com.codingshuttle.linkedin.ConnectionService.service.PersonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/core")
public class PersonController {

    @Autowired
    private PersonServiceImpl personService;

    @GetMapping("/first/{userId}")
    public ResponseEntity<List<Person>> getFirstConnections(@PathVariable Long userId){
        return ResponseEntity.ok(personService.getFirstDegreeConnections(userId));
    }

    @PostMapping("/send-invite/{userId}")
    public ResponseEntity<Boolean> sendConnectionRequest(@PathVariable Long userId)
    {
         return ResponseEntity.ok(personService.sendConnectionRequest(userId));
    }

    @PostMapping("/accept-invite/{userId}")
    public ResponseEntity<Boolean> acceptConnectionRequest(@PathVariable Long userId)
    {
        return ResponseEntity.ok(personService.acceptConnectionRequest(userId));
    }

    @PostMapping("/cancel-invite/{userId}")
    public ResponseEntity<Boolean> cancelInvite(@PathVariable Long userId)
    {
        return ResponseEntity.ok(personService.cancelConnectionRequest(userId));
    }
}
