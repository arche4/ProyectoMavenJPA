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
import com.co.poli.appcoiso.controller.CasoPersonaJpaController;
import com.co.poli.appcoiso.controller.CitasPersonaJpaController;
import com.co.poli.appcoiso.controller.EmpresaJpaController;
import com.co.poli.appcoiso.controller.EpsJpaController;
import com.co.poli.appcoiso.controller.PersonaempresaJpaController;
import com.co.poli.appcoiso.controller.PersonasJpaController;
import com.co.poli.appcoiso.model.Afp;
import com.co.poli.appcoiso.model.Arl;
import com.co.poli.appcoiso.model.Cargo;
import com.co.poli.appcoiso.model.Caso;
import com.co.poli.appcoiso.model.CasoPersona;
import com.co.poli.appcoiso.model.CitasPersona;
import com.co.poli.appcoiso.model.Empresa;
import com.co.poli.appcoiso.model.Eps;
import com.co.poli.appcoiso.model.Personaempresa;
import com.co.poli.appcoiso.model.Personas;
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
@WebServlet(name = "PersonaServlet", urlPatterns = {"/PersonaServlet"})
public class PersonaServlet extends HttpServlet {

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
        PersonasJpaController jpaperson = new PersonasJpaController(JPAFactory.getFACTORY());
        CargoJpaController cargoJpaController = new CargoJpaController(JPAFactory.getFACTORY());
        EmpresaJpaController empJpa = new EmpresaJpaController(JPAFactory.getFACTORY());
        CasoJpaController casoJPA = new CasoJpaController(JPAFactory.getFACTORY());
        PersonaempresaJpaController personEmpresaJPA = new PersonaempresaJpaController(JPAFactory.getFACTORY());
        CasoPersonaJpaController casoPersonaJPA = new CasoPersonaJpaController(JPAFactory.getFACTORY());
        String mensaje = "";
        CitasPersonaJpaController citaJPA = new CitasPersonaJpaController(JPAFactory.getFACTORY());

        String accion = request.getParameter("accion");
        String cedula = request.getParameter("cedula");
        String nombre = request.getParameter("nombre");
        String apellidoUno = request.getParameter("primerApellido");
        String apellidoDos = request.getParameter("segundoApellido");
        String genero = request.getParameter("genero");
        String fechaCumpleaños = request.getParameter("cumpleaños");
        String direccion = request.getParameter("direccion");
        String correo = request.getParameter("correo");
        String telefono = request.getParameter("telefono");
        String celular = request.getParameter("celular");
        String codigoEps = request.getParameter("eps");
        String codigoArl = request.getParameter("arl");
        String codigoAFP = request.getParameter("afp");
        String profesion = request.getParameter("profesion");
        String codigoEmpresa = request.getParameter("empresa");
        String codigoCargo = request.getParameter("cargo");
        String fechaClinica = request.getParameter("FechaClinica");
        String añosExperiencia = request.getParameter("anosExperiencia");
        String area = request.getParameter("area");
        String idcaso = request.getParameter("idCaso");
        String descripcionCaso = request.getParameter("descripcionCaso");
        String fechaAfectacion = request.getParameter("InicioAfectacion");
        String origenDictamen = request.getParameter("origenDictamen");
        String pcl = request.getParameter("pcl");
        String parteAfectada = request.getParameter("parteAfectada");
        String tiempoIncapacidad = request.getParameter("tiempoIncapacidad");
        String observacioon = request.getParameter("observaciones");
        String recomendado = request.getParameter("recomendado");
        String fechaCita = request.getParameter("cita");

        String ver = request.getParameter("ver");
        String asginar = request.getParameter("asginar");
        String verCita = request.getParameter("verCita");
        String editarCita = request.getParameter("editarCita");

        Personas persona;
        CitasPersona cita;
        Eps eps = new Eps(codigoEps);
        Arl arl = new Arl(codigoArl);
        Afp afp = new Afp(codigoAFP);
        Cargo cargo = new Cargo(codigoCargo);
        List<Personas> listPersonas;

        if (accion != null && !accion.equals("")) {
            switch (accion) {
                case "crear":
                    //validar si empresa existe
                    Empresa emp = empJpa.validarEmpresa(codigoEmpresa);
                    if (emp == null) {
                        Empresa em = new Empresa(codigoEmpresa, "", "", "", "");
                        mensaje = empJpa.crear(em);
                    }
                    //validar cargo
                    cargo = cargoJpaController.validarCargo(codigoCargo);
                    if (cargo == null) {
                        cargo = new Cargo(codigoCargo, "", "", emp);
                        mensaje = cargoJpaController.crear(cargo);
                    }
                    //Crear persona
                    persona = new Personas(cedula, nombre, apellidoUno, apellidoDos, genero, fechaCumpleaños, telefono,
                            celular, eps, arl, afp, profesion, fechaClinica, cargo, correo, direccion, añosExperiencia, area, recomendado);
                    try {
                        mensaje = jpaperson.crear(persona);
                    } catch (Exception e) {
                    }
                    Personas person = new Personas(cedula);
                    //crear persona empresa
                    Personaempresa personEmpresa = new Personaempresa(cedula, emp, person);
                    mensaje = personEmpresaJPA.crear(personEmpresa);

                    //Crear caso
                    Caso caso = new Caso(idcaso, descripcionCaso, fechaAfectacion, origenDictamen, pcl, parteAfectada, tiempoIncapacidad, observacioon);
                    mensaje = casoJPA.crearCaso(caso);

                    //Crear Caso persona 
                    Caso c = new Caso(idcaso);
                    CasoPersona casoPerson = new CasoPersona(cedula, caso, person);
                    mensaje = casoPersonaJPA.crear(casoPerson);

                    listPersonas = jpaperson.findPersonasEntities();
                    session.setAttribute("personas", listPersonas);
                    rd = request.getRequestDispatcher("/view/listadoPersonas.jsp");
                    break;
                case "listar":

                    listPersonas = jpaperson.findPersonasEntities();
                    session.setAttribute("personas", listPersonas);
                    rd = request.getRequestDispatcher("/view/listadoPersonas.jsp");
                    break;

                case "editar":
                    persona = new Personas(cedula, nombre, apellidoUno, apellidoDos, genero, fechaCumpleaños, telefono,
                            celular, eps, arl, afp, profesion, fechaClinica, cargo, correo, direccion, añosExperiencia, area, recomendado);

                    try {
                        jpaperson.edit(persona);
                    } catch (Exception ex) {
                        Logger.getLogger(PersonaServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    Caso casoEdit = new Caso(idcaso, descripcionCaso, fechaAfectacion, origenDictamen, pcl, parteAfectada, tiempoIncapacidad, observacioon);

                    try {
                        casoJPA.edit(casoEdit);
                    } catch (Exception ex) {
                        Logger.getLogger(PersonaServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    List<Personas> ListPersona = jpaperson.findPersonasEntities();
                    session.setAttribute("Persona", ListPersona);

                    rd = request.getRequestDispatcher("/view/listadoPersonas.jsp");
                    break;
                case "guardarCita":
                    persona = new Personas(cedula, "", "", "", "", "", "", "", eps, arl, afp, "",
                            "", cargo, "", "", "", "", "");
                    cita = new CitasPersona(cedula, fechaCita, observacioon, persona);
                     {
                        try {
                            citaJPA.create(cita);
                        } catch (Exception ex) {
                            Logger.getLogger(PersonaServlet.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    List<CitasPersona> listcitas = citaJPA.findCitasPersonaEntities();
                    session.setAttribute("citas", listcitas);
                    rd = request.getRequestDispatcher("/view/listadoCitas.jsp");

                    break;
                case "volver":
                    rd = request.getRequestDispatcher("/view/listadoPersonas.jsp");
                    break;
                case "verCita":
                    rd = request.getRequestDispatcher("/view/listadoPersonas.jsp");
                    break;

            }
        } else if (ver != null && !ver.equals("")) {
            session.setAttribute("cedula", ver);
            List<Personaempresa> personEmpresaList = personEmpresaJPA.findPersonaempresaEntities();
            session.setAttribute("empresa", personEmpresaList);
            CasoPersona casoPersonaList = casoPersonaJPA.buscarCasoPersona(ver);
            if (casoPersonaList != null) {
                String casoid = casoPersonaList.getCasoIdCaso().getIdCaso().toString();
                Caso casoList = casoJPA.buscarCaso(casoid);
                if (casoList != null) {
                    String caso;
                    caso = casoList.getIdCaso();
                    session.setAttribute("casoid", caso);
                    List<Caso> casoT = casoJPA.findCasoEntities();
                    session.setAttribute("caso", casoT);
                }
            }
            rd = request.getRequestDispatcher("/view/verPersona.jsp");
        }
        if (asginar != null && !asginar.equals("")) {
            session.setAttribute("cedula", asginar);
            rd = request.getRequestDispatcher("/view/asignarCitas.jsp");

        }

        if (verCita != null && !verCita.equals("")) {
            session.setAttribute("codigoCita", verCita);
            rd = request.getRequestDispatcher("/view/verCita.jsp");

        }
        if (editarCita != null && !editarCita.equals("")) {
            persona = new Personas(cedula, "", "", "", "", "", "", "", eps, arl, afp, "",
                    "", cargo, "", "", "", "", "");
            cita = new CitasPersona(cedula, fechaCita, observacioon, persona);
            try {
                citaJPA.edit(cita);
            } catch (Exception ex) {
                Logger.getLogger(PersonaServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            List<CitasPersona> listcitas = citaJPA.findCitasPersonaEntities();
            session.setAttribute("citas", listcitas);
            rd = request.getRequestDispatcher("/view/listadoCitas.jsp");

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
