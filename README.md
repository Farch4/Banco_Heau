# Banco_Heau
![image](https://user-images.githubusercontent.com/88545603/152862521-e30ab953-73e8-40f5-80ae-1d5c27b54b4d.png)

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
```

## Pré-requisitos

```
Postman, Swagger ou afim
JRE8 ou superior

```

## Instalação

Clone:
```
git clone https://github.com/Farch4/Banco_Heau.git
```
Entre em Banco_Heau->Heau, abra o cmd e execute o comando:

```
mvn clean install -DskipTests
```
Isto irá gerar o executável(.jar) da aplicação, dentro da pasta target, provavelmente com o nome "heau-0.0.1-SNAPSHOT.jar".

## Como utilizar dos Endpoints
obs: será rodada, por padrão, na porta 8081, segundo o que está configurado no application.properties


### Para cadastrar novo Cliente
Acesse http://localhost:8081/heau/v1/clientes/cadastro, configure o método para POST, o tipo para JSON, e envie o corpo no seguinte formato:
```
{
    "Nome do Cliente":"Nome Qualquer",
    "Saldo Inicial":50
}

![Postman_ra5wvjPYwG](https://user-images.githubusercontent.com/88545603/152870527-48762cee-04b4-4309-a214-352ba3208ca9.gif)

```

### Para ver lista de clientes cadastrados
acesse http://localhost:8081/heau/v1/clientes/lista

```
![Postman_mx0XqZGX7G](https://user-images.githubusercontent.com/88545603/152870996-909014d8-8ae8-4dad-9b97-d3f54a7eb936.gif)
```

### Para buscar clientes pelo número da conta
acesse http://localhost:8081/heau/v1/transferencias/listarTransferencias?numeroConta=NUMERO
onde NUMERO representa o número da conta da qual se quer informações do Cliente

```
![Postman_hVxJafPWrG](https://user-images.githubusercontent.com/88545603/152872127-f49f408f-7561-442c-ad8c-a12d63aeca15.gif)
```

### Para realizar transferência
acesse http://localhost:8081/heau/v1/transferencias/realizarTransferencia e envie o corpo no seguinte formato:

```
{
    "Número da Conta Origem:":1,
    "Número da Conta Destino:":2,
    "Valor da Transferência:":100
}

![Postman_CNDJt4JrWL](https://user-images.githubusercontent.com/88545603/152872758-6984c16d-9d0d-4891-8ad1-7c9f5c7a8c31.gif)

```

### Para buscar as transferências relacionadas à uma conta, por ordem de data decrescente
acesse http://localhost:8081/heau/v1/transferencias/listarTransferencias?numeroConta=NUMERO
onde NUMERO representa o número da conta da qual se quer obter o histórico de transfer~encias

```
![Postman_DkbEkLSUFH](https://user-images.githubusercontent.com/88545603/152873089-b3e613f0-ae77-4d40-8d98-a5dbcb582b28.gif)
```


