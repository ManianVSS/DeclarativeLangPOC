package org.mvss.xlang.dto;

import lombok.*;
import org.mvss.xlang.runtime.Step;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Scope {
    private String currentFunction;
    @Builder.Default
    private ConcurrentHashMap<String, Serializable> variables = new ConcurrentHashMap<>();
    @Builder.Default
    private ConcurrentHashMap<String, ArrayList<Step>> functions = new ConcurrentHashMap<>();
}
