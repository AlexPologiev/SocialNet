package ru.socialnet.team43.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.socialnet.team43.dto.FriendDto;
import ru.socialnet.team43.dto.PersonDto;
import ru.socialnet.team43.repository.FriendRepository;
import ru.socialnet.team43.repository.PersonRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FriendService {

    private final FriendRepository friendRepo;
    private final PersonRepository personRepo;

    public long getFriendsCount(String email) {
        return friendRepo.getFriendsCount(email);
    }

    public List<FriendDto> getRecommendations(String email) {
        return friendRepo.getRecommendations(email);
    }

    public List<PersonDto> searchFriends(String status,
                                         String firstName,
                                         Integer ageFrom,
                                         Integer ageTo,
                                         String country,
                                         String city,
                                         String email,
                                         Pageable page) {

        return friendRepo.searchFriends(status, firstName, ageFrom, ageTo, country, city, email, page);
    }

    public List<Long> searchFriendsByStatus(String statusCode, String email, Pageable page) {

        return friendRepo.findFriendsIdsByStatus(statusCode, email, page);
    }

    public void approveFriendRequest(Long id, String email) {
        Long accountId = personRepo.getPersonIdByEmail(email);
        String friendStatus = friendRepo.getStatus(accountId, id);

        if (friendStatus.equals("REQUEST_FROM")) {
            friendRepo.updateStatus(accountId, id, "FRIEND");
        }
    }

    public void deleteFriend(Long id, String email) {
        Long accountId = personRepo.getPersonIdByEmail(email);
        friendRepo.deleteFriendship(id, accountId);
    }

    public void friendRequest(Long id, String email) {
        Long accountId = personRepo.getPersonIdByEmail(email);
        String friendStatus = friendRepo.getStatus(accountId, id);

        if (friendStatus.equals("REQUEST_FROM")) {
            friendRepo.updateStatus(accountId, id, "FRIEND");
        } else if (!friendStatus.equals("FRIEND")) {
            friendRepo.deleteFriendship(id, accountId);
            friendRepo.saveFriendship(accountId, id, "REQUEST_TO", "REQUEST_FROM");
        }
    }

    public ResponseEntity<FriendDto> getFriendsById(Long id, String email) {

        Optional<FriendDto> optionalFriendDto = friendRepo.getOptionalFriendDtoById(id, email);

        return optionalFriendDto.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    public void subscribe(Long id, String email) {
        Long accountId = personRepo.getPersonIdByEmail(email);
        friendRepo.deleteFriendship(id, accountId);
        friendRepo.saveFriendship(accountId, id, "WATCHING", "SUBSCRIBED");
    }
}
