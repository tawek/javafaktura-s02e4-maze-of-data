package org.javafaktura.springdatademo;

import org.springframework.context.ApplicationListener;
import org.springframework.data.relational.core.mapping.event.BeforeSaveEvent;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PostBeforeSaveListener implements ApplicationListener<BeforeSaveEvent> {
    @Override
    public void onApplicationEvent(BeforeSaveEvent beforeSaveEvent) {
        if (beforeSaveEvent.getEntity() instanceof Post) {
//            Post entity = (Post) beforeSaveEvent.getEntity();
//            entity.setId(UUID.randomUUID().toString());
        }
    }
}
