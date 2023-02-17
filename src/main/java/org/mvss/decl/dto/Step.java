package org.mvss.decl.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.*;

import java.io.Serializable;
import java.util.HashMap;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public abstract class Step implements Serializable
{
   protected String                        step;
   protected HashMap<String, Serializable> data;

   public abstract boolean run( Scope scope ) throws Throwable;

   public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper( new YAMLFactory() );

   static
   {
      OBJECT_MAPPER.disable( DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES );
      OBJECT_MAPPER.setSerializationInclusion( JsonInclude.Include.NON_NULL );
      OBJECT_MAPPER.findAndRegisterModules();
   }
}
