package ru.socialnet.team43.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.socialnet.team43.dto.enums.PostType;

import java.time.OffsetDateTime;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {

    private OffsetDateTime time;
    private OffsetDateTime timeChanged;
    private Long authorId;
    private String title;
    private PostType type;
    private String postText;
    private Boolean isBlocked;
    private Integer commentsCount;
    private String reactionType;
    private String myReaction;
    private Integer likeAmount;
    private Boolean myLike;
    private List<String> imagesPaths;
    private List<TagDto> tags;

}
