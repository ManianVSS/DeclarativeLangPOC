package org.mvss.xlang.runtime;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.mvss.xlang.dto.Step;
import org.mvss.xlang.utils.ClassPathLoaderUtils;
import org.mvss.xlang.utils.XMLParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.Serializable;
import java.util.HashMap;

public class Runner {

    public static final String STEP_MAPPING_XML = "StepMapping.xml";
    private final HashMap<String, Class<? extends Step>> stepDefMapping = new HashMap<>();

    public static ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());

    static {
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    public Runner() {
        try {
            String mappingFileContents = ClassPathLoaderUtils.readAllText(STEP_MAPPING_XML);
            //noinspection unchecked
            HashMap<String, Serializable> stepDefImplNameMapping = (HashMap<String, Serializable>) XMLParser.readObject(mappingFileContents);

            for (String key : stepDefImplNameMapping.keySet()) {
                String value = (String) stepDefImplNameMapping.get(key);
                //noinspection unchecked
                stepDefMapping.put(key, (Class<? extends Step>) Class.forName(value));
            }
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public void run(String xmlFileName) throws Throwable {
//        String mappingFileContents = ClassPathLoaderUtils.readAllText(xmlFileName);
//        //noinspection unchecked
//        HashMap<String, Serializable> steps = (HashMap<String, Serializable>) XMLParser.readObject(mappingFileContents);
//
//        for (String stepName : steps.keySet()) {
//
//            if (!stepDefMapping.containsKey(stepName)) {
//                throw new RuntimeException("Could not find implementation for step type: " + stepName);
//            }
//
//            Class<? extends Step> stepDefClass = stepDefMapping.get(stepName);
//            Step stepObject = objectMapper.convertValue(steps.get(stepName), stepDefClass);
//            stepObject.execute(null);
//        }

        Document doc = XMLParser.readDocumentFromFile(xmlFileName);
        Element rootElement = doc.getDocumentElement();
        rootElement.normalize();

        NodeList stepNodeList = rootElement.getChildNodes();

        for (int itr = 0; itr < stepNodeList.getLength(); itr++) {
            Node stepNode = stepNodeList.item(itr);
            stepNode.normalize();
            // Attributes somehow don't make it in getChildNodes to switch case hence separate loop.
            if (stepNode.getNodeType() == Node.ELEMENT_NODE) {
                Element stepElement = (Element) stepNode;
//                    parsedObject.put(stepElement.getNodeName(), readObject(stepElement));
                String step = stepElement.getNodeName();

                if (!stepDefMapping.containsKey(step)) {
                    throw new RuntimeException("Could not find implementation for step type: " + step);
                }

                Class<? extends Step> stepDefClass = stepDefMapping.get(step);
//                    Step stepObject = stepDefClass.getDeclaredConstructor().newInstance();
//                    stepObject.execute();
                Serializable objectRead = XMLParser.readObject(stepElement);
                Step stepObject = objectMapper.convertValue(objectRead, stepDefClass);
                stepObject.execute();
            }
        }
    }
}
