package net.dromard.movies.model;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

public class Movie {
    private Integer id;
    private String title;
    private String originalTitle;
    private Integer size;
    private Integer year;
    private long length;
    private String synopsis;
    private String format;
    private String quality;
    private String provider;
    private List<String> versions = new ArrayList<String>();
    private List<String> genres = new ArrayList<String>();
    private List<String> nationalities = new ArrayList<String>();
    private List<String> directors = new ArrayList<String>();
    private List<String> cast = new ArrayList<String>();
	private Image cover;
    
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the length
	 */
	public long getLength() {
		return length;
	}
	/**
	 * @param length the length to set
	 */
	public void setLength(long length) {
		this.length = length;
	}
	/**
	 * @return the originalTitle
	 */
	public String getOriginalTitle() {
		return originalTitle;
	}
	/**
	 * @param originalTitle the originalTitle to set
	 */
	public void setOriginalTitle(String originalTitle) {
		this.originalTitle = originalTitle;
	}
	/**
	 * @return the size
	 */
	public Integer getSize() {
		return size;
	}
	/**
	 * @param size the size to set
	 */
	public void setSize(Integer size) {
		this.size = size;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the director
	 */
	public List<String> getDirectors() {
		return directors;
	}
	/**
	 * @param director the director to set
	 */
	public void setDirectors(List<String> directors) {
		this.directors = directors;
	}
	/**
	 * @param director the director to set
	 */
	public void addDirector(String director) {
		this.directors.add(director);
	}
	/**
	 * @return the synopsis
	 */
	public String getSynopsis() {
		return synopsis;
	}
	/**
	 * @param synopsis the synopsis to set
	 */
	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}
	
	/**
	 * @return the cast
	 */
	public List<String> getCast() {
		return cast;
	}
	/**
	 * @param cast the cast to set
	 */
	public void setCast(List<String> cast) {
		this.cast = cast;
	}
	/**
	 * @param cast the cast to set
	 */
	public void addCast(String cast) {
		this.cast.add(cast);
	}
	/**
	 * @param format the format to set
	 */
	public void setFormat(String format) {
		this.format = format;
	}
	/**
	 * @return the genre
	 */
	public List<String> getGenres() {
		return genres;
	}
	/**
	 * @param genres the genre to set
	 */
	public void setGenres(List<String> genres) {
		this.genres = genres;
	}
	/**
	 * @param nationality A nationality of the movie
	 */
	public void addGenre(String genre) {
		this.genres.add(genre);
	}
	/**
	 * @return the nationality
	 */
	public List<String> getNationalities() {
		return nationalities;
	}
	/**
	 * @param nationalities the nationality to set
	 */
	public void setNationalities(List<String> nationalities) {
		this.nationalities = nationalities;
	}
	/**
	 * @param nationality A nationality of the movie
	 */
	public void addNationality(String nationality) {
		this.nationalities.add(nationality);
	}
	/**
	 * @return the quality
	 */
	public String getQuality() {
		return quality;
	}
	/**
	 * @param quality the quality to set
	 */
	public void setQuality(String quality) {
		this.quality = quality;
	}
	/**
	 * @return the version (the language of the movie)
	 */
	public List<String> getVersions() {
		return versions;
	}
	/**
	 * @param versions the version to set
	 */
	public void setVersions(List<String> versions) {
		this.versions = versions;
	}
	/**
	 * @param version The movie version (language)
	 */
	public void addVersion(String version) {
		this.versions.add(version);
	}
	/**
	 * @return the year
	 */
	public Integer getYear() {
		return year;
	}
	/**
	 * @param year the year to set
	 */
	public void setYear(Integer year) {
		this.year = year;
	}
	
	/**
	 * @return the provider
	 */
	public String getProvider() {
		return provider;
	}
	/**
	 * @param provider the provider to set
	 */
	public void setProvider(String provider) {
		this.provider = provider;
	}
	/**
	 * @return the format (of the movie support)
	 */
	public String getFormat() {
		return format;
	}
	/**
	 * @return the cover image.
	 */
	public Image getCover() {
		return cover;
	}
	/**
	 * @param cover the cover image to set
	 */
	public void setCover(Image cover) {
		this.cover = cover;
	}

	/**
	 * 
	 */
	@Override
	public String toString() {
		StringBuffer s = new StringBuffer();
		s.append(id);
		s.append(", ");
		s.append(title);
		s.append(", ");
		s.append(originalTitle);
		s.append(", ");
	    s.append(format);
		s.append(", ");
	    s.append(versions);
		s.append(", ");
	    s.append(quality);
		s.append(", ");
	    s.append(genres);
		s.append(", ");
	    s.append(year);
		s.append(", ");
		s.append(size);
		s.append(", ");
		s.append(length);
		s.append(", ");
	    s.append(synopsis);
		s.append(", ");
	    s.append(nationalities);
		s.append(", ");
	    s.append(provider);
		return "[Movie: " + s.toString() + "]";
	}
}