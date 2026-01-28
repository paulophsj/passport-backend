# 🛂 Passport - Sistema de Gerenciamento de Transportes

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)
![Swagger](https://img.shields.io/badge/-Swagger-%23C0E317?style=for-the-badge&logo=swagger&logoColor=black)

> Sistema de gerenciamento de usuários e cartões de transporte, desenvolvido com **Spring Boot**, focado em segurança, organização e boas práticas.

---

## 📌 Visão Geral

O **Passport** é uma API REST desenvolvida em **Spring Boot** com o objetivo de **gerenciar usuários e seus cartões de transporte**. O projeto foi pensado para ser escalável, seguro e bem estruturado, seguindo padrões comuns do ecossistema Java e do GitHub.

O sistema possui **dois tipos de usuários**:

* 👑 **ADMIN** – possui permissões administrativas e acesso ampliado
* 👤 **CLIENTE** – possui acesso restrito às funcionalidades permitidas

Esses perfis garantem **autorizações distintas**, controladas via **Spring Security + JWT**.

---

## 🧱 Arquitetura do Projeto

A estrutura do projeto foi organizada para facilitar manutenção e escalabilidade:

```text
src/main/java/com/phsj/passport
├── api
│   ├── controllers
│   └── dto
├── model
│   ├── entities
│   └── services
├── util
│   └── helpers e ferramentas auxiliares
├── config
│   ├── segurança
│   └── configuração de rotas e autorizações
```

---

## 🚀 Tecnologias Utilizadas

* **Java 17**
* **Spring Boot**
* **Spring Data JPA**
* **Spring Security**
* **JWT (JSON Web Token)**
* **Bean Validation**
* **PostgreSQL**
* **Swagger / SpringDoc OpenAPI**
* **Docker & Docker Compose**
* **Maven**

---

## 📦 Dependências Principais

* 🔐 **JWT** – Autenticação e autorização baseada em token
* 🛡 **Spring Security** – Controle de acesso e permissões
* 🧪 **Validation** – Validação de dados de entrada
* 🗄 **JPA / Hibernate** – Persistência e integração com banco de dados
* 📘 **Swagger** – Documentação interativa da API

---

## 🐘 Banco de Dados (PostgreSQL com Docker)

O projeto já conta com um **docker-compose** para subir o banco de dados automaticamente.

### 📄 docker-compose.yml

```yaml
services:
  passport:
    image: postgres:18
    restart: always
    environment:
      POSTGRES_DB: passport
      POSTGRES_PASSWORD: passport
      POSTGRES_USER: passport
    ports:
      - 5436:5432
    volumes:
      - ./data/passport:/var/lib/postgresql
      - ./src/main/resources/data.sql:/docker-entrypoint-initdb.d/data.sql
```

> ⚠️ **Importante:**
> Caso a porta `5436` já esteja em uso na sua máquina, basta alterá-la para outra disponível.

---

## 🔌 Configuração do Banco de Dados

A conexão com o banco é feita **via URL**, contendo usuário e senha embutidos.

### 📄 application.properties

```properties
spring.application.name=passport

server.port=8080
spring.config.import=optional:file:.env[.properties]

# Datasource (levantando a aplicação sem container)
spring.datasource.driver-class-name=org.postgresql.Driver
spring.datasource.url=jdbc:postgresql://localhost:5436/passport?user=passport&password=passport

# JPA
spring.jpa.hibernate.ddl-auto=update
spring.sql.init.mode=always
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.properties.hibernate.temp.use_jdbc_metadata_defaults=false

# Spring Security (PT_BR)
spring.messages.basename=messages
spring.mvc.locale=pt_BR
spring.mvc.locale-resolver=fixed

# Swagger
springdoc.api-docs.enabled=true
springdoc.swagger-ui.enabled=true

springdoc.enable-data-rest=false
springdoc.enable-hateoas=false
springdoc.enable-kotlin=false
springdoc.packages-to-scan=com.phsj.passport

# JWT
security.jwt.secret-key=3cfa76ef14937c1c0ea519f8fc057a80fcd04a7420f8e8bcd0a7567c272e007b
security.jwt.expiration-time=3600000
```

---

## 🛠️ Como Clonar e Executar o Projeto

### 1️⃣ Clonar o repositório

```bash
git clone https://github.com/paulophsj/passport-backend.git
```

```bash
cd passport
```

---

### 2️⃣ Subir o banco de dados com Docker

Certifique-se de que o **Docker** e o **Docker Compose** estão instalados.

```bash
docker compose up -d
```

> Isso irá subir um container PostgreSQL já configurado para o projeto.

---

### 3️⃣ Rodar a aplicação Spring Boot

Você pode rodar de duas formas:

#### ▶️ Via Maven

```bash
mvn spring-boot:run
```

#### ▶️ Via IDE (IntelliJ / Eclipse)

* Abra o projeto
* Localize a classe `PassportApplication`
* Execute como **Spring Boot Application**

---

### 4️⃣ Acessar a aplicação

* 🚀 **API:** `http://localhost:8080`
* 📘 **Swagger UI:**

  ```
  http://localhost:8080/swagger-ui.html
  ```

---

## 🔐 Segurança e Autorização

* Autenticação baseada em **JWT**
* Tokens com expiração de **1 hora**
* Perfis de acesso:

    * **ADMIN**
    * **CLIENTE**
* Controle de rotas feito via **Spring Security**

---

## 📖 Documentação da API

A documentação é gerada automaticamente pelo **Swagger**.

Após subir o projeto, acesse:

```
http://localhost:8080/swagger-ui.html
```

---

## 📄 Licença

Este projeto é de uso educacional e pode ser adaptado conforme a necessidade.

---

✨ **Passport** — Segurança, organização e controle no gerenciamento de usuários e cartões de transporte.
