package org.mvss.xlang.test.xmlparser;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.mvss.xlang.runtime.Runner;
import org.mvss.xlang.steps.Step;
import org.mvss.xlang.utils.XMLParser;

import java.util.ArrayList;

public class TestXMLSerialization {
    public static void main(String[] args) throws Throwable {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.disable(JsonGenerator.Feature.IGNORE_UNKNOWN);
        Runner runner = new Runner();
        ArrayList<Step> steps = runner.getSteps(XMLParser.readDocumentFromFile(args[0]).getDocumentElement());
        System.out.println("XML serialization using XML Mapper for steps is as follows:");
        System.out.println(xmlMapper.writerWithDefaultPrettyPrinter().writeValueAsString(steps));
    }
}
