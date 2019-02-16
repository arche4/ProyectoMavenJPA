/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.poli.appcoiso.controller;

import com.co.poli.appcoiso.controller.exceptions.IllegalOrphanException;
import com.co.poli.appcoiso.controller.exceptions.NonexistentEntityException;
import com.co.poli.appcoiso.controller.exceptions.PreexistingEntityException;
import com.co.poli.appcoiso.model.Caso;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.co.poli.appcoiso.model.CasoPersona;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Manuela
 */
public class CasoJpaController implements Serializable {

    public CasoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Caso caso) throws PreexistingEntityException, Exception {
        if (caso.getCasoPersonaList() == null) {
            caso.setCasoPersonaList(new ArrayList<CasoPersona>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<CasoPersona> attachedCasoPersonaList = new ArrayList<CasoPersona>();
            for (CasoPersona casoPersonaListCasoPersonaToAttach : caso.getCasoPersonaList()) {
                casoPersonaListCasoPersonaToAttach = em.getReference(casoPersonaListCasoPersonaToAttach.getClass(), casoPersonaListCasoPersonaToAttach.getIdCasoPersona());
                attachedCasoPersonaList.add(casoPersonaListCasoPersonaToAttach);
            }
            caso.setCasoPersonaList(attachedCasoPersonaList);
            em.persist(caso);
            for (CasoPersona casoPersonaListCasoPersona : caso.getCasoPersonaList()) {
                Caso oldCasoIdCasoOfCasoPersonaListCasoPersona = casoPersonaListCasoPersona.getCasoIdCaso();
                casoPersonaListCasoPersona.setCasoIdCaso(caso);
                casoPersonaListCasoPersona = em.merge(casoPersonaListCasoPersona);
                if (oldCasoIdCasoOfCasoPersonaListCasoPersona != null) {
                    oldCasoIdCasoOfCasoPersonaListCasoPersona.getCasoPersonaList().remove(casoPersonaListCasoPersona);
                    oldCasoIdCasoOfCasoPersonaListCasoPersona = em.merge(oldCasoIdCasoOfCasoPersonaListCasoPersona);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
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

    public void edit(Caso caso) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Caso persistentCaso = em.find(Caso.class, caso.getIdCaso());
            List<String> illegalOrphanMessages = null;
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            
            caso = em.merge(caso);
            em.getTransaction().commit();
        } catch (Exception ex) {
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

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Caso caso;
            try {
                caso = em.getReference(Caso.class, id);
                caso.getIdCaso();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The caso with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<CasoPersona> casoPersonaListOrphanCheck = caso.getCasoPersonaList();
            for (CasoPersona casoPersonaListOrphanCheckCasoPersona : casoPersonaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Caso (" + caso + ") cannot be destroyed since the CasoPersona " + casoPersonaListOrphanCheckCasoPersona + " in its casoPersonaList field has a non-nullable casoIdCaso field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(caso);
            em.getTransaction().commit();
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
    
    
        public String crearCaso(Caso caso) {
        EntityManager em = null;
        String respuesta = "Caso Creado";
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(caso);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCaso(caso.getIdCaso()) != null) {
                respuesta = "caso ya existe";
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return respuesta;

    }
        
        
        public Caso buscarCaso(String id) {
        EntityManager em = getEntityManager();
        Caso caso = null;
        try {
            List<Caso> listado = findCasoEntities();
            for (Caso c : listado) {
                if (id.equals(c.getIdCaso())) {
                    caso = new Caso();
                    caso = c;

                }
            }
            return caso;
        } finally {
            em.close();
        }
    }
    
    
}
