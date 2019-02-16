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
    , @NamedQuery(name = "Cargo.findByRiesgoCargo", query = "SELECT c FROM Cargo c WHERE c.riesgoCargo = :riesgoCargo")})
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
    @Size(max = 500)
    @Column(name = "riesgo_cargo")
    private String riesgoCargo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cargoCodigocargo")
    private List<Personas> personasList;
    @JoinColumn(name = "empresa_nit", referencedColumnName = "nit")
    @ManyToOne(optional = false)
    private Empresa empresaNit;

    public Cargo() {
    }

    public Cargo(String codigocargo) {
        this.codigocargo = codigocargo;
    }
    
     public Cargo(String codigocargo, String nombre, String riesgoCargo, Empresa empresaNit) {
        this.codigocargo = codigocargo;
        this.nombre = nombre;
        this.riesgoCargo = riesgoCargo;
        this.empresaNit = empresaNit;
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

    public String getRiesgoCargo() {
        return riesgoCargo;
    }

    public void setRiesgoCargo(String riesgoCargo) {
        this.riesgoCargo = riesgoCargo;
    }

    public List<Personas> getPersonasList() {
        return personasList;
    }

    public void setPersonasList(List<Personas> personasList) {
        this.personasList = personasList;
    }

    public Empresa getEmpresaNit() {
        return empresaNit;
    }

    public void setEmpresaNit(Empresa empresaNit) {
        this.empresaNit = empresaNit;
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
        return "com.co.poli.appcoiso.model.Cargo[ codigocargo=" + codigocargo + " ]";
    }
    
}
