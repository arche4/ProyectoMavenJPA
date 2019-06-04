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
import com.coiso.org.entidades.Afp;
import com.coiso.org.entidades.Arl;
import com.coiso.org.entidades.Cargo;
import com.coiso.org.entidades.Eps;
import com.coiso.org.entidades.Profesion;
import com.coiso.org.entidades.Personas;
import com.coiso.org.entidades.CasoPersona;
import java.util.ArrayList;
import java.util.Collection;
import com.coiso.org.entidades.Personaempresa;
import com.coiso.org.entidades.UsuarioPersona;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Manuela
 */
public class PersonasJpaController implements Serializable {

    public PersonasJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Personas personas) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (personas.getCasoPersonaCollection() == null) {
            personas.setCasoPersonaCollection(new ArrayList<CasoPersona>());
        }
        if (personas.getPersonaempresaCollection() == null) {
            personas.setPersonaempresaCollection(new ArrayList<Personaempresa>());
        }
        if (personas.getUsuarioPersonaCollection() == null) {
            personas.setUsuarioPersonaCollection(new ArrayList<UsuarioPersona>());
        }
        if (personas.getPersonasCollection() == null) {
            personas.setPersonasCollection(new ArrayList<Personas>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
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
            Cargo codigocargoFk = personas.getCodigocargoFk();
            if (codigocargoFk != null) {
                codigocargoFk = em.getReference(codigocargoFk.getClass(), codigocargoFk.getCodigocargo());
                personas.setCodigocargoFk(codigocargoFk);
            }
            Eps codigoepsFk = personas.getCodigoepsFk();
            if (codigoepsFk != null) {
                codigoepsFk = em.getReference(codigoepsFk.getClass(), codigoepsFk.getCodigoeps());
                personas.setCodigoepsFk(codigoepsFk);
            }
            Profesion codigoprofesionFk = personas.getCodigoprofesionFk();
            if (codigoprofesionFk != null) {
                codigoprofesionFk = em.getReference(codigoprofesionFk.getClass(), codigoprofesionFk.getCodigoprofesion());
                personas.setCodigoprofesionFk(codigoprofesionFk);
            }
            Personas recomendadoFk = personas.getRecomendadoFk();
            if (recomendadoFk != null) {
                recomendadoFk = em.getReference(recomendadoFk.getClass(), recomendadoFk.getCedula());
                personas.setRecomendadoFk(recomendadoFk);
            }
            Collection<CasoPersona> attachedCasoPersonaCollection = new ArrayList<CasoPersona>();
            for (CasoPersona casoPersonaCollectionCasoPersonaToAttach : personas.getCasoPersonaCollection()) {
                casoPersonaCollectionCasoPersonaToAttach = em.getReference(casoPersonaCollectionCasoPersonaToAttach.getClass(), casoPersonaCollectionCasoPersonaToAttach.getIdCasoPersona());
                attachedCasoPersonaCollection.add(casoPersonaCollectionCasoPersonaToAttach);
            }
            personas.setCasoPersonaCollection(attachedCasoPersonaCollection);
            Collection<Personaempresa> attachedPersonaempresaCollection = new ArrayList<Personaempresa>();
            for (Personaempresa personaempresaCollectionPersonaempresaToAttach : personas.getPersonaempresaCollection()) {
                personaempresaCollectionPersonaempresaToAttach = em.getReference(personaempresaCollectionPersonaempresaToAttach.getClass(), personaempresaCollectionPersonaempresaToAttach.getId());
                attachedPersonaempresaCollection.add(personaempresaCollectionPersonaempresaToAttach);
            }
            personas.setPersonaempresaCollection(attachedPersonaempresaCollection);
            Collection<UsuarioPersona> attachedUsuarioPersonaCollection = new ArrayList<UsuarioPersona>();
            for (UsuarioPersona usuarioPersonaCollectionUsuarioPersonaToAttach : personas.getUsuarioPersonaCollection()) {
                usuarioPersonaCollectionUsuarioPersonaToAttach = em.getReference(usuarioPersonaCollectionUsuarioPersonaToAttach.getClass(), usuarioPersonaCollectionUsuarioPersonaToAttach.getIdUsuarioPersona());
                attachedUsuarioPersonaCollection.add(usuarioPersonaCollectionUsuarioPersonaToAttach);
            }
            personas.setUsuarioPersonaCollection(attachedUsuarioPersonaCollection);
            Collection<Personas> attachedPersonasCollection = new ArrayList<Personas>();
            for (Personas personasCollectionPersonasToAttach : personas.getPersonasCollection()) {
                personasCollectionPersonasToAttach = em.getReference(personasCollectionPersonasToAttach.getClass(), personasCollectionPersonasToAttach.getCedula());
                attachedPersonasCollection.add(personasCollectionPersonasToAttach);
            }
            personas.setPersonasCollection(attachedPersonasCollection);
            em.persist(personas);
            if (codigoafpFk != null) {
                codigoafpFk.getPersonasCollection().add(personas);
                codigoafpFk = em.merge(codigoafpFk);
            }
            if (codigoarlFk != null) {
                codigoarlFk.getPersonasCollection().add(personas);
                codigoarlFk = em.merge(codigoarlFk);
            }
            if (codigocargoFk != null) {
                codigocargoFk.getPersonasCollection().add(personas);
                codigocargoFk = em.merge(codigocargoFk);
            }
            if (codigoepsFk != null) {
                codigoepsFk.getPersonasCollection().add(personas);
                codigoepsFk = em.merge(codigoepsFk);
            }
            if (codigoprofesionFk != null) {
                codigoprofesionFk.getPersonasCollection().add(personas);
                codigoprofesionFk = em.merge(codigoprofesionFk);
            }
            if (recomendadoFk != null) {
                recomendadoFk.getPersonasCollection().add(personas);
                recomendadoFk = em.merge(recomendadoFk);
            }
            for (CasoPersona casoPersonaCollectionCasoPersona : personas.getCasoPersonaCollection()) {
                Personas oldPersonasCedulaOfCasoPersonaCollectionCasoPersona = casoPersonaCollectionCasoPersona.getPersonasCedula();
                casoPersonaCollectionCasoPersona.setPersonasCedula(personas);
                casoPersonaCollectionCasoPersona = em.merge(casoPersonaCollectionCasoPersona);
                if (oldPersonasCedulaOfCasoPersonaCollectionCasoPersona != null) {
                    oldPersonasCedulaOfCasoPersonaCollectionCasoPersona.getCasoPersonaCollection().remove(casoPersonaCollectionCasoPersona);
                    oldPersonasCedulaOfCasoPersonaCollectionCasoPersona = em.merge(oldPersonasCedulaOfCasoPersonaCollectionCasoPersona);
                }
            }
            for (Personaempresa personaempresaCollectionPersonaempresa : personas.getPersonaempresaCollection()) {
                Personas oldCedulaFkOfPersonaempresaCollectionPersonaempresa = personaempresaCollectionPersonaempresa.getCedulaFk();
                personaempresaCollectionPersonaempresa.setCedulaFk(personas);
                personaempresaCollectionPersonaempresa = em.merge(personaempresaCollectionPersonaempresa);
                if (oldCedulaFkOfPersonaempresaCollectionPersonaempresa != null) {
                    oldCedulaFkOfPersonaempresaCollectionPersonaempresa.getPersonaempresaCollection().remove(personaempresaCollectionPersonaempresa);
                    oldCedulaFkOfPersonaempresaCollectionPersonaempresa = em.merge(oldCedulaFkOfPersonaempresaCollectionPersonaempresa);
                }
            }
            for (UsuarioPersona usuarioPersonaCollectionUsuarioPersona : personas.getUsuarioPersonaCollection()) {
                Personas oldPersonasCedulaOfUsuarioPersonaCollectionUsuarioPersona = usuarioPersonaCollectionUsuarioPersona.getPersonasCedula();
                usuarioPersonaCollectionUsuarioPersona.setPersonasCedula(personas);
                usuarioPersonaCollectionUsuarioPersona = em.merge(usuarioPersonaCollectionUsuarioPersona);
                if (oldPersonasCedulaOfUsuarioPersonaCollectionUsuarioPersona != null) {
                    oldPersonasCedulaOfUsuarioPersonaCollectionUsuarioPersona.getUsuarioPersonaCollection().remove(usuarioPersonaCollectionUsuarioPersona);
                    oldPersonasCedulaOfUsuarioPersonaCollectionUsuarioPersona = em.merge(oldPersonasCedulaOfUsuarioPersonaCollectionUsuarioPersona);
                }
            }
            for (Personas personasCollectionPersonas : personas.getPersonasCollection()) {
                Personas oldRecomendadoFkOfPersonasCollectionPersonas = personasCollectionPersonas.getRecomendadoFk();
                personasCollectionPersonas.setRecomendadoFk(personas);
                personasCollectionPersonas = em.merge(personasCollectionPersonas);
                if (oldRecomendadoFkOfPersonasCollectionPersonas != null) {
                    oldRecomendadoFkOfPersonasCollectionPersonas.getPersonasCollection().remove(personasCollectionPersonas);
                    oldRecomendadoFkOfPersonasCollectionPersonas = em.merge(oldRecomendadoFkOfPersonasCollectionPersonas);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
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

    public void edit(Personas personas) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Personas persistentPersonas = em.find(Personas.class, personas.getCedula());
            Afp codigoafpFkOld = persistentPersonas.getCodigoafpFk();
            Afp codigoafpFkNew = personas.getCodigoafpFk();
            Arl codigoarlFkOld = persistentPersonas.getCodigoarlFk();
            Arl codigoarlFkNew = personas.getCodigoarlFk();
            Cargo codigocargoFkOld = persistentPersonas.getCodigocargoFk();
            Cargo codigocargoFkNew = personas.getCodigocargoFk();
            Eps codigoepsFkOld = persistentPersonas.getCodigoepsFk();
            Eps codigoepsFkNew = personas.getCodigoepsFk();
            Profesion codigoprofesionFkOld = persistentPersonas.getCodigoprofesionFk();
            Profesion codigoprofesionFkNew = personas.getCodigoprofesionFk();
            Personas recomendadoFkOld = persistentPersonas.getRecomendadoFk();
            Personas recomendadoFkNew = personas.getRecomendadoFk();
            Collection<CasoPersona> casoPersonaCollectionOld = persistentPersonas.getCasoPersonaCollection();
            Collection<CasoPersona> casoPersonaCollectionNew = personas.getCasoPersonaCollection();
            Collection<Personaempresa> personaempresaCollectionOld = persistentPersonas.getPersonaempresaCollection();
            Collection<Personaempresa> personaempresaCollectionNew = personas.getPersonaempresaCollection();
            Collection<UsuarioPersona> usuarioPersonaCollectionOld = persistentPersonas.getUsuarioPersonaCollection();
            Collection<UsuarioPersona> usuarioPersonaCollectionNew = personas.getUsuarioPersonaCollection();
            Collection<Personas> personasCollectionOld = persistentPersonas.getPersonasCollection();
            Collection<Personas> personasCollectionNew = personas.getPersonasCollection();
            List<String> illegalOrphanMessages = null;
            for (CasoPersona casoPersonaCollectionOldCasoPersona : casoPersonaCollectionOld) {
                if (!casoPersonaCollectionNew.contains(casoPersonaCollectionOldCasoPersona)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CasoPersona " + casoPersonaCollectionOldCasoPersona + " since its personasCedula field is not nullable.");
                }
            }
            for (Personaempresa personaempresaCollectionOldPersonaempresa : personaempresaCollectionOld) {
                if (!personaempresaCollectionNew.contains(personaempresaCollectionOldPersonaempresa)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Personaempresa " + personaempresaCollectionOldPersonaempresa + " since its cedulaFk field is not nullable.");
                }
            }
            for (UsuarioPersona usuarioPersonaCollectionOldUsuarioPersona : usuarioPersonaCollectionOld) {
                if (!usuarioPersonaCollectionNew.contains(usuarioPersonaCollectionOldUsuarioPersona)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain UsuarioPersona " + usuarioPersonaCollectionOldUsuarioPersona + " since its personasCedula field is not nullable.");
                }
            }
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
            if (codigocargoFkNew != null) {
                codigocargoFkNew = em.getReference(codigocargoFkNew.getClass(), codigocargoFkNew.getCodigocargo());
                personas.setCodigocargoFk(codigocargoFkNew);
            }
            if (codigoepsFkNew != null) {
                codigoepsFkNew = em.getReference(codigoepsFkNew.getClass(), codigoepsFkNew.getCodigoeps());
                personas.setCodigoepsFk(codigoepsFkNew);
            }
            if (codigoprofesionFkNew != null) {
                codigoprofesionFkNew = em.getReference(codigoprofesionFkNew.getClass(), codigoprofesionFkNew.getCodigoprofesion());
                personas.setCodigoprofesionFk(codigoprofesionFkNew);
            }
            if (recomendadoFkNew != null) {
                recomendadoFkNew = em.getReference(recomendadoFkNew.getClass(), recomendadoFkNew.getCedula());
                personas.setRecomendadoFk(recomendadoFkNew);
            }
            Collection<CasoPersona> attachedCasoPersonaCollectionNew = new ArrayList<CasoPersona>();
            for (CasoPersona casoPersonaCollectionNewCasoPersonaToAttach : casoPersonaCollectionNew) {
                casoPersonaCollectionNewCasoPersonaToAttach = em.getReference(casoPersonaCollectionNewCasoPersonaToAttach.getClass(), casoPersonaCollectionNewCasoPersonaToAttach.getIdCasoPersona());
                attachedCasoPersonaCollectionNew.add(casoPersonaCollectionNewCasoPersonaToAttach);
            }
            casoPersonaCollectionNew = attachedCasoPersonaCollectionNew;
            personas.setCasoPersonaCollection(casoPersonaCollectionNew);
            Collection<Personaempresa> attachedPersonaempresaCollectionNew = new ArrayList<Personaempresa>();
            for (Personaempresa personaempresaCollectionNewPersonaempresaToAttach : personaempresaCollectionNew) {
                personaempresaCollectionNewPersonaempresaToAttach = em.getReference(personaempresaCollectionNewPersonaempresaToAttach.getClass(), personaempresaCollectionNewPersonaempresaToAttach.getId());
                attachedPersonaempresaCollectionNew.add(personaempresaCollectionNewPersonaempresaToAttach);
            }
            personaempresaCollectionNew = attachedPersonaempresaCollectionNew;
            personas.setPersonaempresaCollection(personaempresaCollectionNew);
            Collection<UsuarioPersona> attachedUsuarioPersonaCollectionNew = new ArrayList<UsuarioPersona>();
            for (UsuarioPersona usuarioPersonaCollectionNewUsuarioPersonaToAttach : usuarioPersonaCollectionNew) {
                usuarioPersonaCollectionNewUsuarioPersonaToAttach = em.getReference(usuarioPersonaCollectionNewUsuarioPersonaToAttach.getClass(), usuarioPersonaCollectionNewUsuarioPersonaToAttach.getIdUsuarioPersona());
                attachedUsuarioPersonaCollectionNew.add(usuarioPersonaCollectionNewUsuarioPersonaToAttach);
            }
            usuarioPersonaCollectionNew = attachedUsuarioPersonaCollectionNew;
            personas.setUsuarioPersonaCollection(usuarioPersonaCollectionNew);
            Collection<Personas> attachedPersonasCollectionNew = new ArrayList<Personas>();
            for (Personas personasCollectionNewPersonasToAttach : personasCollectionNew) {
                personasCollectionNewPersonasToAttach = em.getReference(personasCollectionNewPersonasToAttach.getClass(), personasCollectionNewPersonasToAttach.getCedula());
                attachedPersonasCollectionNew.add(personasCollectionNewPersonasToAttach);
            }
            personasCollectionNew = attachedPersonasCollectionNew;
            personas.setPersonasCollection(personasCollectionNew);
            personas = em.merge(personas);
            if (codigoafpFkOld != null && !codigoafpFkOld.equals(codigoafpFkNew)) {
                codigoafpFkOld.getPersonasCollection().remove(personas);
                codigoafpFkOld = em.merge(codigoafpFkOld);
            }
            if (codigoafpFkNew != null && !codigoafpFkNew.equals(codigoafpFkOld)) {
                codigoafpFkNew.getPersonasCollection().add(personas);
                codigoafpFkNew = em.merge(codigoafpFkNew);
            }
            if (codigoarlFkOld != null && !codigoarlFkOld.equals(codigoarlFkNew)) {
                codigoarlFkOld.getPersonasCollection().remove(personas);
                codigoarlFkOld = em.merge(codigoarlFkOld);
            }
            if (codigoarlFkNew != null && !codigoarlFkNew.equals(codigoarlFkOld)) {
                codigoarlFkNew.getPersonasCollection().add(personas);
                codigoarlFkNew = em.merge(codigoarlFkNew);
            }
            if (codigocargoFkOld != null && !codigocargoFkOld.equals(codigocargoFkNew)) {
                codigocargoFkOld.getPersonasCollection().remove(personas);
                codigocargoFkOld = em.merge(codigocargoFkOld);
            }
            if (codigocargoFkNew != null && !codigocargoFkNew.equals(codigocargoFkOld)) {
                codigocargoFkNew.getPersonasCollection().add(personas);
                codigocargoFkNew = em.merge(codigocargoFkNew);
            }
            if (codigoepsFkOld != null && !codigoepsFkOld.equals(codigoepsFkNew)) {
                codigoepsFkOld.getPersonasCollection().remove(personas);
                codigoepsFkOld = em.merge(codigoepsFkOld);
            }
            if (codigoepsFkNew != null && !codigoepsFkNew.equals(codigoepsFkOld)) {
                codigoepsFkNew.getPersonasCollection().add(personas);
                codigoepsFkNew = em.merge(codigoepsFkNew);
            }
            if (codigoprofesionFkOld != null && !codigoprofesionFkOld.equals(codigoprofesionFkNew)) {
                codigoprofesionFkOld.getPersonasCollection().remove(personas);
                codigoprofesionFkOld = em.merge(codigoprofesionFkOld);
            }
            if (codigoprofesionFkNew != null && !codigoprofesionFkNew.equals(codigoprofesionFkOld)) {
                codigoprofesionFkNew.getPersonasCollection().add(personas);
                codigoprofesionFkNew = em.merge(codigoprofesionFkNew);
            }
            if (recomendadoFkOld != null && !recomendadoFkOld.equals(recomendadoFkNew)) {
                recomendadoFkOld.getPersonasCollection().remove(personas);
                recomendadoFkOld = em.merge(recomendadoFkOld);
            }
            if (recomendadoFkNew != null && !recomendadoFkNew.equals(recomendadoFkOld)) {
                recomendadoFkNew.getPersonasCollection().add(personas);
                recomendadoFkNew = em.merge(recomendadoFkNew);
            }
            for (CasoPersona casoPersonaCollectionNewCasoPersona : casoPersonaCollectionNew) {
                if (!casoPersonaCollectionOld.contains(casoPersonaCollectionNewCasoPersona)) {
                    Personas oldPersonasCedulaOfCasoPersonaCollectionNewCasoPersona = casoPersonaCollectionNewCasoPersona.getPersonasCedula();
                    casoPersonaCollectionNewCasoPersona.setPersonasCedula(personas);
                    casoPersonaCollectionNewCasoPersona = em.merge(casoPersonaCollectionNewCasoPersona);
                    if (oldPersonasCedulaOfCasoPersonaCollectionNewCasoPersona != null && !oldPersonasCedulaOfCasoPersonaCollectionNewCasoPersona.equals(personas)) {
                        oldPersonasCedulaOfCasoPersonaCollectionNewCasoPersona.getCasoPersonaCollection().remove(casoPersonaCollectionNewCasoPersona);
                        oldPersonasCedulaOfCasoPersonaCollectionNewCasoPersona = em.merge(oldPersonasCedulaOfCasoPersonaCollectionNewCasoPersona);
                    }
                }
            }
            for (Personaempresa personaempresaCollectionNewPersonaempresa : personaempresaCollectionNew) {
                if (!personaempresaCollectionOld.contains(personaempresaCollectionNewPersonaempresa)) {
                    Personas oldCedulaFkOfPersonaempresaCollectionNewPersonaempresa = personaempresaCollectionNewPersonaempresa.getCedulaFk();
                    personaempresaCollectionNewPersonaempresa.setCedulaFk(personas);
                    personaempresaCollectionNewPersonaempresa = em.merge(personaempresaCollectionNewPersonaempresa);
                    if (oldCedulaFkOfPersonaempresaCollectionNewPersonaempresa != null && !oldCedulaFkOfPersonaempresaCollectionNewPersonaempresa.equals(personas)) {
                        oldCedulaFkOfPersonaempresaCollectionNewPersonaempresa.getPersonaempresaCollection().remove(personaempresaCollectionNewPersonaempresa);
                        oldCedulaFkOfPersonaempresaCollectionNewPersonaempresa = em.merge(oldCedulaFkOfPersonaempresaCollectionNewPersonaempresa);
                    }
                }
            }
            for (UsuarioPersona usuarioPersonaCollectionNewUsuarioPersona : usuarioPersonaCollectionNew) {
                if (!usuarioPersonaCollectionOld.contains(usuarioPersonaCollectionNewUsuarioPersona)) {
                    Personas oldPersonasCedulaOfUsuarioPersonaCollectionNewUsuarioPersona = usuarioPersonaCollectionNewUsuarioPersona.getPersonasCedula();
                    usuarioPersonaCollectionNewUsuarioPersona.setPersonasCedula(personas);
                    usuarioPersonaCollectionNewUsuarioPersona = em.merge(usuarioPersonaCollectionNewUsuarioPersona);
                    if (oldPersonasCedulaOfUsuarioPersonaCollectionNewUsuarioPersona != null && !oldPersonasCedulaOfUsuarioPersonaCollectionNewUsuarioPersona.equals(personas)) {
                        oldPersonasCedulaOfUsuarioPersonaCollectionNewUsuarioPersona.getUsuarioPersonaCollection().remove(usuarioPersonaCollectionNewUsuarioPersona);
                        oldPersonasCedulaOfUsuarioPersonaCollectionNewUsuarioPersona = em.merge(oldPersonasCedulaOfUsuarioPersonaCollectionNewUsuarioPersona);
                    }
                }
            }
            for (Personas personasCollectionOldPersonas : personasCollectionOld) {
                if (!personasCollectionNew.contains(personasCollectionOldPersonas)) {
                    personasCollectionOldPersonas.setRecomendadoFk(null);
                    personasCollectionOldPersonas = em.merge(personasCollectionOldPersonas);
                }
            }
            for (Personas personasCollectionNewPersonas : personasCollectionNew) {
                if (!personasCollectionOld.contains(personasCollectionNewPersonas)) {
                    Personas oldRecomendadoFkOfPersonasCollectionNewPersonas = personasCollectionNewPersonas.getRecomendadoFk();
                    personasCollectionNewPersonas.setRecomendadoFk(personas);
                    personasCollectionNewPersonas = em.merge(personasCollectionNewPersonas);
                    if (oldRecomendadoFkOfPersonasCollectionNewPersonas != null && !oldRecomendadoFkOfPersonasCollectionNewPersonas.equals(personas)) {
                        oldRecomendadoFkOfPersonasCollectionNewPersonas.getPersonasCollection().remove(personasCollectionNewPersonas);
                        oldRecomendadoFkOfPersonasCollectionNewPersonas = em.merge(oldRecomendadoFkOfPersonasCollectionNewPersonas);
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

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Personas personas;
            try {
                personas = em.getReference(Personas.class, id);
                personas.getCedula();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The personas with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<CasoPersona> casoPersonaCollectionOrphanCheck = personas.getCasoPersonaCollection();
            for (CasoPersona casoPersonaCollectionOrphanCheckCasoPersona : casoPersonaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Personas (" + personas + ") cannot be destroyed since the CasoPersona " + casoPersonaCollectionOrphanCheckCasoPersona + " in its casoPersonaCollection field has a non-nullable personasCedula field.");
            }
            Collection<Personaempresa> personaempresaCollectionOrphanCheck = personas.getPersonaempresaCollection();
            for (Personaempresa personaempresaCollectionOrphanCheckPersonaempresa : personaempresaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Personas (" + personas + ") cannot be destroyed since the Personaempresa " + personaempresaCollectionOrphanCheckPersonaempresa + " in its personaempresaCollection field has a non-nullable cedulaFk field.");
            }
            Collection<UsuarioPersona> usuarioPersonaCollectionOrphanCheck = personas.getUsuarioPersonaCollection();
            for (UsuarioPersona usuarioPersonaCollectionOrphanCheckUsuarioPersona : usuarioPersonaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Personas (" + personas + ") cannot be destroyed since the UsuarioPersona " + usuarioPersonaCollectionOrphanCheckUsuarioPersona + " in its usuarioPersonaCollection field has a non-nullable personasCedula field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Afp codigoafpFk = personas.getCodigoafpFk();
            if (codigoafpFk != null) {
                codigoafpFk.getPersonasCollection().remove(personas);
                codigoafpFk = em.merge(codigoafpFk);
            }
            Arl codigoarlFk = personas.getCodigoarlFk();
            if (codigoarlFk != null) {
                codigoarlFk.getPersonasCollection().remove(personas);
                codigoarlFk = em.merge(codigoarlFk);
            }
            Cargo codigocargoFk = personas.getCodigocargoFk();
            if (codigocargoFk != null) {
                codigocargoFk.getPersonasCollection().remove(personas);
                codigocargoFk = em.merge(codigocargoFk);
            }
            Eps codigoepsFk = personas.getCodigoepsFk();
            if (codigoepsFk != null) {
                codigoepsFk.getPersonasCollection().remove(personas);
                codigoepsFk = em.merge(codigoepsFk);
            }
            Profesion codigoprofesionFk = personas.getCodigoprofesionFk();
            if (codigoprofesionFk != null) {
                codigoprofesionFk.getPersonasCollection().remove(personas);
                codigoprofesionFk = em.merge(codigoprofesionFk);
            }
            Personas recomendadoFk = personas.getRecomendadoFk();
            if (recomendadoFk != null) {
                recomendadoFk.getPersonasCollection().remove(personas);
                recomendadoFk = em.merge(recomendadoFk);
            }
            Collection<Personas> personasCollection = personas.getPersonasCollection();
            for (Personas personasCollectionPersonas : personasCollection) {
                personasCollectionPersonas.setRecomendadoFk(null);
                personasCollectionPersonas = em.merge(personasCollectionPersonas);
            }
            em.remove(personas);
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
    
}
