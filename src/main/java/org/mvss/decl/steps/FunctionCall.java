package org.mvss.decl.steps;

import com.fasterxml.jackson.databind.jsontype.NamedType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mvss.decl.dto.Scope;
import org.mvss.decl.dto.Step;

import java.io.Serializable;
import java.util.HashMap;

@Getter
@Setter
@NoArgsConstructor
public class FunctionCall extends Step
{
   static
   {
      OBJECT_MAPPER.registerSubtypes( new NamedType( FunctionCall.class, "call" ) );
   }

   public FunctionCall( String step, HashMap<String, Serializable> data )
   {
      super( step, data );
   }

   @Override
   public boolean run( Scope scope )
   {
      //      if ( ( data != null ) && data.containsKey( "value" ) )
      //      {
      //         //TODO: Extract return value
      //         System.err.println( "Error running call: no data provided" );
      //         return false;
      //      }

      System.out.println( "Not implemented yet" );
      return true;
   }
}
