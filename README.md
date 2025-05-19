## Play recruitment project - SMS validation

### How to run

- Building from source code:
    - run `docker compose up --build`
- From image:
    - run `docker pull mswierad/playproject-server:latest`
    - launch image

The application will be then accessible under `localhost:8080`.

### HTTP endpoints

- `POST /messages`
    - body:
      ```
      {
        "sender": string,
        "recipient": string,
        "message": string
      }
      ```
    - Analyzes the received message and checks if it contains a URL **or**, if the `recipient` field contains the
      configured number, if the message is a request to opt in/opt out of SMS evaluation (the `message` field is equal
      to either `START` or `STOP`). If it contains a URL and the recipient opted in for evaluation, the URL is sent to
      an evaluation service. If the service response indicates a threat level higher than the configured maximum, the
      message is not saved in the database and the incident is logged. Otherwise it is logged quietly.
- `GET /messages`
    - param: `number`
    - Returns all stored messages received by the specified number.
- `GET /customers`
    - Returns all customers.

### Assumptions and notes

- All URLs are assumed to start with a protocol (either `http://` or `https://`), optionally followed by `www`, followed
  by domain, port and path.
- An internal H2 database is used for this project for testing purposes, but it can easily be replaced with PostgresSQL
  or other relational database.
- Some initial entries for users are included in the `resources/data.sql` file, mainly for testing and showcasing
  purposes.
- Configuration is provided in `resources/application.properties`. It contains the default number for opting in/out of
  evaluation, the max allowed threat level and the checked threat types.
- The application does not use an actual, external evaluation service. It is currently implemented as
  `DummyEvaluationService`, which returns a random threat level per each request.
- The database schema is not defined in a file, it is instead created automatically by Spring.