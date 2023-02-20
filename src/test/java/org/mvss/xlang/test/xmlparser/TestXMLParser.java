package org.mvss.xlang.test.xmlparser;

import org.mvss.xlang.utils.XMLParser;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.Serializable;

public class TestXMLParser {
    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
        Serializable parsedObject = XMLParser.readObjectFromFile(args[0]);
        System.out.println(XMLParser.objectMapper.writeValueAsString(parsedObject));
        System.out.println(XMLParser.writeObject("XLang", parsedObject));
    }
}
