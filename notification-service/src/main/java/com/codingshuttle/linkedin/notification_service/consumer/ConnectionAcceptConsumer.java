package com.codingshuttle.linkedin.notification_service.consumer;

import com.codingshuttle.linkedin.ConnectionService.event.ConnectionRequest;
import com.codingshuttle.linkedin.notification_service.entity.Notification;
import com.codingshuttle.linkedin.notification_service.respository.NotificationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

public class ConnectionAcceptConsumer {

    @Autowired
    private NotificationRepository notificationRepository;

    private static final Logger logger = LoggerFactory.getLogger(ConnectionAcceptConsumer.class);

    @KafkaListener(topics = "connection-accept-topic")
    public void handleConnectionRequests(ConnectionRequest connectionRequest)
    {
        String message = "Accepted the connection request of " + connectionRequest.getSenderId() +
                " for reciever "+ connectionRequest.getRecieverId();

        logger.info(message);

        sendNotification(connectionRequest.getSenderId(),message);
    }

    private void sendNotification(Long userId, String message)
    {
        Notification notification = new Notification();
        notification.setUserId(userId);
        notification.setMessage(message);
        notificationRepository.save(notification);
    }
}
