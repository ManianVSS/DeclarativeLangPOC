package org.mvss.decl.tasks;

import lombok.*;

import java.io.Serializable;
import java.util.HashMap;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Command implements Serializable
{
   private String                        name;
   private String                        type;
   private HashMap<String, Serializable> params;
}
