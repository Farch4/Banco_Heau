# Banco_Heau
![image](https://user-images.githubusercontent.com/88545603/152873466-834fdcc0-760f-4b6e-b3a7-77b1bd4f53ba.png)

## Sobre
Banco Heau é uma API desenvolvida para simular algumas funções bancárias, tais como: cadastro de novos clientes, transferências e consultas das respectivas informações.
A aplicação utiliza de banco de dados em memória (a saber, H2), e as consultas e inserções podem ser feitas manualmente, com a ajuda de um software próprio para testes de APIS, como o Postman.

## Tecnologias envolvidas

```
Java 
Spring FrameWork
Maven
Mockito
Junit
Postman
H2
Spring Data JPA
Docker
```

## Pré-requisitos

```
Postman ou afim
+
```
JRE8+ e Maven
```
Ou
```
Docker e Docker Compose
```

## Instalação

### Instalação com Docker

Clone:
```
git clone https://github.com/Farch4/Banco_Heau.git
```
Entre no diretório Banco_Heau/Heau, abra o cmd e execute o comando:
```
mvn clean install -DskipTests
```
Isto irá gerar o build da aplicação para que possamos gerar a imagem Docker a ser utilizada. A seguir, no mesmo diretório:
```
docker build -t heau-image .
```
e então
```
docker-compose up
````

### Instalação Clássica

Clone:
```
git clone https://github.com/Farch4/Banco_Heau.git
```
Entre no diretório Banco_Heau/Heau, abra o cmd e execute o comando:

```
mvn clean install -DskipTests
```
Isto irá gerar o executável (.jar) da aplicação, dentro da pasta target, provavelmente com o nome "heau-0.0.1-SNAPSHOT.jar".


## Testes automatizados
Para rodar os testes, acesse o diretório no diretório Banco_Heau/Heau, abra o cmd e execute o comando:
```
mvn test
```
ou, a partir da IDE de sua preferência.


## Como utilizar dos Endpoints
obs: será rodada, por padrão, na porta 8081, segundo o que está configurado no application.properties. Não é necessário nenhum tipo de autenticação para o uso dos endpoints.


### Para cadastrar novo Cliente
Configure o método para POST, o tipo para JSON, e acesse http://localhost:8081/heau/v1/clientes/cadastro, enviando o corpo no seguinte formato:
```
{
    "Nome do Cliente":"Nome Qualquer",
    "Saldo Inicial":50
}
```

### Para ver lista de clientes cadastrados
Configure o método para GET e acesse http://localhost:8081/heau/v1/clientes/lista 


### Para buscar clientes pelo número da conta
Configure o método para GET e acesse http://localhost:8081/heau/v1/clientes/buscaPelaConta?numeroConta=NUMERO
onde NUMERO representa o número da conta da qual se quer informações do Cliente


### Para realizar transferência
Configure o método para POST, o tipo para JSON, acesse http://localhost:8081/heau/v1/transferencias/realizarTransferencia, e envie o corpo no seguinte formato:
```
{
    "Número da Conta Origem:":1,
    "Número da Conta Destino:":2,
    "Valor da Transferência:":100
}
```

### Para buscar as transferências relacionadas à uma conta, por ordem de data decrescente:
Configure o método para GET acesse http://localhost:8081/heau/v1/transferencias/listarTransferencias?numeroConta=NUMERO
onde NUMERO representa o número da conta da qual se quer obter o histórico de transferências.


