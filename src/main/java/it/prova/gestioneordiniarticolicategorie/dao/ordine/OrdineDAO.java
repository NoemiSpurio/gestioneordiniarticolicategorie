package it.prova.gestioneordiniarticolicategorie.dao.ordine;

import java.util.List;

import it.prova.gestioneordiniarticolicategorie.dao.IBaseDAO;
import it.prova.gestioneordiniarticolicategorie.model.Categoria;
import it.prova.gestioneordiniarticolicategorie.model.Ordine;

public interface OrdineDAO extends IBaseDAO<Ordine> {

	public List<Ordine> findAllOrdiniPerUnaDataCategoria(Categoria categoriaInput) throws Exception;

	public List<Categoria> findAllDistinctCategorieInDatoOrdine(Ordine ordineInput) throws Exception;

	public Ordine findSpedizionePiuRecentePerDataCategoria(Categoria categoriaInput) throws Exception;

	public List<String> findIndirizziDoveSerialeArticoliContiene(String contenutoNumeroSeriale) throws Exception;
}
