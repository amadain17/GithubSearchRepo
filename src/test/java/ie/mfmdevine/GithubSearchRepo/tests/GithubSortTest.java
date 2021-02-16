package ie.mfmdevine.GithubSearchRepo.tests;

import io.restassured.response.Response;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

import static ie.mfmdevine.GithubSearchRepo.util.Consts.ITEMS;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertTrue;

public class GithubSortTest extends BaseTest {

    @Test
    public void testSortResultsQueryDesc() {
        Response response = given().spec(requestSpecification)
                .queryParam("q", "tetris+language:assembly")
                .queryParam("sort", "stars")
                .queryParam("order", "desc")
                .when().get()
                .then().statusCode(SC_OK).extract().response();
        List<HashMap> results = response.getBody().jsonPath().getList(ITEMS);

        boolean firsttime = true;
        int number = 0;

        for (HashMap item: results) {
            if (firsttime) {
                number = (int) item.get("stargazers_count");
                firsttime = false;
                continue;
            }
            int nextNum = (int) item.get("stargazers_count");
            assertTrue(String.format("Number of stars %s should be less than or equal to %s", nextNum, number), nextNum<=number);
            number = nextNum;
        }
    }

}
