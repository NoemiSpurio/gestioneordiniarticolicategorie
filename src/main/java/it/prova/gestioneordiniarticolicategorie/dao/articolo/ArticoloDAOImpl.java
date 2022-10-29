package it.prova.gestioneordiniarticolicategorie.dao.articolo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import it.prova.gestioneordiniarticolicategorie.model.Articolo;
import it.prova.gestioneordiniarticolicategorie.model.Categoria;

public class ArticoloDAOImpl implements ArticoloDAO {

	private EntityManager entityManager;

	@Override
	public List<Articolo> list() throws Exception {
		return entityManager.createQuery("from Articolo", Articolo.class).getResultList();
	}

	@Override
	public Articolo get(Long id) throws Exception {
		return entityManager.find(Articolo.class, id);
	}

	@Override
	public void update(Articolo articoloInstance) throws Exception {
		if (articoloInstance == null) {
			throw new Exception("Problema valore in input");
		}
		articoloInstance = entityManager.merge(articoloInstance);
	}

	@Override
	public void insert(Articolo articoloInstance) throws Exception {
		if (articoloInstance == null) {
			throw new Exception("Problema valore in input");
		}
		entityManager.persist(articoloInstance);
	}

	@Override
	public void delete(Articolo articoloInstance) throws Exception {
		if (articoloInstance == null) {
			throw new Exception("Problema valore in input");
		}
		entityManager.remove(entityManager.merge(articoloInstance));
	}

	@Override
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public void deleteCategoria(Long idArticolo) throws Exception {
		entityManager.createNativeQuery("delete from articolo_categoria where articolo_id = ?1")
				.setParameter(1, idArticolo).executeUpdate();
	}

	@Override
	public Articolo findByIdFetchingCategorie(Long id) {
		TypedQuery<Articolo> query = entityManager.createQuery(
				"from Articolo a left join fetch a.categorie left join fetch a.ordine where a.id = ?1", Articolo.class);
		query.setParameter(1, id);
		return query.getResultList().stream().findFirst().orElse(null);
	}

	@Override
	public long sumPricesByCategory(Categoria categoriaInput) throws Exception {
		TypedQuery<Long> query = entityManager
				.createQuery("select sum(a.prezzoSingolo) from Articolo a join a.categorie c where c = ?1", Long.class);
		query.setParameter(1, categoriaInput);
		return query.getSingleResult().longValue();
	}

	@Override
	public Long sumPricesByDestinatario(String destinatarioInput) throws Exception {
		TypedQuery<Long> query = entityManager.createQuery(
				"select sum(a.prezzoSingolo) from Articolo a join a.ordine o where o.nomeDestinatario = ?1",
				Long.class);
		query.setParameter(1, destinatarioInput);
		return query.getSingleResult();
	}

}
