package se.parthenope.test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.specification.RequestSpecification;
import org.junit.Test;


public class ApplicationIT {

    private static final String TARGET_URL="TARGET_URL";
    private static final String BASE_URL = "/apis/";
    private static final String API_GENERATION_ENDPOINT_PATH = "generate/key/%s";
    private static final String API_PUBLIC_KEYS_ENDPOINT_PATH ="public/keys";
    private static final String API_GENERATOR_ENDPOINT_SCHEMA_FILE = "generate_key_schema.json";
    private static final String API_PUBLIC_KEYS_ENDPOINT_SCHEMA_FILE = "public_keys_schema.json";

    private static final RequestSpecification SPECIFICATION = new RequestSpecBuilder()
            .setBaseUri(System.getenv(TARGET_URL))
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .setRelaxedHTTPSValidation("TLS")
            .setBasePath(BASE_URL)
            .build();


    @Test
    public void acceptance_test_generate_key_endpoint() {

        RestAssured.given()
                .spec(SPECIFICATION)
                .when()
                .get(String.format(API_GENERATION_ENDPOINT_PATH,"terminal"))
                .then()
                .statusCode(201)
                .contentType(ContentType.JSON)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath(API_GENERATOR_ENDPOINT_SCHEMA_FILE));
    }

    @Test
    public void acceptance_test_public_keys_endpoint() {

        RestAssured.given()
                .spec(SPECIFICATION)
                .when()
                .get(API_PUBLIC_KEYS_ENDPOINT_PATH)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath(API_PUBLIC_KEYS_ENDPOINT_SCHEMA_FILE));
    }


}
