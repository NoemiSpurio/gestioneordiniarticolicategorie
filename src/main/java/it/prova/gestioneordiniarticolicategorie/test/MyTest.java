package it.prova.gestioneordiniarticolicategorie.test;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import it.prova.gestioneordiniarticolicategorie.dao.EntityManagerUtil;
import it.prova.gestioneordiniarticolicategorie.exception.OrdineConArticoliAssociatiException;
import it.prova.gestioneordiniarticolicategorie.model.Articolo;
import it.prova.gestioneordiniarticolicategorie.model.Categoria;
import it.prova.gestioneordiniarticolicategorie.model.Ordine;
import it.prova.gestioneordiniarticolicategorie.service.ArticoloService;
import it.prova.gestioneordiniarticolicategorie.service.CategoriaService;
import it.prova.gestioneordiniarticolicategorie.service.MyServiceFactory;
import it.prova.gestioneordiniarticolicategorie.service.OrdineService;

public class MyTest {

	public static void main(String[] args) {

		ArticoloService articoloServiceInstance = MyServiceFactory.getArticoloServiceInstance();
		OrdineService ordineServiceInstance = MyServiceFactory.getOrdineServiceInstance();
		CategoriaService categoriaServiceInstance = MyServiceFactory.getCategoriaServiceInstance();

		try {
			System.out.println(
					"**************************** inizio batteria di test ********************************************");
			System.out.println(
					"*************************************************************************************************");

			testInserimentoRimozioneOrdine(ordineServiceInstance);

			testAggiornamentoOrdine(ordineServiceInstance);

			testInserimentoRimozioneArticolo(ordineServiceInstance, articoloServiceInstance);

			testAggiornamentoArticolo(ordineServiceInstance, articoloServiceInstance);

			testInserimentoRimozioneCategoria(categoriaServiceInstance);

			testAggiornamentoCategoria(categoriaServiceInstance);

			testAggiungiArticoloACategoria(categoriaServiceInstance, ordineServiceInstance, articoloServiceInstance);

			testAggiungiCategoriaAdArticolo(categoriaServiceInstance, ordineServiceInstance, articoloServiceInstance);

			testRimozioneArticolo(categoriaServiceInstance, ordineServiceInstance, articoloServiceInstance);

			testRimozioneCategoria(categoriaServiceInstance, ordineServiceInstance, articoloServiceInstance);
			
			testRimozioneOrdineLegatoArticolo(ordineServiceInstance, articoloServiceInstance);
			

			System.out.println(
					"****************************** fine batteria di test ********************************************");
			System.out.println(
					"*************************************************************************************************");
		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			EntityManagerUtil.shutdown();
		}
	}

	private static void testInserimentoRimozioneOrdine(OrdineService ordineServiceInstance) throws Exception {
		System.out.println(".......testInserimentoRimozioneOrdine inizio.............");
		Ordine nuovoOrdine = new Ordine("Pinco Pallino", "Via Colombo 101", new Date());

		ordineServiceInstance.inserisciNuovo(nuovoOrdine);

		if (nuovoOrdine.getId() == null)
			throw new RuntimeException("testInserimentoRimozioneOrdine failed: inserimento non andato a buon fine.");

		ordineServiceInstance.rimuovi(nuovoOrdine);

		if (ordineServiceInstance.caricaSingoloElemento(nuovoOrdine.getId()) != null)
			throw new RuntimeException("testInserimentoRimozioneOrdine failed: rimozione non andata a buon fine.");

		System.out.println(".......testInserimentoRimozioneOrdine fine: PASSED.............");
	}

	private static void testAggiornamentoOrdine(OrdineService ordineServiceInstance) throws Exception {
		System.out.println(".......testAggiornamentoOrdine inizio.............");
		Ordine nuovoOrdine = new Ordine("Pinco Pallino", "Via Colombo 101", new Date());

		ordineServiceInstance.inserisciNuovo(nuovoOrdine);

		nuovoOrdine.setNomeDestinatario("Pluto Plutotto");
		ordineServiceInstance.aggiorna(nuovoOrdine);

		if (!ordineServiceInstance.caricaSingoloElemento(nuovoOrdine.getId()).getNomeDestinatario()
				.equals("Pluto Plutotto"))
			throw new RuntimeException("testAggiornamentoOrdine failed: aggiornamento non andato a buon fine.");

		ordineServiceInstance.rimuovi(nuovoOrdine);
		System.out.println(".......testAggiornamentoOrdine fine: PASSED.............");
	}

	private static void testInserimentoRimozioneArticolo(OrdineService ordineServiceInstance,
			ArticoloService articoloServiceInstance) throws Exception {

		System.out.println(".......testInserimentoRimozioneArticolo inizio.............");
		Ordine nuovoOrdine = new Ordine("Pinco Pallino", "Via Colombo 101", new Date());

		ordineServiceInstance.inserisciNuovo(nuovoOrdine);

		Articolo nuovoArticolo = new Articolo("Sedia", "ABC123", 50, new Date(), nuovoOrdine);

		articoloServiceInstance.inserisciNuovo(nuovoArticolo);
		if (nuovoArticolo.getId() == null)
			throw new RuntimeException("testInserimentoRimozioneArticolo failed: inserimento non andato a buon fine.");

		articoloServiceInstance.rimuovi(nuovoArticolo);
		if (articoloServiceInstance.caricaSingoloElemento(nuovoArticolo.getId()) != null)
			throw new RuntimeException("testInserimentoRimozioneArticolo failed: rimozione non andata a buon fine.");

		ordineServiceInstance.rimuovi(nuovoOrdine);
		System.out.println(".......testInserimentoRimozioneArticolo fine: PASSED.............");
	}

	private static void testAggiornamentoArticolo(OrdineService ordineServiceInstance,
			ArticoloService articoloServiceInstance) throws Exception {

		System.out.println(".......testAggiornamentoArticolo inizio.............");

		Ordine nuovoOrdine = new Ordine("Pinco Pallino", "Via Colombo 101", new Date());
		ordineServiceInstance.inserisciNuovo(nuovoOrdine);
		Articolo nuovoArticolo = new Articolo("Sedia", "ABC123", 50, new Date(), nuovoOrdine);
		articoloServiceInstance.inserisciNuovo(nuovoArticolo);

		nuovoArticolo.setDescrizione("Tavolo");
		articoloServiceInstance.aggiorna(nuovoArticolo);

		if (!articoloServiceInstance.caricaSingoloElemento(nuovoArticolo.getId()).getDescrizione().equals("Tavolo"))
			throw new RuntimeException("testAggiornamentoArticolo failed: aggiornamento non andato a buon fine.");

		articoloServiceInstance.rimuovi(nuovoArticolo);
		ordineServiceInstance.rimuovi(nuovoOrdine);
		System.out.println(".......testAggiornamentoArticolo fine: PASSED.............");
	}

	private static void testInserimentoRimozioneCategoria(CategoriaService categoriaServiceInstance) throws Exception {

		System.out.println(".......testInserimentoRimozioneCategoria inizio.............");

		Categoria nuovaCategoria = new Categoria("Oggetti per la casa", "abc123");

		categoriaServiceInstance.inserisciNuovo(nuovaCategoria);

		if (nuovaCategoria.getId() == null)
			throw new RuntimeException("testInserimentoRimozioneCategoria failed: inserimento non andato a buon fine.");

		categoriaServiceInstance.rimuovi(nuovaCategoria);

		if (categoriaServiceInstance.caricaSingoloElemento(nuovaCategoria.getId()) != null)
			throw new RuntimeException("testInserimentoRimozioneCategoria failed: rimozione non andata a buon fine.");

		System.out.println(".......testInserimentoRimozioneCategoria fine: PASSED.............");
	}

	private static void testAggiornamentoCategoria(CategoriaService categoriaServiceInstance) throws Exception {

		System.out.println(".......testAggiornamentoCategoria inizio.............");

		Categoria nuovaCategoria = new Categoria("Oggetti per la casa", "abc123");
		categoriaServiceInstance.inserisciNuovo(nuovaCategoria);

		nuovaCategoria.setCodice("qwe321");

		categoriaServiceInstance.aggiorna(nuovaCategoria);

		if (!categoriaServiceInstance.caricaSingoloElemento(nuovaCategoria.getId()).getCodice().equals("qwe321"))
			throw new RuntimeException("testAggiornamentoCategoria failed: aggiornamento non andato a buon fine.");

		categoriaServiceInstance.rimuovi(nuovaCategoria);
		System.out.println(".......testAggiornamentoCategoria fine: PASSED.............");
	}

	private static void testAggiungiArticoloACategoria(CategoriaService categoriaServiceInstance,
			OrdineService ordineServiceInstance, ArticoloService articoloServiceInstance) throws Exception {

		System.out.println(".......testAggiungiArticoloACategoria inizio.............");
		Ordine nuovoOrdine = new Ordine("Pinco Pallino", "Via Colombo 101", new Date());
		ordineServiceInstance.inserisciNuovo(nuovoOrdine);
		Articolo nuovoArticolo = new Articolo("Sedia", "ABC123", 50, new Date(), nuovoOrdine);
		articoloServiceInstance.inserisciNuovo(nuovoArticolo);
		Categoria nuovaCategoria = new Categoria("Oggetti per la casa", "abc123");
		categoriaServiceInstance.inserisciNuovo(nuovaCategoria);

		categoriaServiceInstance.aggiungiArticolo(nuovoArticolo, nuovaCategoria);
		nuovaCategoria = categoriaServiceInstance.caricaSingoloElementoEagerArticoli(nuovaCategoria.getId());

		if (nuovaCategoria.getArticoli().size() == 0)
			throw new RuntimeException("testAggiungiArticoloACategoria failed: aggiunta non andata a buon fine.");

		System.out.println(".......testAggiungiArticoloACategoria fine: PASSED.............");
	}

	private static void testAggiungiCategoriaAdArticolo(CategoriaService categoriaServiceInstance,
			OrdineService ordineServiceInstance, ArticoloService articoloServiceInstance) throws Exception {
		System.out.println(".......testAggiungiCategoriaAdArticolo inizio.............");
		Ordine nuovoOrdine = new Ordine("Pinco Pallino", "Via Colombo 101", new Date());
		ordineServiceInstance.inserisciNuovo(nuovoOrdine);
		Articolo nuovoArticolo = new Articolo("Sedia", "ABC123", 50, new Date(), nuovoOrdine);
		articoloServiceInstance.inserisciNuovo(nuovoArticolo);
		Categoria nuovaCategoria = new Categoria("Oggetti per la casa", "abc123");
		categoriaServiceInstance.inserisciNuovo(nuovaCategoria);

		articoloServiceInstance.aggiungiCategoria(nuovoArticolo, nuovaCategoria);
		nuovoArticolo = articoloServiceInstance.caricaSingoloElementoEagerCategorie(nuovoArticolo.getId());

		if (nuovoArticolo.getCategorie().size() == 0)
			throw new RuntimeException("testAggiungiCategoriaAdArticolo failed: aggiunta non andata a buon fine.");

		System.out.println(".......testAggiungiCategoriaAdArticolo fine: PASSED.............");

	}

	private static void testRimozioneArticolo(CategoriaService categoriaServiceInstance,
			OrdineService ordineServiceInstance, ArticoloService articoloServiceInstance) throws Exception {
		System.out.println(".......testRimozioneArticolo inizio.............");
		Ordine nuovoOrdine = new Ordine("Pinco Pallino", "Via Colombo 101", new Date());
		ordineServiceInstance.inserisciNuovo(nuovoOrdine);
		Articolo nuovoArticolo = new Articolo("Sedia", "ABC123", 50, new Date(), nuovoOrdine);
		articoloServiceInstance.inserisciNuovo(nuovoArticolo);
		Categoria nuovaCategoria = new Categoria("Oggetti per la casa", "abc123");
		categoriaServiceInstance.inserisciNuovo(nuovaCategoria);

		categoriaServiceInstance.aggiungiArticolo(nuovoArticolo, nuovaCategoria);

		articoloServiceInstance.rimuovi(nuovoArticolo);

		if (articoloServiceInstance.caricaSingoloElemento(nuovoArticolo.getId()) != null)
			throw new RuntimeException("testRimozioneArticolo failed: rimozione non andata a buon fine.");

		System.out.println(".......testRimozioneArticolo fine: PASSED.............");

	}

	private static void testRimozioneCategoria(CategoriaService categoriaServiceInstance,
			OrdineService ordineServiceInstance, ArticoloService articoloServiceInstance) throws Exception {
		System.out.println(".......testRimozioneCategoria inizio.............");
		Ordine nuovoOrdine = new Ordine("Pinco Pallino", "Via Colombo 101", new Date());
		ordineServiceInstance.inserisciNuovo(nuovoOrdine);
		Articolo nuovoArticolo = new Articolo("Sedia", "ABC123", 50, new Date(), nuovoOrdine);
		articoloServiceInstance.inserisciNuovo(nuovoArticolo);
		Categoria nuovaCategoria = new Categoria("Oggetti per la casa", "abc123");
		categoriaServiceInstance.inserisciNuovo(nuovaCategoria);

		categoriaServiceInstance.aggiungiArticolo(nuovoArticolo, nuovaCategoria);

		categoriaServiceInstance.rimuovi(nuovaCategoria);

		if (categoriaServiceInstance.caricaSingoloElemento(nuovaCategoria.getId()) != null)
			throw new RuntimeException("testRimozioneCategoria failed: rimozione non andata a buon fine.");

		System.out.println(".......testRimozioneCategoria fine: PASSED.............");
	}

	private static void testRimozioneOrdineLegatoArticolo(OrdineService ordineServiceInstance,
			ArticoloService articoloServiceInstance) throws Exception {
		System.out.println(".......testRimozioneOrdineLegatoArticolo inizio.............");
		Ordine nuovoOrdine = new Ordine("Pinco Pallino", "Via Colombo 101", new Date());
		ordineServiceInstance.inserisciNuovo(nuovoOrdine);
		Articolo nuovoArticolo = new Articolo("Sedia", "ABC123", 50, new Date(), nuovoOrdine);
		articoloServiceInstance.inserisciNuovo(nuovoArticolo);
		nuovoOrdine.getArticoli().add(nuovoArticolo);
		try {
			ordineServiceInstance.rimuovi(nuovoOrdine);
			throw new RuntimeException("testRimozioneOrdineLegatoArticolo failed: rimozione avvenuta.");
		} catch (OrdineConArticoliAssociatiException e) {
			
		}
		
		System.out.println(".......testRimozioneOrdineLegatoArticolo fine: PASSED.............");
	}

}
