# 1. stopnja: gradnja jar datoteke
FROM eclipse-temurin:25-jdk AS build
WORKDIR /app
# najprej samo odvisnosti, da jih Docker pri spremembi kode ne prenaša znova
COPY pom.xml mvnw ./
COPY .mvn .mvn
RUN ./mvnw -q dependency:go-offline
COPY src src
RUN ./mvnw -q -DskipTests package

# 2. stopnja: samo JRE in jar, da je slika majhna
FROM eclipse-temurin:25-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
