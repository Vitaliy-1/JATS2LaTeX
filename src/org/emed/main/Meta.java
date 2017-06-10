package org.emed.main;
/**
 * @file /src/org/emed/main/Meta.java
 *
 * Copyright (c) 2017 Vitaliy Bezsheiko
 * 
 * Distributed under the GNU GPL v3.
 */
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import org.emed.classes.AffilNumbers;
import org.emed.classes.Affiliation;
import org.emed.classes.ArticleMeta;
import org.emed.classes.Author;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Meta {
	protected static ArticleMeta meta(Document document, XPath xPath)
			throws XPathExpressionException, DOMException {
		ArticleMeta articleMeta = new ArticleMeta();
		// set affiliations 
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
			if (institution != null) {
				affiliation.setInstitution(institution.getTextContent().trim());
			}
			Node city = (Node) xPath.compile("addr-line/named-content").evaluate(affiliationNode, XPathConstants.NODE);
			if (city != null) {
				affiliation.setCity(city.getTextContent().trim());
			}
			Node country = (Node) xPath.compile("country").evaluate(affiliationNode, XPathConstants.NODE);
			if (country != null) {
				affiliation.setCountry(country.getTextContent().trim());
			}
			
        	articleMeta.getInstitution().put(aff, affiliation);
        	
		}
        
        // set authors names	
        NodeList contribs = (NodeList) xPath.compile("contrib").evaluate(contribGroup, XPathConstants.NODESET);
        for (int i = 0; i < contribs.getLength(); i++) {
        	Node contrib = contribs.item(i);
        	NodeList xref = (NodeList) xPath.compile("xref[@ref-type='aff']").evaluate(contrib, XPathConstants.NODESET);
        	AffilNumbers affil = new AffilNumbers();
        	if (xref != null) {
	        	for (int j = 0; j < xref.getLength(); j++) {
	        		affil.getAffilnumbers().add(xref.item(j).getTextContent());
	        	}
        	}
        	
			Author author = new Author();
			Node surname = (Node) xPath.compile("name/surname").evaluate(contrib, XPathConstants.NODE);
			if (surname != null) {
				author.setSurname(surname.getTextContent().trim());
			}
        	Node given = (Node) xPath.compile("name/given-names").evaluate(contrib, XPathConstants.NODE);
        	if (given != null) {
        		author.setGiven(given.getTextContent().trim());
        	}
        	
        //  set authors email
        	Node corresp = (Node) xPath.compile("xref[@ref-type='corresp']").evaluate(contrib, XPathConstants.NODE);
        	NodeList emailNodes = (NodeList) xPath.compile("/article/front/article-meta/author-notes/corresp").evaluate(document, XPathConstants.NODESET);
        	if (corresp != null && corresp.getAttributes().getNamedItem("rid") != null) {
	        	for (int y = 0; y < emailNodes.getLength(); y++) {
	        		Node emailNode = (Node) xPath.compile("email").evaluate(emailNodes.item(y), XPathConstants.NODE);
	        		if (emailNode != null && (corresp.getAttributes().getNamedItem("rid").getNodeValue().equals(emailNodes.item(y).getAttributes().getNamedItem("id").getNodeValue()))) {
	        			author.setEmail(emailNode.getTextContent().trim());
	        		}	
	        	}
        	}
        	articleMeta.getName().put(affil, author);
        }
        
        // set English abstract
        Node abstractNode = (Node) xPath.compile("/article/front/article-meta/abstract").evaluate(document, XPathConstants.NODE);
        if (abstractNode != null && abstractNode.getAttributes() != null && abstractNode.getAttributes().getNamedItem("abstract-type") != null && abstractNode.getAttributes().getNamedItem("abstract-type").getNodeValue().equals("section")) {
        	NodeList abstractSecs = (NodeList) xPath.compile("sec").evaluate(abstractNode, XPathConstants.NODESET);
        	for (int i = 0; i < abstractSecs.getLength(); i++) {
        		Node titleNode = (Node) xPath.compile("title").evaluate(abstractSecs.item(i), XPathConstants.NODE);
        		Node pNode = (Node) xPath.compile("p").evaluate(abstractSecs.item(i), XPathConstants.NODE);
        		articleMeta.getAbstractEng().put(titleNode.getTextContent(), pNode.getTextContent().trim());
        	}
        } else if ((abstractNode != null) && (abstractNode.getAttributes() == null || abstractNode.getAttributes().getNamedItem("abstract-type").getNodeValue().equals("short"))) {
        	Node abstractShort = (Node) xPath.compile("p|sec/p").evaluate(abstractNode, XPathConstants.NODE);
        	try {
				articleMeta.getAbstractEng().put(null, abstractShort.getTextContent().trim());
			} catch (Exception e) {
				System.err.println("Cannot find abstract. Error code for developers: " + e);
			}
        }
        
        
        
        //set Translated abstract
        Node abstractTransNode = (Node) xPath.compile("/article/front/article-meta/trans-abstract").evaluate(document, XPathConstants.NODE);
        if (abstractTransNode != null && abstractTransNode.getAttributes() !=null && abstractTransNode.getAttributes().getNamedItem("abstract-type") !=null && abstractTransNode.getAttributes().getNamedItem("abstract-type").getNodeValue().equals("section")) {
        	NodeList abstractSecs = (NodeList) xPath.compile("sec").evaluate(abstractTransNode, XPathConstants.NODESET);
        	for (int i = 0; i < abstractSecs.getLength(); i++) {
        		Node titleNode = (Node) xPath.compile("title").evaluate(abstractSecs.item(i), XPathConstants.NODE);
        		Node pNode = (Node) xPath.compile("p").evaluate(abstractSecs.item(i), XPathConstants.NODE);
        		articleMeta.getAbstractUkr().put(titleNode.getTextContent(), pNode.getTextContent().trim());
        	}
        } else if ((abstractTransNode != null) && (abstractTransNode.getAttributes() != null) && (abstractTransNode.getAttributes().getNamedItem("abstract-type") == null || abstractTransNode.getAttributes().getNamedItem("abstract-type").getNodeValue().equals("short"))) {
        	Node abstractShort = (Node) xPath.compile("p").evaluate(abstractTransNode, XPathConstants.NODE);
        	articleMeta.getAbstractUkr().put(null, abstractShort.getTextContent().trim());
        }
        
        // set Journal title
        Node journalNameNode = (Node) xPath.compile("/article/front/journal-meta/journal-id[@journal-id-type='publisher']").evaluate(document, XPathConstants.NODE);
        if (journalNameNode != null) {
        	articleMeta.setJournal(journalNameNode.getTextContent().trim());
        }
        // set article title
        NodeList articleTitle = (NodeList) xPath.compile("/article/front/article-meta/title-group/article-title").evaluate(document, XPathConstants.NODESET);
        for (int i = 0; i < articleTitle.getLength(); i++) {
        	if (articleTitle.item(i) != null && articleTitle.item(i).getAttributes().getNamedItem("xml:lang") != null) {
        		articleMeta.setTitleUkr(articleTitle.item(i).getTextContent());
        	} else {
        		articleMeta.setTitleEng(articleTitle.item(i).getTextContent());
        	}
        }
        
        // set article volume
        try {
			Node volume = (Node) xPath.compile("/article/front/article-meta/volume").evaluate(document, XPathConstants.NODE);
			if (volume != null) {
				articleMeta.setVolume(Integer.parseInt(volume.getTextContent()));		
			}
			Node issue = (Node) xPath.compile("/article/front/article-meta/issue").evaluate(document, XPathConstants.NODE);
			if (issue != null) {
				articleMeta.setIssue(Integer.parseInt(issue.getTextContent()));
			}
		} catch (NumberFormatException e) {
			System.err.println("Error: volume and issue must be numbers");
			e.printStackTrace();
		}
        
        //set article number
        Node electronicNumber = (Node) xPath.compile("/article/front/article-meta/elocation-id").evaluate(document, XPathConstants.NODE);
        if (electronicNumber != null) {
        	articleMeta.setId(electronicNumber.getTextContent());
        }
        
        //set article year
        try {
			Node year = (Node) xPath.compile("/article/front/article-meta/pub-date/year").evaluate(document, XPathConstants.NODE);
			if (year != null) {
				articleMeta.setYear(Integer.parseInt(year.getTextContent()));
			}
		} catch (NumberFormatException e) {
			System.err.println("Error: year must be a valid number");
			e.printStackTrace();
		}
        
        // set article month
        try {
			Node month = (Node) xPath.compile("/article/front/article-meta/pub-date/month").evaluate(document, XPathConstants.NODE);
			if (month != null) {
				String monthString;
				switch (Integer.parseInt(month.getTextContent())) {
			    	case 1:  monthString = "January";
			        		 break;
				    case 2:  monthString = "February";
				             break;
				    case 3:  monthString = "March";
				             break;
				    case 4:  monthString = "April";
				             break;
				    case 5:  monthString = "May";
				             break;
				    case 6:  monthString = "June";
				             break;
				    case 7:  monthString = "July";
				             break;
				    case 8:  monthString = "August";
				             break;
				    case 9:  monthString = "September";
				             break;
				    case 10: monthString = "October";
				             break;
				    case 11: monthString = "November";
				             break;
				    case 12: monthString = "December";
				             break;
				    default: monthString = "Invalid month";
				            break;
				}
				articleMeta.setMonth(monthString);
			}
		} catch (NumberFormatException e) {
			System.err.println("Month should be a valid number from 1 to 12");
			e.printStackTrace();
		}
		
        // set article month Ukrainian
        try {
			Node month = (Node) xPath.compile("/article/front/article-meta/pub-date/month").evaluate(document, XPathConstants.NODE);
			if (month != null) {
				String monthString;
				switch (Integer.parseInt(month.getTextContent())) {
			    	case 1:  monthString = "Січень";
			        		 break;
				    case 2:  monthString = "Лютий";
				             break;
				    case 3:  monthString = "Березень";
				             break;
				    case 4:  monthString = "Квітень";
				             break;
				    case 5:  monthString = "Травень";
				             break;
				    case 6:  monthString = "Червень";
				             break;
				    case 7:  monthString = "Липень";
				             break;
				    case 8:  monthString = "Серпень";
				             break;
				    case 9:  monthString = "Вересень";
				             break;
				    case 10: monthString = "Жовтень";
				             break;
				    case 11: monthString = "Листопад";
				             break;
				    case 12: monthString = "Грудень";
				             break;
				    default: monthString = "Invalid month";
				            break;
				}
				articleMeta.setMonthUkr(monthString);
			}
		} catch (NumberFormatException e) {
			System.err.println("Month should be a valid number from 1 to 12");
			e.printStackTrace();
		}
        
        // set keywords
        NodeList keywords = (NodeList) xPath.compile("/article/front/article-meta/kwd-group/kwd").evaluate(document, XPathConstants.NODESET);
        for (int i = 0; i < keywords.getLength(); i++) {
        	articleMeta.getKeywords().add(keywords.item(i).getTextContent());
        }
        
        //set udc
        Node udc = (Node) xPath.compile("/article/front/article-meta/article-id[@pub-id-type='other']").evaluate(document, XPathConstants.NODE);
        if (udc != null) {
        	articleMeta.setUdc(udc.getTextContent());
        }
        // set doi
        Node doi = (Node) xPath.compile("/article/front/article-meta/article-id[@pub-id-type='doi']").evaluate(document, XPathConstants.NODE);
        if (doi != null) {
        	articleMeta.setDoi(doi.getTextContent());
        }
		return articleMeta;
	}	
}
