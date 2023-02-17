package org.mvss.decl.steps;

import com.fasterxml.jackson.databind.jsontype.NamedType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.mvss.decl.dto.Function;
import org.mvss.decl.dto.Scope;
import org.mvss.decl.dto.Step;

import java.io.Serializable;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class FunctionDefinition extends Step
{
   static
   {
      OBJECT_MAPPER.registerSubtypes( new NamedType( FunctionDefinition.class, "func" ) );
   }

   public FunctionDefinition( String step, HashMap<String, Serializable> data )
   {
      super( step, data );
   }

   @Override
   public boolean run( Scope scope )
   {
      if ( ( data == null ) || !data.containsKey( "name" ) || !data.containsKey( "params" ) || !data.containsKey( "returns" ) || !data.containsKey(
               "steps" ) )
      {
         System.err.println( "Error running func: required field not provided" );
         return false;
      }

      Function function = OBJECT_MAPPER.convertValue( data, Function.class );

      ConcurrentHashMap<String, Function> scopeFunctionMap = scope.getFunctions();

      if ( scopeFunctionMap.containsKey( function.getName() ) )
      {
         System.err.println( "Function with name " + function.getName() + "is already in scope" );
         return false;
      }

      scopeFunctionMap.put( function.getName(), function );
      return true;
   }
}
