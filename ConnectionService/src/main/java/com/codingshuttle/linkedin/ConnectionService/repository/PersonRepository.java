package com.codingshuttle.linkedin.ConnectionService.repository;

import com.codingshuttle.linkedin.ConnectionService.entity.Person;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends Neo4jRepository<Person, Long> {

    Optional<Person> findByName(String name);

    @Query("MATCH (p:Person)-[:CONNECTED_TO]-(friend:Person) WHERE p.userId = $userId RETURN friend")
    List<Person> getFirstDegreeConnections(Long userId);

    @Query("MATCH (p:Person)-[r:CONNECTED_TO]-(friend:Person) WHERE p.userId = $senderId AND friend.userId = $recieverId RETURN COUNT(r) > 0")
    Boolean connectionExists(Long senderId, Long recieverId);

    @Query("MATCH (sender:Person)-[r:REQUESTED_TO]->(friend:Person) WHERE sender.userId = $senderId AND friend.userId = $recieverId RETURN COUNT(r) > 0")
    Boolean requestExists(Long senderId, Long recieverId);

    @Query("MATCH (sender:Person),(reciever:Person) WHERE " +
            "sender.userId=$senderId AND reciever.userId=$recieverId " +
            "CREATE (sender)-[:REQUESTED_TO]->(reciever)")
    void addConnectionRequest(Long senderId, Long recieverId);

    @Query("MATCH (sender:Person)-[r:REQUESTED_TO]->(friend:Person)" +
            " WHERE sender.userId = $senderId AND friend.userId = $recieverId " +
            "DELETE r " +
            "CREATE (sender)-[:CONNECTED_TO]-(reciever)")
    void requestAccepted(Long senderId, Long recieverId);

    @Query("MATCH (sender:Person)-[r:REQUESTED_TO]->(reciever:Person) " +
            "WHERE sender.userId = $senderId AND reciever.userId = $reciverId " +
            "DELETE r")
    void  cancelRequest(Long senderId, Long recieverId);

}
