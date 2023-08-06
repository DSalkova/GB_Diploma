package API;

import io.restassured.path.json.JsonPath;
import jdk.jfr.Description;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MyPostsTest extends AbstractTest{
    Logger logger = LoggerFactory.getLogger(MyPostsTest.class);
    @Test
    @DisplayName("Test-case №1")
    @Description("Get my posts without query parameters")
    void myPostsTest1() {
        given()
                .header("X-Auth-Token", getTokenWithPosts())
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
    @Description("Result includes the last post's title 'Post11 - The Last'")
    void myPostsTest2() {
        given()
                .header("X-Auth-Token", getTokenWithPosts())
                .log().all()
                .when()
                .get(getBaseUrl() + "/api/posts")
                .prettyPeek()
                .then()
                .assertThat()
                .statusCode(200)
                .body("data[0].title", containsString("Post11 - The Last"));
    }
    @Test
    @DisplayName("Test-case №3")
    @Description("Count all my posts > 10")
    void myPostsTest3() {
        given()
                .header("X-Auth-Token", getTokenWithPosts())
                .log().all()
                .when()
                .get(getBaseUrl() + "/api/posts")
                .prettyPeek()
                .then()
                .assertThat()
                .statusCode(200)
                .body("meta.count", greaterThan(10));
    }
    @Test
    @DisplayName("Test-case №4")
    @Description("Count posts on the 1st page = 10")
    void myPostsTest4() {
        given()
                .header("X-Auth-Token", getTokenWithPosts())
                .log().all()
                .when()
                .get(getBaseUrl() + "/api/posts")
                .prettyPeek()
                .then()
                .assertThat()
                .statusCode(200)
                .body("data.size()", equalTo(10));
        logger.error("Test-case №4 failed! Expected data.size() 10 but was 4.");
    }
    @SneakyThrows
    @Test
    @DisplayName("Test-case №5")
    @Description("Check default sort: new - old")
    void myPostsTest5() {
        JsonPath response = given()
                .header("X-Auth-Token", getTokenWithPosts())
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
    @DisplayName("Test-case №6")
    @Description("Check open page = 2")
    void myPostsTest6() {
        given()
                .header("X-Auth-Token", getTokenWithPosts())
                .queryParam("page", 2)
                .log().all()
                .when()
                .get(getBaseUrl() + "/api/posts")
                .prettyPeek()
                .then()
                .assertThat()
                .statusCode(200)
                .body("meta.prevPage", equalTo(1))
                .body("meta.nextPage", equalTo(3));
    }
    @Test
    @DisplayName("Test-case №7")
    @Description("Check open last page")
    void myPostsTest7() {
        given()
                .header("X-Auth-Token", getTokenWithPosts())
                .queryParam("page", 3)
                .log().all()
                .when()
                .get(getBaseUrl() + "/api/posts")
                .prettyPeek()
                .then()
                .assertThat()
                .statusCode(200)
                .body("meta.nextPage", equalTo(null));
    }
    @Test
    @DisplayName("Test-case №8")
    @Description("Check open non-existent page")
    void myPostsTest8() {
        given()
                .header("X-Auth-Token", getTokenWithPosts())
                .queryParam("page", 5)
                .log().all()
                .when()
                .get(getBaseUrl() + "/api/posts")
                .prettyPeek()
                .then()
                .assertThat()
                .statusCode(200)
                .body("meta.prevPage", equalTo(4))
                .body("meta.nextPage", equalTo(null));
    }
    @SneakyThrows
    @Test
    @DisplayName("Test-case №9")
    @Description("Check sort = ASC")
    void myPostsTest9() {
        JsonPath response = given()
                .header("X-Auth-Token", getTokenWithPosts())
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
    @Test
    @DisplayName("Test-case №10")
    @Description("Result includes the 1st post's title 'Post1 - The First'")
    void myPostsTest10() {
        given()
                .header("X-Auth-Token", getTokenWithPosts())
                .queryParam("sort", "createdAt")
                .queryParam("order", "ASC")
                .log().all()
                .when()
                .get(getBaseUrl() + "/api/posts")
                .prettyPeek()
                .then()
                .assertThat()
                .statusCode(200)
                .body("data[0].title", containsString("Post1 - The First"));
    }
    @SneakyThrows
    @Test
    @DisplayName("Test-case №11")
    @Description("Check sort = DESC")
    void myPostsTest11() {
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
    @DisplayName("Test-case №12")
    @Description("Result includes the last post's title 'Post11 - The Last'")
    void myPostsTest12() {
        given()
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
                .body("data[0].title", containsString("Post11 - The Last"));
    }
    @Test
    @DisplayName("Test-case №13")
    @Description("Get result without posts")
    void myPostsTest13() {
        given()
                .header("X-Auth-Token", getTokenWithoutPosts())
                .log().all()
                .when()
                .get(getBaseUrl() + "/api/posts")
                .prettyPeek()
                .then()
                .assertThat()
                .statusCode(200)
                .body("data", empty());
                //.body("data.size()", equalTo(0));
    }
    @Test
    @DisplayName("Test-case №14")
    @Description("My post includes not empty fields: 'title', 'description', 'mainImage'")
    void myPostsTest14() {
        given()
                .header("X-Auth-Token", getTokenWithPosts())
                .queryParam("sort", "createdAt")
                .queryParam("order", "ASC")
                .queryParam("page", "2")
                .log().all()
                .when()
                .get(getBaseUrl() + "/api/posts")
                .prettyPeek()
                .then()
                .assertThat()
                .statusCode(200)
                .body("data[0].title", notNullValue())
                .body("data[0].description", notNullValue())
                .body("data[0].mainImage", notNullValue());
    }
    @Test
    @DisplayName("Test-case №15")
    @Description("Get my posts without token")
    void myPostsTest15() {
        given()
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
    @DisplayName("Test-case №16")
    @Description("Get my posts with invalid token")
    void myPostsTest16() {
        given()
                .header("X-Auth-Token", "qw1er2ty3ui4op5as6df7gh8jk9lz0xc")
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