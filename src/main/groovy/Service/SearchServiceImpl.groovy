package Service

import POJO.TestCount
import POJO.TestResult
import groovy.transform.CompileStatic

/**
 * Created by jimin on 7/28/15.
 */

@CompileStatic
class SearchServiceImpl implements SearchService {

    @Override
    TestResult search(String keyword) {
        println("### SearchService.search START ###")
        int folderName = TestCount.testCount.addAndGet(1);

        TestResult result = new TestResult()
        result.searchWord = keyword
        result.currentTestNo = folderName

        keyword = keyword.replaceAll("\\s+", "+")

        // Generate Test Case from sample
        File sampleFile = new File("/root/Repository/robotframework-test-plan/google-search-test" +
                "-case/search_result.txt")

        // create directory to store each test case
        //("mkdir /root/TestCases/" + folderName).execute()
        ("ls").execute()

        // prepare test case
        File stLocation = new File("/root/TestCases/" + folderName)
        if (!stLocation.mkdirs()) {
            println("### Can't create directories !!!! ###")
        }
        String storedLocation = "/root/TestCases/" + folderName;
        String testCaseLocation = storedLocation + "/search_result.txt"
        File storedFile = new File(testCaseLocation)
        String fileText = sampleFile.text
        String testCase = fileText.replaceAll("keyword", keyword)
        File stFile = new File(stLocation, "search_result.txt")
        println("### Prepare to write test case ###")
        stFile.write(testCase)
        //storedFile.mkdirs()
        //storedFile.write(testCase)
        println("### Preare to copy variables.txt")
        ("cp /root/Repository/robotframework-test-plan/google-search-test-case/variables.txt "
                + storedLocation).execute()

        // execute test
        String robotframework = "/root/Repository/robotframework-httplibrary/bin/robotframework";
        String testResult = (robotframework + " " + testCaseLocation).execute().text

        /**
         * TODO: it can be a better way to verify the result, but it maybe need to change source
         * code of robotframework-httplibrary or use pygoogle
         */
        result.result = testResult.contains("0 failed") == true ? false : true;

        println("### SearchService.search END ###")
        return result
    }
}
