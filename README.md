# Elasticsearch with Spring Boot
This project demonstrates how to integrate Elasticsearch with a Spring Boot application for efficient search functionality.

## Features:
- [X] Elastic Spring Data JPA to implement CRUD.
- [X] Custom `/search` endpoint using custom queries with both : **Criteria** and **NativeQuery** options.
- [X] Pagination & Sorting.
- [X] Centralized Logging using **ELK Stack** (Elasticsearch + Logstash + Kibana).
- [X] Dockerized environment with all dependencies.

## Running the Application:
### 1. Prerequisites:
- Java 17+
- Maven

### 2. Clone the project:
``` Bash
git clone https://github.com/3umarG/Elasticsearch-Spring-Boot.git
```

### 3. Run Dokcer Compose:
``` Bash
docker-compose up -d
```

### 4. Check started dependencies:
- ElasticSearch is running on http://localhost:9200
- Kibana is running on http://localhost:5601

### 5. Build and Run
``` Bash
mvn clean install
mvn spring-boot:run
```

****

## Endpoints:
1. **GET /api/books/log**: create some logs for testing purposes
   - CURL Example:
     ```bash
     curl -X GET http://localhost:8080/api/books/log
     ```

2. **GET /api/books**: get all books.
   - CURL Example:
     ```bash
     curl -X GET http://localhost:8080/api/books
     ```

3. **GET /api/books/{id}**: get book by id.
   - CURL Example:
     ```bash
     curl -X GET http://localhost:8080/api/books/{id}
     ```

4. **POST /api/books**: create new book.
   - CURL Example:
     ```bash
     curl -X POST -H "Content-Type: application/json" -d '{"title":"Book Title", "author":"Author Name", "price":19.99, "content":"Book Content"}' http://localhost:8080/api/books
     ```

5. **PUT /api/books/{id}**: update book by id.
   - CURL Example:
     ```bash
     curl -X PUT -H "Content-Type: application/json" -d '{"title":"Updated Title", "author":"Updated Author", "price":29.99, "content":"Updated Content"}' http://localhost:8080/api/books/{id}
     ```

6. **DELETE /api/books/{id}**: delete book by id.
   - CURL Example:
     ```bash
     curl -X DELETE http://localhost:8080/api/books/{id}
     ```

7. **GET /api/books/search/native-query**: search using **`NativeQuery`**
   - CURL Example:
     ```bash
     curl -X GET 'http://localhost:8080/api/books/search/native-query?q=search_query&page=1&size=10'
     ```

8. **GET /api/books/search/criteria-query**: search using **`CriteriaQuery`**
   - CURL Example:
     ```bash
     curl -X GET 'http://localhost:8080/api/books/search/criteria-query?q=search_query&page=1&size=10'
     ```

     ***
## What is ELK ??
### 1. Elasticsearch
Elasticsearch is primarily used as a search and analytics engine.<br>
For logs management, Elasticsearch serves as the storage and search engine which indexes the log data received from Logstash, making it searchable and allowing for fast and efficient querying and analysis of the logs.

### 2.Logstash
A log pipeline tool that accepts inputs from various sources, executes different transformations, and exports the data to various targets such as Elasticsearch.

### 3.Kibana
Kibana is an open-source data visualization and exploration tool designed for use with Elasticsearch.<br>
Kibana provides a user-friendly interface for exploring, analyzing, and visualizing data(logs) stored in Elasticsearch indices.

![ELK-stack-700x513](https://github.com/3umarG/Elasticsearch-Spring-Boot/assets/90159439/7f1d0d67-78d2-4a10-abe8-dd5fd89174f1)

***

## Setup Logs:
**Logstash** reads the input/logs collected from our log files that are in `/logs` directory and then forwards it to the ElasticSearch at specified port as an output.
<br>
So, to read the logs and forward it to ElasticSearch, we need to specify input and output elements under `logstash.conf` file.


## Setup Index Patterns using Kibana
As your Kibana will be running on http://localhost:5601/ , you can create your index pattern from _Menu_ → _Stack Management_ → _Index Pattern_ i.e http://localhost:5601/app/management/kibana/indexPatterns. Below screen will be displayed.

![image](https://github.com/3umarG/Elasticsearch-Spring-Boot/assets/90159439/9d5b0576-4758-4e5d-92c9-7082548952a8)


## References:
- [Medium blog](https://medium.com/@shala.p02/centralized-logging-using-elk-as-a-docker-container-for-microservices-in-just-4-steps-4f4cdf278712)
- [Taweel Introduction video](https://youtu.be/61DRyjvQ2qY?feature=shared)
- [Playlist for Elasticsearch Fundamentals](https://www.youtube.com/playlist?list=PLCgehTvigkDOrHcRNjvq5yDH5fyNw50ja)
- [Eng. Yasser Elsayed live](https://www.youtube.com/watch?v=4AnqN9NgWuM)
- [Spring Data Elasticsearch Playlist](https://www.youtube.com/playlist?list=PLXy8DQl3058OoJqGLFdqoBkBKm2T0kS9B)
- [Elasticsearch with Spring Boot concepts Playlist](https://www.youtube.com/playlist?list=PLoNChWlyFPxcB-jY277teAoJXtNNCcifM)
- [Eltaweel Deep dive live](https://www.youtube.com/live/ESWsUqoDGPI?feature=shared)

