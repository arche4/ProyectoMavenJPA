/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coiso.org.dao;

import com.coiso.org.dao.exceptions.IllegalOrphanException;
import com.coiso.org.dao.exceptions.NonexistentEntityException;
import com.coiso.org.dao.exceptions.PreexistingEntityException;
import com.coiso.org.dao.exceptions.RollbackFailureException;
import com.coiso.org.entidades.Eps;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.coiso.org.entidades.Personas;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Manuela
 */
public class EpsJpaController implements Serializable {

    public EpsJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Eps eps) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (eps.getPersonasCollection() == null) {
            eps.setPersonasCollection(new ArrayList<Personas>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<Personas> attachedPersonasCollection = new ArrayList<Personas>();
            for (Personas personasCollectionPersonasToAttach : eps.getPersonasCollection()) {
                personasCollectionPersonasToAttach = em.getReference(personasCollectionPersonasToAttach.getClass(), personasCollectionPersonasToAttach.getCedula());
                attachedPersonasCollection.add(personasCollectionPersonasToAttach);
            }
            eps.setPersonasCollection(attachedPersonasCollection);
            em.persist(eps);
            for (Personas personasCollectionPersonas : eps.getPersonasCollection()) {
                Eps oldCodigoepsFkOfPersonasCollectionPersonas = personasCollectionPersonas.getCodigoepsFk();
                personasCollectionPersonas.setCodigoepsFk(eps);
                personasCollectionPersonas = em.merge(personasCollectionPersonas);
                if (oldCodigoepsFkOfPersonasCollectionPersonas != null) {
                    oldCodigoepsFkOfPersonasCollectionPersonas.getPersonasCollection().remove(personasCollectionPersonas);
                    oldCodigoepsFkOfPersonasCollectionPersonas = em.merge(oldCodigoepsFkOfPersonasCollectionPersonas);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findEps(eps.getCodigoeps()) != null) {
                throw new PreexistingEntityException("Eps " + eps + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Eps eps) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Eps persistentEps = em.find(Eps.class, eps.getCodigoeps());
            Collection<Personas> personasCollectionOld = persistentEps.getPersonasCollection();
            Collection<Personas> personasCollectionNew = eps.getPersonasCollection();
            List<String> illegalOrphanMessages = null;
            for (Personas personasCollectionOldPersonas : personasCollectionOld) {
                if (!personasCollectionNew.contains(personasCollectionOldPersonas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Personas " + personasCollectionOldPersonas + " since its codigoepsFk field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Personas> attachedPersonasCollectionNew = new ArrayList<Personas>();
            for (Personas personasCollectionNewPersonasToAttach : personasCollectionNew) {
                personasCollectionNewPersonasToAttach = em.getReference(personasCollectionNewPersonasToAttach.getClass(), personasCollectionNewPersonasToAttach.getCedula());
                attachedPersonasCollectionNew.add(personasCollectionNewPersonasToAttach);
            }
            personasCollectionNew = attachedPersonasCollectionNew;
            eps.setPersonasCollection(personasCollectionNew);
            eps = em.merge(eps);
            for (Personas personasCollectionNewPersonas : personasCollectionNew) {
                if (!personasCollectionOld.contains(personasCollectionNewPersonas)) {
                    Eps oldCodigoepsFkOfPersonasCollectionNewPersonas = personasCollectionNewPersonas.getCodigoepsFk();
                    personasCollectionNewPersonas.setCodigoepsFk(eps);
                    personasCollectionNewPersonas = em.merge(personasCollectionNewPersonas);
                    if (oldCodigoepsFkOfPersonasCollectionNewPersonas != null && !oldCodigoepsFkOfPersonasCollectionNewPersonas.equals(eps)) {
                        oldCodigoepsFkOfPersonasCollectionNewPersonas.getPersonasCollection().remove(personasCollectionNewPersonas);
                        oldCodigoepsFkOfPersonasCollectionNewPersonas = em.merge(oldCodigoepsFkOfPersonasCollectionNewPersonas);
                    }
                }
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
                String id = eps.getCodigoeps();
                if (findEps(id) == null) {
                    throw new NonexistentEntityException("The eps with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Eps eps;
            try {
                eps = em.getReference(Eps.class, id);
                eps.getCodigoeps();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The eps with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Personas> personasCollectionOrphanCheck = eps.getPersonasCollection();
            for (Personas personasCollectionOrphanCheckPersonas : personasCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Eps (" + eps + ") cannot be destroyed since the Personas " + personasCollectionOrphanCheckPersonas + " in its personasCollection field has a non-nullable codigoepsFk field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(eps);
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

    public List<Eps> findEpsEntities() {
        return findEpsEntities(true, -1, -1);
    }

    public List<Eps> findEpsEntities(int maxResults, int firstResult) {
        return findEpsEntities(false, maxResults, firstResult);
    }

    private List<Eps> findEpsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Eps.class));
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

    public Eps findEps(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Eps.class, id);
        } finally {
            em.close();
        }
    }

    public int getEpsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Eps> rt = cq.from(Eps.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
