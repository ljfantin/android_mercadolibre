package com.leandrofantin.ejerciciomercadolibre.fragments;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.leandrofantin.ejerciciomercadolibre.R;
import com.leandrofantin.ejerciciomercadolibre.json.Product;
import com.leandrofantin.ejerciciomercadolibre.json.ResponseSearch;
import com.leandrofantin.ejerciciomercadolibre.service.MeliServiceClientImpl;
import com.leandrofantin.ejerciciomercadolibre.ui.ListProductAdapter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * @author lfantin (lea)
 * Fragmento que muestra los resultados de busqueda.
 */
public class ListItemFragment extends ListFragment {

    public static final String TAG = ListItemFragment.class.getName();
    private static final String ARG_QUERY = "query";
    private static final String ARG_CANT_RESULTS = "cantidad";
    private static final String ARG_RESULTS = "results";

    private String query;
    private String cantidadResultados;
    private ArrayList<Product> products = null;

    private MeliServiceClientImpl meliServiceClient=null;

    private OnFragmentInteractionListener listener;

    private ProgressBar progressBar = null;

    private WeakReference<HttpRequestTask> asyncTaskWeakRef;

    /*
    * Factory method que crea una instancia del fragmento y le pasa como argumento el query.
    * */
    public static ListItemFragment newInstance(String query) {
        ListItemFragment fragment = new ListItemFragment();
        Bundle args = new Bundle();
        args.putString(ARG_QUERY, query);
        fragment.setArguments(args);
        return fragment;
    }

    public ListItemFragment() {
        super();
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate");
        this.meliServiceClient = new MeliServiceClientImpl();
        if (getArguments() != null) {
            this.query = getArguments().getString(ARG_QUERY);
            this.cantidadResultados = getArguments().getString(ARG_CANT_RESULTS);
            this.products = getArguments().getParcelableArrayList(ARG_RESULTS);
        }
        setRetainInstance(true);
    }

    @Override
    public void onStart() {

        super.onStart();
        Log.d(TAG,"onStart");
        updateTextViews();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {

            throw new ClassCastException(activity.toString()
                + "La actividad debe implementar OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        if (listener !=null) {
            ListProductAdapter adapter = (ListProductAdapter) l.getAdapter();
            listener.onFragmentInteraction(adapter.getItem(position));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG,"onCreateView");
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        Log.d(TAG,"onActivityCreated");
        super.onActivityCreated(savedInstanceState);
        this.progressBar = (ProgressBar) getActivity().findViewById(R.id.progressBarListProduct);
        if (savedInstanceState!=null)   {

            this.query = savedInstanceState.getString(ARG_QUERY);
            this.cantidadResultados = savedInstanceState.getString(ARG_CANT_RESULTS);
            this.products = savedInstanceState.getParcelableArrayList(ARG_RESULTS);
            ListProductAdapter adapter = new ListProductAdapter(getActivity(), android.R.layout.simple_list_item_1, products);
            setListAdapter(adapter);
            Log.d(TAG, String.format("Restore data - Query=%s, CountResult=%s", query, cantidadResultados));
        }
        if (products == null)
            runSearch();
    }

    private void runSearch()    {

        //actualizo el mensaje
        updateTextViews();
        HttpRequestTask task = new HttpRequestTask(this);
        this.asyncTaskWeakRef = new WeakReference<HttpRequestTask>(task);
        task.execute(query);
    }

    public void runSearch(String query)    {
        this.setQuery(query);
        this.setCantidadResultados(null);
        this.runSearch();
    }

    private void updateTextViews()  {
        try {
            TextView textView = (TextView) this.getActivity().findViewById(R.id.textViewQuery);
            String message = query;
            String message2 = "";
            TextView textViewNumeroPagina = (TextView) this.getActivity().findViewById(R.id.textViewNumeroPagina);
            if (cantidadResultados != null) {
                message = getString(R.string.pager_message, this.query, this.cantidadResultados);
                message2 = getString(R.string.pager_message2);
            }

            textView.setText(message);
            textViewNumeroPagina.setText(message2);
        } catch(Exception e)    {
            Log.e(TAG,"Error armando mensaje de query",e);
        }
    }

    private String getQuery() {
        return query;
    }

    private void setQuery(String query) {
        this.query = query;
    }

    private String getCantidadResultados() {
        return cantidadResultados;
    }

    private void setCantidadResultados(String cantidadResultados) {
        this.cantidadResultados = cantidadResultados;
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG,"onPause");
    }

    @Override
    public void onStop() {
        super.onPause();
        Log.d(TAG,"onStop");
        if (isAsyncTaskPendingOrRunning())
            this.asyncTaskWeakRef.get().cancel(false);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        Log.d(TAG,"onSaveInstanceState");
        //guardo el query
        savedInstanceState.putString(ARG_QUERY,this.query);
        //guardo la cantidad de resultados
        savedInstanceState.putString(ARG_CANT_RESULTS, this.cantidadResultados);
        //guardo la lista de productos
        if (this.products!=null)
            savedInstanceState.putParcelableArrayList(ARG_RESULTS, this.products);
        //llamo a la implementacion del padre
        super.onSaveInstanceState(savedInstanceState);
    }

    private boolean isAsyncTaskPendingOrRunning() {

        return this.asyncTaskWeakRef != null &&
                this.asyncTaskWeakRef.get() != null &&
                this.asyncTaskWeakRef.get().getStatus().equals(AsyncTask.Status.FINISHED)==false;
    }

    private void setResults(ArrayList<Product> products, String query, String count)    {
        this.products = products;
        ListProductAdapter adapter = new ListProductAdapter(getActivity(), android.R.layout.simple_list_item_1, this.products);
        setListAdapter(adapter);
        this.query = query;
        this.cantidadResultados = count;
        this.updateTextViews();
    }
    /**
     * Interface que deben inplementar las activdades que contengan este fragmento, para que puedan
     * notificarse con el producto seleccionado.
     * @see <a href="http://developer.android.com/training/basics/fragments/communicating.html"
     */
    public interface OnFragmentInteractionListener {

        public void onFragmentInteraction(Product product);
    }


    private static class HttpRequestTask extends AsyncTask<String, Void, ResponseSearch> {

        private WeakReference<ListItemFragment> fragmentWeakRef;
        private String search = null;

        private HttpRequestTask(ListItemFragment fragmentWeakRef) {
            this.fragmentWeakRef = new WeakReference<ListItemFragment>(fragmentWeakRef);
        }

        @Override
        protected ResponseSearch doInBackground(String... params) {
            if (this.fragmentWeakRef!=null && this.fragmentWeakRef.get()!=null) {

                try {
                    this.search = params[0];
                    return this.fragmentWeakRef.get().meliServiceClient.search(search);

                } catch (Exception e) {
                    Log.e(TAG, "Ocurrio un error consultando el servicio", e);
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(ResponseSearch response) {
            if (this.fragmentWeakRef!=null && this.fragmentWeakRef.get()!=null && isCancelled()==false) {
                final ListItemFragment frame = this.fragmentWeakRef.get();
                frame.progressBar.setVisibility(View.GONE);
                if (response!=null) {
                    frame.setResults(response.getResults(),this.search,response.getPager().getTotal());
                } else   {
                    //significa que ocurrio un error
                    if (this.fragmentWeakRef!=null && this.fragmentWeakRef.get()!=null) {

                        Toast.makeText(fragmentWeakRef.get().getActivity(), R.string.error_message_meli, Toast.LENGTH_LONG)
                                .show();
                    }
                }
            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            if (this.fragmentWeakRef!=null && this.fragmentWeakRef.get()!=null) {
                final ListItemFragment frame = fragmentWeakRef.get();
                frame.progressBar.setVisibility(View.GONE);
            }
        }

        @Override
        protected void onPreExecute() {
            if (this.fragmentWeakRef.get() != null) {
                final ListItemFragment frame = fragmentWeakRef.get();
                ListProductAdapter adapter = ((ListProductAdapter)  frame.getListAdapter());
                if (adapter!=null)
                    adapter.clear();
                frame.progressBar.setVisibility(View.VISIBLE);
            }
        }

    }
}
