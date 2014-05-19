package com.leandrofantin.ejerciciomercadolibre;

import android.app.Application;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;


/**
 * @author lfantin (lea)
 *         Inicializa Android Universal Image Loader, libreria utilizada para cachear imagenes en memoria y en disco
 * @see https://github.com/nostra13/Android-Universal-Image-Loader
 */
public class MercadolibreApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.ic_product)
                .showImageOnFail(R.drawable.ic_product)
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .build();
        ImageLoader.getInstance().init(config);
    }
}