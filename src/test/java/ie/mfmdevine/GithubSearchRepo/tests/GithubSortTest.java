package ie.mfmdevine.GithubSearchRepo.tests;

import static ie.mfmdevine.GithubSearchRepo.util.Consts.ITEMS;
import static ie.mfmdevine.GithubSearchRepo.util.ResponseHelper.compareConsecutiveListItems;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;

import io.restassured.response.Response;
import java.util.HashMap;
import java.util.List;
import org.junit.Test;

public class GithubSortTest extends BaseTest {

  @Test
  public void testSortResultsQueryDesc() {
    Response response = given().spec(requestSpecification)
        .queryParam("q", "tetris+language:assembly")
        .queryParam("sort", "stars")
        .queryParam("order", "desc")
        .when().get()
        .then().statusCode(SC_OK).extract().response();
    List<HashMap<String, Object>> results = response.getBody().jsonPath().getList(ITEMS);

    compareConsecutiveListItems(results, "stargazers_count", false);
  }
}
