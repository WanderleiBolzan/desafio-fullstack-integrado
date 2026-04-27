💳 Sistema de Benefícios
Este é um projeto fullstack distribuído, desenvolvido para a gestão e transferência de saldos entre benefícios. A aplicação utiliza uma arquitetura multi-módulo com Spring Boot, EJB, JPA e um frontend moderno em Angular 17+.

🚀 Tecnologias Utilizadas
Backend
Java 17 / Spring Boot 3: Framework principal e gestão de API.

EJB (Enterprise JavaBeans): Camada de serviços e lógica de negócio.

Spring Data JPA: Abstração da camada de persistência.

H2 Database: Banco de dados em memória para testes rápidos.

SpringDoc OpenAPI (Swagger): Documentação automatizada dos endpoints.

Maven: Gestão de dependências e módulos.

Frontend
Angular 17: Framework web com arquitetura Standalone.

Signals & Computed: Gestão de estado reativa e otimizada.

TypeScript: Tipagem estática para maior segurança.

SCSS: Estilização avançada de componentes.

🛠️ Como Executar o Projeto
1. Backend (Java)
Certifique-se de ter o Maven e o JDK 17 instalados.

Navegue até a pasta raiz do backend.

Execute o comando:

Bash
mvn clean install
mvn spring-boot:run
O servidor estará rodando em: http://localhost:8080

2. Frontend (Angular)
Certifique-se de ter o Node.js e o Angular CLI instalados.

Navegue até a pasta frontend.

Instale as dependências:

Bash
npm install
Inicie a aplicação:

Bash
npm start
Acesse no navegador: http://localhost:4200

🔗 Links e Endpoints Úteis
Frontend UI: http://localhost:4200

Swagger Documentation: http://localhost:8080/swagger-ui/index.html

H2 Console: http://localhost:8080/h2-console

JDBC URL: jdbc:h2:mem:testdb

User: sa | Pass: (em branco)

📋 Funcionalidades Implementadas
[x] Listagem de Benefícios: Consumo de API via GET para exibir saldos em tempo real.

[x] Transferência entre Contas: Lógica de negócio robusta via EJB para transferência de valores entre IDs.

[x] Tratamento de Erros: Respostas HTTP adequadas (400 Bad Request) para saldos insuficientes ou dados inválidos.

[x] Validação Reativa: UI inteligente que bloqueia ações inválidas (campos vazios, valores negativos ou IDs iguais) e fornece feedback visual de "Loading".

[x] Documentação de API: Contrato Swagger detalhado com descrições de parâmetros e respostas.

👨‍💻 Autor
Wanderlei Bolzan
Senior Fullstack Engineer
