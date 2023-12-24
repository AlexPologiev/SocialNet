package ru.socialnet.team43.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.socialnet.team43.client.DatabaseClient;
import ru.socialnet.team43.dto.AccountSearchDto;
import ru.socialnet.team43.dto.PersonDto;
import ru.socialnet.team43.service.geo.GeoService;
import ru.socialnet.team43.util.ControllerUtil;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfileServiceImpl implements ProfileService {

    private static final String EMAIL_REGEX = "^(.+)@(\\S+)$";
    private final DatabaseClient databaseClient;
    private final GeoService geoService;

    @Override
    public ResponseEntity<Page<PersonDto>> proceedSearch(
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
            throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        ResponseEntity<Page<PersonDto>> response;

        AccountSearchDto accountSearchDto =
                generateSearchDtoFromSeparateFields(
                        firstName.toLowerCase(),
                        lastName.toLowerCase(),
                        city.toLowerCase(),
                        country.toLowerCase(),
                        isDeleted,
                        ageTo,
                        ageFrom);

        if (accountSearchDto == null) {
            accountSearchDto = generateSearchDtoFromSingleField(author.toLowerCase(), isDeleted);
        }

        if (accountSearchDto != null) {
            response =
                    databaseClient.getAccountsSearchResult(
                            mapper.writeValueAsString(accountSearchDto), pageable);
        } else {
            response = ResponseEntity.ok(new PageImpl<>(Collections.emptyList()));
        }

        return response;
    }

    private AccountSearchDto generateSearchDtoFromSeparateFields(
            String firstName,
            String lastName,
            String city,
            String country,
            Boolean isDeleted,
            Integer ageTo,
            Integer ageFrom) {
        boolean isDtoGenerated = false;
        AccountSearchDto.AccountSearchDtoBuilder dtoBuilder = AccountSearchDto.builder();

        dtoBuilder.isDeleted(isDeleted);

        if (StringUtils.hasText(firstName)) {
            dtoBuilder.firstName(Collections.singleton(firstName));
            isDtoGenerated = true;
        }

        if (StringUtils.hasText(lastName)) {
            dtoBuilder.lastName(Collections.singleton(lastName));
            isDtoGenerated = true;
        }

        if (StringUtils.hasText(city)) {
            dtoBuilder.city(Collections.singleton(city));
            isDtoGenerated = true;
        }

        if (StringUtils.hasText(country)) {
            dtoBuilder.country(Collections.singleton(country));
            isDtoGenerated = true;
        }

        if (ageFrom != 0) {
            dtoBuilder.ageFrom(ageFrom);
            isDtoGenerated = true;
        }

        if (ageTo != 0) {
            dtoBuilder.ageTo(ageTo);
            isDtoGenerated = true;
        }

        AccountSearchDto accountSearchDto = isDtoGenerated ? dtoBuilder.build() : null;

        return accountSearchDto;
    }

    private AccountSearchDto generateSearchDtoFromSingleField(String author, Boolean isDeleted)
            throws Exception {
        if (!StringUtils.hasText(author)) {
            return null;
        }

        AccountSearchDto.AccountSearchDtoBuilder dtoBuilder = AccountSearchDto.builder();

        dtoBuilder.isDeleted(isDeleted);

        String[] searchStringFragmentsArr = author.split("\\s+");
        Set<String> searchStringFragments = new HashSet<>();
        Collections.addAll(searchStringFragments, searchStringFragmentsArr);

        Set<String> possibleEmails =
                searchStringFragments.stream()
                        .filter(fragment -> fragment.matches(EMAIL_REGEX))
                        .collect(Collectors.toSet());

        if (!possibleEmails.isEmpty()) {
            dtoBuilder.author(possibleEmails);
        } else {
            Set<Integer> intValues = segregateIntValues(searchStringFragments);
            fillSearchedAge(dtoBuilder, intValues);
            fillSearchedLocations(dtoBuilder, searchStringFragments);
            dtoBuilder.firstName(searchStringFragments);
            dtoBuilder.lastName(searchStringFragments);
        }

        return dtoBuilder.build();
    }

    private void fillSearchedLocations(
            AccountSearchDto.AccountSearchDtoBuilder dtoBuilder, Set<String> searchStringFragments)
            throws Exception {
        List<String> requestedCountries =
                geoService.getCountriesTitlesByPossibleTitles(searchStringFragments);

        if (requestedCountries != null && !requestedCountries.isEmpty()) {
            dtoBuilder.country(
                    requestedCountries.stream()
                            .map(city -> city.toLowerCase())
                            .collect(Collectors.toSet()));
        }

        List<String> requestedCities =
                geoService.getCitiesTitlesByPossibleTitles(searchStringFragments);
        if (requestedCities != null && !requestedCities.isEmpty()) {
            dtoBuilder.city(
                    requestedCities.stream()
                            .map(city -> city.toLowerCase())
                            .collect(Collectors.toSet()));
        }
    }

    private void fillSearchedAge(
            AccountSearchDto.AccountSearchDtoBuilder dtoBuilder, Set<Integer> intValues) {
        if (!intValues.isEmpty()) {
            int age =
                    intValues.stream()
                            .filter(intVal -> intVal > 0 && intVal < 150)
                            .findFirst()
                            .get();
            if (age != 0) {
                dtoBuilder.ageFrom(age - 1);
                dtoBuilder.ageTo(age + 1);
            }
        }
    }

    private Set<Integer> segregateIntValues(Set<String> searchStringFragments) {
        Map<String, Integer> intValues = new HashMap<>();

        for (String fragment : searchStringFragments) {
            try {
                Integer intVal = Integer.parseInt(fragment);
                intValues.put(fragment, intVal);
            } catch (NumberFormatException ex) {
                log.info("\"" + fragment + "\" is not a number");
            }
        }

        searchStringFragments.removeAll(intValues.keySet());

        return new HashSet<>(intValues.values());
    }
}
