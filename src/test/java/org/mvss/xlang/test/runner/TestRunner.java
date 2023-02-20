package org.mvss.xlang.test.runner;

import org.mvss.xlang.runtime.Runner;

public class TestRunner {
    public static void main(String[] args) throws Throwable {
        Runner runner = new Runner();
        runner.run("SampleXMLFile.xml");
    }
}
