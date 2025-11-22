# 🌍 API Agência de Viagens - Completa

API REST desenvolvida com **Java 17**, **Spring Boot 3.2**, **Spring Data JPA**, **PostgreSQL** e **Spring Security** para gerenciar destinos de viagem com autenticação e autorização por perfis.

---

## 📋 Pré-requisitos

- Java 17+
- Maven 3.6+
- PostgreSQL 12+
- Postman ou Insomnia (para testes)

---

## 🚀 Configuração e Execução

### 1. Criar o Banco de Dados PostgreSQL

```sql
CREATE DATABASE agencia_viagens;
```

### 2. Configurar Credenciais

Edite o arquivo `src/main/resources/application.properties`:

```properties
spring.datasource.username=postgres
spring.datasource.password=SUA_SENHA_AQUI
```

### 3. Executar a Aplicação

```bash
mvn clean install
mvn spring-boot:run
```

A API estará disponível em: `http://localhost:8080`

---

## 🔐 Autenticação

A API usa **HTTP Basic Authentication**. Dois usuários são criados automaticamente:

| Username | Password  | Role       | Permissões                          |
|----------|-----------|------------|-------------------------------------|
| `admin`  | `admin123`| ROLE_ADMIN | Todas (criar, editar, excluir, avaliar) |
| `user`   | `user123` | ROLE_USER  | Apenas avaliar destinos             |

### Como Autenticar no Postman/Insomnia:

1. Na aba **Authorization**
2. Selecione **Basic Auth**
3. Digite o username e password
4. Envie a requisição

---

## 📡 Endpoints da API

### 🌐 Endpoints Públicos (sem autenticação)

#### 1. Listar Todos os Destinos
```
GET http://localhost:8080/api/destinos
```

#### 2. Buscar Destino por ID
```
GET http://localhost:8080/api/destinos/{id}
```

#### 3. Pesquisar Destinos
```
GET http://localhost:8080/api/destinos/pesquisar?termo=Paris
```

---

### 👤 Endpoints USER (requer autenticação USER ou ADMIN)

#### 4. Avaliar Destino
```
PATCH http://localhost:8080/api/destinos/{id}/avaliar
```

**Autenticação**: `user` / `user123` ou `admin` / `admin123`

**Body (JSON)**:
```json
{
  "avaliacao": 9
}
```

**Validação**: Nota deve ser entre 1 e 10.

---

### 🔒 Endpoints ADMIN (requer autenticação ADMIN)

#### 5. Cadastrar Novo Destino
```
POST http://localhost:8080/api/destinos
```

**Autenticação**: `admin` / `admin123`

**Body (JSON)**:
```json
{
  "nome": "Paris",
  "localizacao": "França",
  "descricao": "Cidade Luz, famosa pela Torre Eiffel"
}
```

**Validações**:
- `nome`: obrigatório
- `localizacao`: obrigatória
- `descricao`: opcional

#### 6. Atualizar Destino
```
PUT http://localhost:8080/api/destinos/{id}
```

**Autenticação**: `admin` / `admin123`

**Body (JSON)**:
```json
{
  "nome": "Paris Atualizado",
  "localizacao": "França",
  "descricao": "Nova descrição da cidade"
}
```

#### 7. Excluir Destino
```
DELETE http://localhost:8080/api/destinos/{id}
```

**Autenticação**: `admin` / `admin123`

---

## 🔍 Verificando o Banco de Dados

Conecte-se ao PostgreSQL e execute:

```sql
-- Ver usuários
SELECT * FROM usuarios;

-- Ver destinos
SELECT * FROM destinos;
```

---

## 📊 Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Data JPA** (integração com banco de dados)
- **Spring Security** (autenticação e autorização)
- **PostgreSQL** (banco de dados)
- **BCrypt** (criptografia de senhas)
- **Maven** (gerenciamento de dependências)

---

## 📝 Notas Importantes

1. **Ambiente de Desenvolvimento**: Esta configuração usa `ddl-auto=update` que é adequado para desenvolvimento. Em produção, use migrations (Flyway/Liquibase).

2. **Senhas**: As senhas são criptografadas com BCrypt antes de serem salvas no banco.

3. **Sessões**: A API é stateless (`SessionCreationPolicy.STATELESS`), ou seja, não mantém sessão no servidor.

4. **CORS**: Se precisar acessar de um frontend, adicione configuração de CORS no `SecurityConfig`.

---