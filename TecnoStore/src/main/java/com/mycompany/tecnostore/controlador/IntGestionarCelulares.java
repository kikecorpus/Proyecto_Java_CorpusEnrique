
package com.mycompany.tecnostore.controlador;

import com.mycompany.tecnostore.modelo.Celular;
import java.util.ArrayList;


public interface IntGestionarCelulares {
    
    void registrarC(Celular celular);
    void actualizarC(Celular celular);
    void eliminarC(int id);
    ArrayList<Celular> listarC();
    
}
