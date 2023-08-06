package API;

import io.restassured.path.json.JsonPath;
import jdk.jfr.Description;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NotMyPostsTest extends AbstractTest{
    Logger logger = LoggerFactory.getLogger(NotMyPostsTest.class);
    @Test
    @DisplayName("Test-case №1")
    @Description("Get not my posts")
    void notMyPostsTest1() {
        given()
                .header("X-Auth-Token", getTokenWithPosts())
                .queryParam("owner", "notMe")
                .log().all()
                .when()
                .get(getBaseUrl() + "/api/posts")
                .prettyPeek()
                .then()
                .assertThat()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 OK")
                .time(greaterThan(200L));
    }
    @Test
    @DisplayName("Test-case №2")
    @Description("Count not my posts on the 1st page = 4")
    void notMyPostsTest2() {
        given()
                .header("X-Auth-Token", getTokenWithPosts())
                .queryParam("owner", "notMe")
                .log().all()
                .when()
                .get(getBaseUrl() + "/api/posts")
                .prettyPeek()
                .then()
                .assertThat()
                .statusCode(200)
                .body("data.size()", equalTo(4));
    }
    @SneakyThrows
    @Test
    @DisplayName("Test-case №3")
    @Description("Check default sort: new - old")
    void notMyPostsTest3() {
        JsonPath response = given()
                .header("X-Auth-Token", getTokenWithPosts())
                .queryParam("owner", "notMe")
                .log().all()
                .when()
                .get(getBaseUrl() + "/api/posts")
                .prettyPeek()
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .jsonPath();
        assertTrue(simpleDateFormat.parse(response.get("data[0].createdAt")).getTime() >
                simpleDateFormat.parse(response.get("data[1].createdAt")).getTime());
    }
    @Test
    @DisplayName("Test-case №4")
    @Description("Check open page = 3333")
    void notMyPostsTest4() {
        given()
                .header("X-Auth-Token", getTokenWithPosts())
                .queryParam("owner", "notMe")
                .queryParam("page", 3333)
                .log().all()
                .when()
                .get(getBaseUrl() + "/api/posts")
                .prettyPeek()
                .then()
                .assertThat()
                .statusCode(200)
                .body("meta.prevPage", equalTo(3332))
                .body("meta.nextPage", equalTo(3334));
    }
    @Test
    @DisplayName("Test-case №5")
    @Description("Check open non-existent page")
    void notMyPostsTest5() {
        given()
                .header("X-Auth-Token", getTokenWithPosts())
                .queryParam("owner", "notMe")
                .queryParam("page", 33333)
                .log().all()
                .when()
                .get(getBaseUrl() + "/api/posts")
                .prettyPeek()
                .then()
                .assertThat()
                .statusCode(200)
                .body("meta.prevPage", equalTo(33332))
                .body("meta.nextPage", equalTo(null));
    }
    @SneakyThrows
    @Test
    @DisplayName("Test-case №6")
    @Description("Check sort = ASC")
    void notMyPostsTest6() {
        JsonPath response = given()
                .header("X-Auth-Token", getTokenWithPosts())
                .queryParam("owner", "notMe")
                .queryParam("sort", "createdAt")
                .queryParam("order", "ASC")
                .log().all()
                .when()
                .get(getBaseUrl() + "/api/posts")
                .prettyPeek()
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .jsonPath();
        assertTrue(simpleDateFormat.parse(response.get("data[0].createdAt")).getTime() <
                simpleDateFormat.parse(response.get("data[1].createdAt")).getTime());
    }
    @SneakyThrows
    @Test
    @DisplayName("Test-case №7")
    @Description("Check sort = DESC")
    void notMyPostsTest7() {
        JsonPath response = given()
                .header("X-Auth-Token", getTokenWithPosts())
                .queryParam("sort", "createdAt")
                .queryParam("order", "DESC")
                .log().all()
                .when()
                .get(getBaseUrl() + "/api/posts")
                .prettyPeek()
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .jsonPath();
        assertTrue(simpleDateFormat.parse(response.get("data[0].createdAt")).getTime() >
                simpleDateFormat.parse(response.get("data[1].createdAt")).getTime());
    }
    @Test
    @DisplayName("Test-case №8")
    @Description("Get all not my posts")
    void notMyPostsTest8() {
        given()
                .header("X-Auth-Token", getTokenWithPosts())
                .queryParam("owner", "notMe")
                .queryParam("sort", "createdAt")
                .queryParam("order", "ALL")
                .log().all()
                .when()
                .get(getBaseUrl() + "/api/posts")
                .prettyPeek()
                .then()
                .assertThat()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 OK")
                .time(greaterThan(200L));
        logger.error("Test-case №8 failed! Expected status code 200 but was 500.");
    }
    @Test
    @DisplayName("Test-case №9")
    @Description("Get my posts without token")
    void notMyPostsTest9() {
        given()
                .queryParam("owner", "notMe")
                .log().all()
                .when()
                .get(getBaseUrl() + "/api/posts")
                .prettyPeek()
                .then()
                .assertThat()
                .statusCode(401)
                .statusLine("HTTP/1.1 401 Unauthorized");
    }
    @Test
    @DisplayName("Test-case №10")
    @Description("Get my posts with invalid token")
    void notMyPostsTest10() {
        given()
                .header("X-Auth-Token", "qw1er2ty3ui4op5as6df7gh8jk9lz0xc")
                .queryParam("owner", "notMe")
                .log().all()
                .when()
                .get(getBaseUrl() + "/api/posts")
                .prettyPeek()
                .then()
                .assertThat()
                .statusCode(401)
                .statusLine("HTTP/1.1 401 Unauthorized");
    }
}