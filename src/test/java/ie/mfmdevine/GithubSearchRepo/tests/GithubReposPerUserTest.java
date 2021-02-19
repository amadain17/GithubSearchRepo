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

public class GithubReposPerUserTest extends BaseTest {

  private static final String USER = "user:tonybaloney";

  @Test
  public void testGetReposForUser() {
    Response response = given().spec(requestSpecification)
        .queryParam("q", USER)
        .queryParam("sort", "stars")
        .queryParam("order", "asc")
        .when().get()
        .then().statusCode(SC_OK).extract().response();
    List<HashMap<String, Object>> results = response.getBody().jsonPath().getList(ITEMS);

    for (HashMap<String, Object> item : results) {
      HashMap<String, String> owner = (HashMap<String, String>) item.get("owner");
      assertThat((owner.get("login"))).isEqualTo(USER.split(":")[1]);
    }

    compareConsecutiveListItems(results, "stargazers_count", true);
  }
}
