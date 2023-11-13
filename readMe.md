# Bank api java spring project

## Instructions

- clone the project
- download all dependencies
- create a db according to the application.properties information file
- run the app


## Endpoints

### FOR CLIENTS:

- GET: /api/clients?pageNumber=0&pageSize=10

- GET: /api/clients/:clientId

- POST: /api/clients

Body example: 

{
"address":  "Quito",
"age": 34,
"gender": "male",
"identification": 1716192847,
"name": "Olaf Deutsch",
"phoneNumber": 8830988790,
"password": "secret",
"status": true
}

- PUT / PATCH: /api/clients/:clientId

Body example:
{
"address":  "Quito",
"age": 36,
"gender": "male",
"identification": 1716192847,
"name": "Alex Tonello",
"phoneNumber": 8830988790,
"password": "12345",
"status": true
}

- DELETE: /api/clients/:clientId




### FOR ACCOUNTS

- POST (Create new account): /api/clients/:clientId/accounts

{
"accountNumber": 1505,
"accountType": "corriente" or "ahorros",
"initialBalance": 45000,
"state": true
}

- GET: /api/clients/:clientId/accounts

- GET: /api/clients/:clientId/accounts/:accountId

- PUT / PATCH: /api/clients/:clientId/accounts/:accountId

Body Example:
{
"accountNumber": 11,
"accountType": "ahorros",
"initialBalance": 10000,
"state": true
}

- DELETE: /api/clients/:clientId/accounts/:accountId




### FOR MOVEMENTS

- POST (Create new movement): /api/account/:accountId/movements

Body:
{
"movementType": "corriente",
"value": 500
}

- PUT/ PATCH: /api/account/:accountId/movements/:movementId

{
"date": "2023-06-06T03:36:58.811+00:00",
"movementType": "corriente",
"value": 55000,
"balance": 0,
"initialBalance": 0
}

- GET ALL: /api/account/:accountId/movements
- GET SINGLE: /api/account/:accountId/movements/:id
- DELETE: /api/account/:accountId/movements/:id




### FOR REPORTS

- GET: /api/account/22/movements/report

Params:

startDate : 2023-06-06T04:13:19.985Z

endDate : 2023-06-07T04:13:19.985Z
