import com.fasterxml.jackson.datatype.jsr310.JSR310Module
import config.SetupModule
import pojo.config.Application
import ratpack.config.ConfigData
import service.SearchService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import ratpack.jackson.guice.JacksonModule;
import static ratpack.groovy.Groovy.ratpack
import static ratpack.jackson.Jackson.json

/**
 * Created by jimin on 7/28/15.
 */

final Logger logger = LoggerFactory.getLogger(ratpack.class);

ratpack {
    bindings {
        ConfigData configData = ConfigData.of(new JSR310Module())
                .props("$serverConfig.baseDir.file/application.properties")
                .env()
                .sysProps()
                .build()
        bindInstance(Application, configData.get("/app", Application))

        module JacksonModule
        module SetupModule
    }

    handlers {
        get {
            logger.info("### Default Path for Testing ###")
            render json("Hello World!")
        }

        get("google-search/:keyword") { SearchService searchService ->
            render json(searchService.search("$pathTokens.keyword"))
        }
    }
}
