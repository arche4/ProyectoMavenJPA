/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.poli.appcoiso.controller;

import com.co.poli.appcoiso.controller.exceptions.IllegalOrphanException;
import com.co.poli.appcoiso.controller.exceptions.NonexistentEntityException;
import com.co.poli.appcoiso.controller.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.co.poli.appcoiso.model.Afp;
import com.co.poli.appcoiso.model.Arl;
import com.co.poli.appcoiso.model.Cargo;
import com.co.poli.appcoiso.model.Eps;
import com.co.poli.appcoiso.model.CasoPersona;
import java.util.ArrayList;
import java.util.List;
import com.co.poli.appcoiso.model.Personaempresa;
import com.co.poli.appcoiso.model.CitasPersona;
import com.co.poli.appcoiso.model.Personas;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Manuela
 */
public class PersonasJpaController implements Serializable {

    public PersonasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Personas personas) throws PreexistingEntityException, Exception {
      
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Afp codigoafpFk = personas.getCodigoafpFk();
            if (codigoafpFk != null) {
                codigoafpFk = em.getReference(codigoafpFk.getClass(), codigoafpFk.getCodigoafp());
                personas.setCodigoafpFk(codigoafpFk);
            }
            Arl codigoarlFk = personas.getCodigoarlFk();
            if (codigoarlFk != null) {
                codigoarlFk = em.getReference(codigoarlFk.getClass(), codigoarlFk.getCodigoarl());
                personas.setCodigoarlFk(codigoarlFk);
            }
            Cargo cargoCodigocargo = personas.getCargoCodigocargo();
            if (cargoCodigocargo != null) {
                cargoCodigocargo = em.getReference(cargoCodigocargo.getClass(), cargoCodigocargo.getCodigocargo());
                personas.setCargoCodigocargo(cargoCodigocargo);
            }
            Eps codigoepsFk = personas.getCodigoepsFk();
            if (codigoepsFk != null) {
                codigoepsFk = em.getReference(codigoepsFk.getClass(), codigoepsFk.getCodigoeps());
                personas.setCodigoepsFk(codigoepsFk);
            }
            List<CasoPersona> attachedCasoPersonaList = new ArrayList<CasoPersona>();
            
        
            em.persist(personas);
            if (codigoafpFk != null) {
                codigoafpFk.getPersonasList().add(personas);
                codigoafpFk = em.merge(codigoafpFk);
            }
            if (codigoarlFk != null) {
                codigoarlFk.getPersonasList().add(personas);
                codigoarlFk = em.merge(codigoarlFk);
            }
            if (cargoCodigocargo != null) {
                cargoCodigocargo.getPersonasList().add(personas);
                cargoCodigocargo = em.merge(cargoCodigocargo);
            }
            if (codigoepsFk != null) {
                codigoepsFk.getPersonasList().add(personas);
                codigoepsFk = em.merge(codigoepsFk);
            }
          
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPersonas(personas.getCedula()) != null) {
                throw new PreexistingEntityException("Personas " + personas + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Personas personas) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Personas persistentPersonas = em.find(Personas.class, personas.getCedula());
            Afp codigoafpFkOld = persistentPersonas.getCodigoafpFk();
            Afp codigoafpFkNew = personas.getCodigoafpFk();
            Arl codigoarlFkOld = persistentPersonas.getCodigoarlFk();
            Arl codigoarlFkNew = personas.getCodigoarlFk();
            Cargo cargoCodigocargoOld = persistentPersonas.getCargoCodigocargo();
            Cargo cargoCodigocargoNew = personas.getCargoCodigocargo();
            Eps codigoepsFkOld = persistentPersonas.getCodigoepsFk();
            Eps codigoepsFkNew = personas.getCodigoepsFk();
           
            List<String> illegalOrphanMessages = null;
            
        
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (codigoafpFkNew != null) {
                codigoafpFkNew = em.getReference(codigoafpFkNew.getClass(), codigoafpFkNew.getCodigoafp());
                personas.setCodigoafpFk(codigoafpFkNew);
            }
            if (codigoarlFkNew != null) {
                codigoarlFkNew = em.getReference(codigoarlFkNew.getClass(), codigoarlFkNew.getCodigoarl());
                personas.setCodigoarlFk(codigoarlFkNew);
            }
            if (cargoCodigocargoNew != null) {
                cargoCodigocargoNew = em.getReference(cargoCodigocargoNew.getClass(), cargoCodigocargoNew.getCodigocargo());
                personas.setCargoCodigocargo(cargoCodigocargoNew);
            }
            if (codigoepsFkNew != null) {
                codigoepsFkNew = em.getReference(codigoepsFkNew.getClass(), codigoepsFkNew.getCodigoeps());
                personas.setCodigoepsFk(codigoepsFkNew);
            }
       
            personas = em.merge(personas);
            if (codigoafpFkOld != null && !codigoafpFkOld.equals(codigoafpFkNew)) {
                codigoafpFkOld.getPersonasList().remove(personas);
                codigoafpFkOld = em.merge(codigoafpFkOld);
            }
            if (codigoafpFkNew != null && !codigoafpFkNew.equals(codigoafpFkOld)) {
                codigoafpFkNew.getPersonasList().add(personas);
                codigoafpFkNew = em.merge(codigoafpFkNew);
            }
            if (codigoarlFkOld != null && !codigoarlFkOld.equals(codigoarlFkNew)) {
                codigoarlFkOld.getPersonasList().remove(personas);
                codigoarlFkOld = em.merge(codigoarlFkOld);
            }
            if (codigoarlFkNew != null && !codigoarlFkNew.equals(codigoarlFkOld)) {
                codigoarlFkNew.getPersonasList().add(personas);
                codigoarlFkNew = em.merge(codigoarlFkNew);
            }
            if (cargoCodigocargoOld != null && !cargoCodigocargoOld.equals(cargoCodigocargoNew)) {
                cargoCodigocargoOld.getPersonasList().remove(personas);
                cargoCodigocargoOld = em.merge(cargoCodigocargoOld);
            }
            if (cargoCodigocargoNew != null && !cargoCodigocargoNew.equals(cargoCodigocargoOld)) {
                cargoCodigocargoNew.getPersonasList().add(personas);
                cargoCodigocargoNew = em.merge(cargoCodigocargoNew);
            }
            if (codigoepsFkOld != null && !codigoepsFkOld.equals(codigoepsFkNew)) {
                codigoepsFkOld.getPersonasList().remove(personas);
                codigoepsFkOld = em.merge(codigoepsFkOld);
            }
            if (codigoepsFkNew != null && !codigoepsFkNew.equals(codigoepsFkOld)) {
                codigoepsFkNew.getPersonasList().add(personas);
                codigoepsFkNew = em.merge(codigoepsFkNew);
            }
         
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = personas.getCedula();
                if (findPersonas(id) == null) {
                    throw new NonexistentEntityException("The personas with id " + id + " no longer exists.");
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
            Personas personas;
            try {
                personas = em.getReference(Personas.class, id);
                personas.getCedula();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The personas with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;

            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Afp codigoafpFk = personas.getCodigoafpFk();
            if (codigoafpFk != null) {
                codigoafpFk.getPersonasList().remove(personas);
                codigoafpFk = em.merge(codigoafpFk);
            }
            Arl codigoarlFk = personas.getCodigoarlFk();
            if (codigoarlFk != null) {
                codigoarlFk.getPersonasList().remove(personas);
                codigoarlFk = em.merge(codigoarlFk);
            }
            Cargo cargoCodigocargo = personas.getCargoCodigocargo();
            if (cargoCodigocargo != null) {
                cargoCodigocargo.getPersonasList().remove(personas);
                cargoCodigocargo = em.merge(cargoCodigocargo);
            }
            Eps codigoepsFk = personas.getCodigoepsFk();
            if (codigoepsFk != null) {
                codigoepsFk.getPersonasList().remove(personas);
                codigoepsFk = em.merge(codigoepsFk);
            }
            em.remove(personas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Personas> findPersonasEntities() {
        return findPersonasEntities(true, -1, -1);
    }

    public List<Personas> findPersonasEntities(int maxResults, int firstResult) {
        return findPersonasEntities(false, maxResults, firstResult);
    }

    private List<Personas> findPersonasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Personas.class));
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

    public Personas findPersonas(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Personas.class, id);
        } finally {
            em.close();
        }
    }

    public int getPersonasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Personas> rt = cq.from(Personas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public String  crear(Personas personas){
        String respuesta = null;
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(personas);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPersonas(personas.getCedula()) != null) {
                 respuesta = "Persona ya existe";
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
            return respuesta;
    }
    
}
