package it.prova.gestioneordiniarticolicategorie.service;

public class MyServiceFactory {

	private static ArticoloService articoloServiceInstance = null;
	private static OrdineService ordineServiceInstance = null;
	private static CategoriaService categoriaServiceInstance = null;
	
	public static ArticoloService getArticoloServiceInstance() {
		if(articoloServiceInstance == null)
			articoloServiceInstance = new ArticoloServiceImpl();
		return articoloServiceInstance;
	}
	
	public static OrdineService getOrdineServiceInstance() {
		if(ordineServiceInstance == null)
			ordineServiceInstance = new OrdineServiceImpl();
		return ordineServiceInstance;
	}
	
	public static CategoriaService getCategoriaServiceInstance() {
		if(categoriaServiceInstance == null)
			categoriaServiceInstance = new CategoriaServiceImpl();
		return categoriaServiceInstance;
	}
}
