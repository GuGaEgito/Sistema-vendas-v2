# Sistema de Vendas + API REST da Clínica

Aplicação Spring Boot com dois módulos:

- **Sistema de vendas** (telas em Thymeleaf): clientes, produtos, pedidos, login e controle de acesso.
- **API REST da clínica** (JSON): médicos, pacientes e consultas.

## Créditos

- **Base do sistema de vendas** (clientes, produtos, pedidos, login/Spring Security, telas e Docker) desenvolvida por **Victor Lemos** — repositório original: <https://github.com/Victor-l-dev/Sistema-vendas-v2>
- **Módulo da clínica** (API REST de médicos, pacientes e consultas, migração para MySQL com Flyway) desenvolvido por **GuGa Egito**.

## Tecnologias

Java 25, Spring Boot 4.1.2, Spring Data JPA, Bean Validation, Spring Security, Thymeleaf, MySQL, Flyway, Lombok e Maven.

## Como executar

1. Tenha um MySQL rodando em `localhost:3306` com usuário `root` e senha `aluno` (o banco `medpro_api` é criado automaticamente).
   Ou suba o MySQL do Docker com `docker compose up -d` e defina `DB_URL=jdbc:mysql://localhost:3307/medpro_api`.
2. Rode a aplicação:

   ```bash
   ./mvnw spring-boot:run
   ```

3. O Flyway cria as tabelas da clínica (`src/main/resources/db/migration`) na inicialização.

## Endpoints da API da clínica

| Verbo  | URI                        | Ação                              |
|--------|----------------------------|-----------------------------------|
| POST   | `/medicos`                 | Cadastrar médico                  |
| GET    | `/medicos?page=0&sort=nome`| Listar médicos (paginado)         |
| GET    | `/medicos/{id}`            | Detalhar médico                   |
| PUT    | `/medicos/{id}`            | Atualizar nome, telefone, endereço|
| DELETE | `/medicos/{id}`            | Excluir médico                    |
| POST   | `/pacientes`               | Cadastrar paciente                |
| GET    | `/pacientes`               | Listar pacientes (paginado)       |
| GET    | `/pacientes/{id}`          | Detalhar paciente                 |
| PUT    | `/pacientes/{id}`          | Atualizar paciente                |
| DELETE | `/pacientes/{id}`          | Excluir paciente                  |
| POST   | `/consultas`               | Agendar consulta                  |
| GET    | `/consultas`               | Listar consultas (paginado)       |
| GET    | `/consultas/{id}`          | Detalhar consulta                 |
| PUT    | `/consultas/{id}/cancelar` | Cancelar consulta                 |

Exemplo de cadastro de médico (`POST /medicos`):

```json
{
  "nome": "André Roberto",
  "email": "andre.roberto@medpro.com",
  "telefone": "16999999999",
  "crm": "123456",
  "especialidade": "ortopedia",
  "endereco": {
    "logradouro": "rua 1",
    "bairro": "bairro",
    "cep": "12345678",
    "cidade": "São Carlos",
    "uf": "SP",
    "numero": "1",
    "complemento": "complemento"
  }
}
```
