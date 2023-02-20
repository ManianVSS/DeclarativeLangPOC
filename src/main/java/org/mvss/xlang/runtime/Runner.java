package org.mvss.xlang.runtime;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.mvss.xlang.dto.Scope;
import org.mvss.xlang.steps.*;
import org.mvss.xlang.utils.ClassPathLoaderUtils;
import org.mvss.xlang.utils.NullAwareBeanUtilsBean;
import org.mvss.xlang.utils.XMLParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Runner {

    public static final String STEP_MAPPING_XML = "StepMapping.xml";

    public static final ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
    public static final NullAwareBeanUtilsBean NULL_AWARE_BEAN_UTILS_BEAN = new NullAwareBeanUtilsBean();


    static {
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    private final HashMap<String, Class<? extends Step>> stepDefMapping = new HashMap<>();

    private final Scope scope;

    public Runner() {

        //Built-ins
        stepDefMapping.put("var", VariableDefinition.class);
        stepDefMapping.put("func", FunctionDefinition.class);
        stepDefMapping.put("call", FunctionCall.class);
        stepDefMapping.put("return", Return.class);
        stepDefMapping.put("import", Import.class);

        try {
            String mappingFileContents = ClassPathLoaderUtils.readAllText(STEP_MAPPING_XML);
            //noinspection unchecked
            HashMap<String, Serializable> stepDefImplNameMapping = (HashMap<String, Serializable>) XMLParser.readObject(mappingFileContents);

            for (String key : stepDefImplNameMapping.keySet()) {

                if (stepDefMapping.containsKey(key)) {
                    throw new RuntimeException("Step definition already mapped for: " + key + " to " + stepDefMapping.get(key).getCanonicalName());
                }

                String value = (String) stepDefImplNameMapping.get(key);
                //noinspection unchecked
                stepDefMapping.put(key, (Class<? extends Step>) Class.forName(value));
            }
            scope = new Scope();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Step> getSteps(Element element) throws Throwable {
        ArrayList<Step> steps = new ArrayList<>();
        NodeList stepNodeList = element.getChildNodes();

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
                Serializable objectRead = XMLParser.readAttributesAsObject(stepElement);
                Step stepObjectRead = objectMapper.convertValue(objectRead, stepDefClass);
                stepObjectRead.setSteps(getSteps(stepElement));
                steps.add(stepObjectRead);
            }
        }
        return steps;
    }

    public void run(String xmlFileName) throws Throwable {
        run(xmlFileName, this.scope);
    }

    public void run(String xmlFileName, Scope scope) throws Throwable {
        Document doc = XMLParser.readDocumentFromFile(xmlFileName);
        Element rootElement = doc.getDocumentElement();
        rootElement.normalize();
        run(rootElement, scope);
    }

    public void run(Element element) throws Throwable {
        run(element, this.scope);
    }

    public void run(Element element, Scope scope) throws Throwable {
        run(getSteps(element), scope);
    }

    public void run(List<Step> steps) throws Throwable {
        run(steps, this.scope);
    }

    public void run(List<Step> steps, Scope scope) throws Throwable {

        try {
            for (Step step : steps) {
                step.execute(this, scope);
            }
        } catch (FunctionCallReturnException ignored) {
        }
    }
}
