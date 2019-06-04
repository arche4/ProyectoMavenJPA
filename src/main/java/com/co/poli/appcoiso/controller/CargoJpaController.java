/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.co.poli.appcoiso.controller;

import com.co.poli.appcoiso.controller.exceptions.IllegalOrphanException;
import com.co.poli.appcoiso.controller.exceptions.NonexistentEntityException;
import com.co.poli.appcoiso.controller.exceptions.PreexistingEntityException;
import com.co.poli.appcoiso.model.Cargo;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.co.poli.appcoiso.model.Empresa;
import com.co.poli.appcoiso.model.Personas;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Manuela
 */
public class CargoJpaController implements Serializable {

    public CargoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cargo cargo) throws PreexistingEntityException, Exception {
        if (cargo.getPersonasList() == null) {
            cargo.setPersonasList(new ArrayList<Personas>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empresa empresaNit = cargo.getEmpresaNit();
            if (empresaNit != null) {
                empresaNit = em.getReference(empresaNit.getClass(), empresaNit.getNit());
                cargo.setEmpresaNit(empresaNit);
            }
            List<Personas> attachedPersonasList = new ArrayList<Personas>();
            for (Personas personasListPersonasToAttach : cargo.getPersonasList()) {
                personasListPersonasToAttach = em.getReference(personasListPersonasToAttach.getClass(), personasListPersonasToAttach.getCedula());
                attachedPersonasList.add(personasListPersonasToAttach);
            }
            cargo.setPersonasList(attachedPersonasList);
            em.persist(cargo);
            if (empresaNit != null) {
                empresaNit.getCargoList().add(cargo);
                empresaNit = em.merge(empresaNit);
            }
            for (Personas personasListPersonas : cargo.getPersonasList()) {
                Cargo oldCargoCodigocargoOfPersonasListPersonas = personasListPersonas.getCargoCodigocargo();
                personasListPersonas.setCargoCodigocargo(cargo);
                personasListPersonas = em.merge(personasListPersonas);
                if (oldCargoCodigocargoOfPersonasListPersonas != null) {
                    oldCargoCodigocargoOfPersonasListPersonas.getPersonasList().remove(personasListPersonas);
                    oldCargoCodigocargoOfPersonasListPersonas = em.merge(oldCargoCodigocargoOfPersonasListPersonas);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCargo(cargo.getCodigocargo()) != null) {
                throw new PreexistingEntityException("Cargo " + cargo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cargo cargo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cargo persistentCargo = em.find(Cargo.class, cargo.getCodigocargo());
            Empresa empresaNitOld = persistentCargo.getEmpresaNit();
            Empresa empresaNitNew = cargo.getEmpresaNit();
            List<Personas> personasListOld = persistentCargo.getPersonasList();
            List<Personas> personasListNew = cargo.getPersonasList();
            List<String> illegalOrphanMessages = null;
            for (Personas personasListOldPersonas : personasListOld) {
                if (!personasListNew.contains(personasListOldPersonas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Personas " + personasListOldPersonas + " since its cargoCodigocargo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (empresaNitNew != null) {
                empresaNitNew = em.getReference(empresaNitNew.getClass(), empresaNitNew.getNit());
                cargo.setEmpresaNit(empresaNitNew);
            }
            List<Personas> attachedPersonasListNew = new ArrayList<Personas>();
            for (Personas personasListNewPersonasToAttach : personasListNew) {
                personasListNewPersonasToAttach = em.getReference(personasListNewPersonasToAttach.getClass(), personasListNewPersonasToAttach.getCedula());
                attachedPersonasListNew.add(personasListNewPersonasToAttach);
            }
            personasListNew = attachedPersonasListNew;
            cargo.setPersonasList(personasListNew);
            cargo = em.merge(cargo);
            if (empresaNitOld != null && !empresaNitOld.equals(empresaNitNew)) {
                empresaNitOld.getCargoList().remove(cargo);
                empresaNitOld = em.merge(empresaNitOld);
            }
            if (empresaNitNew != null && !empresaNitNew.equals(empresaNitOld)) {
                empresaNitNew.getCargoList().add(cargo);
                empresaNitNew = em.merge(empresaNitNew);
            }
            for (Personas personasListNewPersonas : personasListNew) {
                if (!personasListOld.contains(personasListNewPersonas)) {
                    Cargo oldCargoCodigocargoOfPersonasListNewPersonas = personasListNewPersonas.getCargoCodigocargo();
                    personasListNewPersonas.setCargoCodigocargo(cargo);
                    personasListNewPersonas = em.merge(personasListNewPersonas);
                    if (oldCargoCodigocargoOfPersonasListNewPersonas != null && !oldCargoCodigocargoOfPersonasListNewPersonas.equals(cargo)) {
                        oldCargoCodigocargoOfPersonasListNewPersonas.getPersonasList().remove(personasListNewPersonas);
                        oldCargoCodigocargoOfPersonasListNewPersonas = em.merge(oldCargoCodigocargoOfPersonasListNewPersonas);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = cargo.getCodigocargo();
                if (findCargo(id) == null) {
                    throw new NonexistentEntityException("The cargo with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
     public void editar(Cargo cargo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cargo persistentCargo = em.find(Cargo.class, cargo.getCodigocargo());
            Empresa empresaNitOld = persistentCargo.getEmpresaNit();
            Empresa empresaNitNew = cargo.getEmpresaNit();
            
            List<String> illegalOrphanMessages = null;
            
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (empresaNitNew != null) {
                empresaNitNew = em.getReference(empresaNitNew.getClass(), empresaNitNew.getNit());
                cargo.setEmpresaNit(empresaNitNew);
            }
           
            
            cargo = em.merge(cargo);
            if (empresaNitOld != null && !empresaNitOld.equals(empresaNitNew)) {
                empresaNitOld.getCargoList().remove(cargo);
                empresaNitOld = em.merge(empresaNitOld);
            }
            if (empresaNitNew != null && !empresaNitNew.equals(empresaNitOld)) {
                empresaNitNew.getCargoList().add(cargo);
                empresaNitNew = em.merge(empresaNitNew);
            }
            
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = cargo.getCodigocargo();
                if (findCargo(id) == null) {
                    throw new NonexistentEntityException("The cargo with id " + id + " no longer exists.");
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
            Cargo cargo;
            try {
                cargo = em.getReference(Cargo.class, id);
                cargo.getCodigocargo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cargo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Personas> personasListOrphanCheck = cargo.getPersonasList();
            for (Personas personasListOrphanCheckPersonas : personasListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cargo (" + cargo + ") cannot be destroyed since the Personas " + personasListOrphanCheckPersonas + " in its personasList field has a non-nullable cargoCodigocargo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Empresa empresaNit = cargo.getEmpresaNit();
            if (empresaNit != null) {
                empresaNit.getCargoList().remove(cargo);
                empresaNit = em.merge(empresaNit);
            }
            em.remove(cargo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cargo> findCargoEntities() {
        return findCargoEntities(true, -1, -1);
    }

    public List<Cargo> findCargoEntities(int maxResults, int firstResult) {
        return findCargoEntities(false, maxResults, firstResult);
    }

    private List<Cargo> findCargoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cargo.class));
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

    public Cargo findCargo(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cargo.class, id);
        } finally {
            em.close();
        }
    }

    public int getCargoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cargo> rt = cq.from(Cargo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
     public Cargo validarCargo(String codigoCargo) {
        EntityManager em = getEntityManager();
        Cargo ca = null;
        try{
            List<Cargo> listado = findCargoEntities();
            for (Cargo cargo : listado) {
                if (codigoCargo.equals(cargo.getCodigocargo())) {
                    ca = new Cargo();
                    ca = cargo;
               
                }
            }
                    return ca;
                }finally {
            em.close();
        }
       }
    
    public String crear(Cargo cargo) {
        EntityManager em = null;
        String respuesta = "Cargo Creado";
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(cargo);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCargo(cargo.getCodigocargo()) != null) {
                respuesta = "Cargo ya existe";
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
