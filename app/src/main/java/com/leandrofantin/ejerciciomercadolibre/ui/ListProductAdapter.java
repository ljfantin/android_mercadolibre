package com.leandrofantin.ejerciciomercadolibre.ui;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.leandrofantin.ejerciciomercadolibre.R;
import com.leandrofantin.ejerciciomercadolibre.json.Product;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lfantin (lea)
 * */
public class ListProductAdapter extends ArrayAdapter<Product>  {

	private static final String TAG = ListProductAdapter.class.getName();

	public ListProductAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
	}

    public ListProductAdapter(Context context, int textViewResourceId,List<Product> products) {
        super(context, textViewResourceId, products);
    }

	@Override
	public View getView(int position, View rowView, ViewGroup parent) {

		if (rowView == null) {
			Activity activity = (Activity) this.getContext();
		    LayoutInflater inflater = activity.getLayoutInflater();			
		    rowView = inflater.inflate(R.layout.product_layout, null);
		    TextView textViewTitulo = (TextView) rowView.findViewById(R.id.textViewTitulo);
		    TextView textViewPrecio = (TextView) rowView.findViewById(R.id.textViewPrecio);
		    ImageView imageAvatar = (ImageView) rowView.findViewById(R.id.imageAvatar);
			ViewHolder viewHolder = new ViewHolder(textViewTitulo,textViewPrecio,imageAvatar);
			rowView.setTag(viewHolder);
			
			ViewHolder holder = (ViewHolder) rowView.getTag();
		    Product product = this.getItem(position);
		    holder.getTitulo().setText(product.getTitle());
            String price = this.getContext().getString(R.string.precio_text,product.getPrice());
		    holder.getPrecio().setText(price);

            // Task que permite bajar una imagen desde internet
            //DownloadImagesTask downloadTask = new DownloadImagesTask(holder.avatar);
            //downloadTask.execute(product.getThumbnail());
            //Se reemplaza la tarea que carga imagenes, por la libreria Android Universal Image Loader
            //La cual maneja cache en memoria y en disco.Se debe llamar desde el Thread UI, utiliza executors, no prOduce ANR.
            ImageLoader.getInstance().displayImage(product.getThumbnail(), holder.avatar);
        }

		
		return rowView;
	}


    public ArrayList<Product> getProducts() {

        ArrayList<Product> products = new ArrayList<Product>();
        for (int index = 0; index<this.getCount();index++)
            products.add(index,this.getItem(index));

        return products;
    }

    private static class ViewHolder {
        private TextView titulo = null;
        private TextView precio = null;
        private ImageView avatar = null;

        ViewHolder(TextView titulo, TextView precio, ImageView avatar) {
            this.titulo = titulo;
            this.precio = precio;
            this.avatar = avatar;
        }

        public TextView getTitulo() {
            return titulo;
        }

        public void setTitulo(TextView titulo) {
            this.titulo = titulo;
        }

        public TextView getPrecio() {
            return precio;
        }

        public void setPrecio(TextView precio) {
            this.precio = precio;
        }

        public ImageView getAvatar() {
            return avatar;
        }

        public void setAvatar(ImageView avatar) {
            this.avatar = avatar;
        }
    }
}
