# 📦 Sistema de Vendas e Estoque — API REST com Spring Boot

Esta API foi desenvolvida para gerenciar produtos, vendas e estoque, utilizando Spring Boot + JPA + MariaDB.
O sistema permite registrar produtos, controlar estoque e realizar vendas com validação automática e rollback em caso de erro.

---

## 🚀 Tecnologias Utilizadas

| *Tecnologia* |	*Finalidade* |
| -------------|------------- |
| Java 17+ | Linguagem principal |
| Spring Boot | Criação da API |
| Spring Data JPA	| Persistência de dados |
| MariaDB / MySQL |	Banco de dados |
| Hibernate |	ORM |
| Postman / Insomnia |	Testes da API |

---

## 📁 Estrutura de Pastas |

```bash
src/
 ├─ main/
 │  ├─ java/com/controleestoque/api_estoque/
 │  │   ├─ controller/
 │  │   ├─ dto/
 │  │   ├─ model/
 │  │   ├─ repository/
 │  │   ├─ service/
 │  │   └─ exception/
 │  └─ resources/
 │     └─ application.properties
 ├─

```

--- 

## 🔩 Primeiros passos
Antes de baixar o projeto e começar a testar os recursos da API, será necessário seguir algumas etapas simples:

## ⚙️ Configuração do Banco de Dados

Crie o banco no MariaDB/MySQL:

```bash
CREATE DATABASE estoque_db;
```

## 📄 Configurações do arquivo application.properties (com seus dados)

```bash

spring.datasource.url=jdbc:mariadb://localhost:3306/vendas_db

spring.datasource.username=seu_usuario

spring.datasource.password=sua_senha

spring.datasource.driver-class-name=org.mariadb.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update

spring.jpa.show-sql=true

spring.jpa.properties.hibernate.format_sql=true

spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

```

--- 


## 🖥️ Como Inicializar o Projeto

🔧 1. Instalar Dependências

```bash
mvn clean install
```


▶ 2. Executar a API

```bash
mvn spring-boot:run
```

---

## 🧪 Testes da API

Use Postman / Insomnia / Thunder Client (VSCode) para testar os endpoints.

--- 

## 📌 Exemplos de rotas

| *Método* |	*Endpoint* |	*Descrição* |
----------|-----------|------------|
| POST	| /produtos |	Cadastrar produto |
| GET |	/produtos |	Listar todos produtos |
| POST |	/vendas | Registrar venda |
| GET |	/vendas/{id} |	Detalhar venda |
| DELETE |	/produtos/{id} | Deletar produto |
| PUT |	/produtos/{id}	| Atualizar produto |

---

## ❗Validação automática de estoque

Se tentar vender mais que o disponível, a API retorna:

```bash
{
  "erro": "Estoque insuficiente para produto: Camisa Polo (id=1)"
}
```

--- 

## 👩‍💻 Autora

*Nome:* [Beatriz V. Xavier](https://github.com/Bea-Xavier)

*Tecnologias:* Java


![Java](https://github.com/tandpfun/skill-icons/blob/main/icons/Java-Dark.svg)



Spring Boot | MariaDB

## 📄 Licença

Este projeto é livre para fins acadêmicos e de estudo. Sinta-se à vontade para melhorar e expandir. 🚀
