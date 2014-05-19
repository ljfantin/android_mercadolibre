package com.leandrofantin.ejerciciomercadolibre.asyntasks;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.lang.ref.WeakReference;
import java.net.URL;

/**
 * @author lfantin (lea)
 * */
public class DownloadImagesTask extends AsyncTask<String, Void, Drawable>{

	private static final String TAG = DownloadImagesTask.class.getName();
	
	private WeakReference<ImageView> imageView = null;

	public DownloadImagesTask(ImageView imageView) {
		super();
		this.imageView = new WeakReference<ImageView>(imageView);
	}

	@Override
	protected Drawable doInBackground(String... urls) {
		URL url;
		try {
            ImageView image = this.imageView.get();
//            if (image != null && isCancelled()==false) {
//
//                url = new URL(urls[0]);
//                Log.d(TAG, String.format("Bajando imagen %s", url.toString()));
//                return ImageUtils.loadImageFromURL(url);
//            }
            //Se reemplaza la tarea que carga imagenes, por la libreria Android Universal Image Loader
            //La cual maneja cache en memoria y en disco
//            https://github.com/nostra13/Android-Universal-Image-Loader
            if (image != null && isCancelled()==false) {
                ImageLoader.getInstance().displayImage(urls[0], image);
            }

        } catch (Exception e) {
			Log.e(TAG,"Error leyendo imagen",e);
		}
		return null;
	}

	@Override
	protected void onPostExecute(Drawable result) {
		ImageView image = this.imageView.get();

        if (result != null && image != null && isCancelled()==false)		{
			
			image.setImageDrawable(result);
		}
	}
}
