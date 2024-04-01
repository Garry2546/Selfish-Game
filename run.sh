javac --source-path ./src/main --module-path ./lib/ --add-modules javafx.controls src/main/GameApp.java -d ./bin/

java --class-path ./bin/ --module-path ./lib/ --add-modules=javafx.controls GameApp