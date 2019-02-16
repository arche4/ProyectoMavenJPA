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
import com.coiso.org.entidades.Arl;
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
public class ArlJpaController implements Serializable {

    public ArlJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Arl arl) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (arl.getPersonasCollection() == null) {
            arl.setPersonasCollection(new ArrayList<Personas>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<Personas> attachedPersonasCollection = new ArrayList<Personas>();
            for (Personas personasCollectionPersonasToAttach : arl.getPersonasCollection()) {
                personasCollectionPersonasToAttach = em.getReference(personasCollectionPersonasToAttach.getClass(), personasCollectionPersonasToAttach.getCedula());
                attachedPersonasCollection.add(personasCollectionPersonasToAttach);
            }
            arl.setPersonasCollection(attachedPersonasCollection);
            em.persist(arl);
            for (Personas personasCollectionPersonas : arl.getPersonasCollection()) {
                Arl oldCodigoarlFkOfPersonasCollectionPersonas = personasCollectionPersonas.getCodigoarlFk();
                personasCollectionPersonas.setCodigoarlFk(arl);
                personasCollectionPersonas = em.merge(personasCollectionPersonas);
                if (oldCodigoarlFkOfPersonasCollectionPersonas != null) {
                    oldCodigoarlFkOfPersonasCollectionPersonas.getPersonasCollection().remove(personasCollectionPersonas);
                    oldCodigoarlFkOfPersonasCollectionPersonas = em.merge(oldCodigoarlFkOfPersonasCollectionPersonas);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findArl(arl.getCodigoarl()) != null) {
                throw new PreexistingEntityException("Arl " + arl + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Arl arl) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Arl persistentArl = em.find(Arl.class, arl.getCodigoarl());
            Collection<Personas> personasCollectionOld = persistentArl.getPersonasCollection();
            Collection<Personas> personasCollectionNew = arl.getPersonasCollection();
            List<String> illegalOrphanMessages = null;
            for (Personas personasCollectionOldPersonas : personasCollectionOld) {
                if (!personasCollectionNew.contains(personasCollectionOldPersonas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Personas " + personasCollectionOldPersonas + " since its codigoarlFk field is not nullable.");
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
            arl.setPersonasCollection(personasCollectionNew);
            arl = em.merge(arl);
            for (Personas personasCollectionNewPersonas : personasCollectionNew) {
                if (!personasCollectionOld.contains(personasCollectionNewPersonas)) {
                    Arl oldCodigoarlFkOfPersonasCollectionNewPersonas = personasCollectionNewPersonas.getCodigoarlFk();
                    personasCollectionNewPersonas.setCodigoarlFk(arl);
                    personasCollectionNewPersonas = em.merge(personasCollectionNewPersonas);
                    if (oldCodigoarlFkOfPersonasCollectionNewPersonas != null && !oldCodigoarlFkOfPersonasCollectionNewPersonas.equals(arl)) {
                        oldCodigoarlFkOfPersonasCollectionNewPersonas.getPersonasCollection().remove(personasCollectionNewPersonas);
                        oldCodigoarlFkOfPersonasCollectionNewPersonas = em.merge(oldCodigoarlFkOfPersonasCollectionNewPersonas);
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
                String id = arl.getCodigoarl();
                if (findArl(id) == null) {
                    throw new NonexistentEntityException("The arl with id " + id + " no longer exists.");
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
            Arl arl;
            try {
                arl = em.getReference(Arl.class, id);
                arl.getCodigoarl();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The arl with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Personas> personasCollectionOrphanCheck = arl.getPersonasCollection();
            for (Personas personasCollectionOrphanCheckPersonas : personasCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Arl (" + arl + ") cannot be destroyed since the Personas " + personasCollectionOrphanCheckPersonas + " in its personasCollection field has a non-nullable codigoarlFk field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(arl);
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

    public List<Arl> findArlEntities() {
        return findArlEntities(true, -1, -1);
    }

    public List<Arl> findArlEntities(int maxResults, int firstResult) {
        return findArlEntities(false, maxResults, firstResult);
    }

    private List<Arl> findArlEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Arl.class));
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

    public Arl findArl(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Arl.class, id);
        } finally {
            em.close();
        }
    }

    public int getArlCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Arl> rt = cq.from(Arl.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
