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
import com.coiso.org.entidades.CodigoDiagnostico;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Manuela
 */
public class CodigoDiagnosticoJpaController implements Serializable {

    public CodigoDiagnosticoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CodigoDiagnostico codigoDiagnostico) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Caso casoIdCaso = codigoDiagnostico.getCasoIdCaso();
            if (casoIdCaso != null) {
                casoIdCaso = em.getReference(casoIdCaso.getClass(), casoIdCaso.getIdCaso());
                codigoDiagnostico.setCasoIdCaso(casoIdCaso);
            }
            em.persist(codigoDiagnostico);
            if (casoIdCaso != null) {
                casoIdCaso.getCodigoDiagnosticoCollection().add(codigoDiagnostico);
                casoIdCaso = em.merge(casoIdCaso);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findCodigoDiagnostico(codigoDiagnostico.getIdDiagnosticoCodigo()) != null) {
                throw new PreexistingEntityException("CodigoDiagnostico " + codigoDiagnostico + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CodigoDiagnostico codigoDiagnostico) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            CodigoDiagnostico persistentCodigoDiagnostico = em.find(CodigoDiagnostico.class, codigoDiagnostico.getIdDiagnosticoCodigo());
            Caso casoIdCasoOld = persistentCodigoDiagnostico.getCasoIdCaso();
            Caso casoIdCasoNew = codigoDiagnostico.getCasoIdCaso();
            if (casoIdCasoNew != null) {
                casoIdCasoNew = em.getReference(casoIdCasoNew.getClass(), casoIdCasoNew.getIdCaso());
                codigoDiagnostico.setCasoIdCaso(casoIdCasoNew);
            }
            codigoDiagnostico = em.merge(codigoDiagnostico);
            if (casoIdCasoOld != null && !casoIdCasoOld.equals(casoIdCasoNew)) {
                casoIdCasoOld.getCodigoDiagnosticoCollection().remove(codigoDiagnostico);
                casoIdCasoOld = em.merge(casoIdCasoOld);
            }
            if (casoIdCasoNew != null && !casoIdCasoNew.equals(casoIdCasoOld)) {
                casoIdCasoNew.getCodigoDiagnosticoCollection().add(codigoDiagnostico);
                casoIdCasoNew = em.merge(casoIdCasoNew);
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
                String id = codigoDiagnostico.getIdDiagnosticoCodigo();
                if (findCodigoDiagnostico(id) == null) {
                    throw new NonexistentEntityException("The codigoDiagnostico with id " + id + " no longer exists.");
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
            CodigoDiagnostico codigoDiagnostico;
            try {
                codigoDiagnostico = em.getReference(CodigoDiagnostico.class, id);
                codigoDiagnostico.getIdDiagnosticoCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The codigoDiagnostico with id " + id + " no longer exists.", enfe);
            }
            Caso casoIdCaso = codigoDiagnostico.getCasoIdCaso();
            if (casoIdCaso != null) {
                casoIdCaso.getCodigoDiagnosticoCollection().remove(codigoDiagnostico);
                casoIdCaso = em.merge(casoIdCaso);
            }
            em.remove(codigoDiagnostico);
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

    public List<CodigoDiagnostico> findCodigoDiagnosticoEntities() {
        return findCodigoDiagnosticoEntities(true, -1, -1);
    }

    public List<CodigoDiagnostico> findCodigoDiagnosticoEntities(int maxResults, int firstResult) {
        return findCodigoDiagnosticoEntities(false, maxResults, firstResult);
    }

    private List<CodigoDiagnostico> findCodigoDiagnosticoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CodigoDiagnostico.class));
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

    public CodigoDiagnostico findCodigoDiagnostico(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CodigoDiagnostico.class, id);
        } finally {
            em.close();
        }
    }

    public int getCodigoDiagnosticoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CodigoDiagnostico> rt = cq.from(CodigoDiagnostico.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
