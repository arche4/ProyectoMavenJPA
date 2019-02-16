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
@Table(name = "caso")
@NamedQueries({
    @NamedQuery(name = "Caso.findAll", query = "SELECT c FROM Caso c")
    , @NamedQuery(name = "Caso.findByIdCaso", query = "SELECT c FROM Caso c WHERE c.idCaso = :idCaso")
    , @NamedQuery(name = "Caso.findByDescripcionCaso", query = "SELECT c FROM Caso c WHERE c.descripcionCaso = :descripcionCaso")
    , @NamedQuery(name = "Caso.findByFechaInicioAfectacion", query = "SELECT c FROM Caso c WHERE c.fechaInicioAfectacion = :fechaInicioAfectacion")
    , @NamedQuery(name = "Caso.findByOrigenDictamen", query = "SELECT c FROM Caso c WHERE c.origenDictamen = :origenDictamen")
    , @NamedQuery(name = "Caso.findByPcl", query = "SELECT c FROM Caso c WHERE c.pcl = :pcl")
    , @NamedQuery(name = "Caso.findByParteAfectada", query = "SELECT c FROM Caso c WHERE c.parteAfectada = :parteAfectada")
    , @NamedQuery(name = "Caso.findByDiagnosticoComfirmado", query = "SELECT c FROM Caso c WHERE c.diagnosticoComfirmado = :diagnosticoComfirmado")
    , @NamedQuery(name = "Caso.findByPresuntoDiagnostico", query = "SELECT c FROM Caso c WHERE c.presuntoDiagnostico = :presuntoDiagnostico")
    , @NamedQuery(name = "Caso.findByTiempoIncapacidad", query = "SELECT c FROM Caso c WHERE c.tiempoIncapacidad = :tiempoIncapacidad")
    , @NamedQuery(name = "Caso.findByActuacionAdmistrativa", query = "SELECT c FROM Caso c WHERE c.actuacionAdmistrativa = :actuacionAdmistrativa")
    , @NamedQuery(name = "Caso.findByObservacion", query = "SELECT c FROM Caso c WHERE c.observacion = :observacion")
    , @NamedQuery(name = "Caso.findByEstado", query = "SELECT c FROM Caso c WHERE c.estado = :estado")})
public class Caso implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "id_caso")
    private String idCaso;
    @Size(max = 255)
    @Column(name = "descripcion_caso")
    private String descripcionCaso;
    @Size(max = 15)
    @Column(name = "fecha_inicio_afectacion")
    private String fechaInicioAfectacion;
    @Size(max = 20)
    @Column(name = "origen_dictamen")
    private String origenDictamen;
    @Size(max = 50)
    @Column(name = "pcl")
    private String pcl;
    @Size(max = 20)
    @Column(name = "parte_afectada")
    private String parteAfectada;
    @Size(max = 20)
    @Column(name = "diagnostico_comfirmado")
    private String diagnosticoComfirmado;
    @Size(max = 50)
    @Column(name = "presunto_diagnostico")
    private String presuntoDiagnostico;
    @Size(max = 10)
    @Column(name = "tiempo_incapacidad")
    private String tiempoIncapacidad;
    @Size(max = 20)
    @Column(name = "actuacion_admistrativa")
    private String actuacionAdmistrativa;
    @Size(max = 100)
    @Column(name = "observacion")
    private String observacion;
    @Size(max = 10)
    @Column(name = "estado")
    private String estado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "casoIdCaso")
    private Collection<CasoPersona> casoPersonaCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "casoIdCaso")
    private Collection<CodigoDiagnostico> codigoDiagnosticoCollection;
    @JoinColumn(name = "fk_responsable_caso", referencedColumnName = "id_usuario")
    @ManyToOne
    private Usuario fkResponsableCaso;

    public Caso() {
    }

    public Caso(String idCaso) {
        this.idCaso = idCaso;
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

    public String getDiagnosticoComfirmado() {
        return diagnosticoComfirmado;
    }

    public void setDiagnosticoComfirmado(String diagnosticoComfirmado) {
        this.diagnosticoComfirmado = diagnosticoComfirmado;
    }

    public String getPresuntoDiagnostico() {
        return presuntoDiagnostico;
    }

    public void setPresuntoDiagnostico(String presuntoDiagnostico) {
        this.presuntoDiagnostico = presuntoDiagnostico;
    }

    public String getTiempoIncapacidad() {
        return tiempoIncapacidad;
    }

    public void setTiempoIncapacidad(String tiempoIncapacidad) {
        this.tiempoIncapacidad = tiempoIncapacidad;
    }

    public String getActuacionAdmistrativa() {
        return actuacionAdmistrativa;
    }

    public void setActuacionAdmistrativa(String actuacionAdmistrativa) {
        this.actuacionAdmistrativa = actuacionAdmistrativa;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Collection<CasoPersona> getCasoPersonaCollection() {
        return casoPersonaCollection;
    }

    public void setCasoPersonaCollection(Collection<CasoPersona> casoPersonaCollection) {
        this.casoPersonaCollection = casoPersonaCollection;
    }

    public Collection<CodigoDiagnostico> getCodigoDiagnosticoCollection() {
        return codigoDiagnosticoCollection;
    }

    public void setCodigoDiagnosticoCollection(Collection<CodigoDiagnostico> codigoDiagnosticoCollection) {
        this.codigoDiagnosticoCollection = codigoDiagnosticoCollection;
    }

    public Usuario getFkResponsableCaso() {
        return fkResponsableCaso;
    }

    public void setFkResponsableCaso(Usuario fkResponsableCaso) {
        this.fkResponsableCaso = fkResponsableCaso;
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
        return "com.coiso.org.entidades.Caso[ idCaso=" + idCaso + " ]";
    }
    
}
