Clone the project
Replace your spring.datasource.username and 
             spring.datasource.password
             and other application propertis if any.
Run ToDoAppApplication.java file
Check REST APIS with postmon

     * Register a User
     
     URL: http://localhost:8080/auth/register
     Method: POST
     Content-Type: application/json
     Body: {
    "email": "test@example.com",
    "password": "password123"
    }
    

    * Login User

    URL: http://localhost:8080/auth/login
    Method: POST
    Body :
    {
    "email": "test@example.com",
    "password": "password123"
    }
    Response:
       Success:{
    "token": "YOUR_JWT_TOKEN"
    }

   
   * Create a Todo
   
   URL: http://localhost:8080/todos
   Method: POST
   Headers:
     Authorization: Bearer YOUR_JWT_TOKEN (replace with the token received from login)
   Body: {
    "title": "Buy Groceries",
    "description": "Milk, Bread, Eggs"
    }


  * Get Todos
    
  URL: http://localhost:8080/todos
  Method: GET
  Headers:
    Authorization: Bearer YOUR_JWT_TOKEN (replace with your actual token)


  * Update a Todo
     
  URL: http://localhost:8080/todos/1 (replace 1 with the actual Todo ID)
  Method: PUT
  Headers:
       Authorization: Bearer YOUR_JWT_TOKEN (replace with your actual token)
  Body: {
    "title": "Buy Groceries and Snacks",
    "description": "Milk, Bread, Eggs, Chips"
    }


  * Delete a Todo 
  
  URL: http://localhost:8080/todos/1 (replace 1 with the actual Todo ID)
  Method: DELETE
  Headers:
  Authorization: Bearer YOUR_JWT_TOKEN (replace with your actual token)


    

