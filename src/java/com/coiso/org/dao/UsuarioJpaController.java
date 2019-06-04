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
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.coiso.org.entidades.UsuarioPersona;
import java.util.ArrayList;
import java.util.Collection;
import com.coiso.org.entidades.Caso;
import com.coiso.org.entidades.Usuario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Manuela
 */
public class UsuarioJpaController implements Serializable {

    public UsuarioJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuario usuario) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (usuario.getUsuarioPersonaCollection() == null) {
            usuario.setUsuarioPersonaCollection(new ArrayList<UsuarioPersona>());
        }
        if (usuario.getCasoCollection() == null) {
            usuario.setCasoCollection(new ArrayList<Caso>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<UsuarioPersona> attachedUsuarioPersonaCollection = new ArrayList<UsuarioPersona>();
            for (UsuarioPersona usuarioPersonaCollectionUsuarioPersonaToAttach : usuario.getUsuarioPersonaCollection()) {
                usuarioPersonaCollectionUsuarioPersonaToAttach = em.getReference(usuarioPersonaCollectionUsuarioPersonaToAttach.getClass(), usuarioPersonaCollectionUsuarioPersonaToAttach.getIdUsuarioPersona());
                attachedUsuarioPersonaCollection.add(usuarioPersonaCollectionUsuarioPersonaToAttach);
            }
            usuario.setUsuarioPersonaCollection(attachedUsuarioPersonaCollection);
            Collection<Caso> attachedCasoCollection = new ArrayList<Caso>();
            for (Caso casoCollectionCasoToAttach : usuario.getCasoCollection()) {
                casoCollectionCasoToAttach = em.getReference(casoCollectionCasoToAttach.getClass(), casoCollectionCasoToAttach.getIdCaso());
                attachedCasoCollection.add(casoCollectionCasoToAttach);
            }
            usuario.setCasoCollection(attachedCasoCollection);
            em.persist(usuario);
            for (UsuarioPersona usuarioPersonaCollectionUsuarioPersona : usuario.getUsuarioPersonaCollection()) {
                Usuario oldUsuarioIdUsuarioOfUsuarioPersonaCollectionUsuarioPersona = usuarioPersonaCollectionUsuarioPersona.getUsuarioIdUsuario();
                usuarioPersonaCollectionUsuarioPersona.setUsuarioIdUsuario(usuario);
                usuarioPersonaCollectionUsuarioPersona = em.merge(usuarioPersonaCollectionUsuarioPersona);
                if (oldUsuarioIdUsuarioOfUsuarioPersonaCollectionUsuarioPersona != null) {
                    oldUsuarioIdUsuarioOfUsuarioPersonaCollectionUsuarioPersona.getUsuarioPersonaCollection().remove(usuarioPersonaCollectionUsuarioPersona);
                    oldUsuarioIdUsuarioOfUsuarioPersonaCollectionUsuarioPersona = em.merge(oldUsuarioIdUsuarioOfUsuarioPersonaCollectionUsuarioPersona);
                }
            }
            for (Caso casoCollectionCaso : usuario.getCasoCollection()) {
                Usuario oldFkResponsableCasoOfCasoCollectionCaso = casoCollectionCaso.getFkResponsableCaso();
                casoCollectionCaso.setFkResponsableCaso(usuario);
                casoCollectionCaso = em.merge(casoCollectionCaso);
                if (oldFkResponsableCasoOfCasoCollectionCaso != null) {
                    oldFkResponsableCasoOfCasoCollectionCaso.getCasoCollection().remove(casoCollectionCaso);
                    oldFkResponsableCasoOfCasoCollectionCaso = em.merge(oldFkResponsableCasoOfCasoCollectionCaso);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findUsuario(usuario.getIdUsuario()) != null) {
                throw new PreexistingEntityException("Usuario " + usuario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getIdUsuario());
            Collection<UsuarioPersona> usuarioPersonaCollectionOld = persistentUsuario.getUsuarioPersonaCollection();
            Collection<UsuarioPersona> usuarioPersonaCollectionNew = usuario.getUsuarioPersonaCollection();
            Collection<Caso> casoCollectionOld = persistentUsuario.getCasoCollection();
            Collection<Caso> casoCollectionNew = usuario.getCasoCollection();
            List<String> illegalOrphanMessages = null;
            for (UsuarioPersona usuarioPersonaCollectionOldUsuarioPersona : usuarioPersonaCollectionOld) {
                if (!usuarioPersonaCollectionNew.contains(usuarioPersonaCollectionOldUsuarioPersona)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain UsuarioPersona " + usuarioPersonaCollectionOldUsuarioPersona + " since its usuarioIdUsuario field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<UsuarioPersona> attachedUsuarioPersonaCollectionNew = new ArrayList<UsuarioPersona>();
            for (UsuarioPersona usuarioPersonaCollectionNewUsuarioPersonaToAttach : usuarioPersonaCollectionNew) {
                usuarioPersonaCollectionNewUsuarioPersonaToAttach = em.getReference(usuarioPersonaCollectionNewUsuarioPersonaToAttach.getClass(), usuarioPersonaCollectionNewUsuarioPersonaToAttach.getIdUsuarioPersona());
                attachedUsuarioPersonaCollectionNew.add(usuarioPersonaCollectionNewUsuarioPersonaToAttach);
            }
            usuarioPersonaCollectionNew = attachedUsuarioPersonaCollectionNew;
            usuario.setUsuarioPersonaCollection(usuarioPersonaCollectionNew);
            Collection<Caso> attachedCasoCollectionNew = new ArrayList<Caso>();
            for (Caso casoCollectionNewCasoToAttach : casoCollectionNew) {
                casoCollectionNewCasoToAttach = em.getReference(casoCollectionNewCasoToAttach.getClass(), casoCollectionNewCasoToAttach.getIdCaso());
                attachedCasoCollectionNew.add(casoCollectionNewCasoToAttach);
            }
            casoCollectionNew = attachedCasoCollectionNew;
            usuario.setCasoCollection(casoCollectionNew);
            usuario = em.merge(usuario);
            for (UsuarioPersona usuarioPersonaCollectionNewUsuarioPersona : usuarioPersonaCollectionNew) {
                if (!usuarioPersonaCollectionOld.contains(usuarioPersonaCollectionNewUsuarioPersona)) {
                    Usuario oldUsuarioIdUsuarioOfUsuarioPersonaCollectionNewUsuarioPersona = usuarioPersonaCollectionNewUsuarioPersona.getUsuarioIdUsuario();
                    usuarioPersonaCollectionNewUsuarioPersona.setUsuarioIdUsuario(usuario);
                    usuarioPersonaCollectionNewUsuarioPersona = em.merge(usuarioPersonaCollectionNewUsuarioPersona);
                    if (oldUsuarioIdUsuarioOfUsuarioPersonaCollectionNewUsuarioPersona != null && !oldUsuarioIdUsuarioOfUsuarioPersonaCollectionNewUsuarioPersona.equals(usuario)) {
                        oldUsuarioIdUsuarioOfUsuarioPersonaCollectionNewUsuarioPersona.getUsuarioPersonaCollection().remove(usuarioPersonaCollectionNewUsuarioPersona);
                        oldUsuarioIdUsuarioOfUsuarioPersonaCollectionNewUsuarioPersona = em.merge(oldUsuarioIdUsuarioOfUsuarioPersonaCollectionNewUsuarioPersona);
                    }
                }
            }
            for (Caso casoCollectionOldCaso : casoCollectionOld) {
                if (!casoCollectionNew.contains(casoCollectionOldCaso)) {
                    casoCollectionOldCaso.setFkResponsableCaso(null);
                    casoCollectionOldCaso = em.merge(casoCollectionOldCaso);
                }
            }
            for (Caso casoCollectionNewCaso : casoCollectionNew) {
                if (!casoCollectionOld.contains(casoCollectionNewCaso)) {
                    Usuario oldFkResponsableCasoOfCasoCollectionNewCaso = casoCollectionNewCaso.getFkResponsableCaso();
                    casoCollectionNewCaso.setFkResponsableCaso(usuario);
                    casoCollectionNewCaso = em.merge(casoCollectionNewCaso);
                    if (oldFkResponsableCasoOfCasoCollectionNewCaso != null && !oldFkResponsableCasoOfCasoCollectionNewCaso.equals(usuario)) {
                        oldFkResponsableCasoOfCasoCollectionNewCaso.getCasoCollection().remove(casoCollectionNewCaso);
                        oldFkResponsableCasoOfCasoCollectionNewCaso = em.merge(oldFkResponsableCasoOfCasoCollectionNewCaso);
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
                String id = usuario.getIdUsuario();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
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
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getIdUsuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<UsuarioPersona> usuarioPersonaCollectionOrphanCheck = usuario.getUsuarioPersonaCollection();
            for (UsuarioPersona usuarioPersonaCollectionOrphanCheckUsuarioPersona : usuarioPersonaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the UsuarioPersona " + usuarioPersonaCollectionOrphanCheckUsuarioPersona + " in its usuarioPersonaCollection field has a non-nullable usuarioIdUsuario field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Caso> casoCollection = usuario.getCasoCollection();
            for (Caso casoCollectionCaso : casoCollection) {
                casoCollectionCaso.setFkResponsableCaso(null);
                casoCollectionCaso = em.merge(casoCollectionCaso);
            }
            em.remove(usuario);
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

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
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

    public Usuario findUsuario(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
