/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.poli.appcoiso.controller;

import com.co.poli.appcoiso.controller.exceptions.NonexistentEntityException;
import com.co.poli.appcoiso.controller.exceptions.PreexistingEntityException;
import com.co.poli.appcoiso.model.Formacion;
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
public class FormacionJpaController implements Serializable {

    public FormacionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Formacion formacion) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(formacion);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findFormacion(formacion.getIdFormacion()) != null) {
                throw new PreexistingEntityException("Formacion " + formacion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Formacion formacion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            formacion = em.merge(formacion);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = formacion.getIdFormacion();
                if (findFormacion(id) == null) {
                    throw new NonexistentEntityException("The formacion with id " + id + " no longer exists.");
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
            Formacion formacion;
            try {
                formacion = em.getReference(Formacion.class, id);
                formacion.getIdFormacion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The formacion with id " + id + " no longer exists.", enfe);
            }
            em.remove(formacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Formacion> findFormacionEntities() {
        return findFormacionEntities(true, -1, -1);
    }

    public List<Formacion> findFormacionEntities(int maxResults, int firstResult) {
        return findFormacionEntities(false, maxResults, firstResult);
    }

    private List<Formacion> findFormacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Formacion.class));
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

    public Formacion findFormacion(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Formacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getFormacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Formacion> rt = cq.from(Formacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
