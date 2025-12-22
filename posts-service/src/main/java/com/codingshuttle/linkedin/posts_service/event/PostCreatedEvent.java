package com.codingshuttle.linkedin.posts_service.event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostCreatedEvent {
    private Long postId;
    private Long userId;
    private String content;
}
