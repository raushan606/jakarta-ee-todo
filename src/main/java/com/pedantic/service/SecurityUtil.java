package com.pedantic.service;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.mail.util.ByteArrayDataSource;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RequestScoped
public class SecurityUtil {

    @Inject
    private QueryService queryService;

    public Key generateKey(String keyString) {
        return new SecretKeySpec(keyString.getBytes(), 0, keyString.getBytes().length, "DES");
    }

    public boolean authenticateUser(String email, String password) {
        return queryService.authenticateUser(email, password);
    }

    public Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public boolean passwordMatch(String dbStoredHashedPassword, String saltText, String clearTextPassword) {
        ByteSource salt = ByteSource.Util.bytes(Hex.decode(saltText));
        String hashedPassword = hashAndSaltPassword(clearTextPassword, salt);
        return hashedPassword.equals(dbStoredHashedPassword);
    }

    public Map<String, String> hashPassword(String clearTextPassword) {
        ByteSource salt = getSalt();
        Map<String, String> credMap = new HashMap<>();
        credMap.put("salt", salt.toHex());
        credMap.put("hashedPassword", hashAndSaltPassword(clearTextPassword, salt));
        return credMap;
    }

    private String hashAndSaltPassword(String clearTextPassword, ByteSource salt) {
        return new Sha256Hash(clearTextPassword, salt, 1024).toHex();
    }

    private ByteSource getSalt() {
        return new SecureRandomNumberGenerator().nextBytes();
    }
}
