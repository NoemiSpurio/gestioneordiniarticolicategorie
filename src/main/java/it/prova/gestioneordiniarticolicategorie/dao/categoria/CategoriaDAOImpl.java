package it.prova.gestioneordiniarticolicategorie.dao.categoria;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import it.prova.gestioneordiniarticolicategorie.model.Categoria;

public class CategoriaDAOImpl implements CategoriaDAO {

	private EntityManager entityManager;

	@Override
	public List<Categoria> list() throws Exception {
		return entityManager.createQuery("from Categoria", Categoria.class).getResultList();
	}

	@Override
	public Categoria get(Long id) throws Exception {
		return entityManager.find(Categoria.class, id);
	}

	@Override
	public void update(Categoria categoriaInstance) throws Exception {
		if (categoriaInstance == null) {
			throw new Exception("Problema valore in input");
		}
		categoriaInstance = entityManager.merge(categoriaInstance);
	}

	@Override
	public void insert(Categoria categoriaInstance) throws Exception {
		if (categoriaInstance == null) {
			throw new Exception("Problema valore in input");
		}
		entityManager.persist(categoriaInstance);
	}

	@Override
	public void delete(Categoria categoriaInstance) throws Exception {
		if (categoriaInstance == null) {
			throw new Exception("Problema valore in input");
		}
		entityManager.remove(entityManager.merge(categoriaInstance));
	}

	@Override
	public void deleteArticoli(Long idCategoria) throws Exception {
		entityManager.createNativeQuery("delete from articolo_categoria where categoria_id = ?1")
				.setParameter(1, idCategoria).executeUpdate();
	}

	@Override
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public Categoria findByIdFetchingArticoli(Long id) {
		TypedQuery<Categoria> query = entityManager
				.createQuery("select c FROM Categoria c left join fetch c.articoli a where c.id = ?1", Categoria.class);
		query.setParameter(1, id);
		return query.getResultList().stream().findFirst().orElse(null);
	}

	@Override
	public List<String> findCodiciDiCategorieInOrdiniInDatoMeseDatoAnno(Date dataInput) throws Exception {

		LocalDate dataConvertita = dataInput.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		int month = dataConvertita.getMonthValue();
		int year = dataConvertita.getYear();

		TypedQuery<String> query = entityManager.createQuery(
				"select distinct c.codice from Categoria c join c.articoli a join a.ordine o where month(o.dataSpedizione) = ?1 and year(o.dataSpedizione) = ?2",
				String.class);
		query.setParameter(1, month);
		query.setParameter(2, year);
		return query.getResultList();
	}

}
