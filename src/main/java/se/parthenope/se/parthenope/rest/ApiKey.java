package se.parthenope.se.parthenope.rest;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@ToString
@Builder
@Data
public class ApiKey {

    String key;
    String id;
}
