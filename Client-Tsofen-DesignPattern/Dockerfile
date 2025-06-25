FROM maven AS build
WORKDIR /app
COPY . .
RUN mvn -version && ls -l && ls -l target || true
RUN mvn clean package -X
FROM openjdk
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
CMD ["java", "-jar", "app.jar"]
