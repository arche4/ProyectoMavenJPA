/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.poli.appcoiso.servlet;

import com.co.poli.appcoiso.controller.UsuarioJpaController;
import com.co.poli.appcoiso.controller.exceptions.NonexistentEntityException;
import com.co.poli.appcoiso.model.Usuario;
import com.co.poli.appcoiso.util.JPAFactory;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Manuela
 */
public class UsuarioServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        RequestDispatcher rd = null;
        UsuarioJpaController ujc = new UsuarioJpaController(JPAFactory.getFACTORY());

        String idusuario = request.getParameter("cedula");
        String nombreUsuario = request.getParameter("nombre");
        String apellidoUsuario = request.getParameter("apellido");
        String claveUsuario = request.getParameter("clave");
        String rolUsuario = request.getParameter("rol");

        String accion = request.getParameter("accion");
        String editar = request.getParameter("editar");
        String modificar = request.getParameter("modificar");
        String eliminar = request.getParameter("eliminar");
        String volver = request.getParameter("volver");

        String mensaje = "";

        if (accion != null && !accion.equals("")) {
            switch (accion) {
                case "crear":

                    Usuario usuario = new Usuario(idusuario, nombreUsuario, apellidoUsuario, claveUsuario, rolUsuario);
                    mensaje = ujc.crearUsuario(usuario);
                    List<Usuario> ListUsuario = ujc.findUsuarioEntities();
                    session.setAttribute("Usuario", ListUsuario);
                    rd = request.getRequestDispatcher("/view/usuario.jsp");
                    break;
                case "listar":
                    List<Usuario> lUsuario = ujc.findUsuarioEntities();
                    session.setAttribute("Usuario", lUsuario);
                    rd = request.getRequestDispatcher("/view/usuario.jsp");
                    break;

                case "modificar":
                    Usuario user = new Usuario(idusuario, nombreUsuario, apellidoUsuario, claveUsuario, rolUsuario);
                    try {
                        ujc.edit(user);
                    } catch (Exception ex) {
                        Logger.getLogger(UsuarioServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    List<Usuario> usuarioList = ujc.findUsuarioEntities();
                    session.setAttribute("Usuario", usuarioList);
                    rd = request.getRequestDispatcher("/view/usuario.jsp");
                    break;
                case "volver":
                    rd = request.getRequestDispatcher("/view/usuario.jsp");
                    break;

            }
        } 
        if (editar != null && !editar.equals("")) {
            session.setAttribute("documento", editar);
            rd = request.getRequestDispatcher("/view/verUsuario.jsp");
        } 
        if (eliminar != null && !eliminar.equals("")) {
            try {
                ujc.destroy(idusuario);
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(UsuarioServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            List<Usuario> userList = ujc.findUsuarioEntities();
            session.setAttribute("Usuario", userList);
            rd = request.getRequestDispatcher("/view/usuario.jsp");
        }else{
             rd = request.getRequestDispatcher("/view/verUsuario.jsp");
        }

        rd.forward(request, response);
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
