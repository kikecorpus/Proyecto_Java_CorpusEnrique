
package com.mycompany.tecnostore.controlador;


import com.mycompany.tecnostore.modelo.Cliente;
import java.util.ArrayList;


public interface IntGestionarClientes {
    
    void registrarCl(Cliente cliente);
    void actualizarCl(Cliente cliente, int id);
    void eliminarCl(int id);
    ArrayList<Cliente> listarCl();
    
}
