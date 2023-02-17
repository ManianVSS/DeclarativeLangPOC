package org.mvss.decl.steps;

import com.fasterxml.jackson.databind.jsontype.NamedType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.mvss.decl.dto.Scope;
import org.mvss.decl.dto.Step;
import org.mvss.decl.dto.Variable;

import java.io.Serializable;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class VariableDefinition extends Step
{
   static
   {
      OBJECT_MAPPER.registerSubtypes( new NamedType( VariableDefinition.class, "var" ) );
   }

   public VariableDefinition( HashMap<String, Serializable> data )
   {
      super( "var", data );
   }

   @Override
   public boolean run( Scope scope )
   {
      if ( ( data == null ) || !data.containsKey( "name" ) || !data.containsKey( "value" ) )
      {
         System.err.println( "Error running var: no variable name or value provided" );
         return false;
      }

      Variable                            variable         = OBJECT_MAPPER.convertValue( data, Variable.class );
      ConcurrentHashMap<String, Variable> scopeVariableMap = scope.getVariables();

      if ( scopeVariableMap.containsKey( variable.getName() ) )
      {
         System.err.println( "Variable with name " + variable.getName() + "is already in scope" );
         return false;
      }
      scopeVariableMap.put( variable.getName(), variable );
      return true;
   }
}
