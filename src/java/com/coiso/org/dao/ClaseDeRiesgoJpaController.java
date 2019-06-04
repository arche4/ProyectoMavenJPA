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
import com.coiso.org.entidades.Cargo;
import com.coiso.org.entidades.ClaseDeRiesgo;
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
public class ClaseDeRiesgoJpaController implements Serializable {

    public ClaseDeRiesgoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ClaseDeRiesgo claseDeRiesgo) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (claseDeRiesgo.getCargoCollection() == null) {
            claseDeRiesgo.setCargoCollection(new ArrayList<Cargo>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<Cargo> attachedCargoCollection = new ArrayList<Cargo>();
            for (Cargo cargoCollectionCargoToAttach : claseDeRiesgo.getCargoCollection()) {
                cargoCollectionCargoToAttach = em.getReference(cargoCollectionCargoToAttach.getClass(), cargoCollectionCargoToAttach.getCodigocargo());
                attachedCargoCollection.add(cargoCollectionCargoToAttach);
            }
            claseDeRiesgo.setCargoCollection(attachedCargoCollection);
            em.persist(claseDeRiesgo);
            for (Cargo cargoCollectionCargo : claseDeRiesgo.getCargoCollection()) {
                ClaseDeRiesgo oldFkRiesgoCargoOfCargoCollectionCargo = cargoCollectionCargo.getFkRiesgoCargo();
                cargoCollectionCargo.setFkRiesgoCargo(claseDeRiesgo);
                cargoCollectionCargo = em.merge(cargoCollectionCargo);
                if (oldFkRiesgoCargoOfCargoCollectionCargo != null) {
                    oldFkRiesgoCargoOfCargoCollectionCargo.getCargoCollection().remove(cargoCollectionCargo);
                    oldFkRiesgoCargoOfCargoCollectionCargo = em.merge(oldFkRiesgoCargoOfCargoCollectionCargo);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findClaseDeRiesgo(claseDeRiesgo.getIdRiesgo()) != null) {
                throw new PreexistingEntityException("ClaseDeRiesgo " + claseDeRiesgo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ClaseDeRiesgo claseDeRiesgo) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ClaseDeRiesgo persistentClaseDeRiesgo = em.find(ClaseDeRiesgo.class, claseDeRiesgo.getIdRiesgo());
            Collection<Cargo> cargoCollectionOld = persistentClaseDeRiesgo.getCargoCollection();
            Collection<Cargo> cargoCollectionNew = claseDeRiesgo.getCargoCollection();
            List<String> illegalOrphanMessages = null;
            for (Cargo cargoCollectionOldCargo : cargoCollectionOld) {
                if (!cargoCollectionNew.contains(cargoCollectionOldCargo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cargo " + cargoCollectionOldCargo + " since its fkRiesgoCargo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Cargo> attachedCargoCollectionNew = new ArrayList<Cargo>();
            for (Cargo cargoCollectionNewCargoToAttach : cargoCollectionNew) {
                cargoCollectionNewCargoToAttach = em.getReference(cargoCollectionNewCargoToAttach.getClass(), cargoCollectionNewCargoToAttach.getCodigocargo());
                attachedCargoCollectionNew.add(cargoCollectionNewCargoToAttach);
            }
            cargoCollectionNew = attachedCargoCollectionNew;
            claseDeRiesgo.setCargoCollection(cargoCollectionNew);
            claseDeRiesgo = em.merge(claseDeRiesgo);
            for (Cargo cargoCollectionNewCargo : cargoCollectionNew) {
                if (!cargoCollectionOld.contains(cargoCollectionNewCargo)) {
                    ClaseDeRiesgo oldFkRiesgoCargoOfCargoCollectionNewCargo = cargoCollectionNewCargo.getFkRiesgoCargo();
                    cargoCollectionNewCargo.setFkRiesgoCargo(claseDeRiesgo);
                    cargoCollectionNewCargo = em.merge(cargoCollectionNewCargo);
                    if (oldFkRiesgoCargoOfCargoCollectionNewCargo != null && !oldFkRiesgoCargoOfCargoCollectionNewCargo.equals(claseDeRiesgo)) {
                        oldFkRiesgoCargoOfCargoCollectionNewCargo.getCargoCollection().remove(cargoCollectionNewCargo);
                        oldFkRiesgoCargoOfCargoCollectionNewCargo = em.merge(oldFkRiesgoCargoOfCargoCollectionNewCargo);
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
                String id = claseDeRiesgo.getIdRiesgo();
                if (findClaseDeRiesgo(id) == null) {
                    throw new NonexistentEntityException("The claseDeRiesgo with id " + id + " no longer exists.");
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
            ClaseDeRiesgo claseDeRiesgo;
            try {
                claseDeRiesgo = em.getReference(ClaseDeRiesgo.class, id);
                claseDeRiesgo.getIdRiesgo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The claseDeRiesgo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Cargo> cargoCollectionOrphanCheck = claseDeRiesgo.getCargoCollection();
            for (Cargo cargoCollectionOrphanCheckCargo : cargoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ClaseDeRiesgo (" + claseDeRiesgo + ") cannot be destroyed since the Cargo " + cargoCollectionOrphanCheckCargo + " in its cargoCollection field has a non-nullable fkRiesgoCargo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(claseDeRiesgo);
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

    public List<ClaseDeRiesgo> findClaseDeRiesgoEntities() {
        return findClaseDeRiesgoEntities(true, -1, -1);
    }

    public List<ClaseDeRiesgo> findClaseDeRiesgoEntities(int maxResults, int firstResult) {
        return findClaseDeRiesgoEntities(false, maxResults, firstResult);
    }

    private List<ClaseDeRiesgo> findClaseDeRiesgoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ClaseDeRiesgo.class));
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

    public ClaseDeRiesgo findClaseDeRiesgo(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ClaseDeRiesgo.class, id);
        } finally {
            em.close();
        }
    }

    public int getClaseDeRiesgoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ClaseDeRiesgo> rt = cq.from(ClaseDeRiesgo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
