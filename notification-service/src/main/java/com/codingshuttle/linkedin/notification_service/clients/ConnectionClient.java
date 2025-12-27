package com.codingshuttle.linkedin.notification_service.clients;

import com.codingshuttle.linkedin.notification_service.dto.PersonDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name="ConnectionService", path = "/connections")
public interface ConnectionClient {

    @GetMapping("/core/first/{userId}")
     List<PersonDto> getFirstConnections(@PathVariable Long userId);
}
