package ru.socialnet.team43.sevice;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import ru.socialnet.team43.dto.FriendDto;
import ru.socialnet.team43.dto.PersonDto;

import java.util.List;

public interface FriendService {

    List<FriendDto> searchFriendsByStatus(String statusCode, String email, Pageable page);

    List<PersonDto> searchFriends(String statusCode,
                                  String  firstName,
                                  String  ageFrom,
                                  String  ageTo,
                                  String  country,
                                  String  city,
                                  String  email,
                                  Pageable page);

    ResponseEntity<FriendDto> approveFriendRequest(Long id, String email);

    ResponseEntity<FriendDto> friendRequest(Long id, String email);

    ResponseEntity<FriendDto> getFriendsById(Long id, String email);
}
