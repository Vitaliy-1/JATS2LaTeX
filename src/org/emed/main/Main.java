package org.emed.main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.emed.classes.Italic;
import org.emed.classes.Par;
import org.emed.classes.ParContent;
import org.emed.classes.SecContent;
import org.emed.classes.Section;
import org.emed.classes.ItemList;
import org.emed.classes.SubSec;
import org.emed.classes.Xref;

public class Main {

	public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException, XPathExpressionException, IllegalArgumentException, IllegalAccessException {
		
		BufferedWriter writer = new BufferedWriter (new FileWriter("output.tex"));
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse("bezsh.xml");
		
		List<Section> listSections = new ArrayList<Section>();
		XPath xPath =  XPathFactory.newInstance().newXPath();
		NodeList nodeSections = (NodeList) xPath.compile("/article/body/sec").evaluate(document, XPathConstants.NODESET);
		for (int i = 0; i < nodeSections.getLength(); i++) {
			Node nodeSection = nodeSections.item(i);
			Node nodeSectionTitle = (Node) xPath.compile("title").evaluate(nodeSection,  XPathConstants.NODE);
			Section section = new Section();
			listSections.add(section);
			listSections.get(i).setTitle(nodeSectionTitle.getTextContent());
			writer.write(listSections.get(i).getTitle());
			writer.newLine();
			NodeList nodeSecElements = (NodeList) xPath.compile("p|fig|sec|table-wrap|list").evaluate(nodeSection, XPathConstants.NODESET);
			for (int i1 = 0; i1 < nodeSecElements.getLength(); i1++) {
				Node nodeSecElement = nodeSecElements.item(i1);
				if ((nodeSecElement.getNodeName() == "p") && (nodeSecElement.getTextContent() != null)) {
					ParContent parContent = new ParContent();
					parContent.setTitle("");
					section.getSecContent().add(parContent);
					//par.setParContent(nodeSecElement.getTextContent());
					NodeList insidePars = nodeSecElement.getChildNodes();
					for (int j = 0; j < insidePars.getLength(); j++) {
						Node insidePar = insidePars.item(j);
						
						if (insidePar.getNodeValue() != null) {
							Par par = new Par();
							par.setContent(insidePar.getNodeValue());
							parContent.getParContentList().add(par);
							writer.write(par.getContent());
							writer.newLine();
							System.out.println(insidePar.getNodeValue());
							System.out.println("----------------------------------------------");
						} else if (insidePar.getNodeName() == "italic") {
							Italic italic = new Italic();
							italic.setItContent(insidePar.getTextContent());
							//String italic = insidePar.getTextContent();
							//par.setParContent("\\textit{" + italic + "}");
							parContent.getParContentList().add(italic);
							//writer.write(par.getParContent());
							
						} else if (insidePar.getNodeName() == "xref") {
							  if (insidePar.getAttributes().getNamedItem("ref-type").toString().contains("bibr")) {
								  Xref xref = new Xref();
								  xref.setBibContent(insidePar.getTextContent().trim());
								  parContent.getParContentList().add(xref);
								  writer.write(xref.getBibContent());
					             
							  } else if (insidePar.getAttributes().getNamedItem("ref-type").toString().contains("fig")) {
								  Xref xref = new Xref();
								  String xrefFig = insidePar.getAttributes().getNamedItem("rid").toString().replaceAll("rid=\"|\"", "");
								  
								  xref.setFigContent(xrefFig);
								  parContent.getParContentList().add(xref);
								  writer.write(xref.getFigContent());
							  }
						}
					}
					//section.getSecContent().add(par);
					writer.write("\\par ");
					writer.newLine();
				} else if((nodeSecElement.getNodeName() == "list") && (nodeSecElement.getAttributes().getNamedItem("list-type").getTextContent().contains("unordered"))) { 
					NodeList nodeSubSecListItems = (NodeList) xPath.compile("list-item/p").evaluate(nodeSecElement, XPathConstants.NODESET);
					for (int i111=0; i111 < nodeSubSecListItems.getLength(); i111++) {
						Node nodeSubSecListItem = nodeSubSecListItems.item(i111);
						ItemList itemList = new ItemList();
						itemList.setListContent(nodeSubSecListItem.getTextContent());
						section.getSecContent().add(itemList);
						writer.write(itemList.getListContent());
						writer.newLine();
					}
				} else if ((nodeSecElement.getNodeName() == "sec") && (nodeSecElement.getTextContent() != null)) {
	
					SubSec subSec = new SubSec();
					Node nodeSubSecTitle = (Node) xPath.compile("title").evaluate(nodeSecElement, XPathConstants.NODE);
					subSec.setTitle(nodeSubSecTitle.getTextContent());
					section.getSecContent().add(subSec);
					
					writer.write(subSec.getTitle());
					writer.newLine();
					
					NodeList nodeSubSecElements = (NodeList) xPath.compile("p|fig|table-wrap|list").evaluate(nodeSecElement, XPathConstants.NODESET);
					for (int i11 = 0; i11 < nodeSubSecElements.getLength(); i11++) {
						Node nodeSubSecElement = nodeSubSecElements.item(i11);
						if ((nodeSubSecElements.item(i11).getNodeName() == "p") && (nodeSubSecElements.item(i11)) != null) {
							ParContent parContent = new ParContent();
							parContent.setTitle("");
							subSec.getSecContent().add(parContent);
							//par.setParContent(nodeSecElement.getTextContent());
							NodeList insidePars = nodeSubSecElement.getChildNodes();
							for (int j = 0; j < insidePars.getLength(); j++) {
								Node insidePar = insidePars.item(j);
								
								if (insidePar.getNodeValue() != null) {
									Par par = new Par();
									par.setContent(insidePar.getNodeValue());
									parContent.getParContentList().add(par);
									writer.write(par.getContent());
									writer.newLine();
									System.out.println(insidePar.getNodeValue());
									System.out.println("----------------------------------------------");
								} else if (insidePar.getNodeName() == "italic") {
									Italic italic = new Italic();
									italic.setItContent(insidePar.getTextContent());
									//String italic = insidePar.getTextContent();
									//par.setParContent("\\textit{" + italic + "}");
									parContent.getParContentList().add(italic);
									//writer.write(par.getParContent());
								} else if (insidePar.getNodeName() == "xref") {
									
								}
							}
							writer.newLine();
						} else if ((nodeSubSecElement.getNodeName() == "list") && (nodeSubSecElement.getAttributes().getNamedItem("list-type").getTextContent().contains("unordered"))) {
							NodeList nodeSubSecListItems = (NodeList) xPath.compile("list-item/p").evaluate(nodeSubSecElement, XPathConstants.NODESET);
							for (int i111=0; i111 < nodeSubSecListItems.getLength(); i111++) {
								Node nodeSubSecListItem = nodeSubSecListItems.item(i111);
								ItemList subList = new ItemList();
								subList.setListContent(nodeSubSecListItem.getTextContent());
								subSec.getSecContent().add(subList);
								writer.write(subList.getListContent());
								writer.newLine();
							}
						} 
					}
				}
			}
		}
		
		for (int j = 0; j < listSections.size(); j++) {
			Section section = listSections.get(j);
			System.out.println(section.getTitle());
			List<SecContent> secContents = section.getSecContent();
			for (SecContent secContent : secContents) {
				//System.out.println(secContent.getClass().getName());
				if (secContent.getTitle() != null) {
					System.out.println(secContent.getTitle());
				}
				
				if (secContent.getClass().getName() == "org.emed.classes.ParContent") {
					ParContent parContent = (ParContent) secContent;
					List<ParContent> parContents = parContent.getParContentList();
					
					for (ParContent parContentPar : parContents) {
						
						if (parContentPar.getClass().getName() == "org.emed.classes.Par") {
							Par par = (Par) parContentPar;
							System.out.println(par.getContent());
						} else if (parContentPar.getClass().getName() == "org.emed.classes.Italic") {
							Italic italic = (Italic) parContentPar;
							System.out.println(italic.getItContent());
						} else if (parContentPar.getClass().getName() == "org.emed.classes.Xref") {
							Xref xref = (Xref) parContentPar;
							if (xref.getFigContent() != null) {
							  System.out.println(xref.getFigContent());
							} else if (xref.getBibContent() != null) {
								System.out.println(xref.getBibContent());
							} 
						}
						
					}
					//System.out.println(par.getParContent());
				}
				
				if (secContent.getClass().getName() == "org.emed.classes.SubSec") {
					SubSec subSec = (SubSec) secContent;
					List<SecContent> subSecContents = subSec.getSecContent();
					for (SecContent subSecContent : subSecContents) {
						//System.out.println(subSecContent.getClass().getName());	
						if (subSecContent.getClass().getName() == "org.emed.classes.ParContent") {
							ParContent subParContent = (ParContent) subSecContent;
							List<ParContent> subPars = subParContent.getParContentList();
							for (ParContent subPar : subPars) {
								if (subPar.getClass().getName() == "org.emed.classes.Par") {
									System.out.println(subPar.getContent());
								}
							}
						} else if (subSecContent.getClass().getName() == "org.emed.classes.ItemList"){
							ItemList subList = (ItemList) subSecContent;
							System.out.println(subList.getListContent());
						} else if (subSecContent.getClass().getName() == "org.emed.classes.Xref") {
							Xref xref = (Xref) subSecContent;
							if (xref.getBibContent() != null) {
								System.out.println(xref.getBibContent());
							} else if (xref.getFigContent() != null) {
								System.out.println(xref.getFigContent());
							}
						}
					}
					
				}
				
			}
		}
			
		
			
		  writer.write("");
	      writer.newLine();
	      writer.close();
		
	}

}