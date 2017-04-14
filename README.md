# Implementação do Laboratório de RPC

## Alunos:
	- Ana Melik
	- João Paulo F. Brito

### Exercício 1 - IMC

Escreva um procedimento remoto que calcule o índice de massa corporal de uma
pessoa. Implemente um programa cliente que realiza a chamada remota ao
procedimento remoto criado.

Exemplo:
```
Altura........: 1.75 m
Peso..........: 68.00 kg
Sexo..........: Masc

Seu IMC ficou em...........................: 22,20
Você é considerada(o) uma pessoa: Normal
Para sua altura, seu peso ideal deve ficar entre..: 63,39 e 80,82 kg
```

#### Como rodar

1. Entre na pasta com os binários, em geral `bin`:

  `$ cd bin/`

2. Inicie o servidor:

  `$ java icomp.server.IMCServer [port]`

3. Rode o cliente:

  `$ java icomp.client.IMCClient <serverIP> <serverPort>`

________________________________________________________________

### Exercício 2 - ls

Escreva um procedimento remoto que realiza o comando ls (list), permitindo a
visualização de dois ou mais diretórios remotos. Implemente o cliente de modo
que o usuário entre com o caminho do diretório a ser visualizado. Utilize
threads.

#### Como rodar

1. Entre na pasta com os binários, em geral `bin`:

  `$ cd bin/`

2. Inicie o servidor:

  `$ java icomp.server.LsServer [port]`

3. Rode o cliente:

  `$ java icomp.client.LsClient <serverIP> <serverPort> <targetDirectory>`
