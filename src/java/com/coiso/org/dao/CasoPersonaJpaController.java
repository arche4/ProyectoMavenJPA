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
import com.coiso.org.entidades.Caso;
import com.coiso.org.entidades.CasoPersona;
import com.coiso.org.entidades.Personas;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Manuela
 */
public class CasoPersonaJpaController implements Serializable {

    public CasoPersonaJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CasoPersona casoPersona) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Caso casoIdCaso = casoPersona.getCasoIdCaso();
            if (casoIdCaso != null) {
                casoIdCaso = em.getReference(casoIdCaso.getClass(), casoIdCaso.getIdCaso());
                casoPersona.setCasoIdCaso(casoIdCaso);
            }
            Personas personasCedula = casoPersona.getPersonasCedula();
            if (personasCedula != null) {
                personasCedula = em.getReference(personasCedula.getClass(), personasCedula.getCedula());
                casoPersona.setPersonasCedula(personasCedula);
            }
            em.persist(casoPersona);
            if (casoIdCaso != null) {
                casoIdCaso.getCasoPersonaCollection().add(casoPersona);
                casoIdCaso = em.merge(casoIdCaso);
            }
            if (personasCedula != null) {
                personasCedula.getCasoPersonaCollection().add(casoPersona);
                personasCedula = em.merge(personasCedula);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findCasoPersona(casoPersona.getIdCasoPersona()) != null) {
                throw new PreexistingEntityException("CasoPersona " + casoPersona + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CasoPersona casoPersona) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            CasoPersona persistentCasoPersona = em.find(CasoPersona.class, casoPersona.getIdCasoPersona());
            Caso casoIdCasoOld = persistentCasoPersona.getCasoIdCaso();
            Caso casoIdCasoNew = casoPersona.getCasoIdCaso();
            Personas personasCedulaOld = persistentCasoPersona.getPersonasCedula();
            Personas personasCedulaNew = casoPersona.getPersonasCedula();
            if (casoIdCasoNew != null) {
                casoIdCasoNew = em.getReference(casoIdCasoNew.getClass(), casoIdCasoNew.getIdCaso());
                casoPersona.setCasoIdCaso(casoIdCasoNew);
            }
            if (personasCedulaNew != null) {
                personasCedulaNew = em.getReference(personasCedulaNew.getClass(), personasCedulaNew.getCedula());
                casoPersona.setPersonasCedula(personasCedulaNew);
            }
            casoPersona = em.merge(casoPersona);
            if (casoIdCasoOld != null && !casoIdCasoOld.equals(casoIdCasoNew)) {
                casoIdCasoOld.getCasoPersonaCollection().remove(casoPersona);
                casoIdCasoOld = em.merge(casoIdCasoOld);
            }
            if (casoIdCasoNew != null && !casoIdCasoNew.equals(casoIdCasoOld)) {
                casoIdCasoNew.getCasoPersonaCollection().add(casoPersona);
                casoIdCasoNew = em.merge(casoIdCasoNew);
            }
            if (personasCedulaOld != null && !personasCedulaOld.equals(personasCedulaNew)) {
                personasCedulaOld.getCasoPersonaCollection().remove(casoPersona);
                personasCedulaOld = em.merge(personasCedulaOld);
            }
            if (personasCedulaNew != null && !personasCedulaNew.equals(personasCedulaOld)) {
                personasCedulaNew.getCasoPersonaCollection().add(casoPersona);
                personasCedulaNew = em.merge(personasCedulaNew);
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
                String id = casoPersona.getIdCasoPersona();
                if (findCasoPersona(id) == null) {
                    throw new NonexistentEntityException("The casoPersona with id " + id + " no longer exists.");
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
            CasoPersona casoPersona;
            try {
                casoPersona = em.getReference(CasoPersona.class, id);
                casoPersona.getIdCasoPersona();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The casoPersona with id " + id + " no longer exists.", enfe);
            }
            Caso casoIdCaso = casoPersona.getCasoIdCaso();
            if (casoIdCaso != null) {
                casoIdCaso.getCasoPersonaCollection().remove(casoPersona);
                casoIdCaso = em.merge(casoIdCaso);
            }
            Personas personasCedula = casoPersona.getPersonasCedula();
            if (personasCedula != null) {
                personasCedula.getCasoPersonaCollection().remove(casoPersona);
                personasCedula = em.merge(personasCedula);
            }
            em.remove(casoPersona);
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

    public List<CasoPersona> findCasoPersonaEntities() {
        return findCasoPersonaEntities(true, -1, -1);
    }

    public List<CasoPersona> findCasoPersonaEntities(int maxResults, int firstResult) {
        return findCasoPersonaEntities(false, maxResults, firstResult);
    }

    private List<CasoPersona> findCasoPersonaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CasoPersona.class));
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

    public CasoPersona findCasoPersona(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CasoPersona.class, id);
        } finally {
            em.close();
        }
    }

    public int getCasoPersonaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CasoPersona> rt = cq.from(CasoPersona.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
