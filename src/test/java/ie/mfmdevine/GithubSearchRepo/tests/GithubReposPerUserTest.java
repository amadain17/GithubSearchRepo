package ie.mfmdevine.GithubSearchRepo.tests;

import io.restassured.response.Response;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static ie.mfmdevine.GithubSearchRepo.util.Consts.ITEMS;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

public class GithubReposPerUserTest extends BaseTest{
    private static final String USER = "user:tonybaloney";

    @Test
    public void testGetReposForUser() {
        Response response = given().spec(requestSpecification)
                .queryParam("q", USER)
                .queryParam("sort", "stars")
                .queryParam("order", "asc")
                .when().get()
                .then().statusCode(SC_OK).extract().response();
        List<HashMap> results = response.getBody().jsonPath().getList(ITEMS);

        boolean firsttime = true;
        int number = 0;

        for (HashMap item : results) {
            HashMap<String, String> owner = (HashMap<String, String>) ((HashMap) item).get("owner");
            assertThat((owner.get("login"))).isEqualTo(USER.split(":")[1]);

            if (firsttime) {
                number = (int) item.get("stargazers_count");
                firsttime = false;
                continue;
            }
            int nextNum = (int) item.get("stargazers_count");
            assertTrue(String.format("Number of stars %s should be greater than or equal to %s", nextNum, number), nextNum>=number);
            number = nextNum;

        }
    }
}
