/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.poli.appcoiso.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Manuela
 */
@Entity
@Table(name = "personaasistente")
@NamedQueries({
    @NamedQuery(name = "Personaasistente.findAll", query = "SELECT p FROM Personaasistente p")
    , @NamedQuery(name = "Personaasistente.findByCedulaPersona", query = "SELECT p FROM Personaasistente p WHERE p.cedulaPersona = :cedulaPersona")
    , @NamedQuery(name = "Personaasistente.findByNombrePersona", query = "SELECT p FROM Personaasistente p WHERE p.nombrePersona = :nombrePersona")
    , @NamedQuery(name = "Personaasistente.findByApellidoPersona", query = "SELECT p FROM Personaasistente p WHERE p.apellidoPersona = :apellidoPersona")
    , @NamedQuery(name = "Personaasistente.findByCelular", query = "SELECT p FROM Personaasistente p WHERE p.celular = :celular")
    , @NamedQuery(name = "Personaasistente.findByTelefono", query = "SELECT p FROM Personaasistente p WHERE p.telefono = :telefono")
    , @NamedQuery(name = "Personaasistente.findByCorreo", query = "SELECT p FROM Personaasistente p WHERE p.correo = :correo")
    , @NamedQuery(name = "Personaasistente.findByAsistencia", query = "SELECT p FROM Personaasistente p WHERE p.asistencia = :asistencia")})
public class Personaasistente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "cedula_persona")
    private String cedulaPersona;
    @Size(max = 20)
    @Column(name = "nombre_persona")
    private String nombrePersona;
    @Size(max = 20)
    @Column(name = "apellido_persona")
    private String apellidoPersona;
    @Size(max = 20)
    @Column(name = "celular")
    private String celular;
    @Size(max = 15)
    @Column(name = "telefono")
    private String telefono;
    @Size(max = 30)
    @Column(name = "correo")
    private String correo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "asistencia")
    private String asistencia;

    public Personaasistente() {
    }

    public Personaasistente(String cedulaPersona) {
        this.cedulaPersona = cedulaPersona;
    }
    

    public Personaasistente(String cedulaPersona, String asistencia) {
        this.cedulaPersona = cedulaPersona;
        this.asistencia = asistencia;
    }

    public Personaasistente(String cedulaPersona, String nombrePersona, String apellidoPersona, String celular, String telefono, String correo, String asistencia) {
        this.cedulaPersona = cedulaPersona;
        this.nombrePersona = nombrePersona;
        this.apellidoPersona = apellidoPersona;
        this.celular = celular;
        this.telefono = telefono;
        this.correo = correo;
        this.asistencia = asistencia;
    }
    
    

    public String getCedulaPersona() {
        return cedulaPersona;
    }

    public void setCedulaPersona(String cedulaPersona) {
        this.cedulaPersona = cedulaPersona;
    }

    public String getNombrePersona() {
        return nombrePersona;
    }

    public void setNombrePersona(String nombrePersona) {
        this.nombrePersona = nombrePersona;
    }

    public String getApellidoPersona() {
        return apellidoPersona;
    }

    public void setApellidoPersona(String apellidoPersona) {
        this.apellidoPersona = apellidoPersona;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getAsistencia() {
        return asistencia;
    }

    public void setAsistencia(String asistencia) {
        this.asistencia = asistencia;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cedulaPersona != null ? cedulaPersona.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Personaasistente)) {
            return false;
        }
        Personaasistente other = (Personaasistente) object;
        if ((this.cedulaPersona == null && other.cedulaPersona != null) || (this.cedulaPersona != null && !this.cedulaPersona.equals(other.cedulaPersona))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.co.poli.appcoiso.model.Personaasistente[ cedulaPersona=" + cedulaPersona + " ]";
    }
    
}
