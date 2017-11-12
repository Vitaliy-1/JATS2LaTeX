package org.emed.classes;

import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Node;



public class Section {
	
	private String title;
	private String type;
	private List<Section> seccontent;

	public String getTitle() {
		return title;
	}

	public void setTitle(XPath xPath, Node nodeSection) throws XPathExpressionException {
		Node nodeSectionTitle = (Node) xPath.compile("title").evaluate(nodeSection,  XPathConstants.NODE);
		String title = nodeSectionTitle.getTextContent();
		this.title = title;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Section> getSecContent() {
		if (seccontent == null) {
			seccontent = new ArrayList<Section> ();
		}
		 return this.seccontent;
	}
	
	public void setSecContent(ArrayList<Section> seccontent) {
		if (this.seccontent == null) {
			this.seccontent = seccontent;
		} 
	}
	
}