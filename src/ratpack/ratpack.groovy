import Config.SetupModule
import POJO.TestResult
import Service.SearchService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import ratpack.jackson.guice.JacksonModule;
import static ratpack.groovy.Groovy.ratpack
import static ratpack.jackson.Jackson.json

/**
 * Created by jimin on 7/28/15.
 */

final Logger log = LoggerFactory.getLogger(ratpack.class);

ratpack {
    bindings {
        module JacksonModule
        module SetupModule
    }

    handlers {
        get {
            println("### Default Path for Testing ###")
            render json("Hello World!")
        }

        get("google-search/:keyword") {
            render json(searchService.search("$pathTokens.keyword"))
        }
    }
}
