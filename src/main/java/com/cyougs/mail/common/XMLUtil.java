package com.cyougs.mail.common;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.springframework.context.annotation.Configuration;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
@Configuration
public class XMLUtil {

	private static String xmlPath = "E:\\workspace\\cyougs\\src\\main\\resources\\static\\xml\\config.xml";
	
	 public static List<String> getXmlInfo(){
	        DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
	        dbf.setIgnoringElementContentWhitespace(true);
	        List<String> dominList = new ArrayList<String>();
	        try {
	            DocumentBuilder db = dbf.newDocumentBuilder();
	            Document doc = db.parse(xmlPath); // 使用dom解析xml文件

	            NodeList notelist = doc.getElementsByTagName("postmaster"); 
	            for (int i = 0; i < notelist.getLength(); i++) // 循环处理对象
	            {
	                Element element = (Element)notelist.item(i);;
	                
	                for (Node node = element.getFirstChild(); node != null; ){
	                	dominList.add(node.getFirstChild().getTextContent());
	                }  
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return dominList;
	    }
	    
	 
	 
	public void modifyXML(String nodeName,String nameValue) {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setIgnoringElementContentWhitespace(true);
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document xmldoc = db.parse(xmlPath);

			Element root = xmldoc.getDocumentElement();
			//"/config/James/postmaster"
			Element per = (Element) selectSingleNode(nodeName, root);
			per.getFirstChild().setTextContent(nameValue);

			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer former = factory.newTransformer();
			former.transform(new DOMSource(xmldoc), new StreamResult(new File(xmlPath)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Node selectSingleNode(String express, Element source) {
		Node result = null;
		XPathFactory xpathFactory = XPathFactory.newInstance();
		XPath xpath = xpathFactory.newXPath();
		try {
			result = (Node) xpath.evaluate(express, source, XPathConstants.NODE);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}

		return result;
	}

}