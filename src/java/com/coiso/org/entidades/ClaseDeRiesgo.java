/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coiso.org.entidades;

import java.io.Serializable;
import java.util.Collection;
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
@Table(name = "clase_de_riesgo")
@NamedQueries({
    @NamedQuery(name = "ClaseDeRiesgo.findAll", query = "SELECT c FROM ClaseDeRiesgo c")
    , @NamedQuery(name = "ClaseDeRiesgo.findByIdRiesgo", query = "SELECT c FROM ClaseDeRiesgo c WHERE c.idRiesgo = :idRiesgo")
    , @NamedQuery(name = "ClaseDeRiesgo.findByNombreRiesgo", query = "SELECT c FROM ClaseDeRiesgo c WHERE c.nombreRiesgo = :nombreRiesgo")
    , @NamedQuery(name = "ClaseDeRiesgo.findByDescripcionRiesgo", query = "SELECT c FROM ClaseDeRiesgo c WHERE c.descripcionRiesgo = :descripcionRiesgo")})
public class ClaseDeRiesgo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "id_riesgo")
    private String idRiesgo;
    @Size(max = 20)
    @Column(name = "nombre_riesgo")
    private String nombreRiesgo;
    @Size(max = 20)
    @Column(name = "descripcion_riesgo")
    private String descripcionRiesgo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fkRiesgoCargo")
    private Collection<Cargo> cargoCollection;

    public ClaseDeRiesgo() {
    }

    public ClaseDeRiesgo(String idRiesgo) {
        this.idRiesgo = idRiesgo;
    }

    public String getIdRiesgo() {
        return idRiesgo;
    }

    public void setIdRiesgo(String idRiesgo) {
        this.idRiesgo = idRiesgo;
    }

    public String getNombreRiesgo() {
        return nombreRiesgo;
    }

    public void setNombreRiesgo(String nombreRiesgo) {
        this.nombreRiesgo = nombreRiesgo;
    }

    public String getDescripcionRiesgo() {
        return descripcionRiesgo;
    }

    public void setDescripcionRiesgo(String descripcionRiesgo) {
        this.descripcionRiesgo = descripcionRiesgo;
    }

    public Collection<Cargo> getCargoCollection() {
        return cargoCollection;
    }

    public void setCargoCollection(Collection<Cargo> cargoCollection) {
        this.cargoCollection = cargoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRiesgo != null ? idRiesgo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ClaseDeRiesgo)) {
            return false;
        }
        ClaseDeRiesgo other = (ClaseDeRiesgo) object;
        if ((this.idRiesgo == null && other.idRiesgo != null) || (this.idRiesgo != null && !this.idRiesgo.equals(other.idRiesgo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.coiso.org.entidades.ClaseDeRiesgo[ idRiesgo=" + idRiesgo + " ]";
    }
    
}
