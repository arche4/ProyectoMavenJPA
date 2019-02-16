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
import com.coiso.org.entidades.Cargo;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.coiso.org.entidades.ClaseDeRiesgo;
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
public class CargoJpaController implements Serializable {

    public CargoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cargo cargo) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (cargo.getPersonasCollection() == null) {
            cargo.setPersonasCollection(new ArrayList<Personas>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ClaseDeRiesgo fkRiesgoCargo = cargo.getFkRiesgoCargo();
            if (fkRiesgoCargo != null) {
                fkRiesgoCargo = em.getReference(fkRiesgoCargo.getClass(), fkRiesgoCargo.getIdRiesgo());
                cargo.setFkRiesgoCargo(fkRiesgoCargo);
            }
            Collection<Personas> attachedPersonasCollection = new ArrayList<Personas>();
            for (Personas personasCollectionPersonasToAttach : cargo.getPersonasCollection()) {
                personasCollectionPersonasToAttach = em.getReference(personasCollectionPersonasToAttach.getClass(), personasCollectionPersonasToAttach.getCedula());
                attachedPersonasCollection.add(personasCollectionPersonasToAttach);
            }
            cargo.setPersonasCollection(attachedPersonasCollection);
            em.persist(cargo);
            if (fkRiesgoCargo != null) {
                fkRiesgoCargo.getCargoCollection().add(cargo);
                fkRiesgoCargo = em.merge(fkRiesgoCargo);
            }
            for (Personas personasCollectionPersonas : cargo.getPersonasCollection()) {
                Cargo oldCodigocargoFkOfPersonasCollectionPersonas = personasCollectionPersonas.getCodigocargoFk();
                personasCollectionPersonas.setCodigocargoFk(cargo);
                personasCollectionPersonas = em.merge(personasCollectionPersonas);
                if (oldCodigocargoFkOfPersonasCollectionPersonas != null) {
                    oldCodigocargoFkOfPersonasCollectionPersonas.getPersonasCollection().remove(personasCollectionPersonas);
                    oldCodigocargoFkOfPersonasCollectionPersonas = em.merge(oldCodigocargoFkOfPersonasCollectionPersonas);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findCargo(cargo.getCodigocargo()) != null) {
                throw new PreexistingEntityException("Cargo " + cargo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cargo cargo) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Cargo persistentCargo = em.find(Cargo.class, cargo.getCodigocargo());
            ClaseDeRiesgo fkRiesgoCargoOld = persistentCargo.getFkRiesgoCargo();
            ClaseDeRiesgo fkRiesgoCargoNew = cargo.getFkRiesgoCargo();
            Collection<Personas> personasCollectionOld = persistentCargo.getPersonasCollection();
            Collection<Personas> personasCollectionNew = cargo.getPersonasCollection();
            List<String> illegalOrphanMessages = null;
            for (Personas personasCollectionOldPersonas : personasCollectionOld) {
                if (!personasCollectionNew.contains(personasCollectionOldPersonas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Personas " + personasCollectionOldPersonas + " since its codigocargoFk field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (fkRiesgoCargoNew != null) {
                fkRiesgoCargoNew = em.getReference(fkRiesgoCargoNew.getClass(), fkRiesgoCargoNew.getIdRiesgo());
                cargo.setFkRiesgoCargo(fkRiesgoCargoNew);
            }
            Collection<Personas> attachedPersonasCollectionNew = new ArrayList<Personas>();
            for (Personas personasCollectionNewPersonasToAttach : personasCollectionNew) {
                personasCollectionNewPersonasToAttach = em.getReference(personasCollectionNewPersonasToAttach.getClass(), personasCollectionNewPersonasToAttach.getCedula());
                attachedPersonasCollectionNew.add(personasCollectionNewPersonasToAttach);
            }
            personasCollectionNew = attachedPersonasCollectionNew;
            cargo.setPersonasCollection(personasCollectionNew);
            cargo = em.merge(cargo);
            if (fkRiesgoCargoOld != null && !fkRiesgoCargoOld.equals(fkRiesgoCargoNew)) {
                fkRiesgoCargoOld.getCargoCollection().remove(cargo);
                fkRiesgoCargoOld = em.merge(fkRiesgoCargoOld);
            }
            if (fkRiesgoCargoNew != null && !fkRiesgoCargoNew.equals(fkRiesgoCargoOld)) {
                fkRiesgoCargoNew.getCargoCollection().add(cargo);
                fkRiesgoCargoNew = em.merge(fkRiesgoCargoNew);
            }
            for (Personas personasCollectionNewPersonas : personasCollectionNew) {
                if (!personasCollectionOld.contains(personasCollectionNewPersonas)) {
                    Cargo oldCodigocargoFkOfPersonasCollectionNewPersonas = personasCollectionNewPersonas.getCodigocargoFk();
                    personasCollectionNewPersonas.setCodigocargoFk(cargo);
                    personasCollectionNewPersonas = em.merge(personasCollectionNewPersonas);
                    if (oldCodigocargoFkOfPersonasCollectionNewPersonas != null && !oldCodigocargoFkOfPersonasCollectionNewPersonas.equals(cargo)) {
                        oldCodigocargoFkOfPersonasCollectionNewPersonas.getPersonasCollection().remove(personasCollectionNewPersonas);
                        oldCodigocargoFkOfPersonasCollectionNewPersonas = em.merge(oldCodigocargoFkOfPersonasCollectionNewPersonas);
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
                String id = cargo.getCodigocargo();
                if (findCargo(id) == null) {
                    throw new NonexistentEntityException("The cargo with id " + id + " no longer exists.");
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
            Cargo cargo;
            try {
                cargo = em.getReference(Cargo.class, id);
                cargo.getCodigocargo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cargo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Personas> personasCollectionOrphanCheck = cargo.getPersonasCollection();
            for (Personas personasCollectionOrphanCheckPersonas : personasCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cargo (" + cargo + ") cannot be destroyed since the Personas " + personasCollectionOrphanCheckPersonas + " in its personasCollection field has a non-nullable codigocargoFk field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            ClaseDeRiesgo fkRiesgoCargo = cargo.getFkRiesgoCargo();
            if (fkRiesgoCargo != null) {
                fkRiesgoCargo.getCargoCollection().remove(cargo);
                fkRiesgoCargo = em.merge(fkRiesgoCargo);
            }
            em.remove(cargo);
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

    public List<Cargo> findCargoEntities() {
        return findCargoEntities(true, -1, -1);
    }

    public List<Cargo> findCargoEntities(int maxResults, int firstResult) {
        return findCargoEntities(false, maxResults, firstResult);
    }

    private List<Cargo> findCargoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cargo.class));
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

    public Cargo findCargo(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cargo.class, id);
        } finally {
            em.close();
        }
    }

    public int getCargoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cargo> rt = cq.from(Cargo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
