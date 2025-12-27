package com.codingshuttle.linkedin.notification_service.consumer;

import com.codingshuttle.linkedin.notification_service.clients.ConnectionClient;
import com.codingshuttle.linkedin.notification_service.dto.PersonDto;
import com.codingshuttle.linkedin.notification_service.entity.Notification;
import com.codingshuttle.linkedin.posts_service.event.PostLikedEvent;
import com.codingshuttle.linkedin.notification_service.respository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostLikeConsumer{

    private final ConnectionClient connectionClient;
    private final NotificationRepository notificationRepository;
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(PostLikeConsumer.class);

    public PostLikeConsumer(ConnectionClient connectionClient, NotificationRepository notificationRepository) {
        this.connectionClient = connectionClient;
        this.notificationRepository = notificationRepository;
    }


    @KafkaListener(topics = "post-liked-topic")
    public void handlePostLike(PostLikedEvent postLikedEvent)
    {
        long userId = postLikedEvent.getCreatorId();

        logger.info("Creator Id is "+ userId);

        List<PersonDto> friends = connectionClient.getFirstConnections(userId);

        for(PersonDto friend : friends)
        {
            String message = "Your connection " + friend.getUserId() + " has liked your post " + postLikedEvent.getPostId();
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
