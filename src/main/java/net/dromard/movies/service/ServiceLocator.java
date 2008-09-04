package net.dromard.movies.service;



public class ServiceLocator {
	private static final ServiceLocator singleton = new ServiceLocator();
	private final IMovieExtractorService movieExtractorService = new MovieCoverExtractorService();

	private ServiceLocator() {}

	/**
	 * @return the singleton
	 */
	public static ServiceLocator getInstance() {
		return singleton;
	}

	public IMovieExtractorService getMovieExtractorService() {
		return movieExtractorService;
	}
}
