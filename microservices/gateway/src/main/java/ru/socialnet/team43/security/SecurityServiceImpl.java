package ru.socialnet.team43.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.socialnet.team43.dto.RegDto;
import ru.socialnet.team43.exception.RefreshTokenException;
import ru.socialnet.team43.security.jwt.JwtUtils;
import ru.socialnet.team43.web.model.AuthRequest;
import ru.socialnet.team43.web.model.JwtResponse;
import ru.socialnet.team43.web.model.RefreshRequest;
import ru.socialnet.team43.web.model.SimpleResponse;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SecurityServiceImpl implements SecurityService
{
    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    private final UserDetailsService userDetailsService;

    private final PasswordEncoder passwordEncoder;

    private Map<String, String> refreshTokenCache = new HashMap<>();

    @Override
    public JwtResponse authenticateUser(AuthRequest authRequest)
    {
        Authentication authentication = authenticationManager.
                authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();

        return generateJwtResponse(userDetails);
    }

    @Override
    public JwtResponse refreshToken(RefreshRequest refreshRequest) throws RefreshTokenException
    {
        String refreshToken = refreshRequest.getRefreshToken();

        try
        {
            if(StringUtils.hasText(refreshToken) && jwtUtils.isRefreshTokenValid(refreshToken))
            {
                String userName = jwtUtils.getUserNameFromRefreshToken(refreshToken);

                String savedRefreshToken = refreshTokenCache.get(userName);

                if(StringUtils.hasText(savedRefreshToken) && savedRefreshToken.equals(refreshToken))
                {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(userName);

                    return generateJwtResponse(userDetails);
                }
                else
                {
                    throw new RefreshTokenException(refreshToken, "Refresh token not found");
                }
            }
            else
            {
                throw new RefreshTokenException(refreshToken, "Token is not valid");
            }
        }
        catch(Exception ex)
        {
            String tokenExceptionMessage = jwtUtils.logTokenExceptionMessage(ex);

            throw new RefreshTokenException(refreshToken, tokenExceptionMessage);
        }
    }

    @Override
    public SimpleResponse logout()
    {
        Object currentPrincipal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(currentPrincipal instanceof AppUserDetails)
        {
            UserDetails userDetails = (UserDetails) currentPrincipal;

            String userName = userDetails.getUsername();

            refreshTokenCache.remove(userName);

            return new SimpleResponse("User " + userName + " has been logged out!");
        }

        return new SimpleResponse("Not managed to logout!");
    }

    @Override
    public RegDto getRegDtoWithEncryptedPassword(RegDto inputDto)
    {
        RegDto regDtoWithEncryptedPassword = RegDto.builder()
                .isDeleted(inputDto.isDeleted())
                .email(inputDto.getEmail())
                .password1(passwordEncoder.encode(inputDto.getPassword1()))
                .password2(passwordEncoder.encode(inputDto.getPassword2()))
                .firstName(inputDto.getFirstName())
                .lastName(inputDto.getLastName())
                .captchaCode(inputDto.getCaptchaCode())
                .captchaSecret(inputDto.getCaptchaSecret())
                .build();

        return regDtoWithEncryptedPassword;
    }

    private JwtResponse generateJwtResponse(UserDetails userDetails)
    {
        String accessToken = jwtUtils.generateAccessToken(userDetails);

        String refreshToken = jwtUtils.generateRefreshToken(userDetails);

        refreshTokenCache.put(userDetails.getUsername(), refreshToken);

        JwtResponse jwtResponse = JwtResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken).build();

        return jwtResponse;
    }


}
