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
        println("search " + keyword)
        int postfix = TestCount.testCount.addAndGet(1);
        keyword = keyword.replaceAll("\\s+", "+")

        // Generate Test Case from sample
        def sampleFile = new File("/Users/jimin/Repository/Bitbucket/robotframework-test-plan" +
                "/google-search-test-case/search_result.txt")

        ("mkdir /Users/jimin/Downloads/TestCases/" + postfix).execute()
        String storedLocation = "/Users/jimin/Downloads/TestCases/" + postfix;
        String testCaseLocation = storedLocation + "/search_result.txt"
        println("testCaseLocation = " + testCaseLocation)
        println(testCaseLocation)
        def storedFile = new File(testCaseLocation)
        def fileText = sampleFile.text
        fileText = fileText.replaceAll("keyword", keyword)
        storedFile.write(fileText)
        ("cp /Users/jimin/Repository/Bitbucket/robotframework-test-plan/google-search-test-case/variables.txt "
                + storedLocation).execute()

        println("Execute Robot Framework")
        // Execute Robot Framework
        ("cd " + storedLocation).execute()
        String robotframework = "/Users/jimin/Repository/Bitbucket/robotframework-httplibrary/bin/robotframework";

        println("Prepare execute test case")
        println("CMD = " + robotframework + " " + testCaseLocation)
        String testResult = (robotframework + " " + testCaseLocation).execute().text
        // TODO: it can be a better way to verify the result, but it maybe need to change source
        // code of library
        boolean result = testResult.contains("0 failed") == true ? false : true;

        //return result
        return result
    }
}
