# urlShortener
Url shortener app make using Java and Springboot
The app has methods to:
* Create a mapping from a short path to a long url
* Force delete a mapping
* Redirect a path to a long url in methods (GET, POST, PUT, PATCH and DELETE)
* Get statistics about how many mappings are stored
* Get statistics about redirections made between dates
* Get statistics abour changes in stored mappings (create, scheduler delete, force delete, refresh)

The app also has a scheduled process that deletes old mappings that are not used for more than a configured number of days

The number characters used for the short path is configured
The number of tries to find a unique string is also configured

# To package application into a JAR
```bash
mvn package
```

# To run compose:
```bash
docker-compose up --build
```

# Swagger
Documentation can be found at /v2/api-docs and also on /swagger-ui.html

Date params follows the iso time, for example: 2019-01-01T00:00:00+00:00

