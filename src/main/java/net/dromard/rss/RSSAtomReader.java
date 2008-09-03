package net.dromard.rss;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import net.dromard.rss.atom.Atom;
import net.dromard.rss.atom.AtomEntry;
import net.dromard.rss.atom.Author;
import net.dromard.rss.atom.TypedText;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class RSSAtomReader extends RSSReader {

	private static RSSAtomReader instance = null;
	private RSSAtomReader() {}
	public static RSSAtomReader getInstance() {
		if (instance == null) {
			instance = new RSSAtomReader();
		}
		return instance;
	}

	public Atom load(final URL url) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document doc = builder.parse(url.openStream());
		Atom atom = new Atom();
		NodeList feedNodes = doc.getElementsByTagName("feed");
		for (int f = 0; f < feedNodes.getLength(); f++) {
			Element element = (Element) feedNodes.item(f);
			atom.setId(getElementValue(element, "id"));
			atom.setTitle(getTypedText(element, "title"));
			atom.setSubtitle(getTypedText(element, "subtitle"));
			atom.setUpdated(getElementValue(element, "updated"));
			atom.setAuthor(getAuthor(element));
			
			NodeList entryNodes = doc.getElementsByTagName("entry");
			for (int e = 0; e < entryNodes.getLength(); ++e) {
				element = (Element) entryNodes.item(e);
				AtomEntry entry = new AtomEntry();
				entry.setId(getElementValue(element, "id"));
				entry.setAuthor(getAuthor(element));
				entry.setUpdated(getElementValue(element, "updated"));
				entry.setPublished(getElementValue(element, "published"));
				entry.setCategory(getElementAttribute(element, "category", "term"));
				entry.setTitle(getTypedText(element, "title"));
				entry.setTitle(getTypedText(element, "subtitle"));
				entry.setSummary(getTypedText(element, "summary"));
				entry.setContent(getTypedText(element, "content"));
				atom.addEntry(entry);
			}
		}
		return atom;
	}

	private Author getAuthor(final Element element) {
		Element authorElt = (Element) element.getElementsByTagName(Author.AUTHOR).item(0);
		if (authorElt != null) {
			Author author = new Author();
			author.setName(getElementValue(authorElt, Author.NAME));
			author.setUri(getElementValue(authorElt, Author.URI));
			author.setEmail(getElementValue(authorElt, Author.EMAIL));
			return author;
		}
		return null;
	}

	private TypedText getTypedText(final Element element, final String label) {
		String value = getElementValue(element, label);
		if (value != null && value.length() > 0) {
			return new TypedText(label, getElementAttribute(element, label, "type"), value);
		}
		return null;
	}
	
	public static void main(String[] args) throws Exception {
		RSSAtomReader reader = RSSAtomReader.getInstance();
		//URL u = new File("C:/Projects/picasamoviecollection/src/main/resources/curiouscreature.xml").toURI().toURL();
		URL u = new File("C:/Projects/picasamoviecollection/src/main/resources/albums.xml").toURI().toURL();
		//URL u = new URL("http://www.curious-creature.org/feed/atom/");
		Atom atom = reader.load(u);
		System.out.println(atom.toXML());
	}
}
