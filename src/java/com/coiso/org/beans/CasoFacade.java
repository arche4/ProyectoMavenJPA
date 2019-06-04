/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coiso.org.beans;

import com.coiso.org.entidades.Caso;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Manuela
 */
@Stateless
public class CasoFacade extends AbstractFacade<Caso> {

    @PersistenceContext(unitName = "AppCoisoPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CasoFacade() {
        super(Caso.class);
    }
    
}
