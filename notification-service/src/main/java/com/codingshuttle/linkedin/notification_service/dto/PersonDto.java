package com.codingshuttle.linkedin.notification_service.dto;

import lombok.Data;

@Data
public class PersonDto
{
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private Long id;

    private Long userId;

    private String name;
}
