package com.company;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Задания:
 *
 * 1. Необходимо сформировать коллекцию, содержащую все виды документов в отсортированном порядке.
 * 2. Вывести имена и значения всех атрибутов для par step="1" name="ГРАЖДАНСТВО".
 * 3. Задача со звездочкой: создать в базе таблицу-справочник со значениями из первой части.
 *
 * Исходный файл - input.xml
 *
 */

public class Main {

    public static void main(String[] args) {

        System.out.println(">>> XML Parser started... >>>");
        String xmlFile = "input.xml";
        List<String> vidDok = new ArrayList<>();

        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse(new File(xmlFile));
            document.getDocumentElement().normalize();

            Element tagOrder = document.getDocumentElement();
            Node tagServices = tagOrder.getElementsByTagName("services").item(0);
            // in tag services
            NodeList services = tagServices.getChildNodes();

            for (int s = 0; s < services.getLength(); s++) {
                Node serv = services.item(s);
                if (serv.getNodeType() != Node.TEXT_NODE && serv.getNodeName().equals("serv")) {

                    // in tag serv
                    Node tagPars = ((Element) serv).getElementsByTagName("pars").item(0);
                    // in tag pars
                    NodeList pars = tagPars.getChildNodes();

                    for (int p = 0; p < pars.getLength(); p++) {
                        Node par = pars.item(p);
                        if (par.getNodeType() != Node.TEXT_NODE) {
                            NamedNodeMap parAttrs = par.getAttributes();

                            // TASK 1 - VID_DOK
                            Node name = parAttrs.getNamedItem("name");
                            if (name.getNodeValue().equals("ВИД_ДОК")) {

                                // in tag par with name="ВИД_ДОК"
                                NodeList parList = ((Element) par).getElementsByTagName("par_list");
                                for (int l = 0; l < parList.getLength(); l++) {
                                    Node itemOfList = parList.item(l);
                                    String dok = itemOfList.getAttributes().getNamedItem("value").getNodeValue();
                                    vidDok.add(dok);
                                }
                                Collections.sort(vidDok);
                                System.out.println("\n>>> TASK 1 : Sorted collection ВИД_ДОК:");
                                System.out.println("=======================================");
                                vidDok.stream().forEach(dok -> System.out.println(dok));
                            }

                            // TASK 2 - GRAZHDANSTVO
                            Node step = parAttrs.getNamedItem("step");
                            if (step.getNodeValue().equals("1") && name.getNodeValue().equals("ГРАЖДАНСТВО")) {
                                System.out.println("\n>>> TASK 2 : Choose all attributes for tag : <par step=\"1\" name=\"ГРАЖДАНСТВО\" .. >:");
                                System.out.println("===================================================================================");
                                for (int at = 0; at < parAttrs.getLength(); at++) {
                                    Node attrib = parAttrs.item(at);
                                    System.out.println(attrib.getNodeName() + "=\"" + attrib.getNodeValue() + "\"");
                                }
                            }
                        }
                    }
                }
            }


        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
