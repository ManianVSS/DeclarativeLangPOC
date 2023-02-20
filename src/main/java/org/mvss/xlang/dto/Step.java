package org.mvss.xlang.dto;

import java.io.Serializable;

public interface Step extends Serializable {
    boolean execute() throws Throwable;
}
