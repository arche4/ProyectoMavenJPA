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
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.coiso.org.entidades.Personas;
import com.coiso.org.entidades.Profesion;
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
public class ProfesionJpaController implements Serializable {

    public ProfesionJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Profesion profesion) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (profesion.getPersonasCollection() == null) {
            profesion.setPersonasCollection(new ArrayList<Personas>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<Personas> attachedPersonasCollection = new ArrayList<Personas>();
            for (Personas personasCollectionPersonasToAttach : profesion.getPersonasCollection()) {
                personasCollectionPersonasToAttach = em.getReference(personasCollectionPersonasToAttach.getClass(), personasCollectionPersonasToAttach.getCedula());
                attachedPersonasCollection.add(personasCollectionPersonasToAttach);
            }
            profesion.setPersonasCollection(attachedPersonasCollection);
            em.persist(profesion);
            for (Personas personasCollectionPersonas : profesion.getPersonasCollection()) {
                Profesion oldCodigoprofesionFkOfPersonasCollectionPersonas = personasCollectionPersonas.getCodigoprofesionFk();
                personasCollectionPersonas.setCodigoprofesionFk(profesion);
                personasCollectionPersonas = em.merge(personasCollectionPersonas);
                if (oldCodigoprofesionFkOfPersonasCollectionPersonas != null) {
                    oldCodigoprofesionFkOfPersonasCollectionPersonas.getPersonasCollection().remove(personasCollectionPersonas);
                    oldCodigoprofesionFkOfPersonasCollectionPersonas = em.merge(oldCodigoprofesionFkOfPersonasCollectionPersonas);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findProfesion(profesion.getCodigoprofesion()) != null) {
                throw new PreexistingEntityException("Profesion " + profesion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Profesion profesion) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Profesion persistentProfesion = em.find(Profesion.class, profesion.getCodigoprofesion());
            Collection<Personas> personasCollectionOld = persistentProfesion.getPersonasCollection();
            Collection<Personas> personasCollectionNew = profesion.getPersonasCollection();
            List<String> illegalOrphanMessages = null;
            for (Personas personasCollectionOldPersonas : personasCollectionOld) {
                if (!personasCollectionNew.contains(personasCollectionOldPersonas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Personas " + personasCollectionOldPersonas + " since its codigoprofesionFk field is not nullable.");
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
            profesion.setPersonasCollection(personasCollectionNew);
            profesion = em.merge(profesion);
            for (Personas personasCollectionNewPersonas : personasCollectionNew) {
                if (!personasCollectionOld.contains(personasCollectionNewPersonas)) {
                    Profesion oldCodigoprofesionFkOfPersonasCollectionNewPersonas = personasCollectionNewPersonas.getCodigoprofesionFk();
                    personasCollectionNewPersonas.setCodigoprofesionFk(profesion);
                    personasCollectionNewPersonas = em.merge(personasCollectionNewPersonas);
                    if (oldCodigoprofesionFkOfPersonasCollectionNewPersonas != null && !oldCodigoprofesionFkOfPersonasCollectionNewPersonas.equals(profesion)) {
                        oldCodigoprofesionFkOfPersonasCollectionNewPersonas.getPersonasCollection().remove(personasCollectionNewPersonas);
                        oldCodigoprofesionFkOfPersonasCollectionNewPersonas = em.merge(oldCodigoprofesionFkOfPersonasCollectionNewPersonas);
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
                String id = profesion.getCodigoprofesion();
                if (findProfesion(id) == null) {
                    throw new NonexistentEntityException("The profesion with id " + id + " no longer exists.");
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
            Profesion profesion;
            try {
                profesion = em.getReference(Profesion.class, id);
                profesion.getCodigoprofesion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The profesion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Personas> personasCollectionOrphanCheck = profesion.getPersonasCollection();
            for (Personas personasCollectionOrphanCheckPersonas : personasCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Profesion (" + profesion + ") cannot be destroyed since the Personas " + personasCollectionOrphanCheckPersonas + " in its personasCollection field has a non-nullable codigoprofesionFk field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(profesion);
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

    public List<Profesion> findProfesionEntities() {
        return findProfesionEntities(true, -1, -1);
    }

    public List<Profesion> findProfesionEntities(int maxResults, int firstResult) {
        return findProfesionEntities(false, maxResults, firstResult);
    }

    private List<Profesion> findProfesionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Profesion.class));
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

    public Profesion findProfesion(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Profesion.class, id);
        } finally {
            em.close();
        }
    }

    public int getProfesionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Profesion> rt = cq.from(Profesion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
