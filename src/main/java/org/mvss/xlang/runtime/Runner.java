package org.mvss.xlang.runtime;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.mvss.xlang.dto.Scope;
import org.mvss.xlang.steps.*;
import org.mvss.xlang.steps.conditions.Condition;
import org.mvss.xlang.steps.conditions.Equals;
import org.mvss.xlang.steps.conditions.NotEquals;
import org.mvss.xlang.steps.operations.Increment;
import org.mvss.xlang.utils.ClassPathLoaderUtils;
import org.mvss.xlang.utils.XMLParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Runner {

    public static final String STEP_MAPPING_XML = "StepMapping.xml";

    public static final ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());

    static {
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    private final HashMap<String, Class<? extends Step>> stepDefMapping = new HashMap<>();

    public static final ConcurrentHashMap<String, Class<?>> typeMapping = new ConcurrentHashMap<>();

    private final Scope scope;

    public Runner() {

        //Built-ins
        stepDefMapping.put("var", VariableDefinition.class);
        stepDefMapping.put("func", FunctionDefinition.class);
        stepDefMapping.put("call", FunctionCall.class);
        stepDefMapping.put("return", Return.class);
        stepDefMapping.put("import", Import.class);
        stepDefMapping.put("echo", Echo.class);

        stepDefMapping.put("if", IfStatement.class);
        stepDefMapping.put("then", IfStatement.Then.class);
        stepDefMapping.put("elseif", IfStatement.ElseIf.class);
        stepDefMapping.put("else", IfStatement.Else.class);

        stepDefMapping.put("for", ForStatement.class);
        stepDefMapping.put("init", ForStatement.Init.class);
        stepDefMapping.put("update", ForStatement.Update.class);
        stepDefMapping.put("do", ForStatement.Do.class);

        stepDefMapping.put("increment", Increment.class);

        stepDefMapping.put("equals", Equals.class);
        stepDefMapping.put("notequals", NotEquals.class);

        typeMapping.put("boolean", Boolean.class);
        typeMapping.put("byte", Byte.class);
        typeMapping.put("char", Character.class);
        typeMapping.put("short", Short.class);
        typeMapping.put("int", Integer.class);
        typeMapping.put("long", Long.class);
        typeMapping.put("float", Float.class);
        typeMapping.put("double", Double.class);
        typeMapping.put("string", String.class);

        typeMapping.put("boolean array", boolean[].class);
        typeMapping.put("byte array", byte[].class);
        typeMapping.put("char array", char[].class);
        typeMapping.put("short array", short[].class);
        typeMapping.put("int array", int[].class);
        typeMapping.put("long array", long[].class);
        typeMapping.put("float array", float[].class);
        typeMapping.put("double array", double[].class);
        typeMapping.put("string array", String[].class);

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

    public ArrayList<Condition> getConditions(List<Step> steps) {
        ArrayList<Condition> conditions = null;

        if (steps != null) {
            conditions = new ArrayList<>();
            for (Step step : steps) {
                if (step instanceof Condition) {
                    conditions.add((Condition) step);
                }
            }
        }

        return conditions;
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

    public void importFile(String xmlFileName, Scope scope) throws Throwable {
        Document doc = XMLParser.readDocumentFromFile(xmlFileName);
        Element rootElement = doc.getDocumentElement();
        rootElement.normalize();
        run(rootElement, scope);
    }
}
