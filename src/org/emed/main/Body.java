package org.emed.main;

import java.util.ArrayList;
import java.util.List;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import org.emed.classes.Italic;
import org.emed.classes.ItemList;
import org.emed.classes.Markup;
import org.emed.classes.Par;
import org.emed.classes.ParContent;
import org.emed.classes.Section;
import org.emed.classes.SubSec;
import org.emed.classes.Xref;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Body {
	
	public static List<Section> body(Document document, XPath xPath) throws XPathExpressionException, DOMException {
		NodeList nodeSections = (NodeList) xPath.compile("/article/body/sec").evaluate(document, XPathConstants.NODESET);
		List<Section> listSections = new ArrayList<Section>();
		
		for (int i = 0; i < nodeSections.getLength(); i++) {
			Node nodeSection = nodeSections.item(i);
			Node nodeSectionTitle = (Node) xPath.compile("title").evaluate(nodeSection,  XPathConstants.NODE);
			Section section = new Section();
			listSections.add(section);
			listSections.get(i).setTitle(nodeSectionTitle.getTextContent());
			NodeList nodeSecElements = (NodeList) xPath.compile("p|fig|sec|table-wrap|list").evaluate(nodeSection, XPathConstants.NODESET);
			for (int i1 = 0; i1 < nodeSecElements.getLength(); i1++) {
				
				/* First Level of section nodes*/
				Node nodeSecElement = nodeSecElements.item(i1);
				if ((nodeSecElement.getNodeName() == "p") && (nodeSecElement.getTextContent() != null)) {
					ParContent parContent = new ParContent();
					parContent.setTitle("");
					section.getSecContent().add(parContent);
					NodeList insidePars = nodeSecElement.getChildNodes();
					for (int j = 0; j < insidePars.getLength(); j++) {
						Node insidePar = insidePars.item(j);
						
						if (insidePar.getNodeValue() != null) {
							Par par = new Par();
							par.setContent(insidePar.getNodeValue());
							parContent.getParContentList().add(par);
						} else if (insidePar.getNodeName() == "italic") {
							Italic italic = new Italic();
							italic.setContent(insidePar.getTextContent());					
							parContent.getParContentList().add(italic);
							
						} else if (insidePar.getNodeName() == "xref") {
							  if (insidePar.getAttributes().getNamedItem("ref-type").toString().contains("bibr")) {
								  Xref xref = new Xref();
								  xref.setBibContent(insidePar.getTextContent().trim());
								  parContent.getParContentList().add(xref);
					             
							  } else if (insidePar.getAttributes().getNamedItem("ref-type").toString().contains("fig")) {
								  Xref xref = new Xref();
								  String xrefFig = insidePar.getAttributes().getNamedItem("rid").toString().replaceAll("rid=\"|\"", "");
								  String figDescription = insidePar.getTextContent().trim();
								  xref.setFigDescription(figDescription);
								  xref.setFigContent(xrefFig);
								  parContent.getParContentList().add(xref);
								
							  } else if (insidePar.getAttributes().getNamedItem("ref-type").toString().contains("table")) {
								  Xref xref = new Xref();
								  String TableDescription = insidePar.getTextContent();
								  String xrefTable = insidePar.getAttributes().getNamedItem("rid").toString().replaceAll("rid=\"|\"", "");
								  xref.setTableDescription(TableDescription);
								  xref.setTableContent(xrefTable);
								  parContent.getParContentList().add(xref);
							  }
						}
					}
					//section.getSecContent().add(par);
					
					
				} else if((nodeSecElement.getNodeName() == "list") && (nodeSecElement.getAttributes().getNamedItem("list-type").getTextContent().contains("unordered"))) {
					NodeList nodeSubSecListItems = (NodeList) xPath.compile("list-item/p").evaluate(nodeSecElement, XPathConstants.NODESET);
					Markup markup1 = new Markup();
					markup1.setContent("\\begin{itemize}");
					section.getSecContent().add(markup1);
					for (int i111=0; i111 < nodeSubSecListItems.getLength(); i111++) {
						Node nodeSubSecListItem = nodeSubSecListItems.item(i111);
						ItemList itemList = new ItemList();
						itemList.setListContent(nodeSubSecListItem.getTextContent());
						section.getSecContent().add(itemList);
						
					}
					Markup markup2 = new Markup();
					markup2.setContent("\\end{itemize}");
					section.getSecContent().add(markup2);
				} else if ((nodeSecElement.getNodeName() == "list") && (nodeSecElement.getAttributes().getNamedItem("list-type").getTextContent().contains("ordered"))) {
					NodeList nodeSubSecListItems = (NodeList) xPath.compile("list-item/p").evaluate(nodeSecElement, XPathConstants.NODESET);
					Markup markup1 = new Markup();
					markup1.setContent("\\begin{enumerate}");
					section.getSecContent().add(markup1);
					for (int i111=0; i111 < nodeSubSecListItems.getLength(); i111++) {
						Node nodeSubSecListItem = nodeSubSecListItems.item(i111);
						ItemList itemList = new ItemList();
						itemList.setListContent(nodeSubSecListItem.getTextContent());
						section.getSecContent().add(itemList);
						
					}
					Markup markup2 = new Markup();
					markup2.setContent("\\end{enumerate}");
					section.getSecContent().add(markup2);
					/* Second level - subsections*/
				} else if ((nodeSecElement.getNodeName() == "sec") && (nodeSecElement.getTextContent() != null)) {
	
					subSection(xPath, section, nodeSecElement);  
				}
			}
		} // end of list sections
		return listSections;
	}

	private static void subSection(XPath xPath, Section section, Node nodeSecElement)
			throws XPathExpressionException, DOMException {
		SubSec subSec = new SubSec();
		Node nodeSubSecTitle = (Node) xPath.compile("title").evaluate(nodeSecElement, XPathConstants.NODE);
		subSec.setTitle(nodeSubSecTitle.getTextContent());
		section.getSecContent().add(subSec);
		
		
		NodeList nodeSubSecElements = (NodeList) xPath.compile("p|fig|table-wrap|list").evaluate(nodeSecElement, XPathConstants.NODESET);
		for (int i11 = 0; i11 < nodeSubSecElements.getLength(); i11++) {
			Node nodeSubSecElement = nodeSubSecElements.item(i11);
			if ((nodeSubSecElements.item(i11).getNodeName() == "p") && (nodeSubSecElements.item(i11)) != null) {
				ParContent parContent = new ParContent();
				parContent.setTitle("");
				subSec.getSecContent().add(parContent);
				
				
				NodeList insidePars = nodeSubSecElement.getChildNodes();
				for (int j = 0; j < insidePars.getLength(); j++) {
					Node insidePar = insidePars.item(j);
					
					if (insidePar.getNodeValue() != null) {
						Par par = new Par();
						par.setContent(insidePar.getNodeValue());
						parContent.getParContentList().add(par);
						
					} else if (insidePar.getNodeName() == "italic") {
						Italic italic = new Italic();
						italic.setContent(insidePar.getTextContent());
						
						parContent.getParContentList().add(italic);
						
					} else if (insidePar.getNodeName() == "xref") {
						if (insidePar.getAttributes().getNamedItem("ref-type").toString().contains("bibr")) {
							  Xref xref = new Xref();
							  xref.setBibContent(insidePar.getTextContent().trim());
							  
							  parContent.getParContentList().add(xref);
							 
				             
						  } else if (insidePar.getAttributes().getNamedItem("ref-type").toString().contains("fig")) {
							  Xref xref = new Xref();
							  String xrefFig = insidePar.getAttributes().getNamedItem("rid").toString().replaceAll("rid=\"|\"", "");
							  String figDescription = insidePar.getTextContent().trim();
							  xref.setFigDescription(figDescription);
							  xref.setFigContent(xrefFig);
							  parContent.getParContentList().add(xref);
							 
						  } else if (insidePar.getAttributes().getNamedItem("ref-type").toString().contains("table")) {
							  Xref xref = new Xref();
							  String tableDescription = insidePar.getTextContent();
							  String xrefTable = insidePar.getAttributes().getNamedItem("rid").toString().replaceAll("rid=\"|\"", "");
							  xref.setTableDescription(tableDescription);
							  xref.setTableContent(xrefTable);
							  parContent.getParContentList().add(xref);
							 
						  }
					}
				}
			} else if ((nodeSubSecElement.getNodeName() == "list") && (nodeSubSecElement.getAttributes().getNamedItem("list-type").getTextContent().contains("unordered"))) {
				NodeList nodeSubSecListItems = (NodeList) xPath.compile("list-item/p").evaluate(nodeSubSecElement, XPathConstants.NODESET);
				Markup markup1 = new Markup();
				markup1.setContent("\\begin{itemize}");
				subSec.getSecContent().add(markup1);
				for (int i111=0; i111 < nodeSubSecListItems.getLength(); i111++) {
					Node nodeSubSecListItem = nodeSubSecListItems.item(i111);
					ItemList subList = new ItemList();
					subList.setListContent(nodeSubSecListItem.getTextContent());
					subSec.getSecContent().add(subList);
					
				}
				Markup markup2 = new Markup();
				markup2.setContent("\\end{itemize}");
				subSec.getSecContent().add(markup2);
			} else if ((nodeSecElement.getNodeName() == "list") && (nodeSecElement.getAttributes().getNamedItem("list-type").getTextContent().contains("ordered"))) {
				NodeList nodeSubSecListItems = (NodeList) xPath.compile("list-item/p").evaluate(nodeSecElement, XPathConstants.NODESET);
				Markup markup1 = new Markup();
				markup1.setContent("\\begin{enumerate}");
				subSec.getSecContent().add(markup1);
				for (int i111=0; i111 < nodeSubSecListItems.getLength(); i111++) {
					Node nodeSubSecListItem = nodeSubSecListItems.item(i111);
					ItemList itemList = new ItemList();
					itemList.setListContent(nodeSubSecListItem.getTextContent());
					subSec.getSecContent().add(itemList);
					
				}
				Markup markup2 = new Markup();
				markup2.setContent("\\end{enumerate}");
				subSec.getSecContent().add(markup2);
			} else if ((nodeSubSecElement.getNodeName() == "sec") && (nodeSecElement.getTextContent() != null)) {
				subSection(xPath, section, nodeSubSecElement);  
			}
		}
	}


}
