FROM openjdk:17-alpine AS build
COPY build.gradle settings.gradle gradlew ./
COPY gradle ./gradle
RUN ./gradlew build || return 0
COPY ./src ./src
RUN ./gradlew build

FROM openjdk:17-alpine
EXPOSE 8080
RUN mkdir /app
COPY --from=build /build/libs/*.jar /app/
RUN rm /app/*plain.jar
CMD ["/bin/sh", "-c", "java -jar --enable-preview /app/*.jar"]