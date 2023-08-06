package API;

import org.junit.jupiter.api.BeforeAll;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Properties;

public abstract class AbstractTest {
    static Properties prop = new Properties();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    private static InputStream configFile;
    private static String baseUrl;
    private static String username;
    private static String password;
    private static String tokenWithPosts;
    private static String tokenWithoutPosts;
    @BeforeAll
    static void initTest() throws IOException {
        configFile = new FileInputStream("src/main/resources/my.properties");
        prop.load(configFile);
        tokenWithPosts =  prop.getProperty("tokenWithPosts");
        tokenWithoutPosts =  prop.getProperty("tokenWithoutPosts");
        baseUrl = prop.getProperty("baseURL");
        username =  prop.getProperty("login");
        password = prop.getProperty("password");
    }
    public static String getBaseUrl() {
        return baseUrl;
    }
    public static String getUsername() {
        return username;
    }
    public static String getPassword() {
        return password;
    }
    public static String getTokenWithPosts() {
        return tokenWithPosts;
    }
    public static String getTokenWithoutPosts() {
        return tokenWithoutPosts;
    }
}