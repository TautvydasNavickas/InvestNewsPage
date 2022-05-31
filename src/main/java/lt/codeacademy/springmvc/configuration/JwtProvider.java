package lt.codeacademy.springmvc.configuration;

import lombok.RequiredArgsConstructor;
import lt.codeacademy.springmvc.exception.InvestAccountNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;
import java.time.Instant;

import static io.jsonwebtoken.Jwts.parser;
@Service
@RequiredArgsConstructor
public class JwtProvider {
//    private KeyStore keyStore;
//
//    @PostConstruct
//    public void init() {
//        try {
//            keyStore = KeyStore.getInstance("JKS");
//            InputStream resourceAsStream = getClass().getResourceAsStream("/springblog.jks");
//            keyStore.load(resourceAsStream, "secret".toCharArray());
//        } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
//            throw new InvestAccountNotFoundException("Exception occurred while loading keystore");
//        }
//
//    }
//
//    public String generateToken(Authentication authentication) {
//        org.springframework.security.core.userdetails.User principal = (User) authentication.getPrincipal();
//        return Jwts.builder()
//                .setSubject(principal.getUsername())
//                .signWith(getPrivateKey())
//                .compact();
//    }
//
//    private Key getPrivateKey() {
//        try {
//            return (PrivateKey) keyStore.getKey("springblog", "secret".toCharArray());
//        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
//            throw new InvestAccountNotFoundException("Exception occured while retrieving public key from keystore");
//        }
//    }
//    public boolean validateToken(String jwt) {
//        parser().setSigningKey(getPublickey()).parseClaimsJws(jwt);
//        return true;
//    }
//
//    private PublicKey getPublickey() {
//        try {
//            return keyStore.getCertificate("springblog").getPublicKey();
//        } catch (KeyStoreException e) {
//            throw new InvestAccountNotFoundException("Exception occured while retrieving public key from keystore");
//        }
//    }
//
//    public String getUsernameFromJWT(String token) {
//        Claims claims = parser()
//                .setSigningKey(getPublickey())
//                .parseClaimsJws(token)
//                .getBody();
//
//        return claims.getSubject();
//    }
private final JwtEncoder jwtEncoder;
    @Value("${jwt.expiration.time}")
    private Long jwtExpirationInMillis;

    public String generateToken(Authentication authentication) {
        User principal = (User) authentication.getPrincipal();
        return generateTokenWithUserName(principal.getUsername());
    }

    public String generateTokenWithUserName(String username) {
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusMillis(jwtExpirationInMillis))
                .subject(username)
                .claim("scope", "ROLE_USER")
                .build();

        return this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public Long getJwtExpirationInMillis() {
        return jwtExpirationInMillis;
    }
}
