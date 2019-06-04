package com.co.poli.appcoiso.model;

import com.co.poli.appcoiso.model.Empresa;
import com.co.poli.appcoiso.model.Personas;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-06-03T19:59:05")
@StaticMetamodel(Cargo.class)
public class Cargo_ { 

    public static volatile SingularAttribute<Cargo, Empresa> empresaNit;
    public static volatile ListAttribute<Cargo, Personas> personasList;
    public static volatile SingularAttribute<Cargo, String> riesgoCargo;
    public static volatile SingularAttribute<Cargo, String> nombre;
    public static volatile SingularAttribute<Cargo, String> codigocargo;

}