package ie.mfmdevine.GithubSearchRepo.tests;

import static ie.mfmdevine.GithubSearchRepo.util.Consts.ITEMS;
import static ie.mfmdevine.GithubSearchRepo.util.ResponseHelper.compareConsecutiveListItems;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.response.Response;
import java.util.HashMap;
import java.util.List;
import org.junit.Test;

public class GithubMostStarredTest extends BaseTest {

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
    List<HashMap<String, Object>> results = response.getBody().jsonPath().getList(ITEMS);

    assertThat(results.size()).isEqualTo(NUMBER_OF_RESULTS);

    compareConsecutiveListItems(results, "stargazers_count", false);
  }
}

