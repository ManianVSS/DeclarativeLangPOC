# DeclarativeLangPOC

Declarative Language POC for general automation</br>
</br>
To run from IDE </br>
If you get javax module error for XML, add module option -add-modules java.xml.bind</br>
run org.mvss.xlang.runtime.Main</br>
from /home/manian/Checkouts/DeclarativeLangPOC/target/test-classes</br>
with a JDK 11</br>

To Build </br>
mvn clean install -DskipTests -T 8

To run from command line Example</br>
java -jar DeclTry-1.0-SNAPSHOT-jar-with-dependencies.jar -f test-classes/SampleXMLFile.xml 
