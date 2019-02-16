/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.poli.appcoiso.servlet;

import com.co.poli.appcoiso.controller.AfpJpaController;
import com.co.poli.appcoiso.controller.ArlJpaController;
import com.co.poli.appcoiso.controller.CargoJpaController;
import com.co.poli.appcoiso.controller.CasoJpaController;
import com.co.poli.appcoiso.controller.CitasPersonaJpaController;
import com.co.poli.appcoiso.controller.EmpresaJpaController;
import com.co.poli.appcoiso.controller.EpsJpaController;
import com.co.poli.appcoiso.controller.FormacionJpaController;
import com.co.poli.appcoiso.controller.PersonaasistenteJpaController;
import com.co.poli.appcoiso.controller.PersonaempresaJpaController;
import com.co.poli.appcoiso.controller.PersonasJpaController;
import com.co.poli.appcoiso.controller.UsuarioJpaController;
import com.co.poli.appcoiso.model.Afp;
import com.co.poli.appcoiso.model.Arl;
import com.co.poli.appcoiso.model.Cargo;
import com.co.poli.appcoiso.model.Caso;
import com.co.poli.appcoiso.model.CitasPersona;
import com.co.poli.appcoiso.model.Empresa;
import com.co.poli.appcoiso.model.Eps;
import com.co.poli.appcoiso.model.Formacion;
import com.co.poli.appcoiso.model.Personaasistente;
import com.co.poli.appcoiso.model.Personas;
import com.co.poli.appcoiso.model.Usuario;
import com.co.poli.appcoiso.util.JPAFactory;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Usuario
 */
public class usuarioLogin extends HttpServlet {

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
        String email = request.getParameter("txtid");
        String clave = request.getParameter("txtclave");
        
        UsuarioJpaController ujc = new UsuarioJpaController(JPAFactory.getFACTORY());
        EpsJpaController ejc = new EpsJpaController(JPAFactory.getFACTORY());
        ArlJpaController arl = new ArlJpaController(JPAFactory.getFACTORY());
        AfpJpaController afp = new AfpJpaController(JPAFactory.getFACTORY());
        EmpresaJpaController emp = new EmpresaJpaController(JPAFactory.getFACTORY());
        CargoJpaController car = new CargoJpaController(JPAFactory.getFACTORY());
        PersonasJpaController per = new PersonasJpaController(JPAFactory.getFACTORY());
        CitasPersonaJpaController citaJPA = new CitasPersonaJpaController(JPAFactory.getFACTORY());
        CasoJpaController casoJPA = new CasoJpaController(JPAFactory.getFACTORY());
        PersonaasistenteJpaController  asistenteJPA = new PersonaasistenteJpaController(JPAFactory.getFACTORY());
        FormacionJpaController formacionJPA = new FormacionJpaController(JPAFactory.getFACTORY());
        
        Usuario usuario = ujc.findUsuarioClave(email, clave);
        String Mensaje = "";
        if (usuario == null) {
            Mensaje = "Email o Clave no validos";
            session.setAttribute("MENSAJE", Mensaje);
            rd = request.getRequestDispatcher("index.jsp");
            
            
        } else {
            rd = request.getRequestDispatcher("view/menu.jsp");
            //Mensaje = "Email o Clave no validos";
        }
       
        session.setAttribute("USUARIO", usuario);
        List<Eps> listEps = ejc.findEpsEntities();
        session.setAttribute("EPS", listEps);
        List<Arl> ListArl = arl.findArlEntities();
        session.setAttribute("ARL", ListArl);
        List<Afp> ListAfp = afp.findAfpEntities();
        session.setAttribute("AFP", ListAfp);
        List<Empresa> ListEmpresa = emp.findEmpresaEntities();
        session.setAttribute("Empresa", ListEmpresa);
        List<Cargo> ListCargo = car.findCargoEntities();
        session.setAttribute("Cargo", ListCargo);
        List<Personas> ListPersona = per.findPersonasEntities();
        session.setAttribute("Persona", ListPersona);
        List<CitasPersona> listcitas = citaJPA.findCitasPersonaEntities();
        session.setAttribute("citas", listcitas);
        List<Usuario> ListUsuario = ujc.findUsuarioEntities();
        session.setAttribute("Usuario", ListUsuario);
        String admin = "Administrador";
        session.setAttribute("rol", admin);
        List<Caso> litscaso = casoJPA.findCasoEntities();
        session.setAttribute("caso", litscaso);
        List<Personaasistente> listAsistente = asistenteJPA.findPersonaasistenteEntities();
        session.setAttribute("asistente", listAsistente);
        List<Formacion> listFormacion = formacionJPA.findFormacionEntities();
        session.setAttribute("formacion", listFormacion);
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
