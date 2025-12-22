package com.codingshuttle.linkedIn.notification_service.consumer;

import com.codingshuttle.linkedIn.notification_service.clients.ConnectionClient;
import com.codingshuttle.linkedIn.notification_service.dto.PersonDto;
import com.codingshuttle.linkedIn.notification_service.entity.Notification;
import com.codingshuttle.linkedIn.notification_service.event.PostCreatedEvent;
import com.codingshuttle.linkedIn.notification_service.respository.NotificationRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceConsumer
{

    private final ConnectionClient connectionClient;
    private final NotificationRepository notificationRepository;

    public PostServiceConsumer(ConnectionClient connectionClient, NotificationRepository notificationRepository) {
        this.connectionClient = connectionClient;
        this.notificationRepository = notificationRepository;
    }

    @KafkaListener(topics ="post-created-topic")
    public void handlePostCreated(PostCreatedEvent postCreatedEvent)
    {
        List<PersonDto> friends = connectionClient.getFirstConnections(postCreatedEvent.getUserId());

        for(PersonDto friend : friends)
        {
            String message = "Your connection " + postCreatedEvent.getUserId() +
                    " has created a new post ";
            sendNotification(friend.getUserId(), message);
        }
    }

    private void sendNotification(Long userId, String message)
    {
        Notification notification = new Notification();
        notification.setUserId(userId);
        notification.setMessage(message);
        notificationRepository.save(notification);
    }

}
