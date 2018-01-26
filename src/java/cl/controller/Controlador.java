/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.controller;

import cl.beans.ServicioBeanLocal;
import cl.entities.*;
import cl.util.Hash;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author betomedin
 */
@WebServlet(name = "Controlador", urlPatterns = {"/control.do"})
// Necesario para poder subir fotos(imagenes) al sistemas
@MultipartConfig(location = "/tmp",
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 5 * 5)
public class Controlador extends HttpServlet {

    @EJB
    private ServicioBeanLocal servicio;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String boton = request.getParameter("boton");

        switch (boton) {
            case "login":
                login(request, response);
                break;

            case "nuevacategoria":
                nuevaCategoria(request, response);
                break;

            case "nuevoproducto":
                nuevoProducto(request, response);
                break;

            case "actualizarusuario":
                actualizarUsuario(request, response);
                break;

            case "agregarcarrito":
                agregarCarrito(request, response);
                break;

            case "eliminarcarrito":
                eliminarCarrito(request, response);
                break;

            case "comprar":
                comprar(request, response);
                break;
        }

    }

    protected void login(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String rut = request.getParameter("rut");
        String clave = request.getParameter("clave");

        Usuario user = servicio.iniciarSesion(rut, Hash.md5(clave));
        List<Producto> lista = new ArrayList();

        if (user != null) {
            if (user.getPerfil().getNombrePerfil().equalsIgnoreCase("administrador")) {
                request.getSession().setAttribute("admin", user);
            } else {
                request.getSession().setAttribute("person", user);
                request.getSession().setAttribute("carrito", lista);
            }
            response.sendRedirect("inicio.jsp");
        } else {
            request.setAttribute("msg", "Usuario no encontrado");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }

    }

    protected void nuevaCategoria(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nombre = request.getParameter("nombre");
        if (nombre.isEmpty()) {
            request.setAttribute("msg", "completa el nombre");
        } else {
            Categoria nueva = new Categoria();
            nueva.setNombreCategoria(nombre);
            servicio.guardar(nueva);
            request.setAttribute("msg", "Categoría creada con éxito");
        }
        request.getRequestDispatcher("categoria.jsp").forward(request, response);
    }

    protected void nuevoProducto(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nombre = request.getParameter("nombre");
        String descripcion = request.getParameter("descripcion");
        int precio = 0;
        int unidad = 0;
        int idCategoria = Integer.parseInt(request.getParameter("categoria"));

        InputStream stream = null;
        Part foto = request.getPart("foto");
        if (foto != null) {
            stream = foto.getInputStream();
        }

//        String foto = request.getParameter("foto");
        String errores = "";

        if (nombre.isEmpty()) {
            errores = errores.concat("- Falta nombre<br>");
        }

        if (descripcion.isEmpty()) {
            errores = errores.concat("- Falta descripcion<br>");
        }

        try {
            precio = Integer.parseInt(request.getParameter("precio"));
        } catch (Exception e) {
            errores = errores.concat("- Falta precio<br>");
        }

        try {
            unidad = Integer.parseInt(request.getParameter("unidad"));
        } catch (Exception e) {
            errores = errores.concat("- Falta unidad<br>");
        }

        if (errores.isEmpty()) {
            // Se arma la relación producto categoria
            Producto nuevo = new Producto();
            nuevo.setNombreProducto(nombre);
            nuevo.setPrecioProducto(precio);
            nuevo.setUnidadesProducto(unidad);
            nuevo.setDescripcionProducto(descripcion);
            Categoria cat = servicio.buscarCategoria(idCategoria);
            nuevo.setCategoria(cat);
            if (stream != null) {
                nuevo.setFotoProducto(IOUtils.toByteArray(stream));
            }
            servicio.guardar(nuevo);

            // Aqui se arma la relación categoria producto 
            cat.getProductoList().add(nuevo);
            servicio.sincronizar(cat);

            request.setAttribute("msg", "Producto creado con éxito");
        } else {
            request.setAttribute("msg", errores);
        }

        request.getRequestDispatcher("producto.jsp").forward(request, response);
    }

    protected void actualizarUsuario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String rut = request.getParameter("rut");
        String email = request.getParameter("email");
        String clave1 = request.getParameter("clave1");
        String clave2 = request.getParameter("clave2");

        String errores = "";
        Usuario usuario = servicio.buscarUsuario(rut);

        if (!clave1.equals(clave2)) {
            errores = errores.concat("- Las claves no son iguales<br>");
        }

        if (clave1.isEmpty() || clave2.isEmpty()) {
            errores = errores.concat("- Las claves estan vacías<br>");
        }

        if (email.isEmpty()) {
            errores = errores.concat("- El correo está en blanco<br>");
        }

        if (errores.equals("")) {
            usuario.setEmailUser(email);
            usuario.setClave(Hash.md5(clave1));
            servicio.sincronizar(usuario);
            request.getSession().setAttribute("admin", usuario);
            request.setAttribute("msg", "Usuario actualizado correctamente ");
            request.getRequestDispatcher("misdatos.jsp").forward(request, response);
        } else {
            request.setAttribute("msg", errores);
            request.getRequestDispatcher("misdatos.jsp").forward(request, response);
        }

    }

    protected void agregarCarrito(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int id = 0;

        try {
            id = Integer.parseInt(request.getParameter("id"));
            Producto producto = servicio.buscarProducto(id);
            List<Producto> lista = (List<Producto>) request.getSession().getAttribute("carrito");

            lista.add(producto);
            calcularTotalCarrito(request, response);

            request.getSession().setAttribute("carrito", lista);
            request.getRequestDispatcher("catalogo.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            request.setAttribute("msg", "Error al cargar el producto en el carrito");
            request.getRequestDispatcher("catalogo.jsp").forward(request, response);
        }

    }

    protected void eliminarCarrito(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = 0;

        try {
            id = Integer.parseInt(request.getParameter("id"));
        } catch (Exception e) {
            request.setAttribute("msg", "Hubo un error, reintente.");
            request.getRequestDispatcher("carrito.jsp").forward(request, response);
        }

        List<Producto> lista = (List<Producto>) request.getSession().getAttribute("carrito");

        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getIdProducto() == id) {
                lista.remove(i);
            }
        }
        request.getSession().setAttribute("carrito", lista);
        calcularTotalCarrito(request, response);
        request.getRequestDispatcher("carrito.jsp").forward(request, response);
    }

    protected void calcularTotalCarrito(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int total = 0;

        List<Producto> lista = (List<Producto>) request.getSession().getAttribute("carrito");
        for (Producto p : lista) {
            total += p.getPrecioProducto();
            request.getSession().setAttribute("total", total);
        }
    }

    protected void comprar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        Venta venta = new Venta();
        List<DetalleVenta> detalle = new ArrayList();
        
        Usuario u = (Usuario) request.getSession().getAttribute("person");
        int total = 0;
        
        venta.setUsuario(u);
        venta.setFechaVenta(new Date());
        venta.setTotalVenta(total);
        
        List<Producto> carrito = (List<Producto>) request.getSession().getAttribute("carrito");
        
        for (Producto p : carrito) {
            DetalleVenta d = new DetalleVenta();
            d.setPrecio(p.getPrecioProducto());
            d.setProducto(p);
            d.setVenta(venta);
        }
        
        try {
            total = (int) request.getSession().getAttribute("total");
        } catch (Exception e) {
            request.setAttribute("msg", "Error al calcular el total del carrito, reintente.");
        }
        
        venta.setDetalleVentaList(detalle);
        
        servicio.guardar(venta);
        servicio.guardar(detalle);
        servicio.sincronizar(venta);
        servicio.sincronizar(detalle);
        
        List<Producto> lista = new ArrayList();
        request.getSession().setAttribute("carrito", lista);
        request.getRequestDispatcher("catalogo.jsp").forward(request, response);
        

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
