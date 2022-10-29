package it.prova.gestioneordiniarticolicategorie.service;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import it.prova.gestioneordiniarticolicategorie.dao.EntityManagerUtil;
import it.prova.gestioneordiniarticolicategorie.dao.categoria.CategoriaDAO;
import it.prova.gestioneordiniarticolicategorie.model.Articolo;
import it.prova.gestioneordiniarticolicategorie.model.Categoria;

public class CategoriaServiceImpl implements CategoriaService {
	
	private CategoriaDAO categoriaDao;

	@Override
	public List<Categoria> listAll() throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			categoriaDao.setEntityManager(entityManager);

			return categoriaDao.list();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

	@Override
	public Categoria caricaSingoloElemento(Long id) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			categoriaDao.setEntityManager(entityManager);

			return categoriaDao.get(id);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

	@Override
	public void aggiorna(Categoria categoriaInstance) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			entityManager.getTransaction().begin();

			categoriaDao.setEntityManager(entityManager);

			categoriaDao.update(categoriaInstance);

			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

	@Override
	public void rimuovi(Categoria categoriaInstance) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			entityManager.getTransaction().begin();

			categoriaDao.setEntityManager(entityManager);

			categoriaDao.deleteArticoli(categoriaInstance.getId());
			categoriaDao.delete(categoriaInstance);

			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

	@Override
	public void inserisciNuovo(Categoria categoriaInstance) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			entityManager.getTransaction().begin();

			categoriaDao.setEntityManager(entityManager);

			categoriaDao.insert(categoriaInstance);

			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

	@Override
	public void setCategoriaDAO(CategoriaDAO categoriaDAO) {
		this.categoriaDao = categoriaDAO;
	}

	@Override
	public void aggiungiArticolo(Articolo articoloInstance, Categoria categoriaInstance) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();
		try {

			entityManager.getTransaction().begin();

			categoriaDao.setEntityManager(entityManager);

			categoriaInstance = entityManager.merge(categoriaInstance);
			articoloInstance = entityManager.merge(articoloInstance);

			articoloInstance.getCategorie().add(categoriaInstance);
			categoriaInstance.getArticoli().add(articoloInstance);

			entityManager.getTransaction().commit();

		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

	@Override
	public Categoria caricaSingoloElementoEagerArticoli(Long id) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			categoriaDao.setEntityManager(entityManager);

			return categoriaDao.findByIdFetchingArticoli(id);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

	@Override
	public List<String> trovaCodiciDiCategorieInOrdiniInDatoMeseDatoAnno(Date dataInput) throws Exception {
		EntityManager entityManager = EntityManagerUtil.getEntityManager();

		try {
			categoriaDao.setEntityManager(entityManager);

			return categoriaDao.findCodiciDiCategorieInOrdiniInDatoMeseDatoAnno(dataInput);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			EntityManagerUtil.closeEntityManager(entityManager);
		}
	}

}
