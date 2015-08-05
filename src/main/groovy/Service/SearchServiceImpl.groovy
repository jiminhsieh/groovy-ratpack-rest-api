package service

import com.google.inject.Inject
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import pojo.TestCount
import pojo.TestResult
import groovy.transform.CompileStatic
import pojo.config.Application

/**
 * Created by jimin on 7/28/15.
 */

@CompileStatic
class SearchServiceImpl implements SearchService {

    final Logger logger = LoggerFactory.getLogger(SearchServiceImpl.class);
    final Application appConfig;

    @Inject
    public SearchServiceImpl(Application appConfig) {
        this.appConfig = appConfig;
    }

    @Override
    TestResult search(String keyword) {
        logger.debug("### search START ###")
        int folderName = TestCount.testCount.addAndGet(1);

        TestResult result = new TestResult()
        result.searchWord = keyword
        result.currentTestNo = folderName

        keyword = keyword.replaceAll("\\s+", "+")

        // Generate Test Case from sample
        File sampleFile = new File("${appConfig.testCaseSample}", "search_result.txt")

        // prepare test case
        File parentLocation = new File("${appConfig.testCasesLocation}" + "/"
                + folderName)
        if (!parentLocation.mkdirs()) {
            logger.warn("### Can't create directories !!!! ###")
        }
        String storedLocation = parentLocation.toString();
        String testCaseLocation = storedLocation + "/search_result.txt"
        String fileText = sampleFile.text
        String testCase = fileText.replaceAll("keyword", keyword)
        File storedFile = new File(parentLocation, "search_result.txt")
        storedFile.write(testCase)
        storedFile.write(fileText)
        ("cp " + "${appConfig.variable}" + " " + storedLocation)
                .execute()

        // execute test
        String testResult = ("${appConfig.robotframework}" + " " + testCaseLocation).execute().text
        /**
         * TODO: it can be a better way to verify the result, but it maybe need to change source
         * code of robotframework-httplibrary or use pygoogle
         */
        result.result = testResult.contains("0 failed") == true ? false : true;

        // move documentation of test result to where we store test case
        ("mv " + storedLocation + "/log.html").execute()
        ("mv " + storedLocation + "/output.xml").execute()
        ("mv " + storedLocation + "/report.html").execute()

        logger.debug("### search END ###")
        return result
    }
}
