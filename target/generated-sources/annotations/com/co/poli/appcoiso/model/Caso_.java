package com.co.poli.appcoiso.model;

import com.co.poli.appcoiso.model.CasoPersona;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-06-03T19:59:05")
@StaticMetamodel(Caso.class)
public class Caso_ { 

    public static volatile SingularAttribute<Caso, String> descripcionCaso;
    public static volatile SingularAttribute<Caso, String> parteAfectada;
    public static volatile SingularAttribute<Caso, String> origenDictamen;
    public static volatile SingularAttribute<Caso, String> idCaso;
    public static volatile SingularAttribute<Caso, String> pcl;
    public static volatile SingularAttribute<Caso, String> tiempoIncapacidad;
    public static volatile ListAttribute<Caso, CasoPersona> casoPersonaList;
    public static volatile SingularAttribute<Caso, String> fechaInicioAfectacion;
    public static volatile SingularAttribute<Caso, String> observacion;

}