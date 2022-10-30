package it.prova.gestioneordiniarticolicategorie.service;

import java.util.List;

import it.prova.gestioneordiniarticolicategorie.dao.articolo.ArticoloDAO;
import it.prova.gestioneordiniarticolicategorie.model.Articolo;
import it.prova.gestioneordiniarticolicategorie.model.Categoria;

public interface ArticoloService {

	public List<Articolo> listAll() throws Exception;

	public Articolo caricaSingoloElemento(Long id) throws Exception;

	public void aggiorna(Articolo articoloInstance) throws Exception;

	public void rimuovi(Articolo articoloInstance) throws Exception;

	public void inserisciNuovo(Articolo articoloInstance) throws Exception;

	public void setArticoloDAO(ArticoloDAO articoloDAO);

	void aggiungiCategoria(Articolo articoloInstance, Categoria categoriaInstance) throws Exception;

	public Articolo caricaSingoloElementoEagerCategorie(Long id) throws Exception;

	public long sommaPricesByCategory(Categoria categoriaInput) throws Exception;

	public Long sommaPricesByDestinatario(String destinatarioInput) throws Exception;

	public List<Articolo> trovaAllConDataSpedizioneErrata() throws Exception;
}
