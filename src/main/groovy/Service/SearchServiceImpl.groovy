package Service

import POJO.TestCount
import groovy.transform.CompileStatic

/**
 * Created by jimin on 7/28/15.
 */

@CompileStatic
class SearchServiceImpl implements SearchService {

    @Override
    boolean search(String keyword) {
        println("### SearchService.search START ###")

        int folderName = TestCount.testCount.addAndGet(1);
        keyword = keyword.replaceAll("\\s+", "+")

        // Generate Test Case from sample
        File sampleFile = new File("/root/Repository/robotframework-test-plan/google-search-test" +
                "-case/search_result.txt")

        // create directory to store each test case
        ("mkdir /root/TestCases/" + folderName).execute()

        // prepare test case
        String storedLocation = "/root/TestCases/" + folderName;
        String testCaseLocation = storedLocation + "/search_result.txt"
        File storedFile = new File(testCaseLocation)
        String fileText = sampleFile.text
        String testCase = fileText.replaceAll("keyword", keyword)
        storedFile.write(testCase)
        ("cp /root/Repository/robotframework-test-plan/google-search-test-case/variables.txt "
                + storedLocation).execute()

        // execute test
        String robotframework = "/root/Repository/robotframework-httplibrary/bin/robotframework";
        String testResult = (robotframework + " " + testCaseLocation).execute().text

        /**
         * TODO: it can be a better way to verify the result, but it maybe need to change source
         * code of library or use pygoogle
         */
        boolean result = testResult.contains("0 failed") == true ? false : true;

        println("### SearchService.search END ###")
        return result
    }
}
