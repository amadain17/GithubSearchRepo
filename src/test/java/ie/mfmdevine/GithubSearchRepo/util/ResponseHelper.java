package ie.mfmdevine.GithubSearchRepo.util;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;

public class ResponseHelper {

  public static void compareConsecutiveListItems(List<HashMap<String, Object>> results, String countItemKey,
      boolean firstItemGreaterThanNext) {
    boolean firsttime = true;
    int number = 0;

    for (HashMap<String, Object> item : results) {
      if (firsttime) {
        number = (int) item.get(countItemKey);
        firsttime = false;
        continue;
      }
      int nextNum = (int) item.get(countItemKey);
      if (firstItemGreaterThanNext) {
        assertTrue(String
                .format("Number of stars %s should be greater than or equal to %s", nextNum, number),
            nextNum >= number);
      } else {
        assertTrue(String
                .format("Number of stars %s should be greater than or equal to %s", nextNum, number),
            nextNum <= number);
      }
      number = nextNum;
    }
  }
}
