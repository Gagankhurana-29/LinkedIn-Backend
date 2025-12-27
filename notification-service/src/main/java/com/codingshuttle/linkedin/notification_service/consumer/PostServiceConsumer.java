package com.codingshuttle.linkedin.notification_service.consumer;

import com.codingshuttle.linkedin.notification_service.clients.ConnectionClient;
import com.codingshuttle.linkedin.notification_service.dto.PersonDto;
import com.codingshuttle.linkedin.notification_service.entity.Notification;
import com.codingshuttle.linkedin.posts_service.event.PostCreatedEvent;
import com.codingshuttle.linkedin.notification_service.respository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceConsumer
{

    private final ConnectionClient connectionClient;
    private final NotificationRepository notificationRepository;
    private static final Logger logger = LoggerFactory.getLogger(PostServiceConsumer.class);

    public PostServiceConsumer(ConnectionClient connectionClient, NotificationRepository notificationRepository) {
        this.connectionClient = connectionClient;
        this.notificationRepository = notificationRepository;
    }


    @KafkaListener(topics ="post-created-topic")
    public void handlePostCreated(PostCreatedEvent postCreatedEvent)
    {
        Long userId = postCreatedEvent.getUserId();

        logger.info("The user Id is " + userId);

        List<PersonDto> friends = connectionClient.getFirstConnections(userId);
        for(PersonDto friend : friends)
        {
            String message = "Your connection " + postCreatedEvent.getUserId() +
                    " has created a new post ";

            logger.info(message);
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
