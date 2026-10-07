# InfoManager — Backend

Spring Boot REST API for InfoManager, a small full-stack CRUD application for managing
user records. Written during my summer 2022 internship at PHITECH.

The Angular client that goes with it lives in
[**infomanager-frontend**](https://github.com/muazfurkan/infomanager-frontend).

## Architecture

```
  Angular 14 SPA  ──HTTP/JSON──>  Spring Boot REST API  ──JPA/Hibernate──>  MySQL
  (infomanager-frontend)          (this repository)
```

Layered the conventional Spring way: `controller` -> `service` -> `repo` -> entity.
Filtering and paging are done in the data layer with JPA `Specification`s rather than
in the client, so the API returns a `Page<User>` and the SPA just renders what it gets.

| Package | Role |
|---|---|
| `controller` | `UserController` (CRUD + search), `AuthController` (token endpoint) |
| `service` | `UserServiceImpl`, `AdminServiceImpl`, `CustomAuthenticationProvider`, `AdminDetailsService` |
| `repo` | Spring Data repositories plus `UserSpecification` / `AdminSpecification` for dynamic queries |
| `model` | `User`, `Admin` entities; DTOs; `UserPage` and `UserSearchCriteria` for paging/filter params |
| `config` | `DefaultSecurityConfig`, `AuthServerConfig` (OAuth2 authorization server) |
| `jwt` | `JwtTokenUtil` — token generation and claim parsing |
| `handler` | Centralised `ExceptionHandler` |

## API

| Method | Path | Description |
|---|---|---|
| `GET` | `/users` | Paged list of users (`pageSize`, `pageNumber`, `sortBy`, `sortDirection`) |
| `GET` | `/filtered` | Paged list filtered by `filterText` on a chosen `criteria` field |
| `POST` | `/users/adduser` | Create a user (bean-validated) |
| `GET` | `/users/edituser/{id}` | Fetch one user |
| `PUT` | `/users/edituser/{id}` | Update a user |
| `DELETE` | `/users/delete/{id}` | Delete a user |
| `POST` | `/token` | Issue a JWT |

`User` is `id, name, email, phone, address` (`name` and `email` non-null). `Admin` is
`id, adminName, password, role, enabled`. `src/main/resources/json/MOCK_DATA.json`
holds 1000 generated user records for seeding.

## Stack

Java 17 · Spring Boot 3.0.0-RC1 · Spring Web · Spring Data JPA / Hibernate ·
Spring Security · Spring Authorization Server 1.0.0 · OAuth2 client & resource server ·
jjwt · MySQL (`mysql-connector-j`) · Lombok · Jakarta Validation · Maven

## Running it

**1. Configuration.** The real `application.yml` is gitignored because it holds
credentials. Start from the template:

```bash
cp src/main/resources/application.yml.example src/main/resources/application.yml
```

Then fill in your own `spring.datasource` username/password/url and a
`client-secret`. Values marked `CHANGE_ME_*` must be replaced.

**2. Database.** MySQL needs to be running and reachable at the URL you configured.
`ddl-auto: update` means Hibernate creates and migrates the schema itself, so an empty
database is enough.

**3. Run.**

```bash
./mvnw spring-boot:run
```

**4. Frontend.** Start the Angular client separately — see its README.

## Known issues

This is internship-era code, archived as it was. If you clone it and it doesn't start
or the UI can't reach it, these are the reasons, in the order you'll hit them:

- **Port mismatch.** This service binds `server.port: 9000`, but the frontend hardcodes
  `http://localhost:8080`. Set the port to `8080` here, or change the URLs in the
  client's `user-service.service.ts`.
- **`issuer-uri: http://auth-server:9000`** refers to a hostname that won't resolve on
  a normal machine. Point it at `localhost` or add a hosts entry.
- **Spring Boot `3.0.0-RC1`** is a release candidate pinned in `pom.xml`. It may no
  longer resolve cleanly; moving to a 3.0.x final is the fix.
- **Everything requires authentication.** `DefaultSecurityConfig` applies
  `anyRequest().authenticated()` with form login, so unauthenticated API calls from the
  SPA get redirected to a login page rather than served.
- **`POST /token` doesn't bind its second parameter.** `getToken(@RequestBody String
  username, String password)` has only one `@RequestBody`, so `password` arrives null.
- **Tokens aren't verifiable.** `JwtTokenUtil.generateToken` mints a fresh random
  signing key on every call, so no issued token can be validated afterwards; `getClaims`
  correspondingly parses unsigned claims. Signing needs a single stable key, loaded
  from configuration.

I've left these in place rather than quietly fixing them — the repository is a snapshot
of what I wrote in 2022, not something I maintain.

## Note on configuration

Everything in this project was written against a local internship-era environment, and
the config values were demo values for that machine — not credentials for anything real
or reachable. They are no longer in the tree: `application.yml` is gitignored and
`application.yml.example` is the template to copy. An unused hardcoded constant in
`JwtTokenUtil` was also removed; it was dead code, since the signing key is generated
at call time.
