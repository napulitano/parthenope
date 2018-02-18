package se.parthenope.se.parthenope.rest.model;

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
