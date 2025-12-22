package com.codingshuttle.linkedIn.notification_service.event;

import lombok.Builder;
import lombok.Data;

@Data
public class PostLikedEvent {
    private Long postId;
    private Long creatorId;
    private Long likedByUserId;

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public Long getLikedByUserId() {
        return likedByUserId;
    }

    public void setLikedByUserId(Long likedByUserId) {
        this.likedByUserId = likedByUserId;
    }
}
