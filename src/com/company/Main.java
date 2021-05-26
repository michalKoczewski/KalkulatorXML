package com.company;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.HashMap;
import java.util.Locale;


public class Main {

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        Calculator c = new Calculator();
        System.out.println("Type in one of listed currencies");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String currency = reader.readLine().toUpperCase(Locale.ROOT);
        System.out.println("Type in amount");
        Float amount = Float.valueOf(reader.readLine());
        c.countCurrency(amount,currency);
    }

    public static HashMap<String,String> parseXML() throws ParserConfigurationException, IOException, SAXException {
        File file = new File("eurofxref-daily.xml");

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(file);

        doc.getDocumentElement().normalize();

        NodeList nList = doc.getElementsByTagName("Cube");
        Node nNode;

        HashMap<String, String> map = new HashMap<>();

        for (int i = 0; i < nList.getLength(); i++ ){
            nNode = nList.item(i);
            Element elem = (Element) nNode;
            String country = elem.getAttribute("currency");
            String rate = elem.getAttribute("rate");
            map.put(country,rate);
        }
        return map;
    }
}

class Calculator {
    HashMap map;

    Calculator() throws ParserConfigurationException, IOException, SAXException {
        this.map = Main.parseXML();

        System.out.println("Available currencies");

        for(Object x: map.keySet()){
            System.out.println(x);
        }

        System.out.println("\n");
    }

    String countCurrency(float amount, String currency) {
        float curr = Float.parseFloat((String) map.get(currency));

        return "Amount " + curr * amount + " of " + currency + " in rate " + curr;
    }
}
