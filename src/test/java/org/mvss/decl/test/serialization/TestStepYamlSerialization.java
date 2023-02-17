package org.mvss.decl.test.serialization;

import org.mvss.decl.dto.Scope;
import org.mvss.decl.dto.Step;
import org.mvss.decl.dto.Variable;
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

   public static void main( String[] args )
   {
      Scope           globalScope    = new Scope();
      ArrayList<Step> documentObject = new ArrayList<>();
      documentObject.add( new VariableDefinition( getMapForVariable( "myExpvar1", 10 ) ) );
   }
}
