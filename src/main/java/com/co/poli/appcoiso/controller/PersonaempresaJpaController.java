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
import com.co.poli.appcoiso.model.Empresa;
import com.co.poli.appcoiso.model.Personaempresa;
import com.co.poli.appcoiso.model.Personas;
import com.co.poli.appcoiso.model.Usuario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Manuela
 */
public class PersonaempresaJpaController implements Serializable {

    public PersonaempresaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Personaempresa personaempresa) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empresa nitempresaFk = personaempresa.getNitempresaFk();
            if (nitempresaFk != null) {
                nitempresaFk = em.getReference(nitempresaFk.getClass(), nitempresaFk.getNit());
                personaempresa.setNitempresaFk(nitempresaFk);
            }
            Personas cedulaFk = personaempresa.getCedulaFk();
            if (cedulaFk != null) {
                cedulaFk = em.getReference(cedulaFk.getClass(), cedulaFk.getCedula());
                personaempresa.setCedulaFk(cedulaFk);
            }
            em.persist(personaempresa);
            if (nitempresaFk != null) {
                nitempresaFk.getPersonaempresaList().add(personaempresa);
                nitempresaFk = em.merge(nitempresaFk);
            }
           
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPersonaempresa(personaempresa.getId()) != null) {
                throw new PreexistingEntityException("Personaempresa " + personaempresa + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Personaempresa personaempresa) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Personaempresa persistentPersonaempresa = em.find(Personaempresa.class, personaempresa.getId());
            Empresa nitempresaFkOld = persistentPersonaempresa.getNitempresaFk();
            Empresa nitempresaFkNew = personaempresa.getNitempresaFk();
            Personas cedulaFkOld = persistentPersonaempresa.getCedulaFk();
            Personas cedulaFkNew = personaempresa.getCedulaFk();
            if (nitempresaFkNew != null) {
                nitempresaFkNew = em.getReference(nitempresaFkNew.getClass(), nitempresaFkNew.getNit());
                personaempresa.setNitempresaFk(nitempresaFkNew);
            }
            if (cedulaFkNew != null) {
                cedulaFkNew = em.getReference(cedulaFkNew.getClass(), cedulaFkNew.getCedula());
                personaempresa.setCedulaFk(cedulaFkNew);
            }
            personaempresa = em.merge(personaempresa);
            if (nitempresaFkOld != null && !nitempresaFkOld.equals(nitempresaFkNew)) {
                nitempresaFkOld.getPersonaempresaList().remove(personaempresa);
                nitempresaFkOld = em.merge(nitempresaFkOld);
            }
            if (nitempresaFkNew != null && !nitempresaFkNew.equals(nitempresaFkOld)) {
                nitempresaFkNew.getPersonaempresaList().add(personaempresa);
                nitempresaFkNew = em.merge(nitempresaFkNew);
            }
          
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = personaempresa.getId();
                if (findPersonaempresa(id) == null) {
                    throw new NonexistentEntityException("The personaempresa with id " + id + " no longer exists.");
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
            Personaempresa personaempresa;
            try {
                personaempresa = em.getReference(Personaempresa.class, id);
                personaempresa.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The personaempresa with id " + id + " no longer exists.", enfe);
            }
            Empresa nitempresaFk = personaempresa.getNitempresaFk();
            if (nitempresaFk != null) {
                nitempresaFk.getPersonaempresaList().remove(personaempresa);
                nitempresaFk = em.merge(nitempresaFk);
            }
            Personas cedulaFk = personaempresa.getCedulaFk();
           
            em.remove(personaempresa);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Personaempresa> findPersonaempresaEntities() {
        return findPersonaempresaEntities(true, -1, -1);
    }

    public List<Personaempresa> findPersonaempresaEntities(int maxResults, int firstResult) {
        return findPersonaempresaEntities(false, maxResults, firstResult);
    }

    private List<Personaempresa> findPersonaempresaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Personaempresa.class));
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

    public Personaempresa findPersonaempresa(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Personaempresa.class, id);
        } finally {
            em.close();
        }
    }

    public int getPersonaempresaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Personaempresa> rt = cq.from(Personaempresa.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    public String crear(Personaempresa personaempresa) {
        EntityManager em = null;
        String respuesta = "Empresa Persona Creado";
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empresa nitempresaFk = personaempresa.getNitempresaFk();
            if (nitempresaFk != null) {
                nitempresaFk = em.getReference(nitempresaFk.getClass(), nitempresaFk.getNit());
                personaempresa.setNitempresaFk(nitempresaFk);
            }
            Personas cedulaFk = personaempresa.getCedulaFk();
            if (cedulaFk != null) {
                cedulaFk = em.getReference(cedulaFk.getClass(), cedulaFk.getCedula());
                personaempresa.setCedulaFk(cedulaFk);
            }
            em.persist(personaempresa);
            if (nitempresaFk != null) {
                nitempresaFk.getPersonaempresaList().add(personaempresa);
                nitempresaFk = em.merge(nitempresaFk);
            }
            
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPersonaempresa(personaempresa.getId()) != null) {
                respuesta = "Empresa persona ya existe";
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
