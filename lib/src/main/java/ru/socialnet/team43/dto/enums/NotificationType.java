package ru.socialnet.team43.dto.enums;

import lombok.Getter;
import ru.socialnet.team43.dto.notifications.NotificationTypeDto;

@Getter
public enum NotificationType {
    LIKE(1, "LIKE", "Лайк"),
    POST(2, "POST", "Новый пост"),
    POST_COMMENT(3, "POST_COMMENT", "Комментарий к посту"),
    COMMENT_COMMENT(4, "COMMENT_COMMENT", "Ответ на комментарий"),
    MESSAGE(5, "MESSAGE", "Личное сообщение"),
    FRIEND_REQUEST(6, "FRIEND_REQUEST", "Запрос дружбы"),
    FRIEND_BIRTHDAY(7, "FRIEND_BIRTHDAY", "День рождения друга"),
    SEND_EMAIL_MESSAGE(8, "SEND_EMAIL_MESSAGE", "Отправка оповещений на email");

    private final NotificationTypeDto notificationTypeDto;

    NotificationType(int id, String code, String name) {
        notificationTypeDto = new NotificationTypeDto(id, code, name);
    }
}
