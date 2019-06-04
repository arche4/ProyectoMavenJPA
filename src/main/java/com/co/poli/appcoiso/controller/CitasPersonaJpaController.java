/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.poli.appcoiso.controller;

import com.co.poli.appcoiso.controller.exceptions.NonexistentEntityException;
import com.co.poli.appcoiso.controller.exceptions.PreexistingEntityException;
import com.co.poli.appcoiso.model.CitasPersona;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.co.poli.appcoiso.model.Personas;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Manuela
 */
public class CitasPersonaJpaController implements Serializable {

    public CitasPersonaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CitasPersona citasPersona) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Personas personasCedula = citasPersona.getPersonasCedula();
            if (personasCedula != null) {
                personasCedula = em.getReference(personasCedula.getClass(), personasCedula.getCedula());
                citasPersona.setPersonasCedula(personasCedula);
            }
            em.persist(citasPersona);
          
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCitasPersona(citasPersona.getIdCitas()) != null) {
                throw new PreexistingEntityException("CitasPersona " + citasPersona + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CitasPersona citasPersona) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CitasPersona persistentCitasPersona = em.find(CitasPersona.class, citasPersona.getIdCitas());
            Personas personasCedulaOld = persistentCitasPersona.getPersonasCedula();
            Personas personasCedulaNew = citasPersona.getPersonasCedula();
            if (personasCedulaNew != null) {
                personasCedulaNew = em.getReference(personasCedulaNew.getClass(), personasCedulaNew.getCedula());
                citasPersona.setPersonasCedula(personasCedulaNew);
            }
            citasPersona = em.merge(citasPersona);
         
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = citasPersona.getIdCitas();
                if (findCitasPersona(id) == null) {
                    throw new NonexistentEntityException("The citasPersona with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CitasPersona citasPersona;
            try {
                citasPersona = em.getReference(CitasPersona.class, id);
                citasPersona.getIdCitas();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The citasPersona with id " + id + " no longer exists.", enfe);
            }
            Personas personasCedula = citasPersona.getPersonasCedula();
            
            em.remove(citasPersona);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CitasPersona> findCitasPersonaEntities() {
        return findCitasPersonaEntities(true, -1, -1);
    }

    public List<CitasPersona> findCitasPersonaEntities(int maxResults, int firstResult) {
        return findCitasPersonaEntities(false, maxResults, firstResult);
    }

    private List<CitasPersona> findCitasPersonaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CitasPersona.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public CitasPersona findCitasPersona(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CitasPersona.class, id);
        } finally {
            em.close();
        }
    }

    public int getCitasPersonaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CitasPersona> rt = cq.from(CitasPersona.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
