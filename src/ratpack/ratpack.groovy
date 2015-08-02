import Config.SetupModule
import Service.SearchService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import ratpack.jackson.guice.JacksonModule;
import static ratpack.groovy.Groovy.ratpack

/**
 * Created by jimin on 7/28/15.
 */

final Logger log = LoggerFactory.getLogger(ratpack.class);

ratpack {
    bindings {
        new JacksonModule()
        module SetupModule
    }

    handlers {
        get {
            render "Hello World!"
        }

        get("google-search:keyword") { SearchService searchService ->
            println("$pathTokens.keyword")
            StringBuilder response = new StringBuilder("The Result is ")
            if (searchService.search("$pathTokens.keyword")) {
                response.append("SUCCESS")
            } else {
                response.append("FAIL")
            }
            response.append(" !!!")

            render(response)
        }
    }
}
