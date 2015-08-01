package Config

import Service.SearchService
import Service.SearchServiceImpl
import com.google.inject.AbstractModule
import groovy.transform.CompileStatic

/**
 * Created by jimin on 7/28/15.
 */
@CompileStatic
class SetupModule extends AbstractModule{

    @Override
    protected void configure() {
        println("SetupModule SETUP")
        bind(SearchService.class).to(SearchServiceImpl.class)
    }
}
