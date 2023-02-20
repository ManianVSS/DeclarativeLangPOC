package org.mvss.decl.dto;

import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class Function implements Serializable {
    private String name;
    @Builder.Default
    private HashMap<String, String> params = new HashMap<>();
    @Builder.Default
    private String returns = "void";
    @Builder.Default
    private ArrayList<Step> steps = new ArrayList<>();
}
