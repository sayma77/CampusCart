FROM gradle:8.14.3-jdk21 AS build
WORKDIR /app
COPY . .
# set the docker application properties
COPY docker/application.properties src/main/resources/application.properties
RUN ./gradlew build -x test --no-daemon --parallel

FROM eclipse-temurin:21-jre-alpine

RUN adduser -D springuser

# Install MariaDB + Supervisor
RUN apk update && \
    apk add --no-cache mariadb mariadb-client supervisor && \
    rm -rf /var/cache/apk/*

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
RUN chown -R springuser:springuser /app

ENV PORT=80
EXPOSE 80

CMD ["supervisord", "-n", "-c", "/etc/supervisord.conf"]
