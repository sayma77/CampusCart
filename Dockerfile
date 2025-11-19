FROM gradle:8.5-jdk21 AS build
WORKDIR /app

# Copy only Gradle files first (enables caching)
COPY gradlew .
COPY gradle ./gradle
COPY build.gradle settings.gradle ./

# Let Gradle download itself here and cache the layer
RUN ./gradlew --version

COPY . .
# set the docker application properties
COPY docker/application.properties src/main/resources/application.properties
RUN ./gradlew build -x test --no-daemon

FROM eclipse-temurin:21-jdk-alpine

# Install MariaDB + Supervisor
RUN apk update && \
    apk add --no-cache mariadb mariadb-client supervisor

# Create MySQL directories
RUN mkdir -p /run/mysqld && \
    mkdir -p /var/lib/mysql && \
    chown -R mysql:mysql /var/lib/mysql /run/mysqld

# Init DB (empty)
RUN mariadb-install-db --user=mysql --datadir=/var/lib/mysql

# Create upload dir
RUN mkdir -p /app/uploads

# Supervisor config
COPY docker /app/docker
COPY docker/supervisord.conf /etc/supervisord.conf
COPY docker/my.cnf /etc/my.cnf

# Copy your Spring Boot jar
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar

ENV PORT=80
EXPOSE 80

CMD ["supervisord", "-c", "/etc/supervisord.conf"]
