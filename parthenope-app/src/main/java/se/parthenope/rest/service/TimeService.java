package se.parthenope.rest.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class TimeService {

    private static final Logger LOG = LoggerFactory.getLogger(TimeService.class);


    public String getTTL(Duration duration) {

        return Long.toString(System.currentTimeMillis() + duration.toMillis());
    }


}
