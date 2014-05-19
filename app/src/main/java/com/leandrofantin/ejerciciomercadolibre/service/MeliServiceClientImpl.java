package com.leandrofantin.ejerciciomercadolibre.service;


import android.util.Log;

import com.leandrofantin.ejerciciomercadolibre.json.ResponseSearch;
import com.leandrofantin.ejerciciomercadolibre.json.translators.ResponseSearchTranslatorImpl;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lfantin (lea)
 * @see com.leandrofantin.ejerciciomercadolibre.json.MeliServiceClient
 */
public class MeliServiceClientImpl implements com.leandrofantin.ejerciciomercadolibre.json.MeliServiceClient<ResponseSearch> {

    private static final String TAG = MeliServiceClientImpl.class.getName();
    private static final String URL = "https://api.mercadolibre.com/sites/MLA/search?q={q}";
    private static final String QUERY_SEARCH = "q";

    @Override
    public ResponseSearch search(String query)  {
        Log.d(TAG,String.format("Searching: %s",query));
        //inicializo rest template
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        //cargo parametros
        Map<String, String> params = new HashMap<String, String>();
        params.put(QUERY_SEARCH,query);
        //hago la llamada
        String json = restTemplate.getForObject(URL, String.class, params);
        Log.d(TAG,String.format("Fin searching"));
        //hago un unmarshall desde json
        ResponseSearchTranslatorImpl translator = new ResponseSearchTranslatorImpl();
        ResponseSearch responseSearch = translator.translate(json);
        //devuelvo respuesta
        return responseSearch;
    }


}
