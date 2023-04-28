package racingcar.controller;

import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.assertj.core.api.Assertions;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import racingcar.dto.GameInfoForRequest;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RacingCarWebControllerTest {

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Nested
    class CRUDTest {
        @Test
        void createCarsAndGameRecords() {
            GameInfoForRequest gameInfoForRequest = new GameInfoForRequest("마드,푸우", 10);
            GameInfoForRequest newGameInfoForRequest = new GameInfoForRequest("마드,푸우", 10);

            given()
                    .log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(gameInfoForRequest)
                    .when()
                    .log().all()
                    .post("/plays")
                    .then()
                    .log().all()
                    .statusCode(HttpStatus.CREATED.value())
                    .body("size()", is(2));
        }

        @Test
        void showPlayRecords() {
            GameInfoForRequest gameInfoForRequest1 = new GameInfoForRequest("마드,푸우", 10);
            GameInfoForRequest gameInfoForRequest2 = new GameInfoForRequest("민트,하디", 10);

            given()
                    .log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(gameInfoForRequest1)
                    .when()
                    .log().all()
                    .post("/plays");

            given()
                    .log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .body(gameInfoForRequest2)
                    .when()
                    .log().all()
                    .post("/plays");

//            ExtractableResponse<Response> result = given()
//                    .log().all()
//                    .accept(MediaType.APPLICATION_JSON_VALUE)
//                    .when()
//                    .log().all()
//                    .get("/plays")
//                    .then()
//                    .extract();
//
//            System.out.println("result = " + result.jsonPath().get("[0].winners"));

            given()
                    .log().all()
                    .accept(MediaType.APPLICATION_JSON_VALUE)
                    .when()
                    .log().all()
                    .get("/plays")
                    .then()
                    .log().all()
                    .statusCode(HttpStatus.OK.value())
                    .body("size()", is(2));
        }
    }
}