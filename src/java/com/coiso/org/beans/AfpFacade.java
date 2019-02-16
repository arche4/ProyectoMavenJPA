/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coiso.org.beans;

import com.coiso.org.entidades.Afp;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Manuela
 */
@Stateless
public class AfpFacade extends AbstractFacade<Afp> {

    @PersistenceContext(unitName = "AppCoisoPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AfpFacade() {
        super(Afp.class);
    }
    
}
