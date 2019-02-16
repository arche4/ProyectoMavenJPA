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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "cargo")
@NamedQueries({
    @NamedQuery(name = "Cargo.findAll", query = "SELECT c FROM Cargo c")
    , @NamedQuery(name = "Cargo.findByCodigocargo", query = "SELECT c FROM Cargo c WHERE c.codigocargo = :codigocargo")
    , @NamedQuery(name = "Cargo.findByNombre", query = "SELECT c FROM Cargo c WHERE c.nombre = :nombre")
    , @NamedQuery(name = "Cargo.findByA\u00f1osExperiencia", query = "SELECT c FROM Cargo c WHERE c.a\u00f1osExperiencia = :a\u00f1osExperiencia")})
public class Cargo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "codigocargo")
    private String codigocargo;
    @Size(max = 100)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 10)
    @Column(name = "a\u00f1os_experiencia")
    private String añosExperiencia;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codigocargoFk")
    private Collection<Personas> personasCollection;
    @JoinColumn(name = "fk_riesgo_cargo", referencedColumnName = "id_riesgo")
    @ManyToOne(optional = false)
    private ClaseDeRiesgo fkRiesgoCargo;

    public Cargo() {
    }

    public Cargo(String codigocargo) {
        this.codigocargo = codigocargo;
    }

    public String getCodigocargo() {
        return codigocargo;
    }

    public void setCodigocargo(String codigocargo) {
        this.codigocargo = codigocargo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAñosExperiencia() {
        return añosExperiencia;
    }

    public void setAñosExperiencia(String añosExperiencia) {
        this.añosExperiencia = añosExperiencia;
    }

    public Collection<Personas> getPersonasCollection() {
        return personasCollection;
    }

    public void setPersonasCollection(Collection<Personas> personasCollection) {
        this.personasCollection = personasCollection;
    }

    public ClaseDeRiesgo getFkRiesgoCargo() {
        return fkRiesgoCargo;
    }

    public void setFkRiesgoCargo(ClaseDeRiesgo fkRiesgoCargo) {
        this.fkRiesgoCargo = fkRiesgoCargo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigocargo != null ? codigocargo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cargo)) {
            return false;
        }
        Cargo other = (Cargo) object;
        if ((this.codigocargo == null && other.codigocargo != null) || (this.codigocargo != null && !this.codigocargo.equals(other.codigocargo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.coiso.org.entidades.Cargo[ codigocargo=" + codigocargo + " ]";
    }
    
}
