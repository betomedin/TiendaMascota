/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.beans;

import cl.entities.Categoria;
import cl.entities.Producto;
import cl.entities.Usuario;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author betomedin
 */
@Stateless
public class ServicioBean implements ServicioBeanLocal {

    @PersistenceContext(unitName = "TiendaMascotaPU")
    private EntityManager em;

    @Override
    public Usuario iniciarSesion(String rut, String clave) {
        try {
            return (Usuario) em.createNamedQuery("Usuario.iniciarSesion", Usuario.class)
                    .setParameter("rutUser", rut)
                    .setParameter("clave", clave)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void guardar(Object o) {
        em.persist(o);        
    }

    @Override
    public List<Categoria> getCategorias() {
        return em.createNamedQuery("Categoria.findAll").getResultList();
    }

    @Override
    public List<Producto> getProductos() {
        return em.createNamedQuery("Producto.findAll").getResultList();
    }

    @Override
    public void sincronizar(Object o) {
        em.merge(o);
        em.flush();
    }

    @Override
    public Categoria buscarCategoria(int id) {
        return em.find(Categoria.class, id);
    }

    @Override
    public Usuario buscarUsuario(String rut) {
        return em.find(Usuario.class, rut);
    }

    @Override
    public Producto buscarProducto(int id) {
        return em.find(Producto.class, id);
    }

    @Override
    public void comprar(List<Producto> carrito) {
        
    }
       
}
