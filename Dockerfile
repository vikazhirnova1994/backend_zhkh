# Multi Stage build

# Base Image and name stage as "builder"
FROM maven:3.8.3-openjdk-17 AS builder
# Create App Directory inside our container
WORKDIR /thomas/app/src/

# Copy files
COPY src ./
COPY pom.xml ../

RUN mvn -f /thomas/app/pom.xml clean package

#### 2nd Stage ####

FROM openjdk:17-jdk-slim-buster

WORKDIR /thomas/lib/
LABEL maintainer="vika.zhirn@yandex.ru"

# Copy the Jar from the first Stage (builder) to the 2nd stage working directory
COPY --from=builder /thomas/app/target/backendHome-0.0.1-SNAPSHOT.jar ./backend.jar

# Expose the port to the inner container communication network
EXPOSE 8005

# Run the Java Application
ENTRYPOINT [ "java","-jar","/thomas/lib/backend.jar"]