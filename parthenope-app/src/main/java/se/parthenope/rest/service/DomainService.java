package se.parthenope.rest.service;

import org.springframework.stereotype.Service;

@Service
public class DomainService {

    private final static String DOMAIN = "CN=ATG,OU=DigitaL Solution,DC=%s,DC=parthenope,DC=se";

    public String find(String domain) {

        return String.format(DOMAIN, domain);
    }
}
