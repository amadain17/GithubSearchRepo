package ie.mfmdevine.GithubSearchRepo.tests;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import io.restassured.response.Response;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.*;

import static ie.mfmdevine.GithubSearchRepo.util.Consts.ITEMS;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(DataProviderRunner.class)
public class GithubLanguagesTest extends BaseTest{

    @DataProvider(format = "%m%p[0]ReposCreatedAfter%p[2]")
    public static Object[] langsAndDates() {
        return new Object[][] {
                {"Java", "2021-01-01", new GregorianCalendar(2020, Calendar.DECEMBER, 31, 23, 59, 59).getTime()},
                {"Assembly", "2020-01-01", new GregorianCalendar(2019, Calendar.DECEMBER, 31, 23, 59, 59).getTime()}
        };
    }

    @Test
    @UseDataProvider("langsAndDates")
    public void testNumberOf(String lang, String creationDate, Date previousDate) {
        Response response = given().spec(requestSpecification)
                .queryParam("q", String.format("language:%s created:>%s", lang.toLowerCase(), creationDate))
                .when().get()
                .then().statusCode(SC_OK).extract().response();
        List<Object> results = response.getBody().jsonPath().getList(ITEMS);

        for (Object item : results) {
            assertThat(((HashMap) item).get("language")).isEqualTo(lang);
            Date d = getDateFromIsoString((CharSequence) ((HashMap) item).get("created_at"));
            assertThat(d).isAfter(previousDate);
        }
    }

    private Date getDateFromIsoString(CharSequence isoString) {
        TemporalAccessor ta = DateTimeFormatter.ISO_INSTANT.parse(isoString);
        Instant i = Instant.from(ta);
        return Date.from(i);
    }

}

