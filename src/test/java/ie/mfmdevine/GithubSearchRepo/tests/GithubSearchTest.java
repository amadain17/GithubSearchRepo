package ie.mfmdevine.GithubSearchRepo.tests;

import static ie.mfmdevine.GithubSearchRepo.util.Consts.ITEMS;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.List;
import org.assertj.core.api.SoftAssertions;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(DataProviderRunner.class)
public class GithubSearchTest extends BaseTest {

  @DataProvider(format = "%m_%p[0]_%p[1]")
  public static Object[] queryParamsProvider() {
    return new Object[][]{
        {"tetris", "java", "Java"},
        {"snake", "ruby", "Ruby"},
        {"space", "python", "Python"},
        {"pacman", "assembly", "Assembly"}
    };
  }

  @Test
  @UseDataProvider("queryParamsProvider")
  public void testSearchQuery(String query, String language, String responseLang) {
    SoftAssertions softly = new SoftAssertions();
    Response response = given().spec(requestSpecification)
        .queryParam("q", query + "+language:" + language)
        .when().get()
        .then().statusCode(SC_OK).extract().response();
    List<HashMap<String, Object>> results = response.getBody().jsonPath().getList(ITEMS);

    for (HashMap<String, Object> item : results) {
      softly.assertThat(item.get("language")).isEqualTo(responseLang);
      String description = (String) item.get("description");
      softly.assertThat(description.toLowerCase()).containsSequence(query);
    }
    softly.assertAll();
  }
}
