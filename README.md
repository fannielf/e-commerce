# Buy-01 E‑Commerce Platform

End‑to‑end e‑commerce system built with Spring Boot microservices and an Angular frontend. Clients browse products; sellers manage their catalog.

## Architecture

* **User Service** – registration, login (JWT), roles (CLIENT, SELLER), profile.
* **Product Service** – CRUD for products, ownership checks, image refs.
* **Media Service** – image upload/download, storing images on server, 2MB limit, MIME validation.
* **Gateway** – routing, CORS, JWT validation.
* **Discovery** – service discovery.
* **Frontend** – Angular SPA with routing, guards, interceptors.
* **Database** – MongoDB for services.
* **Kafka** – PRODUCT_DELETED, USER_DELETED events.

## Features

### Backend

* Separate microservices.
* JWT Authentication.
* Sellers: create/edit/delete products and upload/delete images as well as profile avatar.
* Clients: browse products.
* Image validation (image/*, max 2MB).

### Frontend

* Auth pages (login/sign‑up).
* Seller dashboard (manage products and related media).
* Public product listing.
* Angular Material/Bootstrap UI.
* Guards, interceptors, reactive forms.

## Endpoints Summary

All calls go through the Gateway over HTTPS. Internal service URLs are hidden.

## Public (Client)

* GET https://localhost:8443/product-service/api/products — list products 
* GET https://localhost:8443/product-service/api/products/{id} — product details

## Auth (User Service)

* POST https://localhost:8443/user-service/api/auth/signup
* POST https://localhost:8443/user-service/api/auth/login
* GET https://localhost:8443/user-service/api/users/me
* PUT https://localhost:8443/user-service/api/users/me

## Seller (Product Management)

* POST https://localhost:8443/product-service/api/products
* PUT https://localhost:8443/product-service/api/products/{id}
* DELETE https://localhost:8443/product-service/api/products/{id}

## Media (Images)

* GET https://localhost:8443/media-service/api/media/images/{id} — fetch product image
* GET https://localhost:8443/media-service/api/media/avatar/{filename} — fetch user avatar

## Running the Project with Docker

Requires Docker + Docker Compose.

### 1. Clone the Repository

```
git clone https://01.gritlab.ax/git/kschauma/buy-01
cd buy-01
```

### 2. Build Backend Services

Each service has its own `Dockerfile`.

```
docker compose build
```

### 3. Start the Entire Platform

```
docker compose up
```

The following components start:

* user-service
* product-service
* media-service
* gateway-service
* discovery-service
* mongo
* kafka
* angular-frontend

### 4. Access Points

* **Frontend:** [https://localhost:4200](https://localhost:4200)
* **Gateway:** [https://localhost:8443](https://localhost:8443)
* **Discovery UI (dev only):** [http://localhost:8761](http://localhost:8761)

### 5. Stopping

```
docker compose down
```

## Dev Workflow

* Implement services in their folders.
* Export ports for local development.
* Use `docker compose up --build` when updating code.
* Nginx serves the Angular frontend, handles HTTPS, and proxies API requests to the gateway.

## Project Status

Phase 1 focuses on getting the full stack running end-to-end and building the seller-side features.

Upcoming planned phases:

**Phase 2**: Deployment pipeline (CI/CD) with Jenkins.

**Phase 3**: Code quality checks with SonarQube.

**Phase 4**: Client-side features: product ordering + order history.

## Authors

* [Kira](https://github.com/kiraschauman)
* [Linnea](https://github.com/Linnie43)
* [Maris](https://github.com/karusmari)
* [Fanni](https://github.com/fannielf)
