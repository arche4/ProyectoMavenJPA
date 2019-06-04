package com.co.poli.appcoiso.model;

import com.co.poli.appcoiso.model.Cargo;
import com.co.poli.appcoiso.model.Personaempresa;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-06-03T19:59:05")
@StaticMetamodel(Empresa.class)
public class Empresa_ { 

    public static volatile SingularAttribute<Empresa, String> nit;
    public static volatile ListAttribute<Empresa, Cargo> cargoList;
    public static volatile ListAttribute<Empresa, Personaempresa> personaempresaList;
    public static volatile SingularAttribute<Empresa, String> nombre;
    public static volatile SingularAttribute<Empresa, String> sector;
    public static volatile SingularAttribute<Empresa, String> actividadEconomica;
    public static volatile SingularAttribute<Empresa, String> anosAtiguedad;

}