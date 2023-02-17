package org.mvss.decl.steps;

import com.fasterxml.jackson.databind.jsontype.NamedType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.mvss.decl.dto.Scope;
import org.mvss.decl.dto.Step;

import java.io.Serializable;
import java.util.HashMap;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class Echo extends Step
{
   static
   {
      OBJECT_MAPPER.registerSubtypes( new NamedType( Echo.class, "echo" ) );
   }

   public Echo( String step, HashMap<String, Serializable> data )
   {
      super( step, data );
   }

   @Override
   public boolean run( Scope scope )
   {
      if ( ( data == null ) || !data.containsKey( "value" ) )
      {
         System.err.println( "Error running echo: no data provided" );
         return false;
      }

      System.out.println( data.get( "value" ) );
      return true;
   }
}
