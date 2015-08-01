package Controller

import Config.SetupModule
import POJO.TestResult
import Service.SearchService
import ratpack.jackson.internal.DefaultJsonRender

import static ratpack.jackson.Jackson.json
import ratpack.jackson.guice.JacksonModule;
import static ratpack.jackson.Jackson.jsonNode

/**
 * Created by jimin on 7/28/15.
 */
import static ratpack.groovy.Groovy.ratpack

ratpack {
    bindings {
        new JacksonModule()
        module SetupModule
    }

    handlers {
        get {
            println("Render Hellow World")
            render "Hello World!"
        }

        get(":keyword") { SearchService searchService ->
            println("$pathTokens.keyword")
            StringBuilder response = new StringBuilder("The Result is ")
            if (searchService.search("$pathTokens.keyword")) {
                response.append("SUCCESS")
            } else {
                response.append("FAIL")
            }
            response.append(" !!!")

            render (response)
        }

        post("foo") {
            def postBody = parse jsonNode()
            render "Hello world! ${postBody.person}"
        }

    }
}
