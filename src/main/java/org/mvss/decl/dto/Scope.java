package org.mvss.decl.dto;

import lombok.*;

import java.util.concurrent.ConcurrentHashMap;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Scope
{
   @Builder.Default
   private Scope                               parent          = null;
   @Builder.Default
   private String                              currentFunction = null;
   @Builder.Default
   private ConcurrentHashMap<String, Variable> variables       = new ConcurrentHashMap<>();
   @Builder.Default
   private ConcurrentHashMap<String, Function> functions       = new ConcurrentHashMap<>();
   //   private String                                  fileName;
   //   private long                                    stepNumber;
   //   private long                                    column;
}
