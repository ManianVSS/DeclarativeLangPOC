package org.mvss.decl.test.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.mvss.decl.dto.Scope;
import org.mvss.decl.dto.Step;
import org.mvss.decl.steps.VariableDefinition;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class TestStepYamlSerialization
{
   private static HashMap<String, Serializable> getMapForVariable( String name, Serializable value )
   {
      HashMap<String, Serializable> map = new HashMap<>();
      map.put( "name", name );
      map.put( "Value", value );
      return map;
   }

   private static HashMap<String, Serializable> getMapForFunction( String name, Serializable value )
   {
      HashMap<String, Serializable> map = new HashMap<>();
      map.put( "name", name );
      map.put( "Value", value );
      return map;
   }

   public static void main( String[] args ) throws JsonProcessingException
   {
      XmlMapper xmlMapper = new XmlMapper();

      Scope           globalScope    = new Scope();
      ArrayList<Step> documentObject = new ArrayList<>();
      documentObject.add( new VariableDefinition( getMapForVariable( "myExpvar1", 10 ) ) );
      documentObject.add( new VariableDefinition( getMapForVariable( "myExpvar2", true ) ) );
      String serializedString = Step.OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString( documentObject );
      System.out.println( serializedString );

      TypeReference<ArrayList<Step>> arrayListOfStepTypeReference = new TypeReference<>()
      {
      };

      ArrayList<Step> parsedDocument = Step.OBJECT_MAPPER.readValue( serializedString, arrayListOfStepTypeReference );

      System.out.println( "Parsed document is " + parsedDocument );
   }
}
