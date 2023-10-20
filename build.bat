javac -d ./out/ ./form/*.java
javac -d ./out/ ./arbol/*.java
javac -d ./out/ ./random/*.java
robocopy META-INF out/META-INF/
robocopy random/ out/random/
del ".\out\random\Ventana.java" # No se usa en ningún momento
robocopy images/ ./out/images/
cd out
jar cmvf META-INF/MANIFEST.MF "R-info.jar" ./
move "R-info.jar" "../R-info.jar"
cd ..
rmdir /s /q out