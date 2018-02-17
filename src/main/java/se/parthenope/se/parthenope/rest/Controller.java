package se.parthenope.se.parthenope.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.RSAKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class Controller {

    @Autowired
    private DomainService domainService;
    @Autowired
    private JWSUtil jwsUtil;

    @GetMapping("/apis/generate/key/{domain}")
    public Mono<ResponseEntity<ApiKey>> apiKey(@PathVariable String domain) throws JsonProcessingException, JOSEException {

        return Mono.justOrEmpty(new ResponseEntity<ApiKey>(ApiKey.builder()
                .key(jwsUtil.sign(domainService.find(domain)))
                .id(IDUtil.uuid(domain).toString())
                .build(),
                HttpStatus.CREATED)
        );
    }

    @GetMapping("/apis/public/keys")
    public Mono<ResponseEntity<String>> publicKey() {

        JWK jwk = new RSAKey.Builder(jwsUtil.publicKey)
                .keyID(IDUtil.uuid("public-key")) // Give the key some ID (optional)
                .build();
        return Mono.justOrEmpty(new ResponseEntity<String>(jwk.toString(),HttpStatus.OK));
    }

}
