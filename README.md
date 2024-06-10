# Loan Application

This project is a full-stack application that provides an API to fetch loan data and a frontend to display the data.


### Project Structure

- loanApp/ - Contains the Kotlin backend application.
- loan-react/ - Contains the React frontend application.

## Backend

The backend is built with Kotlin and Ktor, and it uses an SQLite database.

### Running the Backend

1. Place the [LoanStats_securev1_2017Q4.sqlite](https://drive.google.com/file/d/1nvkQHOz2KVLRjnhYxdziVsBQzWwj81Ae/view?usp=share_link) file in the backend project root within `loanApp/`.

### API Endpoints

- GET /loans - Retrieves a list of loans with optional query parameters for filtering and sorting.

### Query Parameters

- date: Filter by issue date (e.g., 2020-01-01).
- state: Filter by address state (e.g., CA).
- grade: Filter by loan grade (e.g., A).
- ficoLow: Filter by minimum FICO range (e.g., 600).
- ficoHigh: Filter by maximum FICO range (e.g., 700).
- sortBy: Sort by date, state, grade, or fico.
- order: Ascending by default or set 'desc'
- limit: Limit the number of results (e.g., 50).Query Parameters


### Example URLs for Testing

1. Retrieve the top 100 loans:

```bash
GET http://localhost:8080/loans
```

Filter by date:

```bash
GET http://localhost:8080/loans?date=2020-01-01
```

- Filter by state:

```bash
GET http://localhost:8080/loans?state=CA
```

- Filter by grade:

```bash
GET http://localhost:8080/loans?grade=A
```

- Filter by minimum FICO range:

```bash
GET http://localhost:8080/loans?ficoLow=600
```

- Filter by maximum FICO range:

```bash
GET http://localhost:8080/loans?ficoHigh=700
```

- Sort by date:

```bash
GET http://localhost:8080/loans?sortBy=date
```

- Sort by state:

```bash
GET http://localhost:8080/loans?sortBy=state
```

- Sort by grade:

```bash
GET http://localhost:8080/loans?sortBy=grade
```

- Sort by FICO range:

```bash
GET http://localhost:8080/loans?sortBy=fico
```

- Limit the number of results:

```bash
GET http://localhost:8080/loans?limit=50
```

- Combine filters and sorting:

```bash
GET http://localhost:8080/loans?state=CA&grade=A&ficoLow=600&ficoHigh=700&sortBy=date&limit=50
```

## Frontend

The frontend is built with React.

### Running the Frontend

- Navigate to the loan-react directory.

- Install dependencies:

```sh
npm install
```

- Start the frontend:

```sh
    npm start
```


### Technologies Used

- Backend: Kotlin, Ktor, Exposed, SQLite, HikariCP
- Frontend: React, Axios