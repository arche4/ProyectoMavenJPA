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
@Table(name = "personas")
@NamedQueries({
    @NamedQuery(name = "Personas.findAll", query = "SELECT p FROM Personas p")
    , @NamedQuery(name = "Personas.findByCedula", query = "SELECT p FROM Personas p WHERE p.cedula = :cedula")
    , @NamedQuery(name = "Personas.findByNombre", query = "SELECT p FROM Personas p WHERE p.nombre = :nombre")
    , @NamedQuery(name = "Personas.findByApellidouno", query = "SELECT p FROM Personas p WHERE p.apellidouno = :apellidouno")
    , @NamedQuery(name = "Personas.findByApellidodos", query = "SELECT p FROM Personas p WHERE p.apellidodos = :apellidodos")
    , @NamedQuery(name = "Personas.findByGenero", query = "SELECT p FROM Personas p WHERE p.genero = :genero")
    , @NamedQuery(name = "Personas.findByFechanacimiento", query = "SELECT p FROM Personas p WHERE p.fechanacimiento = :fechanacimiento")
    , @NamedQuery(name = "Personas.findByTelefonofijo", query = "SELECT p FROM Personas p WHERE p.telefonofijo = :telefonofijo")
    , @NamedQuery(name = "Personas.findByCelular", query = "SELECT p FROM Personas p WHERE p.celular = :celular")
    , @NamedQuery(name = "Personas.findByFechaClinica", query = "SELECT p FROM Personas p WHERE p.fechaClinica = :fechaClinica")})
public class Personas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "cedula")
    private String cedula;
    @Size(max = 50)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 50)
    @Column(name = "apellidouno")
    private String apellidouno;
    @Size(max = 50)
    @Column(name = "apellidodos")
    private String apellidodos;
    @Size(max = 5)
    @Column(name = "genero")
    private String genero;
    @Size(max = 15)
    @Column(name = "fechanacimiento")
    private String fechanacimiento;
    @Size(max = 20)
    @Column(name = "telefonofijo")
    private String telefonofijo;
    @Size(max = 20)
    @Column(name = "celular")
    private String celular;
    @Size(max = 15)
    @Column(name = "fecha_clinica")
    private String fechaClinica;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "personasCedula")
    private Collection<CasoPersona> casoPersonaCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cedulaFk")
    private Collection<Personaempresa> personaempresaCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "personasCedula")
    private Collection<UsuarioPersona> usuarioPersonaCollection;
    @JoinColumn(name = "codigoafp_fk", referencedColumnName = "codigoafp")
    @ManyToOne(optional = false)
    private Afp codigoafpFk;
    @JoinColumn(name = "codigoarl_fk", referencedColumnName = "codigoarl")
    @ManyToOne(optional = false)
    private Arl codigoarlFk;
    @JoinColumn(name = "codigocargo_fk", referencedColumnName = "codigocargo")
    @ManyToOne(optional = false)
    private Cargo codigocargoFk;
    @JoinColumn(name = "codigoeps_fk", referencedColumnName = "codigoeps")
    @ManyToOne(optional = false)
    private Eps codigoepsFk;
    @JoinColumn(name = "codigoprofesion_fk", referencedColumnName = "codigoprofesion")
    @ManyToOne(optional = false)
    private Profesion codigoprofesionFk;
    @OneToMany(mappedBy = "recomendadoFk")
    private Collection<Personas> personasCollection;
    @JoinColumn(name = "recomendado_fk", referencedColumnName = "cedula")
    @ManyToOne
    private Personas recomendadoFk;

    public Personas() {
    }

    public Personas(String cedula) {
        this.cedula = cedula;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidouno() {
        return apellidouno;
    }

    public void setApellidouno(String apellidouno) {
        this.apellidouno = apellidouno;
    }

    public String getApellidodos() {
        return apellidodos;
    }

    public void setApellidodos(String apellidodos) {
        this.apellidodos = apellidodos;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getFechanacimiento() {
        return fechanacimiento;
    }

    public void setFechanacimiento(String fechanacimiento) {
        this.fechanacimiento = fechanacimiento;
    }

    public String getTelefonofijo() {
        return telefonofijo;
    }

    public void setTelefonofijo(String telefonofijo) {
        this.telefonofijo = telefonofijo;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getFechaClinica() {
        return fechaClinica;
    }

    public void setFechaClinica(String fechaClinica) {
        this.fechaClinica = fechaClinica;
    }

    public Collection<CasoPersona> getCasoPersonaCollection() {
        return casoPersonaCollection;
    }

    public void setCasoPersonaCollection(Collection<CasoPersona> casoPersonaCollection) {
        this.casoPersonaCollection = casoPersonaCollection;
    }

    public Collection<Personaempresa> getPersonaempresaCollection() {
        return personaempresaCollection;
    }

    public void setPersonaempresaCollection(Collection<Personaempresa> personaempresaCollection) {
        this.personaempresaCollection = personaempresaCollection;
    }

    public Collection<UsuarioPersona> getUsuarioPersonaCollection() {
        return usuarioPersonaCollection;
    }

    public void setUsuarioPersonaCollection(Collection<UsuarioPersona> usuarioPersonaCollection) {
        this.usuarioPersonaCollection = usuarioPersonaCollection;
    }

    public Afp getCodigoafpFk() {
        return codigoafpFk;
    }

    public void setCodigoafpFk(Afp codigoafpFk) {
        this.codigoafpFk = codigoafpFk;
    }

    public Arl getCodigoarlFk() {
        return codigoarlFk;
    }

    public void setCodigoarlFk(Arl codigoarlFk) {
        this.codigoarlFk = codigoarlFk;
    }

    public Cargo getCodigocargoFk() {
        return codigocargoFk;
    }

    public void setCodigocargoFk(Cargo codigocargoFk) {
        this.codigocargoFk = codigocargoFk;
    }

    public Eps getCodigoepsFk() {
        return codigoepsFk;
    }

    public void setCodigoepsFk(Eps codigoepsFk) {
        this.codigoepsFk = codigoepsFk;
    }

    public Profesion getCodigoprofesionFk() {
        return codigoprofesionFk;
    }

    public void setCodigoprofesionFk(Profesion codigoprofesionFk) {
        this.codigoprofesionFk = codigoprofesionFk;
    }

    public Collection<Personas> getPersonasCollection() {
        return personasCollection;
    }

    public void setPersonasCollection(Collection<Personas> personasCollection) {
        this.personasCollection = personasCollection;
    }

    public Personas getRecomendadoFk() {
        return recomendadoFk;
    }

    public void setRecomendadoFk(Personas recomendadoFk) {
        this.recomendadoFk = recomendadoFk;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cedula != null ? cedula.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Personas)) {
            return false;
        }
        Personas other = (Personas) object;
        if ((this.cedula == null && other.cedula != null) || (this.cedula != null && !this.cedula.equals(other.cedula))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.coiso.org.entidades.Personas[ cedula=" + cedula + " ]";
    }
    
}
