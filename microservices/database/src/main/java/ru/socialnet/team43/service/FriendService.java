package ru.socialnet.team43.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.socialnet.team43.dto.PersonDto;
import ru.socialnet.team43.repository.FriendRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendService {

    private final FriendRepository repository;

    public long getFriendsCount(String email) {
        return repository.getFriendsCount(email);
    }

    public List<PersonDto> getRecommendations(String email) {
        return repository.getRecommendations(email);
    }
}
