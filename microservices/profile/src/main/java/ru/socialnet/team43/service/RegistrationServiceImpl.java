package ru.socialnet.team43.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.socialnet.team43.client.UserAuthClient;
import ru.socialnet.team43.dto.PersonDto;
import ru.socialnet.team43.dto.RegDto;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@RequiredArgsConstructor
@Service
@Slf4j
public class RegistrationServiceImpl implements RegistrationService {
    private final UserAuthClient authClient;
    private final PasswordEncoder passwordEncoder;
    private final static String REG_PROCESS = "Registration process: ";

    @Value("${reg.person.isApproved}")
    private boolean isApproved;
    @Value("${reg.person.isBlocked}")
    private boolean isBlocked;
    @Value("${reg.person.messagePermission}")
    private String messagePermission;

    @Override
    public boolean registrationPerson(RegDto regDto) {
        String captchaCode = regDto.getCaptchaCode();
        String captchaSecret = regDto.getCaptchaSecret();
        String password1 = regDto.getPassword1();
        String password2 = regDto.getPassword2();
        String email = regDto.getEmail();

        if (email == null){
            log.info(REG_PROCESS + "email is null");
            return false;
        }

        if (isCaptchaCorrect(captchaCode, captchaSecret)
                && isPasswordsMatch(password1, password2)
                && !isEmailExisted(email)) {

            PersonDto personDto = regDtoToPersonDtoWithEncryptedPassword(regDto);

            return createPerson(personDto);
        }

        return false;
    }

    private boolean isPasswordsMatch(String password1, String password2) {
        if(password1 != null) {
            if (password1.equals(password2)) {
                log.info(REG_PROCESS + "password1 equals password2");
                return true;
            }
        }
        log.info(REG_PROCESS + "password1 does not equals password2");
        return false;

    }

    private boolean isCaptchaCorrect(String captchaCode, String captchaSecret) {
        if (captchaCode != null) {
            if (captchaCode.equals(captchaSecret)) {
                log.info(REG_PROCESS + "Captcha is correct");
                return true;
            }
        }
        log.info(REG_PROCESS + "Captcha is not correct");
        return false;
    }

    private boolean isEmailExisted(String email) {
        HttpStatusCode statusCode = authClient.isEmailExist(email).getStatusCode();

        if (statusCode.is2xxSuccessful()){
            log.info(REG_PROCESS + "Person with email ({}) has already existed," +
                    "registration failed",email);
            return true;
        }
        log.info(REG_PROCESS + "email is unique");
        return false;
    }

    private boolean createPerson(PersonDto personDto) {
        HttpStatusCode statusCode = authClient.createPerson(personDto).getStatusCode();
        if(statusCode.is2xxSuccessful()){
            log.info(REG_PROCESS + "Person has been registered");
            return true;
        }
        log.info(REG_PROCESS + "Person has not been registered, database does not answer");
        return false;
    }



    private PersonDto regDtoToPersonDtoWithEncryptedPassword(RegDto regDto){
        String encryptedPassword = passwordEncoder.encode(regDto.getPassword1());
        return PersonDto.builder()
                .firstName(regDto.getFirstName())
                .lastName(regDto.getLastName())
                .EMail(regDto.getEmail())
                .password(encryptedPassword)
                .lastOnlineTime(OffsetDateTime.now())
                .birthDate(LocalDateTime.now())
                .regDate(OffsetDateTime.now())
                .isApproved(isApproved)
                .isBlocked(isBlocked)
                .messagesPermission(messagePermission)
                .build();
    }


}
