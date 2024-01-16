package ru.socialnet.team43.dto.enums;

import lombok.Getter;
import lombok.ToString;

@ToString
public enum FriendshipStatus {

    REQUEST_FROM("Запрос на подписку"),
    REQUEST_TO("Запрос на добавление в друзья"),
    FRIEND("Друг"),
    BLOCKED("Пользователь в черном списке"),
    SUBSCRIBED("Подписан"),
    WATCHING("Проссматривал Вас"),
    NONE("Нет статуса");

    @Getter
    private final String description;

    FriendshipStatus(String description) {
        this.description = description;
    }
}
