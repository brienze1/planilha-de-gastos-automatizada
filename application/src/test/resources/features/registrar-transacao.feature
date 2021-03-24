# language: pt
@RegistrarTransacaoFeature
Funcionalidade: Teste de auto login de usuario

	O sistema deve registrar as transacoes de forma correta seguindo as seguintes restricoes:
	1-) para poder registrar uma transacao o usuario deve estar logado e ter um token valido
	2-) quando for solicitada uma transacao mas o dispositivo nao foi o ultimo a logar com esse usuario, entao devolver um erro
	3-) quando for registrado com sucesso, deve ser devolvido um objet com a transacao que foi criada
	4-) quando for solicitada a criacao de uma nova transacao e nao for informado o campo valor ou o mesmo for menor que zero, entao retornar um erro
	5-) quando for solicitada a criacao de uma nova transacao e nao for informado qualquer outro campo que nao seja o valor, entao o sistema deve preencher automaticamente esses campos com o default

	Cenario: Registrar transacao com sucesso
		Dado que o dispositivo do usuario informou os seguintes dados na criacao de uma transacao
		  |	tipo				|	mercado							|	
		  |	valor				|	12.99							|	
		  |	data				|	2020-10-12T22:01:45.887971900	|
		  |	meio_de_pagamento	|	debito							|	
		  |	localizacao			|	SP								|	
		  |	descricao			|	Compras do mes					|	
		E que um usuario ja foi cadastrado com os dados abaixo anteriormente
		  |	email				|	teste@gmail.com	|	
		  |	password			|	1234			|	
		  |	last_name			|	Doe				|	
		  |	first_name			|	John			|
		  |	device_id			|	id1				|
		E que o usuario ja realizou o login com os dados abaixo
		  |	email				|	teste@gmail.com	|	
		  |	password			|	1234			|	
		  |	device_id			|	id1				|
		Quando for solicitado a criacao de uma transacao
		Então deve ser retornado a transacao criada
		E retornado o status 200
		E a transacao recebida deve conter os campos abaixo preenchidos
		  |	tipo				|	mercado							|	
		  |	valor				|	12.99							|	
		  |	data				|	2020-10-12T22:01:45.887971900	|
		  |	meio_de_pagamento	|	debito							|	
		  |	localizacao			|	SP								|	
		  |	descricao			|	Compras do mes					|
		  
		 
		 