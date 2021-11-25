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
# For some reason, Heroku container deplyment was unable to find the right jar
# so I gave the fully qualified path here. This would make version changing and
# renaming the project slightly more difficult however
CMD ["/bin/sh", "-c", "java -jar --enable-preview /app/backend-1.0.0.jar"]