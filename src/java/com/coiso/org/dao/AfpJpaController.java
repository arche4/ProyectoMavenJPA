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
import com.coiso.org.entidades.Afp;
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
public class AfpJpaController implements Serializable {

    public AfpJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Afp afp) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (afp.getPersonasCollection() == null) {
            afp.setPersonasCollection(new ArrayList<Personas>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<Personas> attachedPersonasCollection = new ArrayList<Personas>();
            for (Personas personasCollectionPersonasToAttach : afp.getPersonasCollection()) {
                personasCollectionPersonasToAttach = em.getReference(personasCollectionPersonasToAttach.getClass(), personasCollectionPersonasToAttach.getCedula());
                attachedPersonasCollection.add(personasCollectionPersonasToAttach);
            }
            afp.setPersonasCollection(attachedPersonasCollection);
            em.persist(afp);
            for (Personas personasCollectionPersonas : afp.getPersonasCollection()) {
                Afp oldCodigoafpFkOfPersonasCollectionPersonas = personasCollectionPersonas.getCodigoafpFk();
                personasCollectionPersonas.setCodigoafpFk(afp);
                personasCollectionPersonas = em.merge(personasCollectionPersonas);
                if (oldCodigoafpFkOfPersonasCollectionPersonas != null) {
                    oldCodigoafpFkOfPersonasCollectionPersonas.getPersonasCollection().remove(personasCollectionPersonas);
                    oldCodigoafpFkOfPersonasCollectionPersonas = em.merge(oldCodigoafpFkOfPersonasCollectionPersonas);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findAfp(afp.getCodigoafp()) != null) {
                throw new PreexistingEntityException("Afp " + afp + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Afp afp) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Afp persistentAfp = em.find(Afp.class, afp.getCodigoafp());
            Collection<Personas> personasCollectionOld = persistentAfp.getPersonasCollection();
            Collection<Personas> personasCollectionNew = afp.getPersonasCollection();
            List<String> illegalOrphanMessages = null;
            for (Personas personasCollectionOldPersonas : personasCollectionOld) {
                if (!personasCollectionNew.contains(personasCollectionOldPersonas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Personas " + personasCollectionOldPersonas + " since its codigoafpFk field is not nullable.");
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
            afp.setPersonasCollection(personasCollectionNew);
            afp = em.merge(afp);
            for (Personas personasCollectionNewPersonas : personasCollectionNew) {
                if (!personasCollectionOld.contains(personasCollectionNewPersonas)) {
                    Afp oldCodigoafpFkOfPersonasCollectionNewPersonas = personasCollectionNewPersonas.getCodigoafpFk();
                    personasCollectionNewPersonas.setCodigoafpFk(afp);
                    personasCollectionNewPersonas = em.merge(personasCollectionNewPersonas);
                    if (oldCodigoafpFkOfPersonasCollectionNewPersonas != null && !oldCodigoafpFkOfPersonasCollectionNewPersonas.equals(afp)) {
                        oldCodigoafpFkOfPersonasCollectionNewPersonas.getPersonasCollection().remove(personasCollectionNewPersonas);
                        oldCodigoafpFkOfPersonasCollectionNewPersonas = em.merge(oldCodigoafpFkOfPersonasCollectionNewPersonas);
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
                String id = afp.getCodigoafp();
                if (findAfp(id) == null) {
                    throw new NonexistentEntityException("The afp with id " + id + " no longer exists.");
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
            Afp afp;
            try {
                afp = em.getReference(Afp.class, id);
                afp.getCodigoafp();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The afp with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Personas> personasCollectionOrphanCheck = afp.getPersonasCollection();
            for (Personas personasCollectionOrphanCheckPersonas : personasCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Afp (" + afp + ") cannot be destroyed since the Personas " + personasCollectionOrphanCheckPersonas + " in its personasCollection field has a non-nullable codigoafpFk field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(afp);
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

    public List<Afp> findAfpEntities() {
        return findAfpEntities(true, -1, -1);
    }

    public List<Afp> findAfpEntities(int maxResults, int firstResult) {
        return findAfpEntities(false, maxResults, firstResult);
    }

    private List<Afp> findAfpEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Afp.class));
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

    public Afp findAfp(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Afp.class, id);
        } finally {
            em.close();
        }
    }

    public int getAfpCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Afp> rt = cq.from(Afp.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
