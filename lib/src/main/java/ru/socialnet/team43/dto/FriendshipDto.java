package ru.socialnet.team43.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.socialnet.team43.dto.enums.FriendshipStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FriendshipDto {
    private PersonDto srcPersonId;
    private PersonDto dstPersonId;
    private FriendshipStatus status;
}
