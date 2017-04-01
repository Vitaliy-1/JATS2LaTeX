package org.emed.main;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import org.emed.classes.Affiliation;
import org.emed.classes.ArticleMeta;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Meta {
	public static ArticleMeta meta(Document document, XPath xPath) throws XPathExpressionException, DOMException {
		ArticleMeta articleMeta = new ArticleMeta();
		Node contribGroup = (Node) xPath.compile("/article/front/article-meta/contrib-group").evaluate(document, XPathConstants.NODE);
		NodeList affiliations = (NodeList) xPath.compile("aff").evaluate(contribGroup, XPathConstants.NODESET);
        for (int i = 0; i < affiliations.getLength(); i++) {
        	Node affiliationNode = affiliations.item(i);
        	int aff = 0;
			try {
				aff = Integer.parseInt(affiliationNode.getAttributes().getNamedItem("id").getNodeValue().replaceAll("aff", ""));
			} catch (NumberFormatException e) {
				System.err.println("'id' attribute of 'aff' tag inside 'contrib-group' must have format as aff + number, for example 'aff1'. Error code for developers: " + e);
			}
			Affiliation affiliation = new Affiliation();
			Node institution = (Node) xPath.compile("institution").evaluate(affiliationNode, XPathConstants.NODE);
			affiliation.setInstitution(institution.getTextContent());
			Node city = (Node) xPath.compile("addr-line/named-content").evaluate(affiliationNode, XPathConstants.NODE);
			affiliation.setCity(city.getTextContent());
			Node country = (Node) xPath.compile("country").evaluate(affiliationNode, XPathConstants.NODE);
			affiliation.setCountry(country.getTextContent());
			
        	articleMeta.getInstitution().put(aff, affiliation);
		}
		return articleMeta;
	}
}
