FROM arm64v8/openjdk:22-ea-17-jdk

COPY target/To-Do_List-1.0.jar ToDoList.jar

ENTRYPOINT ["java", "-jar", "/ToDoList.jar"]
