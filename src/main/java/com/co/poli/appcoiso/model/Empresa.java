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
@Table(name = "empresa")
@NamedQueries({
    @NamedQuery(name = "Empresa.findAll", query = "SELECT e FROM Empresa e")
    , @NamedQuery(name = "Empresa.findByNit", query = "SELECT e FROM Empresa e WHERE e.nit = :nit")
    , @NamedQuery(name = "Empresa.findByNombre", query = "SELECT e FROM Empresa e WHERE e.nombre = :nombre")
    , @NamedQuery(name = "Empresa.findBySector", query = "SELECT e FROM Empresa e WHERE e.sector = :sector")
    , @NamedQuery(name = "Empresa.findByActividadEconomica", query = "SELECT e FROM Empresa e WHERE e.actividadEconomica = :actividadEconomica")
    , @NamedQuery(name = "Empresa.findByAnosAtiguedad", query = "SELECT e FROM Empresa e WHERE e.anosAtiguedad = :anosAtiguedad")})
public class Empresa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "nit")
    private String nit;
    @Size(max = 100)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 100)
    @Column(name = "sector")
    private String sector;
    @Size(max = 50)
    @Column(name = "actividad_economica")
    private String actividadEconomica;
    @Size(max = 10)
    @Column(name = "anos_atiguedad")
    private String anosAtiguedad;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "nitempresaFk")
    private List<Personaempresa> personaempresaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "empresaNit")
    private List<Cargo> cargoList;

    public Empresa() {
    }

    public Empresa(String nit) {
        this.nit = nit;
    }
    
    
    public Empresa(String nit, String nombre, String sector, String actividadEconomica, String anosAtiguedad) {
        this.nit = nit;
        this.nombre = nombre;
        this.sector = sector;
        this.actividadEconomica = actividadEconomica;
        this.anosAtiguedad = anosAtiguedad;
    }


    
    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getActividadEconomica() {
        return actividadEconomica;
    }

    public void setActividadEconomica(String actividadEconomica) {
        this.actividadEconomica = actividadEconomica;
    }

    public String getAnosAtiguedad() {
        return anosAtiguedad;
    }

    public void setAnosAtiguedad(String anosAtiguedad) {
        this.anosAtiguedad = anosAtiguedad;
    }

    public List<Personaempresa> getPersonaempresaList() {
        return personaempresaList;
    }

    public void setPersonaempresaList(List<Personaempresa> personaempresaList) {
        this.personaempresaList = personaempresaList;
    }

    public List<Cargo> getCargoList() {
        return cargoList;
    }

    public void setCargoList(List<Cargo> cargoList) {
        this.cargoList = cargoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nit != null ? nit.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Empresa)) {
            return false;
        }
        Empresa other = (Empresa) object;
        if ((this.nit == null && other.nit != null) || (this.nit != null && !this.nit.equals(other.nit))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.co.poli.appcoiso.model.Empresa[ nit=" + nit + " ]";
    }
    
}
