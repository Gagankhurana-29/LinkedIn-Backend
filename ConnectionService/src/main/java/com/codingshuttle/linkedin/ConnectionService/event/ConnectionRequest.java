package com.codingshuttle.linkedin.ConnectionService.event;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


public class ConnectionRequest {
        private Long senderId;
        private Long recieverId;

    public Long getRecieverId() {
        return recieverId;
    }

    public void setRecieverId(Long recieverId) {
        this.recieverId = recieverId;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }
}
