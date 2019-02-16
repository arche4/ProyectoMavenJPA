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
    , @NamedQuery(name = "Personas.findByCodigoprofesionFk", query = "SELECT p FROM Personas p WHERE p.codigoprofesionFk = :codigoprofesionFk")
    , @NamedQuery(name = "Personas.findByFechaClinica", query = "SELECT p FROM Personas p WHERE p.fechaClinica = :fechaClinica")
    , @NamedQuery(name = "Personas.findByCorreo", query = "SELECT p FROM Personas p WHERE p.correo = :correo")
    , @NamedQuery(name = "Personas.findByDireccion", query = "SELECT p FROM Personas p WHERE p.direccion = :direccion")
    , @NamedQuery(name = "Personas.findByAnosExperiencia", query = "SELECT p FROM Personas p WHERE p.anosExperiencia = :anosExperiencia")
    , @NamedQuery(name = "Personas.findByArea", query = "SELECT p FROM Personas p WHERE p.area = :area")
    , @NamedQuery(name = "Personas.findByRecomendado", query = "SELECT p FROM Personas p WHERE p.recomendado = :recomendado")})
public class Personas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "cedula")
    private String cedula;
    @Size(max = 500)
    @Column(name = "nombre")
    private String nombre;
    @Size(max = 500)
    @Column(name = "apellidouno")
    private String apellidouno;
    @Size(max = 500)
    @Column(name = "apellidodos")
    private String apellidodos;
    @Size(max = 5)
    @Column(name = "genero")
    private String genero;
    @Size(max = 500)
    @Column(name = "fechanacimiento")
    private String fechanacimiento;
    @Size(max = 500)
    @Column(name = "telefonofijo")
    private String telefonofijo;
    @Size(max = 500)
    @Column(name = "celular")
    private String celular;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "codigoprofesion_fk")
    private String codigoprofesionFk;
    @Size(max = 500)
    @Column(name = "fecha_clinica")
    private String fechaClinica;
    @Size(max = 500)
    @Column(name = "correo")
    private String correo;
    @Size(max = 500)
    @Column(name = "direccion")
    private String direccion;
    @Size(max = 500)
    @Column(name = "anos_experiencia")
    private String anosExperiencia;
    @Size(max = 500)
    @Column(name = "area")
    private String area;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "recomendado")
    private String recomendado;
    @JoinColumn(name = "codigoafp_fk", referencedColumnName = "codigoafp")
    @ManyToOne(optional = false)
    private Afp codigoafpFk;
    @JoinColumn(name = "codigoarl_fk", referencedColumnName = "codigoarl")
    @ManyToOne(optional = false)
    private Arl codigoarlFk;
    @JoinColumn(name = "cargo_codigocargo", referencedColumnName = "codigocargo")
    @ManyToOne(optional = false)
    private Cargo cargoCodigocargo;
    @JoinColumn(name = "codigoeps_fk", referencedColumnName = "codigoeps")
    @ManyToOne(optional = false)
    private Eps codigoepsFk;
  

    public Personas() {
    }

    public Personas(String cedula) {
        this.cedula = cedula;
    }

    public Personas(String cedula, String codigoprofesionFk, String recomendado) {
        this.cedula = cedula;
        this.codigoprofesionFk = codigoprofesionFk;
        this.recomendado = recomendado;
    }
    
      public Personas(String cedula, String nombre, String apellidoUno, String apellidoDos, String genero, String fechaCumpleaños,
            String telefono, String celular, Eps eps, Arl arl, Afp  afp, String  profesion,  String fechaClinica,  Cargo cargo,
            String correo, String direccion,   String añosExperiencia, String area, String recomendado){
  
       this.cedula = cedula;
       this.nombre = nombre;
       this.apellidouno = apellidoUno;
       this.apellidodos = apellidoDos;
       this.genero = genero;
       this.fechanacimiento = fechaCumpleaños;
       this.direccion = direccion;
       this.correo = correo;
       this.telefonofijo = telefono;
       this.celular = celular;
       this.codigoepsFk = eps;
       this.codigoarlFk = arl;
       this.codigoafpFk = afp;
       this.codigoprofesionFk = profesion;
       this.cargoCodigocargo = cargo;
       this.fechaClinica = fechaClinica;
       this.recomendado = recomendado;
       this.area = area;
       this.anosExperiencia = añosExperiencia;
          
    
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

    public String getCodigoprofesionFk() {
        return codigoprofesionFk;
    }

    public void setCodigoprofesionFk(String codigoprofesionFk) {
        this.codigoprofesionFk = codigoprofesionFk;
    }

    public String getFechaClinica() {
        return fechaClinica;
    }

    public void setFechaClinica(String fechaClinica) {
        this.fechaClinica = fechaClinica;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getAnosExperiencia() {
        return anosExperiencia;
    }

    public void setAnosExperiencia(String anosExperiencia) {
        this.anosExperiencia = anosExperiencia;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getRecomendado() {
        return recomendado;
    }

    public void setRecomendado(String recomendado) {
        this.recomendado = recomendado;
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

    public Cargo getCargoCodigocargo() {
        return cargoCodigocargo;
    }

    public void setCargoCodigocargo(Cargo cargoCodigocargo) {
        this.cargoCodigocargo = cargoCodigocargo;
    }

    public Eps getCodigoepsFk() {
        return codigoepsFk;
    }

    public void setCodigoepsFk(Eps codigoepsFk) {
        this.codigoepsFk = codigoepsFk;
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
        return "com.co.poli.appcoiso.model.Personas[ cedula=" + cedula + " ]";
    }
    
}
