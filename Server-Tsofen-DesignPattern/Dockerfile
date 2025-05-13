FROM maven AS build
WORKDIR /app
COPY . .
RUN mvn clean package
FROM openjdk
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
CMD ["java", "-jar", "app.jar"]