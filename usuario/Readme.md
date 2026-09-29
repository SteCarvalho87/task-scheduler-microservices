# 👤 User Service API

Microsserviço responsável pelo gerenciamento de usuários de uma plataforma de agendamento de tarefas com notificações por e-mail.

Este serviço faz parte de uma arquitetura baseada em microsserviços, onde um **Backend For Frontend (BFF)** centraliza as requisições do cliente e orquestra a comunicação entre os demais serviços da aplicação.

O User Service é responsável por:

- Cadastro de usuários
- Autenticação utilizando JWT
- Consulta de usuários
- Atualização de dados cadastrais
- Exclusão de usuários
- Gerenciamento de endereços
- Gerenciamento de telefones
- Criptografia de senhas
- Validação de e-mails duplicados

---

# 🏗️ Arquitetura da Solução

```mermaid
flowchart LR

A[Frontend] --> B[BFF]

B --> C[User Service]
B --> D[Task Service]
B --> E[Notification Service]

C --> F[(MySQL)]

D --> G[(MySQL)]

E --> H[Servidor SMTP]
```

## Papel deste Microsserviço

O User Service é responsável exclusivamente pela gestão dos dados dos usuários da plataforma.

Suas responsabilidades incluem:

- Cadastro de novos usuários
- Autenticação e geração de token JWT
- Atualização de informações cadastrais
- Cadastro de endereços
- Cadastro de telefones
- Exclusão de usuários
- Validação de unicidade de e-mail

Após a autenticação, o sistema gera um token JWT utilizado para autorização das áreas protegidas da aplicação.

---

# 🚀 Tecnologias Utilizadas

## Backend

- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA
- JWT (JSON Web Token)
- Lombok

## Persistência

- MySQL

## Build e Gerenciamento de Dependências

- Gradle

## Arquitetura

- Microsserviços
- REST API
- BFF (Backend For Frontend)

---

# 📂 Estrutura do Projeto

```text
src
├── main
│   ├── java
│   │   └── com.stephanie.usuario
│   │
│   ├── business
│   │   ├── converter
│   │   ├── dto
│   │   └── UsuarioService
│   │
│   ├── controller
│   │   └── UsuarioController
│   │
│   ├── infrastructure
│   │   ├── entity
│   │   ├── exceptions
│   │   ├── repository
│   │   └── security
│   │
│   └── resources
│       └── application.properties
│
└── test
```

## Organização das Camadas

### Controller

Responsável por expor os endpoints REST da aplicação.

### Business

Camada onde ficam as regras de negócio.

### DTO

Objetos de transferência utilizados entre API e cliente.

### Converter

Responsável pela conversão entre Entidades e DTOs.

### Repository

Camada de acesso aos dados utilizando Spring Data JPA.

### Security

Implementação da autenticação e autorização utilizando JWT.

### Exceptions

Tratamento de exceções customizadas da aplicação.

---

# 📦 Dependências e Versões Necessárias

| Dependência | Versão |
|------------|---------|
| Java | 21+ |
| Spring Boot | 3+ |
| Gradle | 8+ |
| MySQL | 8+ |

---

# ⚙️ Como Rodar o Projeto

## 1. Clone o repositório

```bash
git clone https://github.com/seu-usuario/user-service.git
```

## 2. Entre na pasta do projeto

```bash
cd user-service
```

## 3. Configure o banco de dados MySQL

Crie um banco:

```sql
CREATE DATABASE userdb;
```

Configure o arquivo:

```properties
src/main/resources/application.properties
```

Exemplo:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/userdb
spring.datasource.username=root
spring.datasource.password=senha

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

## 4. Execute a aplicação

Linux/Mac:

```bash
./gradlew bootRun
```

Windows:

```bash
gradlew bootRun
```

---

## ✅ Validando a Execução

Após iniciar a aplicação, o terminal deverá exibir uma mensagem semelhante a:

```text
Started UsuarioApplication
```

A API estará disponível em:

```text
http://localhost:8080
```

---

# 🔐 Autenticação

A autenticação é realizada utilizando JWT.

Após efetuar login, o token gerado deve ser enviado no header:

```http
Authorization: Bearer TOKEN_JWT
```

Exemplo:

```http
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

---

# 📌 Endpoints Disponíveis

## ✅ Cadastro de Usuário

### Endpoint

```http
POST /usuario
```

### Request

```json
{
    "nome": "Stephanie Carvalho",
    "email": "stephanie@email.com",
    "senha": "123456"
}
```

### Response

```json
{
    "id": 1,
    "nome": "Stephanie Carvalho",
    "email": "stephanie@email.com"
}
```

---

## ✅ Login

### Endpoint

```http
POST /usuario/login
```

### Request

```json
{
    "email": "stephanie@email.com",
    "senha": "123456"
}
```

### Response

```text
Bearer eyJhbGciOiJIUzI1NiJ9...
```

---

## ✅ Buscar Usuário por E-mail

### Endpoint

```http
GET /usuario?email=stephanie@email.com
```

### Response

```json
{
    "id": 1,
    "nome": "Stephanie Carvalho",
    "email": "stephanie@email.com"
}
```

---

## ✅ Atualizar Dados do Usuário

### Endpoint

```http
PUT /usuario
```

### Header

```http
Authorization: Bearer TOKEN_JWT
```

### Request

```json
{
    "nome": "Stephanie A. Carvalho"
}
```

---

## ✅ Excluir Usuário

### Endpoint

```http
DELETE /usuario/{email}
```

Exemplo:

```http
DELETE /usuario/stephanie@email.com
```

---

## ✅ Cadastrar Endereço

### Endpoint

```http
POST /usuario/endereco
```

### Header

```http
Authorization: Bearer TOKEN_JWT
```

### Request

```json
{
    "logradouro": "Rua Exemplo",
    "numero": "100",
    "cidade": "Rio de Janeiro",
    "estado": "RJ"
}
```

---

## ✅ Atualizar Endereço

### Endpoint

```http
PUT /usuario/endereco?id=1
```

### Request

```json
{
    "logradouro": "Nova Rua",
    "numero": "200"
}
```

---

## ✅ Cadastrar Telefone

### Endpoint

```http
POST /usuario/telefone
```

### Header

```http
Authorization: Bearer TOKEN_JWT
```

### Request

```json
{
    "numero": "21999999999"
}
```

---

## ✅ Atualizar Telefone

### Endpoint

```http
PUT /usuario/telefone?id=1
```

### Request

```json
{
    "numero": "21888888888"
}
```

---

# 📋 Regras de Negócio

### Cadastro de Usuário

- Não é permitido cadastrar dois usuários com o mesmo e-mail.
- A senha é criptografada antes de ser persistida.
- O e-mail é utilizado como identificador principal do usuário.

### Login

- Apenas usuários válidos recebem token JWT.
- O token é utilizado para acessar operações protegidas.

### Atualização de Dados

- O usuário autenticado é identificado através do token JWT.
- Não é necessário informar novamente o e-mail na atualização.

### Endereço e Telefone

- O vínculo com o usuário é realizado automaticamente através da autenticação.
- Não é necessário informar o ID do usuário nas requisições.

---

# 🧪 Como Rodar os Testes

Execute:

```bash
./gradlew test
```

ou

```bash
gradlew test
```

Caso todos os testes passem, o Gradle exibirá:

```text
BUILD SUCCESSFUL
```

---

# ⚠️ Tratamento de Erros

A API possui exceções customizadas para lidar com cenários de negócio.

## ConflictException

Retornada quando um e-mail já está cadastrado.

Exemplo:

```json
{
    "message": "E-mail já cadastrado"
}
```

---

## ResourceNotFoundException

Retornada quando um recurso não é encontrado.

Exemplo:

```json
{
    "message": "Email não encontrado"
}
```

---

# ⚠️ Problemas Enfrentados Durante o Desenvolvimento

## 1. Cadastro de usuários duplicados

### Problema

Permitir múltiplos usuários utilizando o mesmo e-mail poderia comprometer a autenticação e integridade dos dados.

### Solução

Foi implementada uma validação utilizando:

```java
existsByEmail()
```

Com lançamento da exceção:

```java
ConflictException
```

---

## 2. Segurança das credenciais

### Problema

Armazenar senhas em texto puro representa um risco de segurança.

### Solução

Implementação do:

```java
PasswordEncoder
```

para criptografia antes da persistência.

---

## 3. Identificação do usuário autenticado

### Problema

Evitar que usuários alterassem dados de outras contas enviando parâmetros manualmente.

### Solução

Extração do e-mail diretamente do token JWT autenticado.

---

# ⏭️ Próximos Passos

- Dockerização do microsserviço
- Documentação automática com Swagger/OpenAPI
- Testes de integração
- Pipeline CI/CD com GitHub Actions
- Monitoramento utilizando Spring Actuator
- Cache com Redis
- Integração com RabbitMQ
- API Gateway para gerenciamento centralizado dos microsserviços
- Cobertura de testes superior a 80%

---

# 💼 Sobre o Projeto

Este projeto foi desenvolvido com o objetivo de praticar conceitos avançados de desenvolvimento backend, incluindo:

- Arquitetura de Microsserviços
- Spring Boot
- Spring Security
- Autenticação JWT
- REST APIs
- Boas práticas de arquitetura em camadas
- Persistência de dados com JPA
- Tratamento de exceções
- Desenvolvimento orientado a componentes

Além do desenvolvimento técnico, o projeto simula um cenário próximo ao corporativo, onde múltiplos serviços trabalham de forma integrada através de um BFF para oferecer uma experiência unificada ao usuário.

---

# 👩‍💻 Autora

**Stephanie Carvalho**

Desenvolvedora de Software Java Back-end | Spring Boot | PostgreSQL | MongoDB | APIs REST | Microsserviços | Engenheira de Software

🔗 LinkedIn: https://linkedin.com/steph-carvalho/

📧 Contato: ste.aoc@gmail.com