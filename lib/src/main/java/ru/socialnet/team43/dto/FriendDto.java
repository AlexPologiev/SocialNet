package ru.socialnet.team43.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.socialnet.team43.dto.enums.FriendshipStatus;
import ru.socialnet.team43.dto.enums.StatusCode;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FriendDto {

    private FriendshipStatus statusCode;
    private Long friendId;
    private FriendshipStatus previousStatusCode;
    private Integer rating;
}
