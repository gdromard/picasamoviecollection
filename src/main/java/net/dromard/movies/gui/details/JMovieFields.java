package net.dromard.movies.gui.details;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTextField;

import net.dromard.common.swing.JForm;
import net.dromard.common.util.StringHelper;
import net.dromard.movies.AppConf;
import net.dromard.movies.AppConstants;
import net.dromard.movies.model.Movie;

public class JMovieFields extends JForm {
	private static final long serialVersionUID = 5004816566925009743L;
	private Movie movie;

	public JMovieFields(final Movie movie) {
		super(5, 5);
		this.movie = movie;
		setOpaque(false);
		initialize();
	}
	
	private void initialize() {
	    addField("Title", movie.getTitle());
	    addField("Original Title", movie.getOriginalTitle());
	    addField("Director", movie.getDirectors());
	    addField("Year", movie.getYear());
	    addField("Nationality", movie.getNationalities());
	    addField("Genre", movie.getGenres());
	    addField("Length", movie.getLength());
	    //addField("Cast", movie.getCast());
	    addField("Provider", movie.getProvider());
	    addField("Format", movie.getFormat());
	    addField("Quality", movie.getQuality());
	    addField("Size", movie.getSize());
	    addField("Version", movie.getVersions());
	}

	private void addField(final String key, final String value) {
		JTextField fld = createTextField();
		if (value != null) fld.setText(value.toString());
		addLine(createLabel(key), fld, null);
	}

	private void addField(final String key, final Integer value) {
		JTextField fld = createTextField();
		if (value != null) fld.setText(value.toString());
		addLine(createLabel(key), fld, null);
	}

	private void addField(final String key, final long value) {
		JTextField fld = createTextField(new SimpleDateFormat("HH:mm").format(new Date(value)));
		addLine(createLabel(key), fld, null);
	}

	private void addField(final String key, final List<String> values) {
		JTextField fld = createTextField(StringHelper.join(values, ", "));
		addLine(createLabel(key), fld, null);
	}
	
	private static JLabel createLabel(String label) {
		JLabel lbl = new JLabel(label);
		lbl.setFont(AppConf.getInstance().getPropertyAsFont(AppConstants.KEY_APPLICATION_FONT));
		lbl.setForeground(AppConf.getInstance().getPropertyAsColor(AppConstants.KEY_APPLICATION_FGCOLOR).brighter().brighter());
		return lbl;
	}
	
	private static JTextField createTextField(String value) {
		JTextField fld = createTextField();
		fld.setText(value);
		if (value.length() > 30) {
			fld.setToolTipText("<html><div style='width: 100px'>"+value+"</div></html>");
		}
		return fld;
	}
	
	private static JTextField createTextField() {
		JTextField fld = new JTextField();
		fld.setFont(AppConf.getInstance().getPropertyAsFont(AppConstants.KEY_APPLICATION_FONT));
		fld.setForeground(AppConf.getInstance().getPropertyAsColor(AppConstants.KEY_APPLICATION_FGCOLOR));
		fld.setBorder(BorderFactory.createEmptyBorder());
		fld.setEditable(false);
		fld.setSize(100, 20);
		return fld;
	}
}
