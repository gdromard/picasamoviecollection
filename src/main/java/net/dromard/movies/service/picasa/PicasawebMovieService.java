package net.dromard.movies.service.picasa;

import com.google.gdata.client.photos.PicasawebService;


public class PicasawebMovieService {
	private PicasawebService picasawebService = new PicasawebService("PicasaMovieCollection");
	private PicasawebAccount account;
	private PicasawebClient client;
	
	public PicasawebMovieService(final PicasawebAccount account) {
		this.account = account;
		client = new PicasawebClient(picasawebService, account.getUsername(), account.getPassword());
	}
	
	
}
