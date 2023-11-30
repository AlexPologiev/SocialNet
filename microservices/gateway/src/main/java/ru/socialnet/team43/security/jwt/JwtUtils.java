package ru.socialnet.team43.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Date;

@Component
@Slf4j
public class JwtUtils
{
    private String accessSecret;
    private String refreshSecret;

    private Duration accessTokenLifetime;
    private Duration refreshTokenLifetime;

    private final SecretKey accessKey;
    private final SecretKey refreshKey;

    public JwtUtils(@Value("${app.jwt.accessSecret}") String accessSecret,
                    @Value("${app.jwt.refreshSecret}") String refreshSecret,
                    @Value("${app.jwt.accessTokenLifeTime}") Duration accessTokenLifetime,
                    @Value("${app.jwt.accessTokenLifeTime}") Duration refreshTokenLifetime)
    {
        this.accessSecret = accessSecret;
        this.refreshSecret = refreshSecret;
        this.accessTokenLifetime = accessTokenLifetime;
        this.refreshTokenLifetime = refreshTokenLifetime;
        this.accessKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(accessSecret));
        this.refreshKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(refreshSecret));
    }

    public String generateAccessToken(UserDetails userDetails)
    {
        JwtBuilder tokenBuilder = tokenCommonDataBuilder(userDetails);

        return tokenBuilder
                .claim("roles", userDetails.getAuthorities())
                .signWith(accessKey)
                .compact();
    }

    public String generateRefreshToken(UserDetails userDetails)
    {
        JwtBuilder tokenBuilder = tokenCommonDataBuilder(userDetails);

        return tokenBuilder
                .signWith(refreshKey)
                .compact();
    }


    private JwtBuilder tokenCommonDataBuilder(UserDetails userDetails)
    {
        Date issueTime = new Date();
        Date expirationTime = new Date(issueTime.getTime() + accessTokenLifetime.toMillis());

        return Jwts.builder()
                .header()
                .empty()
                .add("typ", "JWT")
                .and()
                .subject(userDetails.getUsername())
                .issuedAt(issueTime)
                .expiration(expirationTime);
    }



    public boolean isAccessTokenValid(String accessToken)
    {
        boolean isAccessTokenValid = false;
        try
        {
            isAccessTokenValid = isTokenValid(accessToken, accessKey);
        }
        catch (Exception ex)
        {
            logTokenExceptionMessage(ex);
        }

        return isAccessTokenValid;
    }

    public boolean isRefreshTokenValid(String refreshToken) throws Exception
    {
        return isTokenValid(refreshToken, refreshKey);
    }

    public String getUserNameFromAccessToken(String accessToken)
    {
        return getUserNameFromToken(accessToken, accessKey);
    }

    public String getUserNameFromRefreshToken(String refreshToken)
    {
        return getUserNameFromToken(refreshToken, refreshKey);
    }

    private String getUserNameFromToken(String token, SecretKey secretKey)
    {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    private boolean isTokenValid(String token, SecretKey secretKey) throws Exception
    {
        Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token);

        return true;
    }

    public String logTokenExceptionMessage(Exception ex)
    {
        String returningMessage = "";
        String logMessageSuffix = ": {}";
        if(ex instanceof SignatureException)
        {
            returningMessage = "Invalid signature";
        }
        else if(ex instanceof MalformedJwtException)
        {
            returningMessage = "Invalid token";
        }
        else if(ex instanceof ExpiredJwtException)
        {
            returningMessage = "Token is expired";
        }
        else if(ex instanceof UnsupportedJwtException)
        {
            returningMessage = "Token is unsupported";
        }
        else if(ex instanceof IllegalArgumentException)
        {
            returningMessage = "Claims string is empty";
        }
        else
        {
            logMessageSuffix = "";
        }

        log.error(returningMessage + logMessageSuffix, ex);

        return returningMessage;
    }

    public void generateKeys()
    {
        String[] keyNames = new String[] {"accessKey", "refreshKey"};
        for(String keyName : keyNames)
        {
            SecretKey key = Jwts.SIG.HS512.key().build();
            String secretString = Encoders.BASE64.encode(key.getEncoded());
            System.out.println(keyName + " : " + secretString);
        }
    }
}
