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
@Table(name = "caso_persona")
@NamedQueries({
    @NamedQuery(name = "CasoPersona.findAll", query = "SELECT c FROM CasoPersona c")
    , @NamedQuery(name = "CasoPersona.findByIdCasoPersona", query = "SELECT c FROM CasoPersona c WHERE c.idCasoPersona = :idCasoPersona")})
public class CasoPersona implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "id_caso_persona")
    private String idCasoPersona;
    @JoinColumn(name = "caso_id_caso", referencedColumnName = "id_caso")
    @ManyToOne(optional = false)
    private Caso casoIdCaso;
    @JoinColumn(name = "personas_cedula", referencedColumnName = "cedula")
    @ManyToOne(optional = false)
    private Personas personasCedula;

    public CasoPersona() {
    }

    public CasoPersona(String idCasoPersona) {
        this.idCasoPersona = idCasoPersona;
    }
    
    
    public CasoPersona(String idCasoPersona, Caso casoIdCaso, Personas personasCedula) {
        this.idCasoPersona = idCasoPersona;
        this.casoIdCaso = casoIdCaso;
        this.personasCedula = personasCedula;
    }

    public String getIdCasoPersona() {
        return idCasoPersona;
    }

    public void setIdCasoPersona(String idCasoPersona) {
        this.idCasoPersona = idCasoPersona;
    }

    public Caso getCasoIdCaso() {
        return casoIdCaso;
    }

    public void setCasoIdCaso(Caso casoIdCaso) {
        this.casoIdCaso = casoIdCaso;
    }

    public Personas getPersonasCedula() {
        return personasCedula;
    }

    public void setPersonasCedula(Personas personasCedula) {
        this.personasCedula = personasCedula;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCasoPersona != null ? idCasoPersona.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CasoPersona)) {
            return false;
        }
        CasoPersona other = (CasoPersona) object;
        if ((this.idCasoPersona == null && other.idCasoPersona != null) || (this.idCasoPersona != null && !this.idCasoPersona.equals(other.idCasoPersona))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.co.poli.appcoiso.model.CasoPersona[ idCasoPersona=" + idCasoPersona + " ]";
    }
    
}
