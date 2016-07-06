/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License;
 * you may not use this file except in compliance with the Elastic License.
 */
package org.elasticsearch.xpack.watcher;


import org.elasticsearch.common.inject.AbstractModule;
import org.elasticsearch.common.inject.util.Providers;
import org.elasticsearch.xpack.XPackPlugin;
import org.elasticsearch.xpack.watcher.support.WatcherIndexTemplateRegistry;


public class WatcherModule extends AbstractModule {

    private final boolean enabled;
    private final boolean transportClientMode;

    public WatcherModule(boolean enabled, boolean transportClientMode) {
        this.enabled = enabled;
        this.transportClientMode = transportClientMode;
    }

    @Override
    protected void configure() {
        XPackPlugin.bindFeatureSet(binder(), WatcherFeatureSet.class);

        if (enabled == false || transportClientMode) {
            bind(WatcherLicensee.class).toProvider(Providers.of(null));
            return;
        }

        bind(WatcherLicensee.class).asEagerSingleton();
        bind(WatcherLifeCycleService.class).asEagerSingleton();
        bind(WatcherIndexTemplateRegistry.class).asEagerSingleton();
    }
}
