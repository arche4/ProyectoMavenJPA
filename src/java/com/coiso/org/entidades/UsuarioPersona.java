/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coiso.org.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "usuario_persona")
@NamedQueries({
    @NamedQuery(name = "UsuarioPersona.findAll", query = "SELECT u FROM UsuarioPersona u")
    , @NamedQuery(name = "UsuarioPersona.findByIdUsuarioPersona", query = "SELECT u FROM UsuarioPersona u WHERE u.idUsuarioPersona = :idUsuarioPersona")})
public class UsuarioPersona implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "id_usuario_persona")
    private String idUsuarioPersona;
    @JoinColumn(name = "personas_cedula", referencedColumnName = "cedula")
    @ManyToOne(optional = false)
    private Personas personasCedula;
    @JoinColumn(name = "usuario_id_usuario", referencedColumnName = "id_usuario")
    @ManyToOne(optional = false)
    private Usuario usuarioIdUsuario;

    public UsuarioPersona() {
    }

    public UsuarioPersona(String idUsuarioPersona) {
        this.idUsuarioPersona = idUsuarioPersona;
    }

    public String getIdUsuarioPersona() {
        return idUsuarioPersona;
    }

    public void setIdUsuarioPersona(String idUsuarioPersona) {
        this.idUsuarioPersona = idUsuarioPersona;
    }

    public Personas getPersonasCedula() {
        return personasCedula;
    }

    public void setPersonasCedula(Personas personasCedula) {
        this.personasCedula = personasCedula;
    }

    public Usuario getUsuarioIdUsuario() {
        return usuarioIdUsuario;
    }

    public void setUsuarioIdUsuario(Usuario usuarioIdUsuario) {
        this.usuarioIdUsuario = usuarioIdUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUsuarioPersona != null ? idUsuarioPersona.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsuarioPersona)) {
            return false;
        }
        UsuarioPersona other = (UsuarioPersona) object;
        if ((this.idUsuarioPersona == null && other.idUsuarioPersona != null) || (this.idUsuarioPersona != null && !this.idUsuarioPersona.equals(other.idUsuarioPersona))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.coiso.org.entidades.UsuarioPersona[ idUsuarioPersona=" + idUsuarioPersona + " ]";
    }
    
}
