package ie.mfmdevine.GithubSearchRepo.util;

import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class ResponseHelper {

    public static void compareConsecutiveListItems(List<HashMap> results, String countItemKey, boolean firstItemGreaterThanNext) {
        boolean firsttime = true;
        int number = 0;

        for (HashMap item: results) {
            if (firsttime) {
                number = (int) item.get(countItemKey);
                firsttime = false;
                continue;
            }
            int nextNum = (int) item.get(countItemKey);
            if (firstItemGreaterThanNext) {
                assertTrue(String.format("Number of stars %s should be greater than or equal to %s", nextNum, number), nextNum >= number);
            } else {
                assertTrue(String.format("Number of stars %s should be greater than or equal to %s", nextNum, number), nextNum <= number);
            }
            number = nextNum;
        }
    }
}
