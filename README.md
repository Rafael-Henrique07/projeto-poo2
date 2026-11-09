# Clássicos à Venda

Sistema web de anúncio e negociação de carros antigos e clássicos, desenvolvido como Projeto Final de POO2 (EC46A) na UTFPR Cornélio Procópio.

## Tecnologias

Java 25, Spring Boot 4.1 (MVC, JDBC, Validation), Thymeleaf, Bootstrap 5, MySQL 8 e Maven.

## Como executar

1. Tenha o MySQL 8 em execução em `localhost:3306`.
2. Na raiz do projeto, crie o arquivo `config-local.properties` com a senha do seu MySQL. Ele não vai para o Git.

   ```properties
   DB_PASSWORD=sua_senha_aqui
   ```

3. Abra a pasta no IntelliJ com o JDK 25 e execute a classe `Projeto_poo2`, ou rode `mvn spring-boot:run`.
4. Acesse <http://localhost:8080>.

O banco `veiculos_bd` e as tabelas são criados automaticamente a partir de `src/main/resources/schema.sql`.

> Se você já tem um `veiculos_bd` de uma versão anterior do projeto, apague esse banco antes da primeira execução, pois as tabelas mudaram.

## Contas de demonstração

| Perfil | E-mail | Senha |
|---|---|---|
| Anunciante | `anunciante@demo.com` | `demo1234` |
| Comprador | `comprador@demo.com` | `demo1234` |
| Administrador | `admin@demo.com` | `demo1234` |

## Funcionalidades

- Cadastro, login, edição de perfil, troca de senha e desativação de conta
- Cadastro, edição, exclusão e alteração de status de anúncios
- Envio de 1 a 15 fotos por anúncio, com ordem e foto principal
- Pesquisa paginada com filtros por categoria, marca, ano, preço, conservação, cidade e UF
- Negociação entre comprador e anunciante, com mensagens e propostas
- Liberação do contato do anunciante quando uma proposta é aceita
- Gerenciamento de categorias e moderação de anúncios pelo administrador

## Organização do código

```text
src/main/java/com/mycompany/projeto_poo2
├── controller   Controllers (recebem as requisições e escolhem a tela)
├── service      Regras de negócio
├── dao          Acesso ao banco via JDBC (PreparedStatement)
├── model        Entidades e enumerações
├── selo         Hierarquia polimórfica de selos
├── dto          Formulários, filtros e dados de sessão
├── config       Configuração web e controle de acesso
└── exception    Exceções da aplicação

src/main/resources
├── templates    Telas em Thymeleaf (View)
├── static/css   Estilos
├── schema.sql   Criação das tabelas
└── data.sql     Dados de demonstração
```
