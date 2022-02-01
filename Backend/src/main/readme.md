# Shop CRUD backend
## Set up database
### Use exact these commands (database config is hardcoded)
### Schema is created on the first run

1. Run Docker Postgres container
```
docker run --name shop-psql -e POSTGRES_PASSWORD=postgres -p 5432:5432 -d postgres:13.2
```
2. Initialize schema

- Connect to the database
```
docker exec -it shop-psql psql -U postgres
```

- Create schema and user
```
CREATE DATABASE shop;
CREATE USER shop_app PASSWORD 'shop';
GRANT ALL PRIVILEGES ON DATABASE shop TO shop_app;
```

