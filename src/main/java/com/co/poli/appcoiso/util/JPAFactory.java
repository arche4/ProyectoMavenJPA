/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.poli.appcoiso.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author sala312
 */
public class JPAFactory {
    
    private static final EntityManagerFactory FACTORY;
    private static final String UP = "com.co.poli_AppCoiso_war_1.0-SNAPSHOTPU";
    
    static {        
        FACTORY = Persistence.createEntityManagerFactory(UP);
    }

    public static EntityManagerFactory getFACTORY() {
        return FACTORY;
    }
        
    
}
