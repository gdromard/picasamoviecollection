package net.dromard.movies.console;

import java.io.IOException;
import java.util.List;

import net.dromard.common.util.system.Console;
import net.dromard.movies.AppContext;
import net.dromard.movies.model.Movie;
import net.dromard.movies.service.moviecovers.MovieCover;

public class MovieConsole {
	private static final String[] FIRST_MENU = new String[] {
			"Movie Manager",
			"Search movie by title",
			"Create a movie",
			"Quit"
	};
	
	private static final void handleFirstMenu() throws IOException {
		String resp = null;
		while (!(resp = Console.printMenu(FIRST_MENU, "Q")).equalsIgnoreCase("Q") || resp.equals("3")) {
			try {
				if (resp.equals("1")) {
					resp = Console.ask("Movie title to search ?", null);
					List<? extends Movie> result = AppContext.getInstance().getServiceLocator().getMovieExtractorService().findByTitle(resp);
					handleMovieCoverSearch(result);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static final void handleMovieCoverSearch(List<? extends Movie> result) throws IOException {
		String[] menu = new String[result.size() + 2];
		menu[0] = "Results";
		for (int i = 0; i < result.size(); i++) {
			menu[i+1] = result.get(i).getTitle();
		}
		menu[menu.length-1] = "Quit";
		String resp;
		while (!(resp = Console.printMenu(menu, "Q")).equalsIgnoreCase("Q") || resp.equals(""+(menu.length-1))) {
			try {
				Movie movie = new Movie();
				AppContext.getInstance().getServiceLocator().getMovieExtractorService().fill(result.get(Integer.parseInt(resp)-1).getTitle(), movie);
				System.out.println(movie.toString());
				/*
				resp = Console.ask("Do you want to create this movie ?", "Yes");
				if (resp.equalsIgnoreCase("yes")) {
					getServiceLocator().getDaoLocator().getMovieDAO().persist(movie);
					return;
				}
				*/
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		handleFirstMenu();
	}
}
