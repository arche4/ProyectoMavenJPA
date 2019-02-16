/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.poli.appcoiso.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Manuela
 */
@Entity
@Table(name = "eps")
@NamedQueries({
    @NamedQuery(name = "Eps.findAll", query = "SELECT e FROM Eps e")
    , @NamedQuery(name = "Eps.findByCodigoeps", query = "SELECT e FROM Eps e WHERE e.codigoeps = :codigoeps")
    , @NamedQuery(name = "Eps.findByNombre", query = "SELECT e FROM Eps e WHERE e.nombre = :nombre")})
public class Eps implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "codigoeps")
    private String codigoeps;
    @Size(max = 100)
    @Column(name = "nombre")
    private String nombre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codigoepsFk")
    private List<Personas> personasList;

    public Eps() {
    }

    public Eps(String codigoeps) {
        this.codigoeps = codigoeps;
    }

    public String getCodigoeps() {
        return codigoeps;
    }

    public void setCodigoeps(String codigoeps) {
        this.codigoeps = codigoeps;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Personas> getPersonasList() {
        return personasList;
    }

    public void setPersonasList(List<Personas> personasList) {
        this.personasList = personasList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoeps != null ? codigoeps.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Eps)) {
            return false;
        }
        Eps other = (Eps) object;
        if ((this.codigoeps == null && other.codigoeps != null) || (this.codigoeps != null && !this.codigoeps.equals(other.codigoeps))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.co.poli.appcoiso.model.Eps[ codigoeps=" + codigoeps + " ]";
    }
    
}
