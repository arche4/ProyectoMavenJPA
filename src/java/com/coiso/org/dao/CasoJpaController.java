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
import com.coiso.org.entidades.Caso;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.coiso.org.entidades.Usuario;
import com.coiso.org.entidades.CasoPersona;
import java.util.ArrayList;
import java.util.Collection;
import com.coiso.org.entidades.CodigoDiagnostico;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Manuela
 */
public class CasoJpaController implements Serializable {

    public CasoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Caso caso) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (caso.getCasoPersonaCollection() == null) {
            caso.setCasoPersonaCollection(new ArrayList<CasoPersona>());
        }
        if (caso.getCodigoDiagnosticoCollection() == null) {
            caso.setCodigoDiagnosticoCollection(new ArrayList<CodigoDiagnostico>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Usuario fkResponsableCaso = caso.getFkResponsableCaso();
            if (fkResponsableCaso != null) {
                fkResponsableCaso = em.getReference(fkResponsableCaso.getClass(), fkResponsableCaso.getIdUsuario());
                caso.setFkResponsableCaso(fkResponsableCaso);
            }
            Collection<CasoPersona> attachedCasoPersonaCollection = new ArrayList<CasoPersona>();
            for (CasoPersona casoPersonaCollectionCasoPersonaToAttach : caso.getCasoPersonaCollection()) {
                casoPersonaCollectionCasoPersonaToAttach = em.getReference(casoPersonaCollectionCasoPersonaToAttach.getClass(), casoPersonaCollectionCasoPersonaToAttach.getIdCasoPersona());
                attachedCasoPersonaCollection.add(casoPersonaCollectionCasoPersonaToAttach);
            }
            caso.setCasoPersonaCollection(attachedCasoPersonaCollection);
            Collection<CodigoDiagnostico> attachedCodigoDiagnosticoCollection = new ArrayList<CodigoDiagnostico>();
            for (CodigoDiagnostico codigoDiagnosticoCollectionCodigoDiagnosticoToAttach : caso.getCodigoDiagnosticoCollection()) {
                codigoDiagnosticoCollectionCodigoDiagnosticoToAttach = em.getReference(codigoDiagnosticoCollectionCodigoDiagnosticoToAttach.getClass(), codigoDiagnosticoCollectionCodigoDiagnosticoToAttach.getIdDiagnosticoCodigo());
                attachedCodigoDiagnosticoCollection.add(codigoDiagnosticoCollectionCodigoDiagnosticoToAttach);
            }
            caso.setCodigoDiagnosticoCollection(attachedCodigoDiagnosticoCollection);
            em.persist(caso);
            if (fkResponsableCaso != null) {
                fkResponsableCaso.getCasoCollection().add(caso);
                fkResponsableCaso = em.merge(fkResponsableCaso);
            }
            for (CasoPersona casoPersonaCollectionCasoPersona : caso.getCasoPersonaCollection()) {
                Caso oldCasoIdCasoOfCasoPersonaCollectionCasoPersona = casoPersonaCollectionCasoPersona.getCasoIdCaso();
                casoPersonaCollectionCasoPersona.setCasoIdCaso(caso);
                casoPersonaCollectionCasoPersona = em.merge(casoPersonaCollectionCasoPersona);
                if (oldCasoIdCasoOfCasoPersonaCollectionCasoPersona != null) {
                    oldCasoIdCasoOfCasoPersonaCollectionCasoPersona.getCasoPersonaCollection().remove(casoPersonaCollectionCasoPersona);
                    oldCasoIdCasoOfCasoPersonaCollectionCasoPersona = em.merge(oldCasoIdCasoOfCasoPersonaCollectionCasoPersona);
                }
            }
            for (CodigoDiagnostico codigoDiagnosticoCollectionCodigoDiagnostico : caso.getCodigoDiagnosticoCollection()) {
                Caso oldCasoIdCasoOfCodigoDiagnosticoCollectionCodigoDiagnostico = codigoDiagnosticoCollectionCodigoDiagnostico.getCasoIdCaso();
                codigoDiagnosticoCollectionCodigoDiagnostico.setCasoIdCaso(caso);
                codigoDiagnosticoCollectionCodigoDiagnostico = em.merge(codigoDiagnosticoCollectionCodigoDiagnostico);
                if (oldCasoIdCasoOfCodigoDiagnosticoCollectionCodigoDiagnostico != null) {
                    oldCasoIdCasoOfCodigoDiagnosticoCollectionCodigoDiagnostico.getCodigoDiagnosticoCollection().remove(codigoDiagnosticoCollectionCodigoDiagnostico);
                    oldCasoIdCasoOfCodigoDiagnosticoCollectionCodigoDiagnostico = em.merge(oldCasoIdCasoOfCodigoDiagnosticoCollectionCodigoDiagnostico);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findCaso(caso.getIdCaso()) != null) {
                throw new PreexistingEntityException("Caso " + caso + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Caso caso) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Caso persistentCaso = em.find(Caso.class, caso.getIdCaso());
            Usuario fkResponsableCasoOld = persistentCaso.getFkResponsableCaso();
            Usuario fkResponsableCasoNew = caso.getFkResponsableCaso();
            Collection<CasoPersona> casoPersonaCollectionOld = persistentCaso.getCasoPersonaCollection();
            Collection<CasoPersona> casoPersonaCollectionNew = caso.getCasoPersonaCollection();
            Collection<CodigoDiagnostico> codigoDiagnosticoCollectionOld = persistentCaso.getCodigoDiagnosticoCollection();
            Collection<CodigoDiagnostico> codigoDiagnosticoCollectionNew = caso.getCodigoDiagnosticoCollection();
            List<String> illegalOrphanMessages = null;
            for (CasoPersona casoPersonaCollectionOldCasoPersona : casoPersonaCollectionOld) {
                if (!casoPersonaCollectionNew.contains(casoPersonaCollectionOldCasoPersona)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CasoPersona " + casoPersonaCollectionOldCasoPersona + " since its casoIdCaso field is not nullable.");
                }
            }
            for (CodigoDiagnostico codigoDiagnosticoCollectionOldCodigoDiagnostico : codigoDiagnosticoCollectionOld) {
                if (!codigoDiagnosticoCollectionNew.contains(codigoDiagnosticoCollectionOldCodigoDiagnostico)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CodigoDiagnostico " + codigoDiagnosticoCollectionOldCodigoDiagnostico + " since its casoIdCaso field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (fkResponsableCasoNew != null) {
                fkResponsableCasoNew = em.getReference(fkResponsableCasoNew.getClass(), fkResponsableCasoNew.getIdUsuario());
                caso.setFkResponsableCaso(fkResponsableCasoNew);
            }
            Collection<CasoPersona> attachedCasoPersonaCollectionNew = new ArrayList<CasoPersona>();
            for (CasoPersona casoPersonaCollectionNewCasoPersonaToAttach : casoPersonaCollectionNew) {
                casoPersonaCollectionNewCasoPersonaToAttach = em.getReference(casoPersonaCollectionNewCasoPersonaToAttach.getClass(), casoPersonaCollectionNewCasoPersonaToAttach.getIdCasoPersona());
                attachedCasoPersonaCollectionNew.add(casoPersonaCollectionNewCasoPersonaToAttach);
            }
            casoPersonaCollectionNew = attachedCasoPersonaCollectionNew;
            caso.setCasoPersonaCollection(casoPersonaCollectionNew);
            Collection<CodigoDiagnostico> attachedCodigoDiagnosticoCollectionNew = new ArrayList<CodigoDiagnostico>();
            for (CodigoDiagnostico codigoDiagnosticoCollectionNewCodigoDiagnosticoToAttach : codigoDiagnosticoCollectionNew) {
                codigoDiagnosticoCollectionNewCodigoDiagnosticoToAttach = em.getReference(codigoDiagnosticoCollectionNewCodigoDiagnosticoToAttach.getClass(), codigoDiagnosticoCollectionNewCodigoDiagnosticoToAttach.getIdDiagnosticoCodigo());
                attachedCodigoDiagnosticoCollectionNew.add(codigoDiagnosticoCollectionNewCodigoDiagnosticoToAttach);
            }
            codigoDiagnosticoCollectionNew = attachedCodigoDiagnosticoCollectionNew;
            caso.setCodigoDiagnosticoCollection(codigoDiagnosticoCollectionNew);
            caso = em.merge(caso);
            if (fkResponsableCasoOld != null && !fkResponsableCasoOld.equals(fkResponsableCasoNew)) {
                fkResponsableCasoOld.getCasoCollection().remove(caso);
                fkResponsableCasoOld = em.merge(fkResponsableCasoOld);
            }
            if (fkResponsableCasoNew != null && !fkResponsableCasoNew.equals(fkResponsableCasoOld)) {
                fkResponsableCasoNew.getCasoCollection().add(caso);
                fkResponsableCasoNew = em.merge(fkResponsableCasoNew);
            }
            for (CasoPersona casoPersonaCollectionNewCasoPersona : casoPersonaCollectionNew) {
                if (!casoPersonaCollectionOld.contains(casoPersonaCollectionNewCasoPersona)) {
                    Caso oldCasoIdCasoOfCasoPersonaCollectionNewCasoPersona = casoPersonaCollectionNewCasoPersona.getCasoIdCaso();
                    casoPersonaCollectionNewCasoPersona.setCasoIdCaso(caso);
                    casoPersonaCollectionNewCasoPersona = em.merge(casoPersonaCollectionNewCasoPersona);
                    if (oldCasoIdCasoOfCasoPersonaCollectionNewCasoPersona != null && !oldCasoIdCasoOfCasoPersonaCollectionNewCasoPersona.equals(caso)) {
                        oldCasoIdCasoOfCasoPersonaCollectionNewCasoPersona.getCasoPersonaCollection().remove(casoPersonaCollectionNewCasoPersona);
                        oldCasoIdCasoOfCasoPersonaCollectionNewCasoPersona = em.merge(oldCasoIdCasoOfCasoPersonaCollectionNewCasoPersona);
                    }
                }
            }
            for (CodigoDiagnostico codigoDiagnosticoCollectionNewCodigoDiagnostico : codigoDiagnosticoCollectionNew) {
                if (!codigoDiagnosticoCollectionOld.contains(codigoDiagnosticoCollectionNewCodigoDiagnostico)) {
                    Caso oldCasoIdCasoOfCodigoDiagnosticoCollectionNewCodigoDiagnostico = codigoDiagnosticoCollectionNewCodigoDiagnostico.getCasoIdCaso();
                    codigoDiagnosticoCollectionNewCodigoDiagnostico.setCasoIdCaso(caso);
                    codigoDiagnosticoCollectionNewCodigoDiagnostico = em.merge(codigoDiagnosticoCollectionNewCodigoDiagnostico);
                    if (oldCasoIdCasoOfCodigoDiagnosticoCollectionNewCodigoDiagnostico != null && !oldCasoIdCasoOfCodigoDiagnosticoCollectionNewCodigoDiagnostico.equals(caso)) {
                        oldCasoIdCasoOfCodigoDiagnosticoCollectionNewCodigoDiagnostico.getCodigoDiagnosticoCollection().remove(codigoDiagnosticoCollectionNewCodigoDiagnostico);
                        oldCasoIdCasoOfCodigoDiagnosticoCollectionNewCodigoDiagnostico = em.merge(oldCasoIdCasoOfCodigoDiagnosticoCollectionNewCodigoDiagnostico);
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
                String id = caso.getIdCaso();
                if (findCaso(id) == null) {
                    throw new NonexistentEntityException("The caso with id " + id + " no longer exists.");
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
            Caso caso;
            try {
                caso = em.getReference(Caso.class, id);
                caso.getIdCaso();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The caso with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<CasoPersona> casoPersonaCollectionOrphanCheck = caso.getCasoPersonaCollection();
            for (CasoPersona casoPersonaCollectionOrphanCheckCasoPersona : casoPersonaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Caso (" + caso + ") cannot be destroyed since the CasoPersona " + casoPersonaCollectionOrphanCheckCasoPersona + " in its casoPersonaCollection field has a non-nullable casoIdCaso field.");
            }
            Collection<CodigoDiagnostico> codigoDiagnosticoCollectionOrphanCheck = caso.getCodigoDiagnosticoCollection();
            for (CodigoDiagnostico codigoDiagnosticoCollectionOrphanCheckCodigoDiagnostico : codigoDiagnosticoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Caso (" + caso + ") cannot be destroyed since the CodigoDiagnostico " + codigoDiagnosticoCollectionOrphanCheckCodigoDiagnostico + " in its codigoDiagnosticoCollection field has a non-nullable casoIdCaso field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Usuario fkResponsableCaso = caso.getFkResponsableCaso();
            if (fkResponsableCaso != null) {
                fkResponsableCaso.getCasoCollection().remove(caso);
                fkResponsableCaso = em.merge(fkResponsableCaso);
            }
            em.remove(caso);
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

    public List<Caso> findCasoEntities() {
        return findCasoEntities(true, -1, -1);
    }

    public List<Caso> findCasoEntities(int maxResults, int firstResult) {
        return findCasoEntities(false, maxResults, firstResult);
    }

    private List<Caso> findCasoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Caso.class));
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

    public Caso findCaso(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Caso.class, id);
        } finally {
            em.close();
        }
    }

    public int getCasoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Caso> rt = cq.from(Caso.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
