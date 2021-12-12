# Jumia Services Exercise

## Build

### Build image

`docker build -t jumia-exercise .`

### Run image

`docker run -p 8080:8080 jumia-exercise`

## Endpoints

### List all phones

`curl http://localhost:8080/listPhones`

### List all phones with pagination

`curl http://localhost:8080/listPhones?pageSize=10&pageNumber=1`

### List phones by country

`curl http://localhost:8080/listPhones?country=Uganda`

### List phones by state

`curl http://localhost:8080/listPhones?state=1`
