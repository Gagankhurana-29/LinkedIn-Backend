package com.codingshuttle.linkedIn.ConnectionService.repository;

import com.codingshuttle.linkedIn.ConnectionService.entity.Person;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends Neo4jRepository<Person, Long> {

    Optional<Person> findByName(String name);

    @Query("MATCH (p:Person)-[:CONNECTED_TO]-(friend:Person) WHERE p.userId = $userId RETURN friend")
    List<Person> getFirstDegreeConnections(Long userId);

}
