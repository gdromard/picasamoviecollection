package net.dromard.movies.gui.details;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import net.dromard.common.swing.JForm;
import net.dromard.common.util.StringHelper;
import net.dromard.movies.model.Movie;

public class JMovieFields extends JForm {
	private static final long serialVersionUID = 5004816566925009743L;
	private Movie movie;

	public JMovieFields(final Movie movie) {
		super(5, 5);
		this.movie = movie;
		initialize();
	}
	
	private void initialize() {
	    addField("title", movie.getTitle());
	    addField("Original Title", movie.getOriginalTitle());
	    addField("Director", movie.getDirectors());
	    addField("Year", movie.getYear());
	    addField("Nationality", movie.getNationalities());
	    addField("Genre", movie.getGenres());
	    addField("Length", movie.getLength());
	    addField("Cast", movie.getCast());
	    addField("Provider", movie.getProvider());
	    addField("Synopsis", movie.getSynopsis());
	    addField("Format", movie.getFormat());
	    addField("Quality", movie.getQuality());
	    addField("Size", movie.getSize());
	    addField("Version", movie.getVersions());
	}

	private void addField(final String key, final String value) {
		if (value != null && value.length() > 50) {
			JTextArea fld = new JTextArea(value.toString());
			fld.setEditable(false);
			addLine(new JLabel(key), fld, null);
		} else {
			JTextField fld = new JTextField();
			if (value != null) fld.setText(value.toString());
			fld.setEditable(false);
			addLine(new JLabel(key), fld, null);
		}
	}

	private void addField(final String key, final Integer value) {
		JTextField fld = new JTextField();
		if (value != null) fld.setText(value.toString());
		fld.setEditable(false);
		addLine(new JLabel(key), fld, null);
	}

	private void addField(final String key, final long value) {
		JTextField fld = new JTextField(new SimpleDateFormat("HH:mm").format(new Date(value)));
		fld.setEditable(false);
		addLine(new JLabel(key), fld, null);
	}

	private void addField(final String key, final List<String> values) {
		JTextField fld = new JTextField(StringHelper.join(values, ", "));
		fld.setEditable(false);
		addLine(new JLabel(key), fld, null);
	}
}
