/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.poli.appcoiso.controller;

import com.co.poli.appcoiso.controller.exceptions.NonexistentEntityException;
import com.co.poli.appcoiso.controller.exceptions.PreexistingEntityException;
import com.co.poli.appcoiso.model.Personaasistente;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Manuela
 */
public class PersonaasistenteJpaController implements Serializable {

    public PersonaasistenteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Personaasistente personaasistente) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(personaasistente);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPersonaasistente(personaasistente.getCedulaPersona()) != null) {
                throw new PreexistingEntityException("Personaasistente " + personaasistente + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Personaasistente personaasistente) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            personaasistente = em.merge(personaasistente);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = personaasistente.getCedulaPersona();
                if (findPersonaasistente(id) == null) {
                    throw new NonexistentEntityException("The personaasistente with id " + id + " no longer exists.");
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
            Personaasistente personaasistente;
            try {
                personaasistente = em.getReference(Personaasistente.class, id);
                personaasistente.getCedulaPersona();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The personaasistente with id " + id + " no longer exists.", enfe);
            }
            em.remove(personaasistente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Personaasistente> findPersonaasistenteEntities() {
        return findPersonaasistenteEntities(true, -1, -1);
    }

    public List<Personaasistente> findPersonaasistenteEntities(int maxResults, int firstResult) {
        return findPersonaasistenteEntities(false, maxResults, firstResult);
    }

    private List<Personaasistente> findPersonaasistenteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Personaasistente.class));
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

    public Personaasistente findPersonaasistente(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Personaasistente.class, id);
        } finally {
            em.close();
        }
    }

    public int getPersonaasistenteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Personaasistente> rt = cq.from(Personaasistente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
