#Transaction Mapper#

You can view the OpenAPI Swagger HTML Documentation here:  `http://localhost:9090/trans-mapper/index.html`

The OpenAPI Swagger JSON file is here:  `trans-mapper/src/main/resources/pbirk007-TransactionMapper-0.0.1-resolved.json`

To build and run the system, issue the following Maven command from trans-mapper 
root directory:  `mvn clean install tomcat7:run`

All of the testcases will run by default when you run the above.  To avoid running them,
add -DskipTests to the above command.

This will start the API in Tomcat and you can access the three endpoints from these URLs:  

`- POST http://localhost:9090/trans-mapper/api/v1/transactions`
`- POST http://localhost:9090/trans-mapper/api/v1/transactions/type/{type}`
`- POST http://localhost:9090/trans-mapper/api/v1/transactions/type/{type}/amount`

I created a Postman Collection and put it in `trans-mapper/src/main/resources/TransactionMapper.postman_collection.json`.
This has sample data ready to run, including the userid/password for accessing the API.

You will need to add a Basic Authorization header for userid=user and password=user.  I realize
this is very simple security, but it demonstrates the user of Spring Security and does not drag
in an OAuth provider and/or LDAP registry dependencies. 

The key parts of the Java code is in the Custom Deserializers.  I did it this way with 
performance in mind.  With control of the deserialization, I could filter, sum, and map during this
process so that I didn't have to process the entire list of transactions more than once.  This can 
map 100 transactions in 15 ms (warmed up).

##Capabilities:##

- No Spring Boot dependencies are used.
- Utilizes the Servlet Spec for the REST API controller function.
- Custom de-serialization done for performance benefits.
- Created the 3 requested endpoints for mapping all transactions, by specified type, and amount by specified type.
- Created JUnit testcases which completely mock all of the API flows.
- Added logging using Logback which includes rollover support.  Logs are available in trans-mapper/logs/app.log.
- All endpoints are secured using Spring Security BasicAuth.  Use user/user for access.  Done this way only for demo purposes.
- The project is packaged as a WAR file in a Maven project.
- The web application starts up using the embedded Tomcat 7 plugin in the Maven pom.xml.
- Swagger OpenAPI file and documentation created and available at http://localhost:9090/trans-mapper/index.html.
- Postman collection is generated to simplify manual testing.
