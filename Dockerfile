# Sử dụng image Maven dựa trên nền Linux
FROM maven:3.9.11-eclipse-temurin-17 AS build
WORKDIR /app

# Copy file cấu hình trước
COPY pom.xml .
#RUN --mount=type=bind,source=.m2-cache,target=/root/.m2 mvn dependency:go-offline -
COPY src ./src
#copy file library by github package
COPY settings.xml /root/.m2/abc_data.xml

#RUN --mount=type=bind,source=.m2-cache,target=/root/.m2 mvn package -DskipTests -o
#RUN mvn package -DskipTests
# syntax=docker/dockerfile:1.7
#run on github
RUN --mount=type=secret,id=maven_settings,target=/root/.m2/abc_data.xml \
    mvn clean package -s /root/.m2/abc_data.xml -DskipTests

    #for debug
# RUN --mount=type=secret,id=maven_settings,target=/root/.m2/abc_data.xml \
#     sh -c 'wc -c /root/.m2/abc_data.xml; head -c 300 /root/.m2/abc_data.xml; echo'

# Stage 2: Run the application
FROM eclipse-temurin:17-jre
WORKDIR /app

#set timezone
# ENV TZ=UTC
# ENV JAVA_TOOL_OPTIONS="-Duser.timezone=UTC"

# Copy file jar đã build thành công từ stage trước
COPY --from=build /app/target/*.jar app.jar

# Expose spring boot default port
EXPOSE 8080


# Execute the application
ENTRYPOINT ["java", "-Duser.timezone=UTC","-jar", "app.jar"]
