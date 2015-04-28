package com.graylog.agent.serverapi;

import com.graylog.agent.annotations.GraylogServerURL;
import com.graylog.agent.guice.AgentModule;
import retrofit.RestAdapter;

public class ServerApiModule extends AgentModule {
    @Override
    protected void configure() {
        bind(String.class).annotatedWith(GraylogServerURL.class).toProvider(ServerURLProvider.class);
        bind(RestAdapter.class).toProvider(RestAdapterProvider.class).asEagerSingleton();
    }
}