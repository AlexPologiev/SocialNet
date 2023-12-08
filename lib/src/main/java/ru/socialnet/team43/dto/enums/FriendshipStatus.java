package ru.socialnet.team43.dto.enums;

import lombok.Getter;
import lombok.ToString;

@ToString
public enum FriendshipStatus {

    REQUEST("Запрос на добавление в друзья"),
    FRIEND("Друг"),
    BLOCKED("Пользователь в черном списке"),
    DECLINED("Запрос на добавление в друзья отклонен"),
    SUBSCRIBED("Подписан");

    @Getter
    private final String description;

    FriendshipStatus(String description) {
        this.description = description;
    }
}
