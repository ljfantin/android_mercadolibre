package com.leandrofantin.ejerciciomercadolibre.utils;

import android.graphics.drawable.Drawable;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;


/**
 * @author lfantin (lea)
 * */
public class ImageUtils {

	public static Drawable loadImageFromURL(URL url) throws IOException	{

		 InputStream is = (InputStream) url.getContent();
	     Drawable d = Drawable.createFromStream(is, "src name");
		 return d;
	}
}
