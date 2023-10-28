# To-Do List Spring Boot Project

This is a simple Spring Boot project called **To-Do List** that allows you to manage a list of tasks. The project implements all the basic CRUD (Create, Read, Update, Delete) operations through both a frontend using Thymeleaf templates and a RESTful API. In addition, it provides a user authentication system, allowing multiple users to use the application. Data is stored in a PostgreSQL database.

## Features

- **Create**: Add new tasks to your to-do list.
- **Read**: View your list of tasks, mark them as complete.
- **Update**: Modify existing tasks to change their details.
- **Delete**: Remove tasks when they are no longer needed.
- **User Authentication**: Register and log in to the application to keep your tasks private.

## Getting Started

Follow these steps to get started:

1. Clone this repository to your local machine.

2. Make sure you have Docker and Maven installed.

3. Navigate to the project directory in your terminal.

4. Run the following command to start the application and the database:

   ```bash
   mvn spring-boot:run
   ```

5. The application and database in Docker will start automatically, you can access the application by opening a web browser and navigating to [http://localhost:8080](http://localhost:8080).

## Technologies Used

- **Spring Boot**: The framework for building the application.
- **Thymeleaf**: Used for server-side HTML templating.
- **Spring Web**: Part of the Spring Framework for web application development.
- **Spring Data**: Simplifies data access and interactions with databases.
- **Spring Security**: Manages authentication and authorization in Spring applications.
- **PostgreSQL**: The relational database for storing task data.
- **Docker**: Used for containerization.

# Rest API
## Authentication

All endpoints require HTTP Basic Authentication. Users must provide valid credentials to access the API.

- **Username:** Your username
- **Password:** Your password

## Endpoints

### 1. Get Tasks

- **Endpoint:** `/api/tasks`
- **Method:** `GET`
- **Description:** Retrieve a list of all tasks in the TodoList.
- **Response:** JSON array containing task objects.

### 2. Get Specific Task

- **Endpoint:** `/api/tasks/{taskId}`
- **Method:** `GET`
- **Description:** Retrieve a specific task by providing its `taskId` as a path parameter.
- **Response:** JSON object representing the task with the specified `taskId`.

### 3. Create Task

- **Endpoint:** `/api/tasks`
- **Method:** `POST`
- **Description:** Create a new task by providing the following fields in the request body:
   - `name` (string): The name of the task.
   - `details` (string): Additional details or description of the task.
   - `status` (boolean): The status of the task (true for completed, false for incomplete).
- **Request Body:** JSON object containing the task details.
- **Response:** JSON object representing the created task.

### 4. Update Task

- **Endpoint:** `/api/tasks/{taskId}`
- **Method:** `PUT`
- **Description:** Update an existing task by providing its `taskId` as a path parameter and the updated task details in the request body. This endpoint replaces the entire task with the provided data.
- **Request Body:** JSON object containing the updated task details (including `name`, `details`, and `status`).
- **Response:** JSON object representing the updated task.

### 5. Toggle Task Status

- **Endpoint:** `/api/tasks/{taskId}`
- **Method:** `PATCH`
- **Description:** Toggle the status of a specific task by providing its `taskId` as a path parameter. If the task is marked as completed, it will be set as incomplete, and vice versa.
- **Response:** JSON object representing the updated task.

### 6. Delete Task

- **Endpoint:** `/api/tasks/{taskId}`
- **Method:** `DELETE`
- **Description:** Delete a specific task by providing its `taskId` as a path parameter.
- **Response:** HTTP status code 204 (No Content) upon successful deletion.

## Development and Contributions

If you would like to contribute to this project or customize it for your needs, feel free to fork the repository and submit pull requests. You can also report any issues you encounter in the [Issues](https://github.com/VaynerAkaWalo/To-Do_List/issues) section.

## License

This project is open-source and available under the [MIT License](LICENSE.md). You are free to use and modify it for your own purposes.

---

**Enjoy managing your to-do list with the To-Do List Spring Boot Project!**
