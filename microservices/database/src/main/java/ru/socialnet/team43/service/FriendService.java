package ru.socialnet.team43.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.socialnet.team43.dto.FriendDto;
import ru.socialnet.team43.dto.PersonDto;
import ru.socialnet.team43.dto.enums.FriendshipStatus;
import ru.socialnet.team43.repository.FriendRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FriendService {

    private final FriendRepository friendRepo;

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

        return  friendRepo.findFriendsIdsByStatus(statusCode, email, page);


    }
}
