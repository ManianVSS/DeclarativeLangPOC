package org.mvss.decl.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class Variable implements Serializable
{
   private String       name;
   @Builder.Default
   private Serializable value = null;
}
