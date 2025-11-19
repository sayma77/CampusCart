#!/bin/sh

# Wait until MariaDB is ready
until mysqladmin ping -h "localhost" --silent; do
  echo "Waiting for MariaDB to start..."
  sleep 2
done

# Create database if it doesn't exist
mariadb -uroot -e "CREATE DATABASE IF NOT EXISTS campuscart;"

# Grant privileges for root to connect from any host (TCP)
mysql -uroot -e "CREATE USER IF NOT EXISTS 'root'@'127.0.0.1' IDENTIFIED BY '';"
mysql -uroot -e "GRANT ALL PRIVILEGES ON *.* TO 'root'@'127.0.0.1' WITH GRANT OPTION;"
mysql -uroot -e "FLUSH PRIVILEGES;"

echo "Database and privileges set up!"
