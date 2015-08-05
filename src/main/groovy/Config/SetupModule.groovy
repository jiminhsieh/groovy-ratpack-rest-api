package config

import service.SearchService
import service.SearchServiceImpl
import com.google.inject.AbstractModule
import groovy.transform.CompileStatic

/**
 * Created by jimin on 7/28/15.
 */
@CompileStatic
class SetupModule extends AbstractModule{

    @Override
    protected void configure() {
        bind(SearchService.class).to(SearchServiceImpl.class)
    }
}
