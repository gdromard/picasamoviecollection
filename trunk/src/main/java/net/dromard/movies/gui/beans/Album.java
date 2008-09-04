package net.dromard.movies.gui.beans;

import java.util.List;

import net.dromard.common.xml.XmlMember;

public class Album extends XmlMember {
	protected Album() {
		super("domain");
	}

	private String name;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	public String getIcon() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<XmlMember> getChilds() {
		// TODO Auto-generated method stub
		return null;
	}
}
