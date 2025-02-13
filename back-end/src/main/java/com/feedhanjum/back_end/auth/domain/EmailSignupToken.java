package com.feedhanjum.back_end.auth.domain;


import com.feedhanjum.back_end.auth.exception.SignupTokenNotValidException;
import lombok.Getter;

import java.io.Serial;
import java.time.LocalDateTime;
import java.util.Random;

@Getter
public class EmailSignupToken implements java.io.Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public static final int TOKEN_LENGTH = 4;
    public static final int EXPIRE_MINUTE = 5;
    private final String email;
    private final String code;
    private final LocalDateTime expireDate;


    private EmailSignupToken(String email, String code) {
        this.email = email;
        this.code = code;
        expireDate = LocalDateTime.now().plusMinutes(EXPIRE_MINUTE);
    }

    private static String generateCode() {
        Random random = new Random();
        String token = "";
        for (int i = 0; i < TOKEN_LENGTH; i++) {
            token += Integer.toString(random.nextInt(10));
        }
        return token;
    }

    public static EmailSignupToken generateNewToken(String email) {
        return new EmailSignupToken(email, generateCode());
    }

    /**
     * @throws SignupTokenNotValidException 토큰 검증 실패
     */
    public void validateToken(String email, String code) {
        if (!(this.email.equals(email) && this.code.equals(code)))
            throw new SignupTokenNotValidException();
        if (LocalDateTime.now().isAfter(expireDate))
            throw new SignupTokenNotValidException();
    }
}
