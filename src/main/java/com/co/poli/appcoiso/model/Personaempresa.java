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
@Table(name = "personaempresa")
@NamedQueries({
    @NamedQuery(name = "Personaempresa.findAll", query = "SELECT p FROM Personaempresa p")
    , @NamedQuery(name = "Personaempresa.findById", query = "SELECT p FROM Personaempresa p WHERE p.id = :id")})
public class Personaempresa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "id")
    private String id;
    @JoinColumn(name = "nitempresa_fk", referencedColumnName = "nit")
    @ManyToOne(optional = false)
    private Empresa nitempresaFk;
    @JoinColumn(name = "cedula_fk", referencedColumnName = "cedula")
    @ManyToOne(optional = false)
    private Personas cedulaFk;

    public Personaempresa() {
    }

    public Personaempresa(String id) {
        this.id = id;
    }
    
    
    public Personaempresa(String id, Empresa nitempresaFk, Personas cedulaFk) {
        this.id = id;
        this.nitempresaFk = nitempresaFk;
        this.cedulaFk = cedulaFk;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Empresa getNitempresaFk() {
        return nitempresaFk;
    }

    public void setNitempresaFk(Empresa nitempresaFk) {
        this.nitempresaFk = nitempresaFk;
    }

    public Personas getCedulaFk() {
        return cedulaFk;
    }

    public void setCedulaFk(Personas cedulaFk) {
        this.cedulaFk = cedulaFk;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Personaempresa)) {
            return false;
        }
        Personaempresa other = (Personaempresa) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.co.poli.appcoiso.model.Personaempresa[ id=" + id + " ]";
    }
    
}
