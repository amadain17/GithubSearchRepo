package ie.mfmdevine.GithubSearchRepo.tests;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import io.restassured.response.Response;
import org.assertj.core.api.SoftAssertions;
import org.junit.Test;
import org.junit.runner.Request;
import org.junit.runner.RunWith;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.*;

@RunWith(DataProviderRunner.class)
public class GithubVerbTest extends BaseTest {

    @DataProvider(format = "%m%p[0]")
    public static Object[] verbs() {
        return new Object[][]{
                {"GET", SC_OK},
                {"POST", SC_NOT_FOUND},
                {"DELETE", SC_NOT_FOUND},
                {"PUT", SC_NOT_FOUND},
                {"TRACE", SC_METHOD_NOT_ALLOWED},
                {"HEAD", SC_OK}
        };
    }

    @Test
    @UseDataProvider("verbs")
    public void testVerb(String verb, int statusCode) {
        given().spec(requestSpecification)
                .queryParam("q", "language:python")
                .when().request(verb)
                .then().statusCode(statusCode);
    }
}