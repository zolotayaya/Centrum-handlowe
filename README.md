# Centrum-handlowe

## Before running application, start docker container with postgres db

```bash
docker run --name shopping-center-postgres \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=datax \
  -e POSTGRES_DB=postgres \
  -p 5432:5432 \
  -d postgres
```