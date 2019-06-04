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
@Table(name = "caso")
@NamedQueries({
    @NamedQuery(name = "Caso.findAll", query = "SELECT c FROM Caso c")
    , @NamedQuery(name = "Caso.findByIdCaso", query = "SELECT c FROM Caso c WHERE c.idCaso = :idCaso")
    , @NamedQuery(name = "Caso.findByDescripcionCaso", query = "SELECT c FROM Caso c WHERE c.descripcionCaso = :descripcionCaso")
    , @NamedQuery(name = "Caso.findByFechaInicioAfectacion", query = "SELECT c FROM Caso c WHERE c.fechaInicioAfectacion = :fechaInicioAfectacion")
    , @NamedQuery(name = "Caso.findByOrigenDictamen", query = "SELECT c FROM Caso c WHERE c.origenDictamen = :origenDictamen")
    , @NamedQuery(name = "Caso.findByPcl", query = "SELECT c FROM Caso c WHERE c.pcl = :pcl")
    , @NamedQuery(name = "Caso.findByParteAfectada", query = "SELECT c FROM Caso c WHERE c.parteAfectada = :parteAfectada")
    , @NamedQuery(name = "Caso.findByTiempoIncapacidad", query = "SELECT c FROM Caso c WHERE c.tiempoIncapacidad = :tiempoIncapacidad")
    , @NamedQuery(name = "Caso.findByObservacion", query = "SELECT c FROM Caso c WHERE c.observacion = :observacion")})
public class Caso implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "id_caso")
    private String idCaso;
    @Size(max = 1000)
    @Column(name = "descripcion_caso")
    private String descripcionCaso;
    @Size(max = 500)
    @Column(name = "fecha_inicio_afectacion")
    private String fechaInicioAfectacion;
    @Size(max = 1000)
    @Column(name = "origen_dictamen")
    private String origenDictamen;
    @Size(max = 500)
    @Column(name = "pcl")
    private String pcl;
    @Size(max = 500)
    @Column(name = "parte_afectada")
    private String parteAfectada;
    @Size(max = 500)
    @Column(name = "tiempo_incapacidad")
    private String tiempoIncapacidad;
    @Size(max = 1000)
    @Column(name = "observacion")
    private String observacion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "casoIdCaso")
    private List<CasoPersona> casoPersonaList;

    public Caso() {
    }

    public Caso(String idCaso) {
        this.idCaso = idCaso;
    }
    
    public Caso(String idCaso, String descripcionCaso, String fechaInicioAfectacion, String origenDictamen,
     String pcl, String parteAfectada, String tiempoIncapacidad, String observacion  ) {
         
        this.idCaso = idCaso;
        this.descripcionCaso = descripcionCaso;
        this.fechaInicioAfectacion = fechaInicioAfectacion;
        this.origenDictamen = origenDictamen;
        this.pcl = pcl;
        this.parteAfectada = parteAfectada;
        this.tiempoIncapacidad = tiempoIncapacidad;
        this.observacion = observacion;
    }

    public String getIdCaso() {
        return idCaso;
    }

    public void setIdCaso(String idCaso) {
        this.idCaso = idCaso;
    }

    public String getDescripcionCaso() {
        return descripcionCaso;
    }

    public void setDescripcionCaso(String descripcionCaso) {
        this.descripcionCaso = descripcionCaso;
    }

    public String getFechaInicioAfectacion() {
        return fechaInicioAfectacion;
    }

    public void setFechaInicioAfectacion(String fechaInicioAfectacion) {
        this.fechaInicioAfectacion = fechaInicioAfectacion;
    }

    public String getOrigenDictamen() {
        return origenDictamen;
    }

    public void setOrigenDictamen(String origenDictamen) {
        this.origenDictamen = origenDictamen;
    }

    public String getPcl() {
        return pcl;
    }

    public void setPcl(String pcl) {
        this.pcl = pcl;
    }

    public String getParteAfectada() {
        return parteAfectada;
    }

    public void setParteAfectada(String parteAfectada) {
        this.parteAfectada = parteAfectada;
    }

    public String getTiempoIncapacidad() {
        return tiempoIncapacidad;
    }

    public void setTiempoIncapacidad(String tiempoIncapacidad) {
        this.tiempoIncapacidad = tiempoIncapacidad;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public List<CasoPersona> getCasoPersonaList() {
        return casoPersonaList;
    }

    public void setCasoPersonaList(List<CasoPersona> casoPersonaList) {
        this.casoPersonaList = casoPersonaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCaso != null ? idCaso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Caso)) {
            return false;
        }
        Caso other = (Caso) object;
        if ((this.idCaso == null && other.idCaso != null) || (this.idCaso != null && !this.idCaso.equals(other.idCaso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.co.poli.appcoiso.model.Caso[ idCaso=" + idCaso + " ]";
    }
    
}
