package se.parthenope.se.parthenope.rest;

import org.springframework.stereotype.Component;

@Component
public class DomainService {

    private final String DOMAIN = "CN=ATG,OU=DigitaL Solution,DC=%s,DC=atg.se,DC=tillsammans.atg.se,DC=se";
    public String find(String domain) {

        return String.format(DOMAIN,domain);
    }
}
