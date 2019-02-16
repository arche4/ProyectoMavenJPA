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
import com.co.poli.appcoiso.model.Personaempresa;
import java.util.ArrayList;
import java.util.List;
import com.co.poli.appcoiso.model.Cargo;
import com.co.poli.appcoiso.model.Empresa;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Manuela
 */
public class EmpresaJpaController implements Serializable {

    public EmpresaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Empresa empresa) throws PreexistingEntityException, Exception {
        if (empresa.getPersonaempresaList() == null) {
            empresa.setPersonaempresaList(new ArrayList<Personaempresa>());
        }
        if (empresa.getCargoList() == null) {
            empresa.setCargoList(new ArrayList<Cargo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Personaempresa> attachedPersonaempresaList = new ArrayList<Personaempresa>();
            for (Personaempresa personaempresaListPersonaempresaToAttach : empresa.getPersonaempresaList()) {
                personaempresaListPersonaempresaToAttach = em.getReference(personaempresaListPersonaempresaToAttach.getClass(), personaempresaListPersonaempresaToAttach.getId());
                attachedPersonaempresaList.add(personaempresaListPersonaempresaToAttach);
            }
            empresa.setPersonaempresaList(attachedPersonaempresaList);
            List<Cargo> attachedCargoList = new ArrayList<Cargo>();
            for (Cargo cargoListCargoToAttach : empresa.getCargoList()) {
                cargoListCargoToAttach = em.getReference(cargoListCargoToAttach.getClass(), cargoListCargoToAttach.getCodigocargo());
                attachedCargoList.add(cargoListCargoToAttach);
            }
            empresa.setCargoList(attachedCargoList);
            em.persist(empresa);
            for (Personaempresa personaempresaListPersonaempresa : empresa.getPersonaempresaList()) {
                Empresa oldNitempresaFkOfPersonaempresaListPersonaempresa = personaempresaListPersonaempresa.getNitempresaFk();
                personaempresaListPersonaempresa.setNitempresaFk(empresa);
                personaempresaListPersonaempresa = em.merge(personaempresaListPersonaempresa);
                if (oldNitempresaFkOfPersonaempresaListPersonaempresa != null) {
                    oldNitempresaFkOfPersonaempresaListPersonaempresa.getPersonaempresaList().remove(personaempresaListPersonaempresa);
                    oldNitempresaFkOfPersonaempresaListPersonaempresa = em.merge(oldNitempresaFkOfPersonaempresaListPersonaempresa);
                }
            }
            for (Cargo cargoListCargo : empresa.getCargoList()) {
                Empresa oldEmpresaNitOfCargoListCargo = cargoListCargo.getEmpresaNit();
                cargoListCargo.setEmpresaNit(empresa);
                cargoListCargo = em.merge(cargoListCargo);
                if (oldEmpresaNitOfCargoListCargo != null) {
                    oldEmpresaNitOfCargoListCargo.getCargoList().remove(cargoListCargo);
                    oldEmpresaNitOfCargoListCargo = em.merge(oldEmpresaNitOfCargoListCargo);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEmpresa(empresa.getNit()) != null) {
                throw new PreexistingEntityException("Empresa " + empresa + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Empresa empresa) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Empresa persistentEmpresa = em.find(Empresa.class, empresa.getNit());
            List<Personaempresa> personaempresaListOld = persistentEmpresa.getPersonaempresaList();
            List<Personaempresa> personaempresaListNew = empresa.getPersonaempresaList();
            List<Cargo> cargoListOld = persistentEmpresa.getCargoList();
            List<Cargo> cargoListNew = empresa.getCargoList();
            List<String> illegalOrphanMessages = null;
            for (Personaempresa personaempresaListOldPersonaempresa : personaempresaListOld) {
                if (!personaempresaListNew.contains(personaempresaListOldPersonaempresa)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Personaempresa " + personaempresaListOldPersonaempresa + " since its nitempresaFk field is not nullable.");
                }
            }
            for (Cargo cargoListOldCargo : cargoListOld) {
                if (!cargoListNew.contains(cargoListOldCargo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cargo " + cargoListOldCargo + " since its empresaNit field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Personaempresa> attachedPersonaempresaListNew = new ArrayList<Personaempresa>();
            for (Personaempresa personaempresaListNewPersonaempresaToAttach : personaempresaListNew) {
                personaempresaListNewPersonaempresaToAttach = em.getReference(personaempresaListNewPersonaempresaToAttach.getClass(), personaempresaListNewPersonaempresaToAttach.getId());
                attachedPersonaempresaListNew.add(personaempresaListNewPersonaempresaToAttach);
            }
            personaempresaListNew = attachedPersonaempresaListNew;
            empresa.setPersonaempresaList(personaempresaListNew);
            List<Cargo> attachedCargoListNew = new ArrayList<Cargo>();
            for (Cargo cargoListNewCargoToAttach : cargoListNew) {
                cargoListNewCargoToAttach = em.getReference(cargoListNewCargoToAttach.getClass(), cargoListNewCargoToAttach.getCodigocargo());
                attachedCargoListNew.add(cargoListNewCargoToAttach);
            }
            cargoListNew = attachedCargoListNew;
            empresa.setCargoList(cargoListNew);
            empresa = em.merge(empresa);
            for (Personaempresa personaempresaListNewPersonaempresa : personaempresaListNew) {
                if (!personaempresaListOld.contains(personaempresaListNewPersonaempresa)) {
                    Empresa oldNitempresaFkOfPersonaempresaListNewPersonaempresa = personaempresaListNewPersonaempresa.getNitempresaFk();
                    personaempresaListNewPersonaempresa.setNitempresaFk(empresa);
                    personaempresaListNewPersonaempresa = em.merge(personaempresaListNewPersonaempresa);
                    if (oldNitempresaFkOfPersonaempresaListNewPersonaempresa != null && !oldNitempresaFkOfPersonaempresaListNewPersonaempresa.equals(empresa)) {
                        oldNitempresaFkOfPersonaempresaListNewPersonaempresa.getPersonaempresaList().remove(personaempresaListNewPersonaempresa);
                        oldNitempresaFkOfPersonaempresaListNewPersonaempresa = em.merge(oldNitempresaFkOfPersonaempresaListNewPersonaempresa);
                    }
                }
            }
            for (Cargo cargoListNewCargo : cargoListNew) {
                if (!cargoListOld.contains(cargoListNewCargo)) {
                    Empresa oldEmpresaNitOfCargoListNewCargo = cargoListNewCargo.getEmpresaNit();
                    cargoListNewCargo.setEmpresaNit(empresa);
                    cargoListNewCargo = em.merge(cargoListNewCargo);
                    if (oldEmpresaNitOfCargoListNewCargo != null && !oldEmpresaNitOfCargoListNewCargo.equals(empresa)) {
                        oldEmpresaNitOfCargoListNewCargo.getCargoList().remove(cargoListNewCargo);
                        oldEmpresaNitOfCargoListNewCargo = em.merge(oldEmpresaNitOfCargoListNewCargo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = empresa.getNit();
                if (findEmpresa(id) == null) {
                    throw new NonexistentEntityException("The empresa with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
     public void editar(Empresa empresa) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            empresa = em.merge(empresa);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = empresa.getNit();
                if (findEmpresa(id) == null) {
                    throw new NonexistentEntityException("The empresa with id " + id + " no longer exists.");
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
            Empresa empresa;
            try {
                empresa = em.getReference(Empresa.class, id);
                empresa.getNit();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The empresa with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Personaempresa> personaempresaListOrphanCheck = empresa.getPersonaempresaList();
            for (Personaempresa personaempresaListOrphanCheckPersonaempresa : personaempresaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Empresa (" + empresa + ") cannot be destroyed since the Personaempresa " + personaempresaListOrphanCheckPersonaempresa + " in its personaempresaList field has a non-nullable nitempresaFk field.");
            }
            List<Cargo> cargoListOrphanCheck = empresa.getCargoList();
            for (Cargo cargoListOrphanCheckCargo : cargoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Empresa (" + empresa + ") cannot be destroyed since the Cargo " + cargoListOrphanCheckCargo + " in its cargoList field has a non-nullable empresaNit field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(empresa);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Empresa> findEmpresaEntities() {
        return findEmpresaEntities(true, -1, -1);
    }

    public List<Empresa> findEmpresaEntities(int maxResults, int firstResult) {
        return findEmpresaEntities(false, maxResults, firstResult);
    }

    private List<Empresa> findEmpresaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Empresa.class));
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

    public Empresa findEmpresa(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Empresa.class, id);
        } finally {
            em.close();
        }
    }

    public int getEmpresaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Empresa> rt = cq.from(Empresa.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    
    
     public Empresa validarEmpresa(String nit) {
        EntityManager em = getEntityManager();
        Empresa emp = null;
        try{
            List<Empresa> listado = findEmpresaEntities();
            for (Empresa empresa : listado) {
                if (nit.equals(empresa.getNit())) {
                    emp = new Empresa();
                    emp = empresa;
               
                }
            }
                    return emp;
                }finally {
            em.close();
        }
       }
     public String crear(Empresa empresa) {
        EntityManager em = null;
        String respuesta = "Usuario Creado";
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(empresa);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEmpresa(empresa.getNit()) != null) {
                respuesta = "Empresa ya existe";
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
