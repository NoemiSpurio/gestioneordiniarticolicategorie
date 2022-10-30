package it.prova.gestioneordiniarticolicategorie.dao.ordine;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import it.prova.gestioneordiniarticolicategorie.model.Categoria;
import it.prova.gestioneordiniarticolicategorie.model.Ordine;

public class OrdineDAOImpl implements OrdineDAO {

	private EntityManager entityManager;

	@Override
	public List<Ordine> list() throws Exception {
		return entityManager.createQuery("from Ordine", Ordine.class).getResultList();
	}

	@Override
	public Ordine get(Long id) throws Exception {
		return entityManager.find(Ordine.class, id);
	}

	@Override
	public void update(Ordine ordineInstance) throws Exception {
		if (ordineInstance == null) {
			throw new Exception("Problema valore in input");
		}
		ordineInstance = entityManager.merge(ordineInstance);
	}

	@Override
	public void insert(Ordine ordineInstance) throws Exception {
		if (ordineInstance == null) {
			throw new Exception("Problema valore in input");
		}
		entityManager.persist(ordineInstance);
	}

	@Override
	public void delete(Ordine ordineInstance) throws Exception {
		if (ordineInstance == null) {
			throw new Exception("Problema valore in input");
		}
		entityManager.remove(entityManager.merge(ordineInstance));
	}

	@Override
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public List<Ordine> findAllOrdiniPerUnaDataCategoria(Categoria categoriaInput) throws Exception {
		if (categoriaInput == null)
			throw new Exception("Problema valore in input");
		TypedQuery<Ordine> query = entityManager.createQuery(
				"select distinct o from Ordine o join fetch o.articoli a join fetch a.categorie c where c.id = ?1",
				Ordine.class);
		query.setParameter(1, categoriaInput.getId());
		return query.getResultList();
	}

	@Override
	public List<Categoria> findAllDistinctCategorieInDatoOrdine(Ordine ordineInput) throws Exception {
		if (ordineInput == null)
			throw new Exception("Problema valore in input");
		TypedQuery<Categoria> query = entityManager.createQuery(
				"select distinct c from Categoria c join fetch c.articoli a where a.ordine.id = ?1", Categoria.class);
		query.setParameter(1, ordineInput.getId());
		return query.getResultList();
	}

	@Override
	public Ordine findSpedizionePiuRecentePerDataCategoria(Categoria categoriaInput) throws Exception {
		if (categoriaInput == null)
			throw new Exception("Problema valore in input");
		TypedQuery<Ordine> query = entityManager
				.createQuery("select o from Ordine o join o.articoli a join a.categorie c where c = ?1 and "
						+ "o.dataSpedizione = (select max(o.dataSpedizione) from Ordine o join o.articoli a join a.categorie c "
						+ "where c = ?2)", Ordine.class);
		query.setParameter(1, categoriaInput);
		query.setParameter(2, categoriaInput);
		return query.getResultList().get(0);
	}

	@Override
	public List<String> findIndirizziDoveSerialeArticoliContiene(String contenutoNumeroSeriale) throws Exception {
		if (contenutoNumeroSeriale == null)
			throw new Exception("Problema valore in input");
		TypedQuery<String> query = entityManager.createQuery(
				"select distinct o.indirizzoDiSpedizione from Ordine o join o.articoli a where a.numeroSeriale like ?1",
				String.class);
		String parametroSeriale = "%" + contenutoNumeroSeriale + "%";
		query.setParameter(1, parametroSeriale);
		return query.getResultList();
	}

}
