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
import com.coiso.org.entidades.Personas;
import com.coiso.org.entidades.Usuario;
import com.coiso.org.entidades.UsuarioPersona;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Manuela
 */
public class UsuarioPersonaJpaController implements Serializable {

    public UsuarioPersonaJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(UsuarioPersona usuarioPersona) throws PreexistingEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Personas personasCedula = usuarioPersona.getPersonasCedula();
            if (personasCedula != null) {
                personasCedula = em.getReference(personasCedula.getClass(), personasCedula.getCedula());
                usuarioPersona.setPersonasCedula(personasCedula);
            }
            Usuario usuarioIdUsuario = usuarioPersona.getUsuarioIdUsuario();
            if (usuarioIdUsuario != null) {
                usuarioIdUsuario = em.getReference(usuarioIdUsuario.getClass(), usuarioIdUsuario.getIdUsuario());
                usuarioPersona.setUsuarioIdUsuario(usuarioIdUsuario);
            }
            em.persist(usuarioPersona);
            if (personasCedula != null) {
                personasCedula.getUsuarioPersonaCollection().add(usuarioPersona);
                personasCedula = em.merge(personasCedula);
            }
            if (usuarioIdUsuario != null) {
                usuarioIdUsuario.getUsuarioPersonaCollection().add(usuarioPersona);
                usuarioIdUsuario = em.merge(usuarioIdUsuario);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findUsuarioPersona(usuarioPersona.getIdUsuarioPersona()) != null) {
                throw new PreexistingEntityException("UsuarioPersona " + usuarioPersona + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(UsuarioPersona usuarioPersona) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            UsuarioPersona persistentUsuarioPersona = em.find(UsuarioPersona.class, usuarioPersona.getIdUsuarioPersona());
            Personas personasCedulaOld = persistentUsuarioPersona.getPersonasCedula();
            Personas personasCedulaNew = usuarioPersona.getPersonasCedula();
            Usuario usuarioIdUsuarioOld = persistentUsuarioPersona.getUsuarioIdUsuario();
            Usuario usuarioIdUsuarioNew = usuarioPersona.getUsuarioIdUsuario();
            if (personasCedulaNew != null) {
                personasCedulaNew = em.getReference(personasCedulaNew.getClass(), personasCedulaNew.getCedula());
                usuarioPersona.setPersonasCedula(personasCedulaNew);
            }
            if (usuarioIdUsuarioNew != null) {
                usuarioIdUsuarioNew = em.getReference(usuarioIdUsuarioNew.getClass(), usuarioIdUsuarioNew.getIdUsuario());
                usuarioPersona.setUsuarioIdUsuario(usuarioIdUsuarioNew);
            }
            usuarioPersona = em.merge(usuarioPersona);
            if (personasCedulaOld != null && !personasCedulaOld.equals(personasCedulaNew)) {
                personasCedulaOld.getUsuarioPersonaCollection().remove(usuarioPersona);
                personasCedulaOld = em.merge(personasCedulaOld);
            }
            if (personasCedulaNew != null && !personasCedulaNew.equals(personasCedulaOld)) {
                personasCedulaNew.getUsuarioPersonaCollection().add(usuarioPersona);
                personasCedulaNew = em.merge(personasCedulaNew);
            }
            if (usuarioIdUsuarioOld != null && !usuarioIdUsuarioOld.equals(usuarioIdUsuarioNew)) {
                usuarioIdUsuarioOld.getUsuarioPersonaCollection().remove(usuarioPersona);
                usuarioIdUsuarioOld = em.merge(usuarioIdUsuarioOld);
            }
            if (usuarioIdUsuarioNew != null && !usuarioIdUsuarioNew.equals(usuarioIdUsuarioOld)) {
                usuarioIdUsuarioNew.getUsuarioPersonaCollection().add(usuarioPersona);
                usuarioIdUsuarioNew = em.merge(usuarioIdUsuarioNew);
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
                String id = usuarioPersona.getIdUsuarioPersona();
                if (findUsuarioPersona(id) == null) {
                    throw new NonexistentEntityException("The usuarioPersona with id " + id + " no longer exists.");
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
            UsuarioPersona usuarioPersona;
            try {
                usuarioPersona = em.getReference(UsuarioPersona.class, id);
                usuarioPersona.getIdUsuarioPersona();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuarioPersona with id " + id + " no longer exists.", enfe);
            }
            Personas personasCedula = usuarioPersona.getPersonasCedula();
            if (personasCedula != null) {
                personasCedula.getUsuarioPersonaCollection().remove(usuarioPersona);
                personasCedula = em.merge(personasCedula);
            }
            Usuario usuarioIdUsuario = usuarioPersona.getUsuarioIdUsuario();
            if (usuarioIdUsuario != null) {
                usuarioIdUsuario.getUsuarioPersonaCollection().remove(usuarioPersona);
                usuarioIdUsuario = em.merge(usuarioIdUsuario);
            }
            em.remove(usuarioPersona);
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

    public List<UsuarioPersona> findUsuarioPersonaEntities() {
        return findUsuarioPersonaEntities(true, -1, -1);
    }

    public List<UsuarioPersona> findUsuarioPersonaEntities(int maxResults, int firstResult) {
        return findUsuarioPersonaEntities(false, maxResults, firstResult);
    }

    private List<UsuarioPersona> findUsuarioPersonaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(UsuarioPersona.class));
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

    public UsuarioPersona findUsuarioPersona(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(UsuarioPersona.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioPersonaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<UsuarioPersona> rt = cq.from(UsuarioPersona.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
