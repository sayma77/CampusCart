#!/bin/sh

# Wait until MariaDB is ready
until mariadb-admin ping -h "localhost" --silent; do
  echo "Waiting for MariaDB to start..."
  sleep 1
done

# Create database if it doesn't exist
mariadb -uroot -e "CREATE DATABASE IF NOT EXISTS campuscart;"

# Grant privileges for root to connect from any host (TCP)
mariadb -uroot -e "CREATE USER IF NOT EXISTS 'root'@'127.0.0.1' IDENTIFIED BY '';"
mariadb -uroot -e "GRANT ALL PRIVILEGES ON *.* TO 'root'@'127.0.0.1' WITH GRANT OPTION;"
mariadb -uroot -e "FLUSH PRIVILEGES;"

echo "Database and privileges set up!"
