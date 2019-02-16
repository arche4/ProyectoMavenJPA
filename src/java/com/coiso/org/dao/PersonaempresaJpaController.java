/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coiso.org.dao;

import com.coiso.org.dao.exceptions.NonexistentEntityException;
import com.coiso.org.dao.exceptions.PreexistingEntityException;
import com.coiso.org.dao.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.coiso.org.entidades.Empresa;
import com.coiso.org.entidades.Personaempresa;
import com.coiso.org.entidades.Personas;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Manuela
 */
public class PersonaempresaJpaController implements Serializable {

    public PersonaempresaJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Personaempresa personaempresa) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Empresa nitempresaFk = personaempresa.getNitempresaFk();
            if (nitempresaFk != null) {
                nitempresaFk = em.getReference(nitempresaFk.getClass(), nitempresaFk.getNit());
                personaempresa.setNitempresaFk(nitempresaFk);
            }
            Personas cedulaFk = personaempresa.getCedulaFk();
            if (cedulaFk != null) {
                cedulaFk = em.getReference(cedulaFk.getClass(), cedulaFk.getCedula());
                personaempresa.setCedulaFk(cedulaFk);
            }
            em.persist(personaempresa);
            if (nitempresaFk != null) {
                nitempresaFk.getPersonaempresaCollection().add(personaempresa);
                nitempresaFk = em.merge(nitempresaFk);
            }
            if (cedulaFk != null) {
                cedulaFk.getPersonaempresaCollection().add(personaempresa);
                cedulaFk = em.merge(cedulaFk);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findPersonaempresa(personaempresa.getId()) != null) {
                throw new PreexistingEntityException("Personaempresa " + personaempresa + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Personaempresa personaempresa) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Personaempresa persistentPersonaempresa = em.find(Personaempresa.class, personaempresa.getId());
            Empresa nitempresaFkOld = persistentPersonaempresa.getNitempresaFk();
            Empresa nitempresaFkNew = personaempresa.getNitempresaFk();
            Personas cedulaFkOld = persistentPersonaempresa.getCedulaFk();
            Personas cedulaFkNew = personaempresa.getCedulaFk();
            if (nitempresaFkNew != null) {
                nitempresaFkNew = em.getReference(nitempresaFkNew.getClass(), nitempresaFkNew.getNit());
                personaempresa.setNitempresaFk(nitempresaFkNew);
            }
            if (cedulaFkNew != null) {
                cedulaFkNew = em.getReference(cedulaFkNew.getClass(), cedulaFkNew.getCedula());
                personaempresa.setCedulaFk(cedulaFkNew);
            }
            personaempresa = em.merge(personaempresa);
            if (nitempresaFkOld != null && !nitempresaFkOld.equals(nitempresaFkNew)) {
                nitempresaFkOld.getPersonaempresaCollection().remove(personaempresa);
                nitempresaFkOld = em.merge(nitempresaFkOld);
            }
            if (nitempresaFkNew != null && !nitempresaFkNew.equals(nitempresaFkOld)) {
                nitempresaFkNew.getPersonaempresaCollection().add(personaempresa);
                nitempresaFkNew = em.merge(nitempresaFkNew);
            }
            if (cedulaFkOld != null && !cedulaFkOld.equals(cedulaFkNew)) {
                cedulaFkOld.getPersonaempresaCollection().remove(personaempresa);
                cedulaFkOld = em.merge(cedulaFkOld);
            }
            if (cedulaFkNew != null && !cedulaFkNew.equals(cedulaFkOld)) {
                cedulaFkNew.getPersonaempresaCollection().add(personaempresa);
                cedulaFkNew = em.merge(cedulaFkNew);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = personaempresa.getId();
                if (findPersonaempresa(id) == null) {
                    throw new NonexistentEntityException("The personaempresa with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Personaempresa personaempresa;
            try {
                personaempresa = em.getReference(Personaempresa.class, id);
                personaempresa.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The personaempresa with id " + id + " no longer exists.", enfe);
            }
            Empresa nitempresaFk = personaempresa.getNitempresaFk();
            if (nitempresaFk != null) {
                nitempresaFk.getPersonaempresaCollection().remove(personaempresa);
                nitempresaFk = em.merge(nitempresaFk);
            }
            Personas cedulaFk = personaempresa.getCedulaFk();
            if (cedulaFk != null) {
                cedulaFk.getPersonaempresaCollection().remove(personaempresa);
                cedulaFk = em.merge(cedulaFk);
            }
            em.remove(personaempresa);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Personaempresa> findPersonaempresaEntities() {
        return findPersonaempresaEntities(true, -1, -1);
    }

    public List<Personaempresa> findPersonaempresaEntities(int maxResults, int firstResult) {
        return findPersonaempresaEntities(false, maxResults, firstResult);
    }

    private List<Personaempresa> findPersonaempresaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Personaempresa.class));
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

    public Personaempresa findPersonaempresa(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Personaempresa.class, id);
        } finally {
            em.close();
        }
    }

    public int getPersonaempresaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Personaempresa> rt = cq.from(Personaempresa.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
