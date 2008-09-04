package net.dromard.movies.model;

import java.util.List;

public class Movie {
    private Integer id;
    private String title;
    private String originalTitle;
    private Integer size;
    private Integer year;
    private long length;
    private String synopsis;
    private String imageLink;
    private String format;
    private String version;
    private String quality;
    private String genre;
    private String nationality;
    private String provider;
    private List<String> directors;
    private List<String> cast;
    
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
	 * @return the imageLink
	 */
	public String getImageLink() {
		return imageLink;
	}
	/**
	 * @param imageLink the imageLink to set
	 */
	public void setImageLink(String imageLink) {
		this.imageLink = imageLink;
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
	public String getGenre() {
		return genre;
	}
	/**
	 * @param genre the genre to set
	 */
	public void setGenre(String genre) {
		this.genre = genre;
	}
	/**
	 * @return the nationality
	 */
	public String getNationality() {
		return nationality;
	}
	/**
	 * @param nationality the nationality to set
	 */
	public void setNationality(String nationality) {
		this.nationality = nationality;
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
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}
	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
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
	 * @return the format
	 */
	public String getFormat() {
		return format;
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
	    s.append(version);
		s.append(", ");
	    s.append(quality);
		s.append(", ");
	    s.append(genre);
		s.append(", ");
	    s.append(year);
		s.append(", ");
		s.append(size);
		s.append(", ");
		s.append(length);
		s.append(", ");
	    s.append(synopsis);
		s.append(", ");
	    s.append(imageLink);
		s.append(", ");
	    s.append(nationality);
		s.append(", ");
	    s.append(provider);
		return "[Movie: " + s.toString() + "]";
	}
}