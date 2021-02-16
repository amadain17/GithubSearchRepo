package ie.mfmdevine.GithubSearchRepo.tests;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static ie.mfmdevine.GithubSearchRepo.util.Consts.ITEMS;
import static ie.mfmdevine.GithubSearchRepo.util.DateUtils.getDateFromIsoString;
import static ie.mfmdevine.GithubSearchRepo.util.DateUtils.getLastDateOfYear;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(DataProviderRunner.class)
public class GithubLanguagesTest extends BaseTest{

    @DataProvider(format = "%m%p[0]ReposCreatedAfter%p[2]")
    public static Object[] langsAndDates() {
        return new Object[][] {
                {"Java", "2021-01-01", getLastDateOfYear(2020)},
                {"Assembly", "2020-01-01", getLastDateOfYear(2019)}
        };
    }

    @Test
    @UseDataProvider("langsAndDates")
    public void testNumberOf(String lang, String creationDate, Date previousDate) {
        Response response = given().spec(requestSpecification)
                .queryParam("q", String.format("language:%s created:>%s", lang.toLowerCase(), creationDate))
                .when().get()
                .then().statusCode(SC_OK).extract().response();
        List<HashMap> results = response.getBody().jsonPath().getList(ITEMS);

        for (HashMap item : results) {
            assertThat(item.get("language")).isEqualTo(lang);
            Date d = getDateFromIsoString((CharSequence) item.get("created_at"));
            assertThat(d).isAfter(previousDate);
        }
    }
}

