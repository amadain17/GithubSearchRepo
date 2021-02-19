package ie.mfmdevine.GithubSearchRepo.tests;

import static ie.mfmdevine.GithubSearchRepo.util.Consts.BASE_URL;
import static ie.mfmdevine.GithubSearchRepo.util.Consts.HEADER;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import java.util.Arrays;
import org.junit.Before;

public class BaseTest {

  static RequestSpecification requestSpecification;

  @Before
  public void setUp() {
    requestSpecification = new RequestSpecBuilder()
        .setRelaxedHTTPSValidation()
        .addFilters(Arrays.asList(new RequestLoggingFilter(), new ResponseLoggingFilter()))
        .setBaseUri(BASE_URL)
        .setAccept(HEADER)
        .build();
  }
}
