package com.leandrofantin.ejerciciomercadolibre.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.leandrofantin.ejerciciomercadolibre.R;
import com.leandrofantin.ejerciciomercadolibre.json.Product;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * @author lfantin (lea)
 * Fragmento que muestra el detalle de una publicacion de un producto.
 */
public class DetailItemFragment extends Fragment {

    public final static String TAG = DetailItemFragment.class.getName();
    private static final String ARG_TITULO = "titulo";
    private static final String ARG_DESCRIPCION = "descripcion";
    private static final String ARG_PRECIO = "precio";
    private static final String ARG_URL_AVATAR = "urlAvatar";
    private static final String ARG_CANT_DISPONIBLE = "cantidadDisponible";
    private static final String ARG_CANT_VENDIDAS = "cantidadVendidas";
    private static final String ARG_CONDICION = "condicion";

    private String titulo;
    private String precio;
    private String descripcion;
    private String urlAvatar;
    private String cantidadDisponible;
    private String cantidadVendidas;
    private String condicion;

    //private WeakReference<DownloadImagesTask> asyncTaskWeakRef;



    /**
     * Factory method para crear una instancia, con los valores del detalle.
     *
     * @param titulo Titulo del producto.
     * @param precio Precio del producto.
     * @return A new instance of fragment DetailItemFragment.
     */
    public static DetailItemFragment newInstance(String titulo, String precio,String descripcion,String urlAvatar,String cantidadDisponible,String cantidadVendidas,String condicion) {
        DetailItemFragment fragment = new DetailItemFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITULO, titulo);
        args.putString(ARG_PRECIO, precio);
        args.putString(ARG_DESCRIPCION, descripcion);
        args.putString(ARG_URL_AVATAR, urlAvatar);
        args.putString(ARG_CANT_DISPONIBLE, cantidadDisponible);
        args.putString(ARG_CANT_VENDIDAS, cantidadVendidas);
        args.putString(ARG_CONDICION, condicion);

        fragment.setArguments(args);
        return fragment;
    }
    public DetailItemFragment() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            titulo = getArguments().getString(ARG_TITULO);
            precio = getArguments().getString(ARG_PRECIO);
            descripcion = getArguments().getString(ARG_DESCRIPCION);
            urlAvatar = getArguments().getString(ARG_URL_AVATAR);
            cantidadDisponible = getArguments().getString(ARG_CANT_DISPONIBLE);
            cantidadVendidas = getArguments().getString(ARG_CANT_VENDIDAS);
            condicion = getArguments().getString(ARG_CONDICION);
        }
        setRetainInstance(true);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Log.d(TAG, "Remove action search");
        menu.removeItem(R.id.action_search);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState!=null)   {
            titulo = savedInstanceState.getString(ARG_TITULO);
            precio = savedInstanceState.getString(ARG_PRECIO);
            descripcion = savedInstanceState.getString(ARG_DESCRIPCION);
            urlAvatar = savedInstanceState.getString(ARG_URL_AVATAR);
            cantidadDisponible = savedInstanceState.getString(ARG_CANT_DISPONIBLE);
            cantidadVendidas = savedInstanceState.getString(ARG_CANT_VENDIDAS);
            condicion = savedInstanceState.getString(ARG_CONDICION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onStart() {
        super.onStart();
        //actualizo los text view
        this.updateTextViews();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString(ARG_TITULO, titulo);
        savedInstanceState.putString(ARG_PRECIO, precio);
        savedInstanceState.putString(ARG_DESCRIPCION, descripcion);
        savedInstanceState.putString(ARG_URL_AVATAR, urlAvatar);
        savedInstanceState.putString(ARG_CANT_DISPONIBLE, cantidadDisponible);
        savedInstanceState.putString(ARG_CANT_VENDIDAS, cantidadVendidas);
        savedInstanceState.putString(ARG_CONDICION, condicion);
        super.onSaveInstanceState(savedInstanceState);
    }

    public void updateDetail(Product product)   {

        this.titulo = product.getTitle();
        this.precio = product.getPrice();
        this.descripcion = product.getSubtitle();
        this.urlAvatar = product.getThumbnail();
        this.cantidadDisponible = product.getAvailableQuantity();
        this.cantidadVendidas = product.getSoldQuantity();
        this.condicion = product.getCondition();
        this.updateTextViews();
    }

    private void updateTextViews()  {

        String sinValor = getString(R.string.sinvalor);

        //titulo
        TextView textViewTitulo = (TextView) this.getActivity().findViewById(R.id.textViewTitulo);
        textViewTitulo.setText(titulo==null?sinValor:titulo);


        //precio
        String price = getString(R.string.precio_text, this.precio==null?sinValor:precio);
        TextView textViewPrecio = (TextView) this.getActivity().findViewById(R.id.textViewPrecio);
        textViewPrecio.setText(price);

        //cantidad disponible
        String textCantidadDisponible = getString(R.string.textViewCantidadDisponible_text, this.cantidadDisponible==null?sinValor:cantidadDisponible);
        TextView textViewCantidadDisponible = (TextView) this.getActivity().findViewById(R.id.textViewCantidadDisponible);
        textViewCantidadDisponible.setText(textCantidadDisponible);

        //cantidad vendidas
        String textCantidadVendidas = getString(R.string.textViewCantidadVendidas_text, this.cantidadVendidas==null?sinValor:cantidadVendidas);
        TextView textViewCantidadVendidas = (TextView) this.getActivity().findViewById(R.id.textViewCantidadVendidas);
        textViewCantidadVendidas.setText(textCantidadVendidas);

        //condicion
        String textCondicion = getString(R.string.textViewCondition_text, this.condicion==null?sinValor:condicion);
        TextView textViewCondicion = (TextView) this.getActivity().findViewById(R.id.textViewCondition);
        textViewCondicion.setText(textCondicion);

        ImageView imageProduct = (ImageView)this.getActivity().findViewById(R.id.imageProduct);
        //this.asyncTaskWeakRef = new WeakReference<DownloadImagesTask>(new DownloadImagesTask(imageProduct));
        //this.asyncTaskWeakRef.get().execute(this.urlAvatar);
        //Se reemplaza la tarea que carga imagenes, por la libreria Android Universal Image Loader
        //La cual maneja cache en memoria y en disco.Se debe llamar desde el Thread UI, utiliza executors, no prOduce ANR.
        ImageLoader.getInstance().displayImage(this.urlAvatar, imageProduct);
    }

//    private boolean isAsyncTaskPendingOrRunning() {
//
//        return this.asyncTaskWeakRef != null &&
//                this.asyncTaskWeakRef.get() != null &&
//                this.asyncTaskWeakRef.get().getStatus().equals(AsyncTask.Status.FINISHED)==false;
//    }

    @Override
    public void onStop() {
        super.onPause();
        Log.d(TAG,"onStop");
        //if (isAsyncTaskPendingOrRunning())
        //    this.asyncTaskWeakRef.get().cancel(false);
    }


}
