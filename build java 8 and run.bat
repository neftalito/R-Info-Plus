javac -source 1.8 -target 1.8 -d ./out/ ./form/*.java
javac -source 1.8 -target 1.8 -d ./out/ ./arbol/*.java
javac -source 1.8 -target 1.8 -d ./out/ ./random/*.java
robocopy META-INF out/META-INF/
robocopy random/ out/random/
del ".\out\random\Ventana.java" # No se usa en ningún momento
robocopy images/ ./out/images/
cd out
jar cmvf META-INF/MANIFEST.MF "R-info+.jar" ./
move "R-info+.jar" "../R-info+J8.jar"
cd ..
rmdir /s /q out
java -jar R-info+J8.jar