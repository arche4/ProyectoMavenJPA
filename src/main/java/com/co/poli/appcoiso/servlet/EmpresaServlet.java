/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.poli.appcoiso.servlet;

import com.co.poli.appcoiso.controller.EmpresaJpaController;
import com.co.poli.appcoiso.controller.exceptions.IllegalOrphanException;
import com.co.poli.appcoiso.controller.exceptions.NonexistentEntityException;
import com.co.poli.appcoiso.model.Empresa;
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
@WebServlet(name = "EmpresaServlet", urlPatterns = {"/EmpresaServlet"})
public class EmpresaServlet extends HttpServlet {

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
        EmpresaJpaController empJpa = new EmpresaJpaController(JPAFactory.getFACTORY());

        String nit = request.getParameter("nit");
        String nombre = request.getParameter("nombre");
        String sector = request.getParameter("sector");
        String actividadEconomica = request.getParameter("actividadEconomica");
        String antiguedad = request.getParameter("antiguedad");

        String mensaje = "";
        String btnaccion = request.getParameter("btnaccion");
        String ver = request.getParameter("ver");
        String modificar = request.getParameter("editar");
        String volver = request.getParameter("volver");

        if (btnaccion != null && !btnaccion.equals("")) {
            //validar si empresa existe
            Empresa emp = empJpa.validarEmpresa(nit);
            if (emp == null) {
                Empresa em = new Empresa(nit, nombre, sector, actividadEconomica, antiguedad);
                mensaje = empJpa.crear(em);

                List<Empresa> ListEmpresa = empJpa.findEmpresaEntities();
                session.setAttribute("Empresa", ListEmpresa);
                rd = request.getRequestDispatcher("/view/registroPersonas.jsp");
            }
            session.setAttribute("mensaje", mensaje);
            rd = request.getRequestDispatcher("/view/registroPersonas.jsp");
        }
        if (ver != null && !ver.equals("")) {
            session.setAttribute("nit", ver);
            rd = request.getRequestDispatcher("/view/verEmpresa.jsp");

        }

        if (modificar != null && !modificar.equals("")) {
            Empresa em = new Empresa(nit, nombre, sector, actividadEconomica, antiguedad);
            try {
                empJpa.editar(em);
            } catch (NonexistentEntityException ex) {
                Logger.getLogger(EmpresaServlet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(EmpresaServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            List<Empresa> ListEmpresa = empJpa.findEmpresaEntities();
            session.setAttribute("Empresa", ListEmpresa);
            rd = request.getRequestDispatcher("/view/registroPersonas.jsp");

        }

        if (volver != null && !volver.equals("")) {
            rd = request.getRequestDispatcher("/view/registroPersonas.jsp");

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
