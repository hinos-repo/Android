package com.mysample.exoIcyProject.lib;

import android.support.annotation.NonNull;

import com.google.android.exoplayer2.ext.okhttp.OkHttpDataSource;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.util.Predicate;

import okhttp3.CacheControl;
import okhttp3.Call;

class HinoIcyDataSourceFactory extends OkHttpDataSource.BaseFactory
{
    private Call.Factory callFactory;
    private String userAgent;
    private Predicate<String> contentTypePredicate;
    private CacheControl cacheControl;
    private HinoIcyDataSource.IcyHeadersListener icyHeadersListener;
    private HinoIcyDataSource.IcyMetadataListener icyMetadataListener;

    private HinoIcyDataSourceFactory() {
        // See class Builder
    }

    /**
     * Constructs a IcyHttpDataSourceFactory.
     */
    public final static class Builder {
        private final HinoIcyDataSourceFactory factory;

        public Builder(@NonNull Call.Factory callFactory) {
            // Apply defaults
            factory = new HinoIcyDataSourceFactory();
            factory.callFactory = callFactory;
        }

        public Builder setUserAgent(@NonNull final String userAgent) {
            factory.userAgent = userAgent;
            return this;
        }

        public Builder setContentTypePredicate(@NonNull final Predicate<String> contentTypePredicate) {
            factory.contentTypePredicate = contentTypePredicate;
            return this;
        }

        public Builder setCacheControl(@NonNull final CacheControl cacheControl) {
            factory.cacheControl = cacheControl;
            return this;
        }

        public Builder setIcyHeadersListener(@NonNull final HinoIcyDataSource.IcyHeadersListener icyHeadersListener) {
            factory.icyHeadersListener = icyHeadersListener;
            return this;
        }

        public Builder setIcyMetadataChangeListener(@NonNull final HinoIcyDataSource.IcyMetadataListener icyMetadataListener) {
            factory.icyMetadataListener = icyMetadataListener;
            return this;
        }

        public HinoIcyDataSourceFactory build() {
            return factory;
        }
    }

    @Override
    protected HinoIcyDataSource createDataSourceInternal(@NonNull HttpDataSource.RequestProperties defaultRequestProperties) {
        return new HinoIcyDataSource.Builder(callFactory)
                .setUserAgent(userAgent)
                .setContentTypePredicate(contentTypePredicate)
                .setCacheControl(cacheControl)
                .setDefaultRequestProperties(defaultRequestProperties)
                .setIcyHeadersListener(icyHeadersListener)
                .setIcyMetadataListener(icyMetadataListener)
                .build();
    }
}