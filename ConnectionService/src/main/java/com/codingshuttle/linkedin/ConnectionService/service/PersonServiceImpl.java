package com.codingshuttle.linkedin.ConnectionService.service;

import com.codingshuttle.linkedin.ConnectionService.auth.UserContextHolder;
import com.codingshuttle.linkedin.ConnectionService.entity.Person;
import com.codingshuttle.linkedin.ConnectionService.event.ConnectionRequest;
import com.codingshuttle.linkedin.ConnectionService.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PersonServiceImpl {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private KafkaTemplate<Long,ConnectionRequest> producer;

    private static final Logger logger = LoggerFactory.getLogger(PersonServiceImpl.class);

    public List<Person> getFirstDegreeConnections(Long userId){
        return personRepository.getFirstDegreeConnections(userId);
    }

    public Boolean sendConnectionRequest(Long recieverId) {
        Long userId = UserContextHolder.getCurrentUserId();

        logger.info("Current userId is "+userId);

        if(userId == recieverId)
        {
            throw new RuntimeException("Sender and reciever are same");
        }

        if(personRepository.connectionExists(userId, recieverId)) {
            logger.info("Connection already exists between "+ userId + " and " + recieverId);
            return false;
        }

        if(personRepository.requestExists(userId,recieverId))
        {
            logger.info("Request already sent "+ userId + " and " + recieverId);
            return false;
        }

        personRepository.addConnectionRequest(userId, recieverId);

        ConnectionRequest connectionRequest = new ConnectionRequest();

        connectionRequest.setSenderId(userId);
        connectionRequest.setRecieverId(recieverId);

        producer.send("connection-requests-topic",connectionRequest);

        logger.info("Request successfully sent");

        return true;
    }

    public Boolean acceptConnectionRequest(Long recieverId) {
        Long userId = UserContextHolder.getCurrentUserId();

        logger.info("Current userId is "+userId);

        if(userId == recieverId)
        {
            throw new RuntimeException("Sender and reciever are same");
        }

        if(personRepository.connectionExists(userId, recieverId)) {
            logger.info("Connection already exists between "+ userId + " and " + recieverId);
            return false;
        }

        if(!personRepository.requestExists(userId,recieverId))
        {
            logger.info("Request doesn't exist for "+ userId + " and " + recieverId);
            return false;
        }

        personRepository.requestAccepted(userId,recieverId);

        logger.info("Request successfully accepted");

        return true;
    }

    public boolean cancelConnectionRequest(Long recieverId){
        Long userId = UserContextHolder.getCurrentUserId();

        logger.info("Current userId is "+userId);

        if(userId == recieverId)
        {
            throw new RuntimeException("Sender and reciever are same");
        }

        if(personRepository.connectionExists(userId, recieverId)) {
            logger.info("Connection already exists between "+ userId + " and " + recieverId);
            return false;
        }

        if(!personRepository.requestExists(userId,recieverId))
        {
            logger.info("Request doesn't exist for "+ userId + " and " + recieverId);
            return false;
        }

        personRepository.cancelRequest(userId,recieverId);

        logger.info("Request successfully cancelled");

        return true;
    }

}
