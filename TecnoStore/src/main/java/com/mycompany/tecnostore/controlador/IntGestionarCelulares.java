
package com.mycompany.tecnostore.controlador;

import com.mycompany.tecnostore.modelo.Celular;


public interface IntGestionarCelulares {
    
    void registrarC(Celular celular);
    void actualizarC(Celular celular, int id);
    void eliminarC(int id);
    void listarC();
    
}
