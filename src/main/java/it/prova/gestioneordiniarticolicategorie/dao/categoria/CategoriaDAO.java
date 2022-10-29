package it.prova.gestioneordiniarticolicategorie.dao.categoria;

import java.util.Date;
import java.util.List;

import it.prova.gestioneordiniarticolicategorie.dao.IBaseDAO;
import it.prova.gestioneordiniarticolicategorie.model.Categoria;

public interface CategoriaDAO extends IBaseDAO<Categoria> {

	public void deleteArticoli(Long idCategoria) throws Exception;

	public Categoria findByIdFetchingArticoli(Long id);
	
	public List<String> findCodiciDiCategorieInOrdiniInDatoMeseDatoAnno(Date dataInput) throws Exception;
}
