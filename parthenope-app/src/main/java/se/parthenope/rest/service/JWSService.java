package se.parthenope.rest.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSASSASigner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.parthenope.rest.util.UidUtil;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;

@Service
public class JWSService {

    private static final String PUBLIC_KEY = "public-key";
    private static final String TTL = "ttl";
    private static final String ALGORITHM = "RSA";
    private static final int KEYSIZE = 2048;
    private static final long TTL_MINUTES = 30L;
    private static ObjectMapper mapper = new ObjectMapper();
    private final KeyPairGenerator keyGenerator;
    private final RSAPublicKey publicKey;
    private final RSAPrivateKey privateKey;
    private final JWSSigner signer;
    private TimeService timeService;

    @Autowired
    public JWSService(TimeService timeService) throws NoSuchAlgorithmException {

        this.timeService = timeService;
        keyGenerator = KeyPairGenerator.getInstance(ALGORITHM);
        keyGenerator.initialize(KEYSIZE);

        KeyPair kp = keyGenerator.genKeyPair();
        publicKey = (RSAPublicKey) kp.getPublic();
        privateKey = (RSAPrivateKey) kp.getPrivate();
        signer = new RSASSASigner(privateKey);
    }

    public RSAPublicKey getPublicKey() {
        return publicKey;
    }

    public String sign(Object obj) throws JsonProcessingException, JOSEException {

        JWSObject jwsObject = new JWSObject(
                new JWSHeader.Builder(JWSAlgorithm.RS256)
                        .keyID(UidUtil.uuid(PUBLIC_KEY))
                        .customParam(TTL, timeService.getTTL(Duration.ofMinutes(TTL_MINUTES))).build(), new Payload(mapper.writeValueAsString(obj)));

        jwsObject.sign(signer);

        return jwsObject.serialize();
    }


}
