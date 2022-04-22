package com.example.temperaturebackend.event;

import com.example.temperaturebackend.entity.AuthUser;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class RegistrationCompleteEvent extends ApplicationEvent {

    private AuthUser authUser;
    private String applicationUrl;

    public RegistrationCompleteEvent(AuthUser authUser, String applicationUrl) {
        super(authUser);
        this.authUser = authUser;
        this.applicationUrl = applicationUrl;
    }
}
