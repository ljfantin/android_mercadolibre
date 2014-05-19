package com.leandrofantin.ejerciciomercadolibre.json.translators;

import com.leandrofantin.ejerciciomercadolibre.json.ResponseService;

/**
 * Created by lfantin on 14/05/2014.
 */
public interface Translator<T extends ResponseService> {
    public T translate(String json);
}
