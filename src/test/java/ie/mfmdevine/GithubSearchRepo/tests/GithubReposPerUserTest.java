package ie.mfmdevine.GithubSearchRepo.tests;

import io.restassured.response.Response;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

import static ie.mfmdevine.GithubSearchRepo.util.Consts.ITEMS;
import static ie.mfmdevine.GithubSearchRepo.util.ResponseHelper.compareConsecutiveListItems;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.Assertions.assertThat;

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

        for (HashMap item : results) {
            HashMap<String, String> owner = (HashMap<String, String>) ((HashMap) item).get("owner");
            assertThat((owner.get("login"))).isEqualTo(USER.split(":")[1]);
        }

        compareConsecutiveListItems(results, "stargazers_count", true);
    }
}
