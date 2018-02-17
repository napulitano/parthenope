package se.parthenope.se.parthenope.rest;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSASSASigner;
import org.springframework.stereotype.Component;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Component
public class JWSUtil {

    private static ObjectMapper mapper = new ObjectMapper();
    KeyPairGenerator keyGenerator;
    public RSAPublicKey publicKey;
    RSAPrivateKey privateKey;
    JWSSigner signer;

    public JWSUtil() throws NoSuchAlgorithmException {

        keyGenerator = KeyPairGenerator.getInstance("RSA");
        keyGenerator.initialize(2048);

        KeyPair kp = keyGenerator.genKeyPair();
        publicKey = (RSAPublicKey)kp.getPublic();
        privateKey = (RSAPrivateKey)kp.getPrivate();
        signer = new RSASSASigner(privateKey);
    }


    public String sign(Object obj) throws JsonProcessingException, JOSEException {

        JWSObject jwsObject = new JWSObject(
                new JWSHeader.Builder(JWSAlgorithm.RS256).keyID(IDUtil.uuid("public-key")).customParam("ttl",System.currentTimeMillis()+360000).build(),
                new Payload(mapper.writeValueAsString(obj)));

        jwsObject.sign(signer);

        String s = jwsObject.serialize();
        return s;
    }


}
