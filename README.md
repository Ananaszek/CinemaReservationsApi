# CinemaReservationsApi
REST api for cinema reservations system - written in Java (1.8) with Spring framework.
Project is using H2 in memory database initialized with basic test data.
There is no frontend created - only REST api.

## Build and run
To build and run application go to file 'src/scripts' and run script called "run.sh"

## Demo 
To run demo based on curls go to file 'src/scripts' and run script called "useCase.sh". Use case covers basic path where user can see the list of movies in interval he chose, then booker (user) can choose screening and book seats, finally user can see expiration date for reservation and how much he has to pay. 

### Validations
It is forbidden to choose seats that are not neighbouring. User has to reserve tickets at least 15 minutes before screening and he has to type his name and surname with capital letters at the beggining.


### Additional - tests
Despite it wasn't requirement there are some basic unit and integration tests attached. There are a few of them but they were made to show that it is not something unknown.
