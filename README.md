# DeclarativeLangPOC
Declarative Language POC for general automation
To run from IDE </br>
If you get javax module error for XML, add module option -add-modules java.xml.bind
run org.mvss.xlang.runtime.Main
from /home/manian/Checkouts/DeclarativeLangPOC/target/test-classes
with a JDK 11 (with )

To Build </br>
mvn clean install -DskipTests -T 8

To run from command line Example</br>
java -jar DeclTry-1.0-SNAPSHOT-jar-with-dependencies.jar -f test-classes/SampleXMLFile.xml 