package API;

import io.restassured.path.json.JsonPath;
import jdk.jfr.Description;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class LoginTest extends AbstractTest{
    Logger logger = LoggerFactory.getLogger(LoginTest.class);
    @Test
    @DisplayName("Test-case №1")
    @Description("Authorization by created user with valid credentials")
    void loginTest1() {
        given()
                .contentType("multipart/form-data")
                .multiPart("username", getUsername())
                .multiPart("password", getPassword())
                .log().all()
                .when()
                .post(getBaseUrl() + "/gateway/login")
                .prettyPeek()
                .then()
                .assertThat()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 OK")
                .time(greaterThan(2000L));
    }
    @Test
    @DisplayName("Test-case №2")
    @Description("id = 22214")
    void loginTest2() {
        JsonPath response = given()
                .contentType("multipart/form-data")
                .multiPart("username", getUsername())
                .multiPart("password", getPassword())
                .log().all()
                .when()
                .post(getBaseUrl() + "/gateway/login")
                .prettyPeek()
                .body()
                .jsonPath();
        assertThat(response.get("id"), equalTo(22214));
    }
    @Test
    @DisplayName("Test-case №3")
    @Description("username is correct")
    void loginTest3() {
        JsonPath response = given()
                .contentType("multipart/form-data")
                .multiPart("username", getUsername())
                .multiPart("password", getPassword())
                .log().all()
                .when()
                .post(getBaseUrl() + "/gateway/login")
                .prettyPeek()
                .body()
                .jsonPath();
        assertThat(response.get("username"), equalTo(getUsername()));
    }
    @Test
    @DisplayName("Test-case №4")
    @Description("Authorization by created user with valid credentials")
    void loginTest4() {
        given()
                .contentType("multipart/form-data")
                .multiPart("username", "qw2")
                .multiPart("password", "b3dac247c7")
                .log().all()
                .when()
                .post(getBaseUrl() + "/gateway/login")
                .prettyPeek()
                .then()
                .assertThat()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 OK")
                .time(greaterThan(200L));
    }
    @Test
    @DisplayName("Test-case №5")
    @Description("Authorization by created user with valid credentials")
    void loginTest5() {
        given()
                .contentType("multipart/form-data")
                .multiPart("username", "1qwerty2qwerty3qwer4")
                .multiPart("password", "e1086c97a2")
                .log().all()
                .when()
                .post(getBaseUrl() + "/gateway/login")
                .prettyPeek()
                .then()
                .assertThat()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 OK")
                .time(greaterThan(200L));
    }
    @Test
    @DisplayName("Test-case №6")
    @Description("Authorization by created user with valid credentials")
    void loginTest6() {
        given()
                .contentType("multipart/form-data")
                .multiPart("username", "qqwweerrttyy")
                .multiPart("password", "af4d952d35")
                .log().all()
                .when()
                .post(getBaseUrl() + "/gateway/login")
                .prettyPeek()
                .then()
                .assertThat()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 OK")
                .time(greaterThan(200L));
    }
    @Test
    @DisplayName("Test-case №7")
    @Description("Authorization by created user with valid credentials")
    void loginTest7() {
        given()
                .contentType("multipart/form-data")
                .multiPart("username", "123456789112")
                .multiPart("password", "22b691e523")
                .log().all()
                .when()
                .post(getBaseUrl() + "/gateway/login")
                .prettyPeek()
                .then()
                .assertThat()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 OK")
                .time(greaterThan(200L));
    }
    @Test
    @DisplayName("Test-case №8")
    @Description("Authorization by not created user with valid credentials")
    void loginTest8() {
        given()
                .contentType("multipart/form-data")
                .multiPart("username", "dsalkova3")
                .multiPart("password", getPassword())
                .log().all()
                .when()
                .post(getBaseUrl() + "/gateway/login")
                .prettyPeek()
                .then()
                .assertThat()
                .statusCode(401)
                .statusLine("HTTP/1.1 401 Unauthorized");
    }
    @Test
    @DisplayName("Test-case №9")
    @Description("Check error message if authorization by not created user with valid credentials")
    void loginTest9() {
        given()
                .contentType("multipart/form-data")
                .multiPart("username", "dsalkova3")
                .multiPart("password", getPassword())
                .log().all()
                .when()
                .post(getBaseUrl() + "/gateway/login")
                .prettyPeek()
                .then()
                .assertThat()
                .body("error", equalTo("Проверьте логин и пароль"));
        logger.error("Test-case №9 failed! Expected error 'Проверьте логин и пароль' but was 'Invalid credentials.'.");
    }
    @Test
    @DisplayName("Test-case №10")
    @Description("Authorization by created user with empty credentials")
    void loginTest10() {
        given()
                .contentType("multipart/form-data")
                .multiPart("username", "")
                .multiPart("password", "")
                .log().all()
                .when()
                .post(getBaseUrl() + "/gateway/login")
                .prettyPeek()
                .then()
                .assertThat()
                .statusCode(401)
                .statusLine("HTTP/1.1 401 Unauthorized");
    }
    @Test
    @DisplayName("Test-case №11")
    @Description("Authorization by created user with empty username")
    void loginTest11() {
        given()
                .contentType("multipart/form-data")
                .multiPart("username", "")
                .multiPart("password", getPassword())
                .log().all()
                .when()
                .post(getBaseUrl() + "/gateway/login")
                .prettyPeek()
                .then()
                .assertThat()
                .statusCode(401)
                .statusLine("HTTP/1.1 401 Unauthorized");
    }
    @Test
    @DisplayName("Test-case №12")
    @Description("Check error message if authorization by created user with empty username")
    void loginTest12() {
        given()
                .contentType("multipart/form-data")
                .multiPart("username", "")
                .multiPart("password", getPassword())
                .log().all()
                .when()
                .post(getBaseUrl() + "/gateway/login")
                .prettyPeek()
                .then()
                .assertThat()
                .statusCode(401)
                .statusLine("HTTP/1.1 401 Unauthorized")
                .body("error", equalTo("Поле не может быть пустым"));
        logger.error("Test-case №12 failed! Expected error 'Поле не может быть пустым' but was 'Invalid credentials.'.");
    }
    @Test
    @DisplayName("Test-case №13")
    @Description("Authorization by created user with empty password")
    void loginTest13() {
        given()
                .contentType("multipart/form-data")
                .multiPart("username", getUsername())
                .multiPart("password", "")
                .log().all()
                .when()
                .post(getBaseUrl() + "/gateway/login")
                .prettyPeek()
                .then()
                .assertThat()
                .statusCode(401)
                .statusLine("HTTP/1.1 401 Unauthorized");
    }
    @Test
    @DisplayName("Test-case №14")
    @Description("Authorization by created user with invalid username")
    void loginTest14() {
        given()
                .contentType("multipart/form-data")
                .multiPart("username", "йцукен123456")
                .multiPart("password", "507d3e5af1")
                .log().all()
                .when()
                .post(getBaseUrl() + "/gateway/login")
                .prettyPeek()
                .then()
                .assertThat()
                .statusCode(401)
                .statusLine("HTTP/1.1 401 Unauthorized");
    }
    @Test
    @DisplayName("Test-case №15")
    @Description("Check error message if authorization by created user with invalid username")
    void loginTest15() {
        given()
                .contentType("multipart/form-data")
                .multiPart("username", "йцукен123456")
                .multiPart("password", "507d3e5af1")
                .log().all()
                .when()
                .post(getBaseUrl() + "/gateway/login")
                .prettyPeek()
                .then()
                .assertThat()
                .statusCode(401)
                .statusLine("HTTP/1.1 401 Unauthorized")
                .body("error", equalTo("Неправильный логин. Может состоять только из латинских букв и цифр, " +
                        "без спецсимволов"));
        logger.error("Test-case №15 failed! Expected error 'Неправильный логин. Может состоять " +
                "только из латинских букв и цифр, без спецсимволов' but was 'Invalid credentials.'.");
    }
    @Test
    @DisplayName("Test-case №16")
    @Description("Authorization by created user with invalid username")
    void loginTest16() {
        given()
                .contentType("multipart/form-data")
                .multiPart("username", "w2")
                .multiPart("password", "62d7d5184b")
                .log().all()
                .when()
                .post(getBaseUrl() + "/gateway/login")
                .prettyPeek()
                .then()
                .assertThat()
                .statusCode(401)
                .statusLine("HTTP/1.1 401 Unauthorized");
        logger.error("Test-case №16 failed! Expected status code 401 but was 200.");
    }
    @Test
    @DisplayName("Test-case №17")
    @Description("Authorization by created user with invalid username")
    void loginTest17() {
        given()
                .contentType("multipart/form-data")
                .multiPart("username", "<>\"&<>\"&<>\"&")
                .multiPart("password", "0421077098")
                .log().all()
                .when()
                .post(getBaseUrl() + "/gateway/login")
                .prettyPeek()
                .then()
                .assertThat()
                .statusCode(401)
                .statusLine("HTTP/1.1 401 Unauthorized");
    }
    @Test
    @DisplayName("Test-case №18")
    @Description("Authorization by created user with invalid username")
    void loginTest18() {
        given()
                .contentType("multipart/form-data")
                .multiPart("username", "qwerty<>\"&<>")
                .multiPart("password", "9020c3b562")
                .log().all()
                .when()
                .post(getBaseUrl() + "/gateway/login")
                .prettyPeek()
                .then()
                .assertThat()
                .statusCode(401)
                .statusLine("HTTP/1.1 401 Unauthorized");
        logger.error("Test-case №18 failed! Expected status code 401 but was 200.");
    }
    @Test
    @DisplayName("Test-case №19")
    @Description("Authorization by created user with invalid username")
    void loginTest19() {
        given()
                .contentType("multipart/form-data")
                .multiPart("username", "123456<>\"&<>")
                .multiPart("password", "f8c90b402f")
                .log().all()
                .when()
                .post(getBaseUrl() + "/gateway/login")
                .prettyPeek()
                .then()
                .assertThat()
                .statusCode(401)
                .statusLine("HTTP/1.1 401 Unauthorized");
        logger.error("Test-case №19 failed! Expected status code 401 but was 200.");
    }
    @Test
    @DisplayName("Test-case №20")
    @Description("Authorization by created user with invalid username")
    void loginTest20() {
        given()
                .contentType("multipart/form-data")
                .multiPart("username", "qwer1234!<>\"")
                .multiPart("password", "44bc6aa428")
                .log().all()
                .when()
                .post(getBaseUrl() + "/gateway/login")
                .prettyPeek()
                .then()
                .assertThat()
                .statusCode(401)
                .statusLine("HTTP/1.1 401 Unauthorized");
        logger.error("Test-case №20 failed! Expected status code 401 but was 200.");
    }
    @Test
    @DisplayName("Test-case №21")
    @Description("Authorization by created user with invalid username")
    void loginTest21() {
        given()
                .contentType("multipart/form-data")
                .multiPart("username", "qwertyuiop12345678901")
                .multiPart("password", "0421077098")
                .log().all()
                .when()
                .post(getBaseUrl() + "/gateway/login")
                .prettyPeek()
                .then()
                .assertThat()
                .statusCode(401)
                .statusLine("HTTP/1.1 401 Unauthorized");
        logger.error("Test-case №21 failed! Expected status code 401 but was 200.");
    }
}