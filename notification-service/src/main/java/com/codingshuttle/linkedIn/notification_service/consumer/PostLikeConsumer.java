package com.codingshuttle.linkedIn.notification_service.consumer;

import com.codingshuttle.linkedIn.notification_service.clients.ConnectionClient;
import com.codingshuttle.linkedIn.notification_service.dto.PersonDto;
import com.codingshuttle.linkedIn.notification_service.entity.Notification;
import com.codingshuttle.linkedIn.notification_service.event.PostLikedEvent;
import com.codingshuttle.linkedIn.notification_service.respository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostLikeConsumer{

    private final ConnectionClient connectionClient;
    private final NotificationRepository notificationRepository;

    public PostLikeConsumer(ConnectionClient connectionClient, NotificationRepository notificationRepository) {
        this.connectionClient = connectionClient;
        this.notificationRepository = notificationRepository;
    }

    @KafkaListener(topics = "post-liked-topic")
    public void handlePostLike(PostLikedEvent postLikedEvent)
    {
        List<PersonDto> friends = connectionClient.getFirstConnections(postLikedEvent.getCreatorId());

        for(PersonDto friend : friends)
        {
            String message = "Your connection " + friend.getUserId() + " has liked your post " + postLikedEvent.getPostId();
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
