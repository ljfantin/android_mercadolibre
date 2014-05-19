package com.leandrofantin.ejerciciomercadolibre.json;

import java.lang.String;

/**
 * @author lfantin (lea)
 * Interfaz que define un cliente de la api de mercadolibre para realizar busquedas.
 */

public interface MeliServiceClient<T extends ResponseService> {

    public T search(String query);
}
