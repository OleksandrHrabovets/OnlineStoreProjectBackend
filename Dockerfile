FROM openjdk:17-slim
COPY target/OnlineStoreProjectBackend-0.0.1.jar online-store-project-backend.jar
ENTRYPOINT ["java","-jar","online-store-project-backend.jar"]