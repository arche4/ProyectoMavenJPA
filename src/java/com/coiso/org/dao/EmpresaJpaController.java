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
import com.coiso.org.entidades.Empresa;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.coiso.org.entidades.Personaempresa;
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
public class EmpresaJpaController implements Serializable {

    public EmpresaJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Empresa empresa) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (empresa.getPersonaempresaCollection() == null) {
            empresa.setPersonaempresaCollection(new ArrayList<Personaempresa>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<Personaempresa> attachedPersonaempresaCollection = new ArrayList<Personaempresa>();
            for (Personaempresa personaempresaCollectionPersonaempresaToAttach : empresa.getPersonaempresaCollection()) {
                personaempresaCollectionPersonaempresaToAttach = em.getReference(personaempresaCollectionPersonaempresaToAttach.getClass(), personaempresaCollectionPersonaempresaToAttach.getId());
                attachedPersonaempresaCollection.add(personaempresaCollectionPersonaempresaToAttach);
            }
            empresa.setPersonaempresaCollection(attachedPersonaempresaCollection);
            em.persist(empresa);
            for (Personaempresa personaempresaCollectionPersonaempresa : empresa.getPersonaempresaCollection()) {
                Empresa oldNitempresaFkOfPersonaempresaCollectionPersonaempresa = personaempresaCollectionPersonaempresa.getNitempresaFk();
                personaempresaCollectionPersonaempresa.setNitempresaFk(empresa);
                personaempresaCollectionPersonaempresa = em.merge(personaempresaCollectionPersonaempresa);
                if (oldNitempresaFkOfPersonaempresaCollectionPersonaempresa != null) {
                    oldNitempresaFkOfPersonaempresaCollectionPersonaempresa.getPersonaempresaCollection().remove(personaempresaCollectionPersonaempresa);
                    oldNitempresaFkOfPersonaempresaCollectionPersonaempresa = em.merge(oldNitempresaFkOfPersonaempresaCollectionPersonaempresa);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findEmpresa(empresa.getNit()) != null) {
                throw new PreexistingEntityException("Empresa " + empresa + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Empresa empresa) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Empresa persistentEmpresa = em.find(Empresa.class, empresa.getNit());
            Collection<Personaempresa> personaempresaCollectionOld = persistentEmpresa.getPersonaempresaCollection();
            Collection<Personaempresa> personaempresaCollectionNew = empresa.getPersonaempresaCollection();
            List<String> illegalOrphanMessages = null;
            for (Personaempresa personaempresaCollectionOldPersonaempresa : personaempresaCollectionOld) {
                if (!personaempresaCollectionNew.contains(personaempresaCollectionOldPersonaempresa)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Personaempresa " + personaempresaCollectionOldPersonaempresa + " since its nitempresaFk field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Personaempresa> attachedPersonaempresaCollectionNew = new ArrayList<Personaempresa>();
            for (Personaempresa personaempresaCollectionNewPersonaempresaToAttach : personaempresaCollectionNew) {
                personaempresaCollectionNewPersonaempresaToAttach = em.getReference(personaempresaCollectionNewPersonaempresaToAttach.getClass(), personaempresaCollectionNewPersonaempresaToAttach.getId());
                attachedPersonaempresaCollectionNew.add(personaempresaCollectionNewPersonaempresaToAttach);
            }
            personaempresaCollectionNew = attachedPersonaempresaCollectionNew;
            empresa.setPersonaempresaCollection(personaempresaCollectionNew);
            empresa = em.merge(empresa);
            for (Personaempresa personaempresaCollectionNewPersonaempresa : personaempresaCollectionNew) {
                if (!personaempresaCollectionOld.contains(personaempresaCollectionNewPersonaempresa)) {
                    Empresa oldNitempresaFkOfPersonaempresaCollectionNewPersonaempresa = personaempresaCollectionNewPersonaempresa.getNitempresaFk();
                    personaempresaCollectionNewPersonaempresa.setNitempresaFk(empresa);
                    personaempresaCollectionNewPersonaempresa = em.merge(personaempresaCollectionNewPersonaempresa);
                    if (oldNitempresaFkOfPersonaempresaCollectionNewPersonaempresa != null && !oldNitempresaFkOfPersonaempresaCollectionNewPersonaempresa.equals(empresa)) {
                        oldNitempresaFkOfPersonaempresaCollectionNewPersonaempresa.getPersonaempresaCollection().remove(personaempresaCollectionNewPersonaempresa);
                        oldNitempresaFkOfPersonaempresaCollectionNewPersonaempresa = em.merge(oldNitempresaFkOfPersonaempresaCollectionNewPersonaempresa);
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
                String id = empresa.getNit();
                if (findEmpresa(id) == null) {
                    throw new NonexistentEntityException("The empresa with id " + id + " no longer exists.");
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
            Empresa empresa;
            try {
                empresa = em.getReference(Empresa.class, id);
                empresa.getNit();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The empresa with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Personaempresa> personaempresaCollectionOrphanCheck = empresa.getPersonaempresaCollection();
            for (Personaempresa personaempresaCollectionOrphanCheckPersonaempresa : personaempresaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Empresa (" + empresa + ") cannot be destroyed since the Personaempresa " + personaempresaCollectionOrphanCheckPersonaempresa + " in its personaempresaCollection field has a non-nullable nitempresaFk field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(empresa);
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

    public List<Empresa> findEmpresaEntities() {
        return findEmpresaEntities(true, -1, -1);
    }

    public List<Empresa> findEmpresaEntities(int maxResults, int firstResult) {
        return findEmpresaEntities(false, maxResults, firstResult);
    }

    private List<Empresa> findEmpresaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Empresa.class));
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

    public Empresa findEmpresa(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Empresa.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmpresaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Empresa> rt = cq.from(Empresa.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
