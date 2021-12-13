# Jumia Services Exercise

## Build

### Build image

`docker build -t jumia-exercise .`

### Run image

`docker run -p 8080:8080 jumia-exercise`

## Endpoints

### List all phones

`curl http://localhost:8080/phones`

### List all phones with pagination

`curl http://localhost:8080/phones?pageSize=10&pageNumber=1`

### List phones by country

`curl http://localhost:8080/phones?country=Uganda`

### List phones by state

`curl http://localhost:8080/phones?state=1`
