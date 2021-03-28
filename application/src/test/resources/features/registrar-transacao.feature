# language: pt
@RegistrarTransacaoFeature
Funcionalidade: Teste de cadastro de transacao

	O sistema deve registrar as transacoes de forma correta seguindo as seguintes restricoes:
	1-) para poder registrar uma transacao o usuario deve estar logado e ter um token valido
	2-) quando for solicitada uma transacao mas o dispositivo nao foi o ultimo a logar com esse usuario, entao devolver um erro
	3-) quando for registrado com sucesso, deve ser devolvido um objeto com a transacao que foi criada
	4-) quando for solicitada a criacao de uma nova transacao e nao for informado o campo valor ou o mesmo for menor que zero, entao retornar um erro
	5-) quando for solicitada a criacao de uma nova transacao e nao for informado qualquer outro campo que nao seja o valor, entao o sistema deve preencher automaticamente esses campos com o default

	Cenario: Registrar transacao com sucesso
		Dado que o dispositivo do usuario informou os seguintes dados na criacao de uma transacao
		  |	id					|	idT1							|	
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
		  |	id					|	idT1							|
		  |	tipo				|	mercado							|	
		  |	valor				|	12.99							|	
		  |	data				|	2020-10-12T22:01:45.887971900	|
		  |	meio_de_pagamento	|	debito							|	
		  |	localizacao			|	SP								|	
		  |	descricao			|	Compras do mes					|

	Cenario: Registrar transacao com sucesso, id nao informado
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

	Cenario: Registrar transacao com sucesso, descricao na informada
		Dado que o dispositivo do usuario informou os seguintes dados na criacao de uma transacao
		  |	id					|	idT1							|
		  |	tipo				|	mercado							|	
		  |	valor				|	12.99							|	
		  |	data				|	2020-10-12T22:01:45.887971900	|
		  |	meio_de_pagamento	|	debito							|	
		  |	localizacao			|	SP								|	
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
		  |	id					|	idT1							|
		  |	tipo				|	mercado							|	
		  |	valor				|	12.99							|	
		  |	data				|	2020-10-12T22:01:45.887971900	|
		  |	meio_de_pagamento	|	debito							|	
		  |	localizacao			|	SP								|	
		  |	descricao			|	Undefined						|

	Cenario: Registrar transacao com sucesso, localizacao nao informada
		Dado que o dispositivo do usuario informou os seguintes dados na criacao de uma transacao
		  |	id					|	idT1							|
		  |	tipo				|	mercado							|	
		  |	valor				|	12.99							|	
		  |	data				|	2020-10-12T22:01:45.887971900	|
		  |	meio_de_pagamento	|	debito							|	
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
		  |	id					|	idT1							|
		  |	tipo				|	mercado							|	
		  |	valor				|	12.99							|	
		  |	data				|	2020-10-12T22:01:45.887971900	|
		  |	meio_de_pagamento	|	debito							|	
		  |	localizacao			|	Unknown							|	
		  |	descricao			|	Compras do mes					|

	Cenario: Registrar transacao com sucesso, meio de pagamento nao informado
		Dado que o dispositivo do usuario informou os seguintes dados na criacao de uma transacao
		  |	id					|	idT1							|
		  |	tipo				|	mercado							|	
		  |	valor				|	12.99							|	
		  |	data				|	2020-10-12T22:01:45.887971900	|
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
		  |	id					|	idT1							|
		  |	tipo				|	mercado							|	
		  |	valor				|	12.99							|	
		  |	data				|	2020-10-12T22:01:45.887971900	|
		  |	meio_de_pagamento	|	Unknown							|	
		  |	localizacao			|	SP								|	
		  |	descricao			|	Compras do mes					|

	Cenario: Registrar transacao com sucesso, data nao informada
		Dado que o dispositivo do usuario informou os seguintes dados na criacao de uma transacao
		  |	id					|	idT1							|
		  |	tipo				|	mercado							|	
		  |	valor				|	12.99							|	
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
		  |	id					|	idT1							|
		  |	tipo				|	mercado							|	
		  |	valor				|	12.99							|	
		  |	meio_de_pagamento	|	debito							|	
		  |	localizacao			|	SP								|	
		  |	descricao			|	Compras do mes					|

	Cenario: Registrar transacao com sucesso, tipo nao informado
		Dado que o dispositivo do usuario informou os seguintes dados na criacao de uma transacao
		  |	id					|	idT1							|
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
		  |	id					|	idT1							|
		  |	tipo				|	Sent							|	
		  |	valor				|	12.99							|	
		  |	data				|	2020-10-12T22:01:45.887971900	|
		  |	meio_de_pagamento	|	debito							|	
		  |	localizacao			|	SP								|	
		  |	descricao			|	Compras do mes					|
		  
	 Cenario: Registrar transacao com erro, valor nao informado
		Dado que o dispositivo do usuario informou os seguintes dados na criacao de uma transacao
		  |	id					|	idT1							|
		  |	tipo				|	mercado							|	
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
		Então deve ocorrer um erro e retornar a mensagem "Value can't be null, zero or less than zero"
		E retornado o status 500
		  
	 Cenario: Registrar transacao com erro, valor menor que zero
		Dado que o dispositivo do usuario informou os seguintes dados na criacao de uma transacao
		  |	id					|	idT1							|
		  |	tipo				|	mercado							|
	   	  |	valor				|	-12.99							|		
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
		Então deve ocorrer um erro e retornar a mensagem "Value can't be null, zero or less than zero"
		E retornado o status 500
		  
	 Cenario: Registrar transacao com erro, usuario nao logado com este dispositivo
		Dado que o dispositivo do usuario informou os seguintes dados na criacao de uma transacao
		  |	id					|	idT1							|
		  |	tipo				|	mercado							|
	   	  |	valor				|	-12.99							|		
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
		E que outro dispositvo fez o login com este usuario
		  |	email				|	teste@gmail.com	|	
		  |	password			|	1234			|	
		  |	device_id			|	id2				|
		Quando for solicitado a criacao de uma transacao
		Então deve ocorrer um erro e retornar a mensagem "Dispositivo que fez a requisicao nao esta logado."
		E retornado o status 500
		 
		 