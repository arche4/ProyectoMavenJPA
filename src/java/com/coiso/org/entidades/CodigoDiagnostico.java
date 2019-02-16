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
@Table(name = "codigo_diagnostico")
@NamedQueries({
    @NamedQuery(name = "CodigoDiagnostico.findAll", query = "SELECT c FROM CodigoDiagnostico c")
    , @NamedQuery(name = "CodigoDiagnostico.findByIdDiagnosticoCodigo", query = "SELECT c FROM CodigoDiagnostico c WHERE c.idDiagnosticoCodigo = :idDiagnosticoCodigo")
    , @NamedQuery(name = "CodigoDiagnostico.findByCodigo1", query = "SELECT c FROM CodigoDiagnostico c WHERE c.codigo1 = :codigo1")
    , @NamedQuery(name = "CodigoDiagnostico.findByDiagnostico1", query = "SELECT c FROM CodigoDiagnostico c WHERE c.diagnostico1 = :diagnostico1")
    , @NamedQuery(name = "CodigoDiagnostico.findByCodigo2", query = "SELECT c FROM CodigoDiagnostico c WHERE c.codigo2 = :codigo2")
    , @NamedQuery(name = "CodigoDiagnostico.findByDiagnostico2", query = "SELECT c FROM CodigoDiagnostico c WHERE c.diagnostico2 = :diagnostico2")
    , @NamedQuery(name = "CodigoDiagnostico.findByCodigo3", query = "SELECT c FROM CodigoDiagnostico c WHERE c.codigo3 = :codigo3")
    , @NamedQuery(name = "CodigoDiagnostico.findByDiagnostico3", query = "SELECT c FROM CodigoDiagnostico c WHERE c.diagnostico3 = :diagnostico3")
    , @NamedQuery(name = "CodigoDiagnostico.findByCodigo4", query = "SELECT c FROM CodigoDiagnostico c WHERE c.codigo4 = :codigo4")
    , @NamedQuery(name = "CodigoDiagnostico.findByDiagnostico4", query = "SELECT c FROM CodigoDiagnostico c WHERE c.diagnostico4 = :diagnostico4")
    , @NamedQuery(name = "CodigoDiagnostico.findByCodigo5", query = "SELECT c FROM CodigoDiagnostico c WHERE c.codigo5 = :codigo5")
    , @NamedQuery(name = "CodigoDiagnostico.findByDiagnostico5", query = "SELECT c FROM CodigoDiagnostico c WHERE c.diagnostico5 = :diagnostico5")
    , @NamedQuery(name = "CodigoDiagnostico.findByCodigo6", query = "SELECT c FROM CodigoDiagnostico c WHERE c.codigo6 = :codigo6")
    , @NamedQuery(name = "CodigoDiagnostico.findByDiagnostico6", query = "SELECT c FROM CodigoDiagnostico c WHERE c.diagnostico6 = :diagnostico6")
    , @NamedQuery(name = "CodigoDiagnostico.findByCodigo7", query = "SELECT c FROM CodigoDiagnostico c WHERE c.codigo7 = :codigo7")
    , @NamedQuery(name = "CodigoDiagnostico.findByDiagnostico", query = "SELECT c FROM CodigoDiagnostico c WHERE c.diagnostico = :diagnostico")})
public class CodigoDiagnostico implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "id_diagnostico_codigo")
    private String idDiagnosticoCodigo;
    @Size(max = 10)
    @Column(name = "codigo1")
    private String codigo1;
    @Size(max = 50)
    @Column(name = "diagnostico1")
    private String diagnostico1;
    @Size(max = 10)
    @Column(name = "codigo2")
    private String codigo2;
    @Size(max = 50)
    @Column(name = "diagnostico2")
    private String diagnostico2;
    @Size(max = 10)
    @Column(name = "codigo3")
    private String codigo3;
    @Size(max = 50)
    @Column(name = "diagnostico3")
    private String diagnostico3;
    @Size(max = 10)
    @Column(name = "codigo4")
    private String codigo4;
    @Size(max = 50)
    @Column(name = "diagnostico4")
    private String diagnostico4;
    @Size(max = 10)
    @Column(name = "codigo5")
    private String codigo5;
    @Size(max = 50)
    @Column(name = "diagnostico5")
    private String diagnostico5;
    @Size(max = 10)
    @Column(name = "codigo6")
    private String codigo6;
    @Size(max = 20)
    @Column(name = "diagnostico6")
    private String diagnostico6;
    @Size(max = 10)
    @Column(name = "codigo7")
    private String codigo7;
    @Size(max = 50)
    @Column(name = "diagnostico")
    private String diagnostico;
    @JoinColumn(name = "caso_id_caso", referencedColumnName = "id_caso")
    @ManyToOne(optional = false)
    private Caso casoIdCaso;

    public CodigoDiagnostico() {
    }

    public CodigoDiagnostico(String idDiagnosticoCodigo) {
        this.idDiagnosticoCodigo = idDiagnosticoCodigo;
    }

    public String getIdDiagnosticoCodigo() {
        return idDiagnosticoCodigo;
    }

    public void setIdDiagnosticoCodigo(String idDiagnosticoCodigo) {
        this.idDiagnosticoCodigo = idDiagnosticoCodigo;
    }

    public String getCodigo1() {
        return codigo1;
    }

    public void setCodigo1(String codigo1) {
        this.codigo1 = codigo1;
    }

    public String getDiagnostico1() {
        return diagnostico1;
    }

    public void setDiagnostico1(String diagnostico1) {
        this.diagnostico1 = diagnostico1;
    }

    public String getCodigo2() {
        return codigo2;
    }

    public void setCodigo2(String codigo2) {
        this.codigo2 = codigo2;
    }

    public String getDiagnostico2() {
        return diagnostico2;
    }

    public void setDiagnostico2(String diagnostico2) {
        this.diagnostico2 = diagnostico2;
    }

    public String getCodigo3() {
        return codigo3;
    }

    public void setCodigo3(String codigo3) {
        this.codigo3 = codigo3;
    }

    public String getDiagnostico3() {
        return diagnostico3;
    }

    public void setDiagnostico3(String diagnostico3) {
        this.diagnostico3 = diagnostico3;
    }

    public String getCodigo4() {
        return codigo4;
    }

    public void setCodigo4(String codigo4) {
        this.codigo4 = codigo4;
    }

    public String getDiagnostico4() {
        return diagnostico4;
    }

    public void setDiagnostico4(String diagnostico4) {
        this.diagnostico4 = diagnostico4;
    }

    public String getCodigo5() {
        return codigo5;
    }

    public void setCodigo5(String codigo5) {
        this.codigo5 = codigo5;
    }

    public String getDiagnostico5() {
        return diagnostico5;
    }

    public void setDiagnostico5(String diagnostico5) {
        this.diagnostico5 = diagnostico5;
    }

    public String getCodigo6() {
        return codigo6;
    }

    public void setCodigo6(String codigo6) {
        this.codigo6 = codigo6;
    }

    public String getDiagnostico6() {
        return diagnostico6;
    }

    public void setDiagnostico6(String diagnostico6) {
        this.diagnostico6 = diagnostico6;
    }

    public String getCodigo7() {
        return codigo7;
    }

    public void setCodigo7(String codigo7) {
        this.codigo7 = codigo7;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public Caso getCasoIdCaso() {
        return casoIdCaso;
    }

    public void setCasoIdCaso(Caso casoIdCaso) {
        this.casoIdCaso = casoIdCaso;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDiagnosticoCodigo != null ? idDiagnosticoCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CodigoDiagnostico)) {
            return false;
        }
        CodigoDiagnostico other = (CodigoDiagnostico) object;
        if ((this.idDiagnosticoCodigo == null && other.idDiagnosticoCodigo != null) || (this.idDiagnosticoCodigo != null && !this.idDiagnosticoCodigo.equals(other.idDiagnosticoCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.coiso.org.entidades.CodigoDiagnostico[ idDiagnosticoCodigo=" + idDiagnosticoCodigo + " ]";
    }
    
}
