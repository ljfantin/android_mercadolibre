package com.leandrofantin.ejerciciomercadolibre.json.translators;

import android.util.Log;

import com.leandrofantin.ejerciciomercadolibre.json.Pager;
import com.leandrofantin.ejerciciomercadolibre.json.Product;
import com.leandrofantin.ejerciciomercadolibre.json.ResponseSearch;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author lfantin (lea)
 */
public class ResponseSearchTranslatorImpl implements  Translator<ResponseSearch>{

    private static final String TAG = ResponseSearchTranslatorImpl.class.getName();
    @Override
    public ResponseSearch translate(String json) {
        ResponseSearch response = new ResponseSearch();
        try {
            JSONObject jsonObject = new JSONObject(json);

            //carga el pager
            Pager pager = getPager(jsonObject.getJSONObject("paging"));
            response.setPager(pager);

            //carga los productos
            JSONArray results = jsonObject.getJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                JSONObject jsonProduct = results.getJSONObject(i);
                Product product = this.getProduct(jsonProduct);
                response.getResults().add(product);
            }

        }   catch(JSONException e)  {
            Log.e(TAG,"Ocurrio un error parsean el json",e);
        }
        return response;
    }

    private Product getProduct(JSONObject jsonObject) throws JSONException {

        Product result = new Product(jsonObject.getString("id"),jsonObject.getString("title"),jsonObject.getString("price"),jsonObject.getString("subtitle"),jsonObject.getString("thumbnail"),jsonObject.getString("available_quantity"),jsonObject.getString("sold_quantity"),jsonObject.getString("condition"));
        return result;
    }
    private Pager getPager(JSONObject jsonObject) throws JSONException {

        Pager result = new Pager(jsonObject.getString("total"),jsonObject.getString("offset"),jsonObject.getString("limit"));
        return result;
    }
}
