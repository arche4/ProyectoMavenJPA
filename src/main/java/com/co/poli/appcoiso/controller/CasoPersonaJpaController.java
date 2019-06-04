/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.poli.appcoiso.controller;

import com.co.poli.appcoiso.controller.exceptions.NonexistentEntityException;
import com.co.poli.appcoiso.controller.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.co.poli.appcoiso.model.Caso;
import com.co.poli.appcoiso.model.CasoPersona;
import com.co.poli.appcoiso.model.Personaempresa;
import com.co.poli.appcoiso.model.Personas;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Manuela
 */
public class CasoPersonaJpaController implements Serializable {

    public CasoPersonaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CasoPersona casoPersona) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
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
                casoIdCaso.getCasoPersonaList().add(casoPersona);
                casoIdCaso = em.merge(casoIdCaso);
            }
            
            em.getTransaction().commit();
        } catch (Exception ex) {
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
    
     public String  crear(CasoPersona casoPersona) {
        EntityManager em = null;
        String mensaje = "";
        try {
            em = getEntityManager();
            em.getTransaction().begin();
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
                casoIdCaso.getCasoPersonaList().add(casoPersona);
                casoIdCaso = em.merge(casoIdCaso);
            }
        
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCasoPersona(casoPersona.getIdCasoPersona()) != null) {
               mensaje = "persona caso ya existe";

            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return mensaje;
    }

    public void edit(CasoPersona casoPersona) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
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
                casoIdCasoOld.getCasoPersonaList().remove(casoPersona);
                casoIdCasoOld = em.merge(casoIdCasoOld);
            }
            if (casoIdCasoNew != null && !casoIdCasoNew.equals(casoIdCasoOld)) {
                casoIdCasoNew.getCasoPersonaList().add(casoPersona);
                casoIdCasoNew = em.merge(casoIdCasoNew);
            }
          
            em.getTransaction().commit();
        } catch (Exception ex) {
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

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CasoPersona casoPersona;
            try {
                casoPersona = em.getReference(CasoPersona.class, id);
                casoPersona.getIdCasoPersona();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The casoPersona with id " + id + " no longer exists.", enfe);
            }
            Caso casoIdCaso = casoPersona.getCasoIdCaso();
            if (casoIdCaso != null) {
                casoIdCaso.getCasoPersonaList().remove(casoPersona);
                casoIdCaso = em.merge(casoIdCaso);
            }
            Personas personasCedula = casoPersona.getPersonasCedula();
          
            em.remove(casoPersona);
            em.getTransaction().commit();
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
    
      public CasoPersona buscarCasoPersona(String id) {
        EntityManager em = getEntityManager();
        CasoPersona cp = null;
        try {
            List<CasoPersona> listado = findCasoPersonaEntities();
            for (CasoPersona casoP : listado) {
                if (id.equals(casoP.getIdCasoPersona())) {
                    cp = new CasoPersona();
                    cp = casoP;

                }
            }
            return cp;
        } finally {
            em.close();
        }
    }
    
}
