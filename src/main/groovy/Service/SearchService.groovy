package service

import pojo.TestResult

/**
 * Created by jimin on 7/28/15.
 */
interface SearchService {
    TestResult search(String keyword)
}
