package it.prova.gestioneordiniarticolicategorie.service;

import java.util.Date;
import java.util.List;

import it.prova.gestioneordiniarticolicategorie.dao.categoria.CategoriaDAO;
import it.prova.gestioneordiniarticolicategorie.model.Articolo;
import it.prova.gestioneordiniarticolicategorie.model.Categoria;

public interface CategoriaService {

	public List<Categoria> listAll() throws Exception;

	public Categoria caricaSingoloElemento(Long id) throws Exception;

	public void aggiorna(Categoria categoriaInstance) throws Exception;

	public void rimuovi(Categoria categoriaInstance) throws Exception;

	public void inserisciNuovo(Categoria categoriaInstance) throws Exception;

	public void setCategoriaDAO(CategoriaDAO categoriaDAO);

	public void aggiungiArticolo(Articolo articoloInstance, Categoria categoriaInstance) throws Exception;

	public Categoria caricaSingoloElementoEagerArticoli(Long id) throws Exception;

	public List<String> trovaCodiciDiCategorieInOrdiniInDatoMeseDatoAnno(Date dataInput) throws Exception;

}
