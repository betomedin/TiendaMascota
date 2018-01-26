/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.beans;

import cl.entities.*;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author betomedin
 */
@Local
public interface ServicioBeanLocal {
    
    Usuario iniciarSesion(String rut, String clave);
            
    Usuario buscarUsuario(String rut);  
    
    Producto buscarProducto(int id);
        
    void guardar(Object o);
    
    void sincronizar(Object o);
    
    Categoria buscarCategoria(int id);
    
    List<Categoria> getCategorias();
    
    List<Producto> getProductos();
    
    void comprar(List<Producto> carrito);
    
        
}
