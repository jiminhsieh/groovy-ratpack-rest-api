package Service

import POJO.TestCount
import groovy.transform.CompileStatic

/**
 * Created by jimin on 7/28/15.
 */

@CompileStatic
class SearchServiceImpl implements SearchService {

    /*@Override
    TestResult search(String keyword) {
        println(keyword)
        TestResult result = new TestResult()
        result.searchResult = 0
        return result
    }*/

    @Override
    boolean search(String keyword) {
        println("### SearchService.search START ###")
        int folderName = TestCount.testCount.addAndGet(1);
        keyword = keyword.replaceAll("\\s+", "+")

        // Generate Test Case from sample
        def sampleFile = new File("/root/Repository/robotframework-test-plan/google-search-test" +
                "-case/search_result.txt")

        ("mkdir /root/TestCases/" + folderName).execute()

        String storedLocation = "/root/TestCases/" + folderName;
        String testCaseLocation = storedLocation + "/search_result.txt"
        println("storedLocation = " + storedLocation)
        println("testCaseLocation = " + testCaseLocation)
        def storedFile = new File(testCaseLocation)
        def fileText = sampleFile.text
        fileText = fileText.replaceAll("keyword", keyword)
        println("After storing test case")
        storedFile.write(fileText)
        println("Before storing test case")
        ("cp /root/Repository/robotframework-test-plan/google-search-test-case/variables.txt "
                + storedLocation).execute()

        println("Execute Robot Framework")
        // Execute Robot Framework
        String robotframework = "/root/Repository/robotframework-httplibrary/bin/robotframework";

        println("Prepare execute test case")
        println("CMD = " + robotframework + " " + testCaseLocation)
        String testResult = (robotframework + " " + testCaseLocation).execute().text
        // TODO: it can be a better way to verify the result, but it maybe need to change source
        // code of library
        boolean result = testResult.contains("0 failed") == true ? false : true;

        //return result
        println("### SearchService.search START ###")
        return result
    }
}
