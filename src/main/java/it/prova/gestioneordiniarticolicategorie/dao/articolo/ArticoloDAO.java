package it.prova.gestioneordiniarticolicategorie.dao.articolo;

import java.util.List;

import it.prova.gestioneordiniarticolicategorie.dao.IBaseDAO;
import it.prova.gestioneordiniarticolicategorie.model.Articolo;
import it.prova.gestioneordiniarticolicategorie.model.Categoria;

public interface ArticoloDAO extends IBaseDAO<Articolo> {

	public void deleteCategoria(Long idArticolo) throws Exception;

	public Articolo findByIdFetchingCategorie(Long id);

	public long sumPricesByCategory(Categoria categoriaInput) throws Exception;

	public Long sumPricesByDestinatario(String destinatarioInput) throws Exception;

	public List<Articolo> findAllConDataSpedizioneErrata() throws Exception;
}
