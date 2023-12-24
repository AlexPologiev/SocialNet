package ru.socialnet.team43.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import ru.socialnet.team43.dto.PersonDto;

public interface ProfileService {
    ResponseEntity<Page<PersonDto>> proceedSearch(
            String author,
            String firstName,
            String lastName,
            String city,
            String country,
            Boolean isDeleted,
            Integer ageTo,
            Integer ageFrom,
            String ids,
            Pageable pageable)
            throws Exception;
}
