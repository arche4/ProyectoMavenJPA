/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.poli.appcoiso.servlet;

import com.co.poli.appcoiso.controller.EmpresaJpaController;
import com.co.poli.appcoiso.controller.PersonaasistenteJpaController;
import com.co.poli.appcoiso.model.Personaasistente;
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
@WebServlet(name = "AsistentesServlet", urlPatterns = {"/AsistentesServlet"})
public class AsistentesServlet extends HttpServlet {

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
        PersonaasistenteJpaController asistenteJAP = new PersonaasistenteJpaController(JPAFactory.getFACTORY());
        Personaasistente asistente;

        String cedula = request.getParameter("cedula");
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String celular = request.getParameter("celular");
        String telefono = request.getParameter("telefono");
        String correo = request.getParameter("correo");
        String asistencia = request.getParameter("asistencia");

        String accion = request.getParameter("accion");
        String verAsistente = request.getParameter("verAsistente");
        String modificar = request.getParameter("editar");
        String volver = request.getParameter("volver");

        if (accion != null && !accion.equals("")) {
            switch (accion) {
                case "crear":
                    asistente = new Personaasistente(cedula, nombre, apellido, celular, telefono, correo, asistencia);
                     {
                        try {
                            asistenteJAP.create(asistente);
                        } catch (Exception ex) {
                            Logger.getLogger(AsistentesServlet.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    List<Personaasistente> listAsistente = asistenteJAP.findPersonaasistenteEntities();
                    session.setAttribute("asistente", listAsistente);
                    rd = request.getRequestDispatcher("/view/asistente.jsp");
                    break;
                case "editar":
                    asistente = new Personaasistente(cedula, nombre, apellido, celular, telefono, correo, asistencia);
                     {
                        try {
                            asistenteJAP.edit(asistente);
                        } catch (Exception ex) {
                            Logger.getLogger(AsistentesServlet.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        List<Personaasistente> listA = asistenteJAP.findPersonaasistenteEntities();
                        session.setAttribute("asistente", listA);
                        rd = request.getRequestDispatcher("/view/asistente.jsp");
                    }
                    break;
            }
        }
        if (verAsistente != null && !verAsistente.equals("")) {
            session.setAttribute("asistenteVer", verAsistente);
            rd = request.getRequestDispatcher("/view/verAsistente.jsp");
        }
        if (volver != null && !volver.equals("")) {
            rd = request.getRequestDispatcher("/view/asistente.jsp");
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
