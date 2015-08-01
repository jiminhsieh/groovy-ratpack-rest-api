package Config

import Service.SearchService
import Service.SearchServiceImpl
import com.google.inject.AbstractModule
import com.google.inject.Scopes
import groovy.transform.CompileStatic
import jdk.nashorn.internal.runtime.Scope

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
