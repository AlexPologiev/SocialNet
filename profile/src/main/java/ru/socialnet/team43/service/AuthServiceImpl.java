package ru.socialnet.team43.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    @Override
    public void login() {
        log.info("Signed in");
    }

    @Override
    public void logout() {
        log.info("Signed out");
    }
}
