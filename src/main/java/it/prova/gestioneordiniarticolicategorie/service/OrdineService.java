package it.prova.gestioneordiniarticolicategorie.service;

import java.util.List;

import it.prova.gestioneordiniarticolicategorie.dao.ordine.OrdineDAO;
import it.prova.gestioneordiniarticolicategorie.model.Categoria;
import it.prova.gestioneordiniarticolicategorie.model.Ordine;

public interface OrdineService {

	public List<Ordine> listAll() throws Exception;

	public Ordine caricaSingoloElemento(Long id) throws Exception;

	public void aggiorna(Ordine ordineInstance) throws Exception;

	public void rimuovi(Ordine ordineInstance) throws Exception;

	public void inserisciNuovo(Ordine ordineInstance) throws Exception;

	public void setOrdineDAO(OrdineDAO ordineDaoInstance);

	public List<Ordine> trovaAllOrdiniPerUnaDataCategoria(Categoria categoriaInput) throws Exception;

	public List<Categoria> trovaAllDistinctCategorieInDatoOrdine(Ordine ordineInput) throws Exception;

	public Ordine trovaSpedizionePiuRecentePerDataCategoria(Categoria categoriaInput) throws Exception;

	public List<String> trovaIndirizziDoveSerialeArticoliContiene(String contenutoNumeroSeriale) throws Exception;

}
