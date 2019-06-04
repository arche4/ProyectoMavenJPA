package com.co.poli.appcoiso.model;

import com.co.poli.appcoiso.model.Afp;
import com.co.poli.appcoiso.model.Arl;
import com.co.poli.appcoiso.model.Cargo;
import com.co.poli.appcoiso.model.Eps;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-06-03T19:59:05")
@StaticMetamodel(Personas.class)
public class Personas_ { 

    public static volatile SingularAttribute<Personas, String> apellidodos;
    public static volatile SingularAttribute<Personas, String> area;
    public static volatile SingularAttribute<Personas, Cargo> cargoCodigocargo;
    public static volatile SingularAttribute<Personas, Afp> codigoafpFk;
    public static volatile SingularAttribute<Personas, String> cedula;
    public static volatile SingularAttribute<Personas, String> direccion;
    public static volatile SingularAttribute<Personas, String> fechaClinica;
    public static volatile SingularAttribute<Personas, String> nombre;
    public static volatile SingularAttribute<Personas, String> apellidouno;
    public static volatile SingularAttribute<Personas, String> fechanacimiento;
    public static volatile SingularAttribute<Personas, String> genero;
    public static volatile SingularAttribute<Personas, String> correo;
    public static volatile SingularAttribute<Personas, String> recomendado;
    public static volatile SingularAttribute<Personas, String> celular;
    public static volatile SingularAttribute<Personas, String> anosExperiencia;
    public static volatile SingularAttribute<Personas, Eps> codigoepsFk;
    public static volatile SingularAttribute<Personas, String> telefonofijo;
    public static volatile SingularAttribute<Personas, String> codigoprofesionFk;
    public static volatile SingularAttribute<Personas, Arl> codigoarlFk;

}