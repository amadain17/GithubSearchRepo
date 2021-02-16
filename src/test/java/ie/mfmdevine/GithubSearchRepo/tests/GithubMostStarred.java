package ie.mfmdevine.GithubSearchRepo.tests;

import io.restassured.response.Response;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

import static ie.mfmdevine.GithubSearchRepo.util.Consts.ITEMS;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

public class GithubMostStarred extends BaseTest {
    private final static int NUMBER_OF_RESULTS = 100;

    @Test
    public void testGetMostStarredRepos() {
        Response response = given().spec(requestSpecification)
                .queryParam("q", "stars:>500")
                .queryParam("per_page", NUMBER_OF_RESULTS)
                .queryParam("sort", "stars")
                .queryParam("order", "desc")
                .when().get()
                .then().statusCode(SC_OK).extract().response();
        List<Object> results = response.getBody().jsonPath().getList(ITEMS);

        assertThat(results.size()).isEqualTo(NUMBER_OF_RESULTS);

        boolean firsttime = true;
        int number = 0;

        for (Object item: results) {
            if (firsttime) {
                number = (int) ((HashMap)item).get("stargazers_count");
                firsttime = false;
                continue;
            }
            int nextNum = (int) ((HashMap)item).get("stargazers_count");
            assertTrue(String.format("Number of stars %s should be less than or equal to %s", nextNum, number), nextNum<=number);
            number = nextNum;
        }
    }
}

