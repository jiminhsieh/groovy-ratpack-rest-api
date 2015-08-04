import config.SetupModule
import service.SearchService
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

        get("google-search/:keyword") { SearchService searchService ->
            render json(searchService.search("$pathTokens.keyword"))
        }
    }
}
