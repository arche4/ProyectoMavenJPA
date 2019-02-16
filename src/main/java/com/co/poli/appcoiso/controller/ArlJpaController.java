/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.poli.appcoiso.controller;

import com.co.poli.appcoiso.controller.exceptions.IllegalOrphanException;
import com.co.poli.appcoiso.controller.exceptions.NonexistentEntityException;
import com.co.poli.appcoiso.controller.exceptions.PreexistingEntityException;
import com.co.poli.appcoiso.model.Arl;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.co.poli.appcoiso.model.Personas;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Manuela
 */
public class ArlJpaController implements Serializable {

    public ArlJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Arl arl) throws PreexistingEntityException, Exception {
        if (arl.getPersonasList() == null) {
            arl.setPersonasList(new ArrayList<Personas>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Personas> attachedPersonasList = new ArrayList<Personas>();
            for (Personas personasListPersonasToAttach : arl.getPersonasList()) {
                personasListPersonasToAttach = em.getReference(personasListPersonasToAttach.getClass(), personasListPersonasToAttach.getCedula());
                attachedPersonasList.add(personasListPersonasToAttach);
            }
            arl.setPersonasList(attachedPersonasList);
            em.persist(arl);
            for (Personas personasListPersonas : arl.getPersonasList()) {
                Arl oldCodigoarlFkOfPersonasListPersonas = personasListPersonas.getCodigoarlFk();
                personasListPersonas.setCodigoarlFk(arl);
                personasListPersonas = em.merge(personasListPersonas);
                if (oldCodigoarlFkOfPersonasListPersonas != null) {
                    oldCodigoarlFkOfPersonasListPersonas.getPersonasList().remove(personasListPersonas);
                    oldCodigoarlFkOfPersonasListPersonas = em.merge(oldCodigoarlFkOfPersonasListPersonas);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findArl(arl.getCodigoarl()) != null) {
                throw new PreexistingEntityException("Arl " + arl + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Arl arl) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Arl persistentArl = em.find(Arl.class, arl.getCodigoarl());
            List<Personas> personasListOld = persistentArl.getPersonasList();
            List<Personas> personasListNew = arl.getPersonasList();
            List<String> illegalOrphanMessages = null;
            for (Personas personasListOldPersonas : personasListOld) {
                if (!personasListNew.contains(personasListOldPersonas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Personas " + personasListOldPersonas + " since its codigoarlFk field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Personas> attachedPersonasListNew = new ArrayList<Personas>();
            for (Personas personasListNewPersonasToAttach : personasListNew) {
                personasListNewPersonasToAttach = em.getReference(personasListNewPersonasToAttach.getClass(), personasListNewPersonasToAttach.getCedula());
                attachedPersonasListNew.add(personasListNewPersonasToAttach);
            }
            personasListNew = attachedPersonasListNew;
            arl.setPersonasList(personasListNew);
            arl = em.merge(arl);
            for (Personas personasListNewPersonas : personasListNew) {
                if (!personasListOld.contains(personasListNewPersonas)) {
                    Arl oldCodigoarlFkOfPersonasListNewPersonas = personasListNewPersonas.getCodigoarlFk();
                    personasListNewPersonas.setCodigoarlFk(arl);
                    personasListNewPersonas = em.merge(personasListNewPersonas);
                    if (oldCodigoarlFkOfPersonasListNewPersonas != null && !oldCodigoarlFkOfPersonasListNewPersonas.equals(arl)) {
                        oldCodigoarlFkOfPersonasListNewPersonas.getPersonasList().remove(personasListNewPersonas);
                        oldCodigoarlFkOfPersonasListNewPersonas = em.merge(oldCodigoarlFkOfPersonasListNewPersonas);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = arl.getCodigoarl();
                if (findArl(id) == null) {
                    throw new NonexistentEntityException("The arl with id " + id + " no longer exists.");
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
            Arl arl;
            try {
                arl = em.getReference(Arl.class, id);
                arl.getCodigoarl();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The arl with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Personas> personasListOrphanCheck = arl.getPersonasList();
            for (Personas personasListOrphanCheckPersonas : personasListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Arl (" + arl + ") cannot be destroyed since the Personas " + personasListOrphanCheckPersonas + " in its personasList field has a non-nullable codigoarlFk field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(arl);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Arl> findArlEntities() {
        return findArlEntities(true, -1, -1);
    }

    public List<Arl> findArlEntities(int maxResults, int firstResult) {
        return findArlEntities(false, maxResults, firstResult);
    }

    private List<Arl> findArlEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Arl.class));
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

    public Arl findArl(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Arl.class, id);
        } finally {
            em.close();
        }
    }

    public int getArlCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Arl> rt = cq.from(Arl.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
