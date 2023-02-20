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
public class Import extends Step
{
   static
   {
      OBJECT_MAPPER.registerSubtypes( new NamedType( Import.class, "import" ) );
   }

   public Import( String step, HashMap<String, Serializable> data )
   {
      super( step, data );
   }

   @Override
   public boolean run( Scope scope )
   {
      System.out.println( "Not implemented yet" );
      return true;
   }
}
