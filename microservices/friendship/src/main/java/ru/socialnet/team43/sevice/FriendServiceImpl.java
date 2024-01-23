package ru.socialnet.team43.sevice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.socialnet.team43.client.DatabaseClient;
import ru.socialnet.team43.dto.FriendDto;
import ru.socialnet.team43.dto.PersonDto;
import ru.socialnet.team43.dto.enums.FriendshipStatus;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService{
    private final DatabaseClient client;

    private final Random random = new Random();
    @Override
    public List<FriendDto> searchFriendsByStatus(String statusCode, String email, Pageable page) {
        List<Long> friendIds = client.searchFriendsByStatus(statusCode, email, page);
        return friendIds.stream()
                .map(friendId -> new FriendDto(FriendshipStatus.valueOf(statusCode),
                        friendId,
                        FriendshipStatus.NONE,
                        random.nextInt(10)))
                .collect(Collectors.toList());
    }

    @Override
    public List<PersonDto> searchFriends(String statusCode, String firstName, String ageFrom, String ageTo, String country, String city, String email, Pageable page) {
        return client.searchFriends(statusCode,
                                    firstName,
                                    ageFrom,
                                    ageTo,
                                    country,
                                    city,
                                    email,
                                    page);
    }

    @Override
    public ResponseEntity<FriendDto> approveFriendRequest(Long id, String email) {

         client.approveFriendRequest(id, email);

         return ResponseEntity.ok(new FriendDto(FriendshipStatus.FRIEND,
                 id,
                 FriendshipStatus.REQUEST_FROM,
                 1));
    }

    @Override
    public ResponseEntity<FriendDto> friendRequest(Long id, String email) {

        client.friendRequest(id, email);

        return ResponseEntity.ok(new FriendDto(FriendshipStatus.REQUEST_TO,
                id,
                FriendshipStatus.NONE,
                1));
    }

    @Override
    public ResponseEntity<FriendDto> getFriendsById(Long id, String email) {
        return client.getFriendsById(id, email);
    }

    @Override
    public ResponseEntity<FriendDto> subscribe(Long id, String email) {

        client.subscribe(id ,email);

        return ResponseEntity.ok(new FriendDto(FriendshipStatus.WATCHING,
                id,
                FriendshipStatus.NONE,
                1));

    }

    @Override
    public ResponseEntity<FriendDto> block(Long id, String email) {

        client.block(id ,email);

        return  ResponseEntity.ok(new FriendDto(FriendshipStatus.BLOCKED,
                id,
                FriendshipStatus.NONE,
                1));
    }

    @Override
    public ResponseEntity<FriendDto> unblock(Long id, String email) {

        client.unblock(id ,email);

        return  ResponseEntity.ok(new FriendDto(FriendshipStatus.NONE,
                id,
                FriendshipStatus.BLOCKED,
                1));
    }
}
