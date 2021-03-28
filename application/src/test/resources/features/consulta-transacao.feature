# language: pt
@ConsultarTransacaoFeature
Funcionalidade: Teste de consulta de transacao

	O sistema deve consultar as transacoes de forma correta seguindo as seguintes restricoes:
	1-) para poder consultar uma transacao o usuario deve estar logado e ter um token valido
	2-) quando for consultado e enviado um id de transacao deve ser retornado somente uma transacao
	3-) quando for consultado e nao for enviado um id de transacao deve ser retornado uma lista de transacoes mesmo que so contenha uma transacao
	4-) quando for consultado a lista e for enviado o parametro data, as transacoes retornadas nao devem ser anteriores a essa data
	5-) quando for consultado a lista e for enviado o parametro data nao valido, deve ser retornado um erro
	6-) quando for consultado a lista e for enviado o parametro quantidade, nao deve ser retornado uma quantidade maior que a especificada
	6-) quando for consultado a lista e for enviado o parametro quantidade e pagina, nao deve ser retornado uma quantidade maior que a especificada e quando o parametro pagina for alterado devem ser retornadas as transacoes referentes a pagina dividaspela quantidade

	Cenario: Consultar uma transacao com sucesso
		Dado que um usuario tenha sido cadastrado com os dados abaixo anteriormente
		  |	email				|	teste@gmail.com	|	
		  |	password			|	1234			|	
		  |	last_name			|	Doe				|	
		  |	first_name			|	John			|
		  |	device_id			|	id1				|
		E que este usuario ja realizou o login com os dados abaixo
		  |	email				|	teste@gmail.com	|	
		  |	password			|	1234			|	
		  |	device_id			|	id1				|
		E que foi solicitado a criacao da transacao com os dados abaixo
		  |	id					|	idT1							|
		  |	tipo				|	mercado							|	
		  |	valor				|	12.99							|	
		  |	data				|	2020-10-12T22:01:45.887971900	|
		  |	meio_de_pagamento	|	debito							|	
		  |	localizacao			|	SP								|	
		  |	descricao			|	Compras do mes					|	
		Quando for solicitado a consulta da transacao com o id "idT1"
		Então deve ser retornado a transacao com os dados abaixo
		  |	id					|	idT1							|
		  |	tipo				|	mercado							|	
		  |	valor				|	12.99							|	
		  |	data				|	2020-10-12T22:01:45.887972		|
		  |	meio_de_pagamento	|	debito							|	
		  |	localizacao			|	SP								|	
		  |	descricao			|	Compras do mes					|	
		E o status retornado da chamada deve ser 200

	Cenario: Consultar lista de transacoes com sucesso
		Dado que um usuario tenha sido cadastrado com os dados abaixo anteriormente
		  |	email				|	teste@gmail.com	|	
		  |	password			|	1234			|	
		  |	last_name			|	Doe				|	
		  |	first_name			|	John			|
		  |	device_id			|	id1				|
		E que este usuario ja realizou o login com os dados abaixo
		  |	email				|	teste@gmail.com	|	
		  |	password			|	1234			|	
		  |	device_id			|	id1				|
		E que foram cadastradas as transacoes com os dados abaixo
		  |	{"id":"id1","tipo":"mercado1","valor":12.99,"descricao":"teste1","data":"2020-10-12T22:01:45.887971900","meio_de_pagamento":"debito","localizacao":"sp1"}	|
		  |	{"id":"id2","tipo":"mercado2","valor":13.99,"descricao":"teste2","data":"2020-10-13T22:01:45.887971900","meio_de_pagamento":"debito","localizacao":"sp2"}	|
		  |	{"id":"id3","tipo":"mercado3","valor":14.99,"descricao":"teste3","data":"2020-10-14T22:01:45.887971900","meio_de_pagamento":"credito","localizacao":"sp3"}	|
		  |	{"id":"id4","tipo":"mercado4","valor":15.99,"descricao":"teste4","data":"2020-10-15T22:01:45.887971900","meio_de_pagamento":"credito","localizacao":"sp4"}	|
		  |	{"id":"id5","tipo":"mercado5","valor":16.99,"descricao":"teste5","data":"2020-10-16T22:01:45.887971900","meio_de_pagamento":"debito","localizacao":"sp5"}	|
		  |	{"id":"id6","tipo":"mercado6","valor":11.99,"descricao":"teste6","data":"2020-10-17T22:01:45.887971900","meio_de_pagamento":"debito","localizacao":"sp6"}	|
		Quando for solicitado a consulta de transacoes com os dados abaixo
		  |	since		|		|
		  |	quantity	|		|
		  |	page		|		|
		Então deve ser retornado as transacoes com os dados abaixo
		  |	{"id":"id1","tipo":"mercado1","valor":12.99,"descricao":"teste1","data":"2020-10-12T22:01:45.887972","meio_de_pagamento":"debito","localizacao":"sp1"}	|
		  |	{"id":"id2","tipo":"mercado2","valor":13.99,"descricao":"teste2","data":"2020-10-13T22:01:45.887972","meio_de_pagamento":"debito","localizacao":"sp2"}	|
		  |	{"id":"id3","tipo":"mercado3","valor":14.99,"descricao":"teste3","data":"2020-10-14T22:01:45.887972","meio_de_pagamento":"credito","localizacao":"sp3"}	|
		  |	{"id":"id4","tipo":"mercado4","valor":15.99,"descricao":"teste4","data":"2020-10-15T22:01:45.887972","meio_de_pagamento":"credito","localizacao":"sp4"}	|
		  |	{"id":"id5","tipo":"mercado5","valor":16.99,"descricao":"teste5","data":"2020-10-16T22:01:45.887972","meio_de_pagamento":"debito","localizacao":"sp5"}	|
		  |	{"id":"id6","tipo":"mercado6","valor":11.99,"descricao":"teste6","data":"2020-10-17T22:01:45.887972","meio_de_pagamento":"debito","localizacao":"sp6"}	|
		E o status retornado da chamada deve ser 200

	Cenario: Consultar lista de transacoes com sucesso
		Dado que um usuario tenha sido cadastrado com os dados abaixo anteriormente
		  |	email				|	teste@gmail.com	|	
		  |	password			|	1234			|	
		  |	last_name			|	Doe				|	
		  |	first_name			|	John			|
		  |	device_id			|	id1				|
		E que este usuario ja realizou o login com os dados abaixo
		  |	email				|	teste@gmail.com	|	
		  |	password			|	1234			|	
		  |	device_id			|	id1				|
		E que foram cadastradas as transacoes com os dados abaixo
		  |	{"id":"id1","tipo":"mercado1","valor":12.99,"descricao":"teste1","data":"2020-10-12T22:01:45.887971900","meio_de_pagamento":"debito","localizacao":"sp1"}	|
		  |	{"id":"id2","tipo":"mercado2","valor":13.99,"descricao":"teste2","data":"2020-10-13T22:01:45.887971900","meio_de_pagamento":"debito","localizacao":"sp2"}	|
		  |	{"id":"id3","tipo":"mercado3","valor":14.99,"descricao":"teste3","data":"2020-10-14T22:01:45.887971900","meio_de_pagamento":"credito","localizacao":"sp3"}	|
		  |	{"id":"id4","tipo":"mercado4","valor":15.99,"descricao":"teste4","data":"2020-10-15T22:01:45.887971900","meio_de_pagamento":"credito","localizacao":"sp4"}	|
		  |	{"id":"id5","tipo":"mercado5","valor":16.99,"descricao":"teste5","data":"2020-10-16T22:01:45.887971900","meio_de_pagamento":"debito","localizacao":"sp5"}	|
		  |	{"id":"id6","tipo":"mercado6","valor":11.99,"descricao":"teste6","data":"2020-10-17T22:01:45.887971900","meio_de_pagamento":"debito","localizacao":"sp6"}	|
		Quando for solicitado a consulta de transacoes com os dados abaixo
		  |	empty		|		|
		Então deve ser retornado as transacoes com os dados abaixo
		  |	{"id":"id1","tipo":"mercado1","valor":12.99,"descricao":"teste1","data":"2020-10-12T22:01:45.887972","meio_de_pagamento":"debito","localizacao":"sp1"}	|
		  |	{"id":"id2","tipo":"mercado2","valor":13.99,"descricao":"teste2","data":"2020-10-13T22:01:45.887972","meio_de_pagamento":"debito","localizacao":"sp2"}	|
		  |	{"id":"id3","tipo":"mercado3","valor":14.99,"descricao":"teste3","data":"2020-10-14T22:01:45.887972","meio_de_pagamento":"credito","localizacao":"sp3"}	|
		  |	{"id":"id4","tipo":"mercado4","valor":15.99,"descricao":"teste4","data":"2020-10-15T22:01:45.887972","meio_de_pagamento":"credito","localizacao":"sp4"}	|
		  |	{"id":"id5","tipo":"mercado5","valor":16.99,"descricao":"teste5","data":"2020-10-16T22:01:45.887972","meio_de_pagamento":"debito","localizacao":"sp5"}	|
		  |	{"id":"id6","tipo":"mercado6","valor":11.99,"descricao":"teste6","data":"2020-10-17T22:01:45.887972","meio_de_pagamento":"debito","localizacao":"sp6"}	|
		E o status retornado da chamada deve ser 200

	Cenario: Consultar lista de transacoes com sucesso
		Dado que um usuario tenha sido cadastrado com os dados abaixo anteriormente
		  |	email				|	teste@gmail.com	|	
		  |	password			|	1234			|	
		  |	last_name			|	Doe				|	
		  |	first_name			|	John			|
		  |	device_id			|	id1				|
		E que este usuario ja realizou o login com os dados abaixo
		  |	email				|	teste@gmail.com	|	
		  |	password			|	1234			|	
		  |	device_id			|	id1				|
		E que foram cadastradas as transacoes com os dados abaixo
		  |	{"id":"id1","tipo":"mercado1","valor":12.99,"descricao":"teste1","data":"2020-10-12T22:01:45.887971900","meio_de_pagamento":"debito","localizacao":"sp1"}	|
		  |	{"id":"id2","tipo":"mercado2","valor":13.99,"descricao":"teste2","data":"2020-10-13T22:01:45.887971900","meio_de_pagamento":"debito","localizacao":"sp2"}	|
		  |	{"id":"id3","tipo":"mercado3","valor":14.99,"descricao":"teste3","data":"2020-10-14T22:01:45.887971900","meio_de_pagamento":"credito","localizacao":"sp3"}	|
		  |	{"id":"id4","tipo":"mercado4","valor":15.99,"descricao":"teste4","data":"2020-10-15T22:01:45.887971900","meio_de_pagamento":"credito","localizacao":"sp4"}	|
		  |	{"id":"id5","tipo":"mercado5","valor":16.99,"descricao":"teste5","data":"2020-10-16T22:01:45.887971900","meio_de_pagamento":"debito","localizacao":"sp5"}	|
		  |	{"id":"id6","tipo":"mercado6","valor":11.99,"descricao":"teste6","data":"2020-10-17T22:01:45.887971900","meio_de_pagamento":"debito","localizacao":"sp6"}	|
		Quando for solicitado a consulta de transacoes com os dados abaixo
		  |	page		|	-1	|
		Então deve ser retornado as transacoes com os dados abaixo
		  |	{"id":"id1","tipo":"mercado1","valor":12.99,"descricao":"teste1","data":"2020-10-12T22:01:45.887972","meio_de_pagamento":"debito","localizacao":"sp1"}	|
		  |	{"id":"id2","tipo":"mercado2","valor":13.99,"descricao":"teste2","data":"2020-10-13T22:01:45.887972","meio_de_pagamento":"debito","localizacao":"sp2"}	|
		  |	{"id":"id3","tipo":"mercado3","valor":14.99,"descricao":"teste3","data":"2020-10-14T22:01:45.887972","meio_de_pagamento":"credito","localizacao":"sp3"}	|
		  |	{"id":"id4","tipo":"mercado4","valor":15.99,"descricao":"teste4","data":"2020-10-15T22:01:45.887972","meio_de_pagamento":"credito","localizacao":"sp4"}	|
		  |	{"id":"id5","tipo":"mercado5","valor":16.99,"descricao":"teste5","data":"2020-10-16T22:01:45.887972","meio_de_pagamento":"debito","localizacao":"sp5"}	|
		  |	{"id":"id6","tipo":"mercado6","valor":11.99,"descricao":"teste6","data":"2020-10-17T22:01:45.887972","meio_de_pagamento":"debito","localizacao":"sp6"}	|
		E o status retornado da chamada deve ser 200

	Cenario: Consultar lista de transacoes com sucesso
		Dado que um usuario tenha sido cadastrado com os dados abaixo anteriormente
		  |	email				|	teste@gmail.com	|	
		  |	password			|	1234			|	
		  |	last_name			|	Doe				|	
		  |	first_name			|	John			|
		  |	device_id			|	id1				|
		E que este usuario ja realizou o login com os dados abaixo
		  |	email				|	teste@gmail.com	|	
		  |	password			|	1234			|	
		  |	device_id			|	id1				|
		E que foram cadastradas as transacoes com os dados abaixo
		  |	{"id":"id1","tipo":"mercado1","valor":12.99,"descricao":"teste1","data":"2020-10-12T22:01:45.887971900","meio_de_pagamento":"debito","localizacao":"sp1"}	|
		  |	{"id":"id2","tipo":"mercado2","valor":13.99,"descricao":"teste2","data":"2020-10-13T22:01:45.887971900","meio_de_pagamento":"debito","localizacao":"sp2"}	|
		  |	{"id":"id3","tipo":"mercado3","valor":14.99,"descricao":"teste3","data":"2020-10-14T22:01:45.887971900","meio_de_pagamento":"credito","localizacao":"sp3"}	|
		  |	{"id":"id4","tipo":"mercado4","valor":15.99,"descricao":"teste4","data":"2020-10-15T22:01:45.887971900","meio_de_pagamento":"credito","localizacao":"sp4"}	|
		  |	{"id":"id5","tipo":"mercado5","valor":16.99,"descricao":"teste5","data":"2020-10-16T22:01:45.887971900","meio_de_pagamento":"debito","localizacao":"sp5"}	|
		  |	{"id":"id6","tipo":"mercado6","valor":11.99,"descricao":"teste6","data":"2020-10-17T22:01:45.887971900","meio_de_pagamento":"debito","localizacao":"sp6"}	|
		Quando for solicitado a consulta de transacoes com os dados abaixo
		  |	quantity	|	-1	|
		Então deve ser retornado as transacoes com os dados abaixo
		  |	{"id":"id1","tipo":"mercado1","valor":12.99,"descricao":"teste1","data":"2020-10-12T22:01:45.887972","meio_de_pagamento":"debito","localizacao":"sp1"}	|
		  |	{"id":"id2","tipo":"mercado2","valor":13.99,"descricao":"teste2","data":"2020-10-13T22:01:45.887972","meio_de_pagamento":"debito","localizacao":"sp2"}	|
		  |	{"id":"id3","tipo":"mercado3","valor":14.99,"descricao":"teste3","data":"2020-10-14T22:01:45.887972","meio_de_pagamento":"credito","localizacao":"sp3"}	|
		  |	{"id":"id4","tipo":"mercado4","valor":15.99,"descricao":"teste4","data":"2020-10-15T22:01:45.887972","meio_de_pagamento":"credito","localizacao":"sp4"}	|
		  |	{"id":"id5","tipo":"mercado5","valor":16.99,"descricao":"teste5","data":"2020-10-16T22:01:45.887972","meio_de_pagamento":"debito","localizacao":"sp5"}	|
		  |	{"id":"id6","tipo":"mercado6","valor":11.99,"descricao":"teste6","data":"2020-10-17T22:01:45.887972","meio_de_pagamento":"debito","localizacao":"sp6"}	|
		E o status retornado da chamada deve ser 200

	Cenario: Consultar lista de transacoes com sucesso por quantitdade
		Dado que um usuario tenha sido cadastrado com os dados abaixo anteriormente
		  |	email				|	teste@gmail.com	|	
		  |	password			|	1234			|	
		  |	last_name			|	Doe				|	
		  |	first_name			|	John			|
		  |	device_id			|	id1				|
		E que este usuario ja realizou o login com os dados abaixo
		  |	email				|	teste@gmail.com	|	
		  |	password			|	1234			|	
		  |	device_id			|	id1				|
		E que foram cadastradas as transacoes com os dados abaixo
		  |	{"id":"id1","tipo":"mercado1","valor":12.99,"descricao":"teste1","data":"2020-10-12T22:01:45.887971900","meio_de_pagamento":"debito","localizacao":"sp1"}	|
		  |	{"id":"id2","tipo":"mercado2","valor":13.99,"descricao":"teste2","data":"2020-10-13T22:01:45.887971900","meio_de_pagamento":"debito","localizacao":"sp2"}	|
		  |	{"id":"id3","tipo":"mercado3","valor":14.99,"descricao":"teste3","data":"2020-10-14T22:01:45.887971900","meio_de_pagamento":"credito","localizacao":"sp3"}	|
		  |	{"id":"id4","tipo":"mercado4","valor":15.99,"descricao":"teste4","data":"2020-10-15T22:01:45.887971900","meio_de_pagamento":"credito","localizacao":"sp4"}	|
		  |	{"id":"id5","tipo":"mercado5","valor":16.99,"descricao":"teste5","data":"2020-10-16T22:01:45.887971900","meio_de_pagamento":"debito","localizacao":"sp5"}	|
		  |	{"id":"id6","tipo":"mercado6","valor":11.99,"descricao":"teste6","data":"2020-10-17T22:01:45.887971900","meio_de_pagamento":"debito","localizacao":"sp6"}	|
		Quando for solicitado a consulta de transacoes com os dados abaixo
		  |	since		|		|
		  |	quantity	|	4	|
		  |	page		|		|
		Então deve ser retornado as transacoes com os dados abaixo
		  |	{"id":"id1","tipo":"mercado1","valor":12.99,"descricao":"teste1","data":"2020-10-12T22:01:45.887972","meio_de_pagamento":"debito","localizacao":"sp1"}	|
		  |	{"id":"id2","tipo":"mercado2","valor":13.99,"descricao":"teste2","data":"2020-10-13T22:01:45.887972","meio_de_pagamento":"debito","localizacao":"sp2"}	|
		  |	{"id":"id3","tipo":"mercado3","valor":14.99,"descricao":"teste3","data":"2020-10-14T22:01:45.887972","meio_de_pagamento":"credito","localizacao":"sp3"}	|
		  |	{"id":"id4","tipo":"mercado4","valor":15.99,"descricao":"teste4","data":"2020-10-15T22:01:45.887972","meio_de_pagamento":"credito","localizacao":"sp4"}	|
		E o status retornado da chamada deve ser 200

	Cenario: Consultar lista de transacoes com sucesso por quantitdade
		Dado que um usuario tenha sido cadastrado com os dados abaixo anteriormente
		  |	email				|	teste@gmail.com	|	
		  |	password			|	1234			|	
		  |	last_name			|	Doe				|	
		  |	first_name			|	John			|
		  |	device_id			|	id1				|
		E que este usuario ja realizou o login com os dados abaixo
		  |	email				|	teste@gmail.com	|	
		  |	password			|	1234			|	
		  |	device_id			|	id1				|
		E que foram cadastradas as transacoes com os dados abaixo
		  |	{"id":"id1","tipo":"mercado1","valor":12.99,"descricao":"teste1","data":"2020-10-12T22:01:45.887971900","meio_de_pagamento":"debito","localizacao":"sp1"}	|
		  |	{"id":"id2","tipo":"mercado2","valor":13.99,"descricao":"teste2","data":"2020-10-13T22:01:45.887971900","meio_de_pagamento":"debito","localizacao":"sp2"}	|
		  |	{"id":"id3","tipo":"mercado3","valor":14.99,"descricao":"teste3","data":"2020-10-14T22:01:45.887971900","meio_de_pagamento":"credito","localizacao":"sp3"}	|
		  |	{"id":"id4","tipo":"mercado4","valor":15.99,"descricao":"teste4","data":"2020-10-15T22:01:45.887971900","meio_de_pagamento":"credito","localizacao":"sp4"}	|
		  |	{"id":"id5","tipo":"mercado5","valor":16.99,"descricao":"teste5","data":"2020-10-16T22:01:45.887971900","meio_de_pagamento":"debito","localizacao":"sp5"}	|
		  |	{"id":"id6","tipo":"mercado6","valor":11.99,"descricao":"teste6","data":"2020-10-17T22:01:45.887971900","meio_de_pagamento":"debito","localizacao":"sp6"}	|
		Quando for solicitado a consulta de transacoes com os dados abaixo
		  |	quantity	|	4	|
		  |	page		|		|
		Então deve ser retornado as transacoes com os dados abaixo
		  |	{"id":"id1","tipo":"mercado1","valor":12.99,"descricao":"teste1","data":"2020-10-12T22:01:45.887972","meio_de_pagamento":"debito","localizacao":"sp1"}	|
		  |	{"id":"id2","tipo":"mercado2","valor":13.99,"descricao":"teste2","data":"2020-10-13T22:01:45.887972","meio_de_pagamento":"debito","localizacao":"sp2"}	|
		  |	{"id":"id3","tipo":"mercado3","valor":14.99,"descricao":"teste3","data":"2020-10-14T22:01:45.887972","meio_de_pagamento":"credito","localizacao":"sp3"}	|
		  |	{"id":"id4","tipo":"mercado4","valor":15.99,"descricao":"teste4","data":"2020-10-15T22:01:45.887972","meio_de_pagamento":"credito","localizacao":"sp4"}	|
		E o status retornado da chamada deve ser 200

	Cenario: Consultar lista de transacoes com sucesso por data
		Dado que um usuario tenha sido cadastrado com os dados abaixo anteriormente
		  |	email				|	teste@gmail.com	|	
		  |	password			|	1234			|	
		  |	last_name			|	Doe				|	
		  |	first_name			|	John			|
		  |	device_id			|	id1				|
		E que este usuario ja realizou o login com os dados abaixo
		  |	email				|	teste@gmail.com	|	
		  |	password			|	1234			|	
		  |	device_id			|	id1				|
		E que foram cadastradas as transacoes com os dados abaixo
		  |	{"id":"id1","tipo":"mercado1","valor":12.99,"descricao":"teste1","data":"2020-10-12T22:01:45.887971900","meio_de_pagamento":"debito","localizacao":"sp1"}	|
		  |	{"id":"id2","tipo":"mercado2","valor":13.99,"descricao":"teste2","data":"2020-10-13T22:01:45.887971900","meio_de_pagamento":"debito","localizacao":"sp2"}	|
		  |	{"id":"id3","tipo":"mercado3","valor":14.99,"descricao":"teste3","data":"2020-10-14T22:01:45.887971900","meio_de_pagamento":"credito","localizacao":"sp3"}	|
		  |	{"id":"id4","tipo":"mercado4","valor":15.99,"descricao":"teste4","data":"2020-10-15T22:01:45.887971900","meio_de_pagamento":"credito","localizacao":"sp4"}	|
		  |	{"id":"id5","tipo":"mercado5","valor":16.99,"descricao":"teste5","data":"2020-10-16T22:01:45.887971900","meio_de_pagamento":"debito","localizacao":"sp5"}	|
		  |	{"id":"id6","tipo":"mercado6","valor":11.99,"descricao":"teste6","data":"2020-10-17T22:01:45.887971900","meio_de_pagamento":"debito","localizacao":"sp6"}	|
		Quando for solicitado a consulta de transacoes com os dados abaixo
		  |	since		|	16/10/2020	|
		  |	quantity	|				|
		  |	page		|				|
		Então deve ser retornado as transacoes com os dados abaixo
		  |	{"id":"id5","tipo":"mercado5","valor":16.99,"descricao":"teste5","data":"2020-10-16T22:01:45.887972","meio_de_pagamento":"debito","localizacao":"sp5"}	|
		  |	{"id":"id6","tipo":"mercado6","valor":11.99,"descricao":"teste6","data":"2020-10-17T22:01:45.887972","meio_de_pagamento":"debito","localizacao":"sp6"}	|
		E o status retornado da chamada deve ser 200

	Cenario: Consultar lista de transacoes com sucesso por quantitdade e pagina
		Dado que um usuario tenha sido cadastrado com os dados abaixo anteriormente
		  |	email				|	teste@gmail.com	|	
		  |	password			|	1234			|	
		  |	last_name			|	Doe				|	
		  |	first_name			|	John			|
		  |	device_id			|	id1				|
		E que este usuario ja realizou o login com os dados abaixo
		  |	email				|	teste@gmail.com	|	
		  |	password			|	1234			|	
		  |	device_id			|	id1				|
		E que foram cadastradas as transacoes com os dados abaixo
		  |	{"id":"id1","tipo":"mercado1","valor":12.99,"descricao":"teste1","data":"2020-10-12T22:01:45.887971900","meio_de_pagamento":"debito","localizacao":"sp1"}	|
		  |	{"id":"id2","tipo":"mercado2","valor":13.99,"descricao":"teste2","data":"2020-10-13T22:01:45.887971900","meio_de_pagamento":"debito","localizacao":"sp2"}	|
		  |	{"id":"id3","tipo":"mercado3","valor":14.99,"descricao":"teste3","data":"2020-10-14T22:01:45.887971900","meio_de_pagamento":"credito","localizacao":"sp3"}	|
		  |	{"id":"id4","tipo":"mercado4","valor":15.99,"descricao":"teste4","data":"2020-10-15T22:01:45.887971900","meio_de_pagamento":"credito","localizacao":"sp4"}	|
		  |	{"id":"id5","tipo":"mercado5","valor":16.99,"descricao":"teste5","data":"2020-10-16T22:01:45.887971900","meio_de_pagamento":"debito","localizacao":"sp5"}	|
		  |	{"id":"id6","tipo":"mercado6","valor":11.99,"descricao":"teste6","data":"2020-10-17T22:01:45.887971900","meio_de_pagamento":"debito","localizacao":"sp6"}	|
		Quando for solicitado a consulta de transacoes com os dados abaixo
		  |	since		|		|
		  |	quantity	|	2	|
		  |	page		|	1	|
		Então deve ser retornado as transacoes com os dados abaixo
		  |	{"id":"id3","tipo":"mercado3","valor":14.99,"descricao":"teste3","data":"2020-10-14T22:01:45.887972","meio_de_pagamento":"credito","localizacao":"sp3"}	|
		  |	{"id":"id4","tipo":"mercado4","valor":15.99,"descricao":"teste4","data":"2020-10-15T22:01:45.887972","meio_de_pagamento":"credito","localizacao":"sp4"}	|
		E o status retornado da chamada deve ser 200

	Cenario: Consultar lista de transacoes com sucesso por data, quantitdade e pagina
		Dado que um usuario tenha sido cadastrado com os dados abaixo anteriormente
		  |	email				|	teste@gmail.com	|	
		  |	password			|	1234			|	
		  |	last_name			|	Doe				|	
		  |	first_name			|	John			|
		  |	device_id			|	id1				|
		E que este usuario ja realizou o login com os dados abaixo
		  |	email				|	teste@gmail.com	|	
		  |	password			|	1234			|	
		  |	device_id			|	id1				|
		E que foram cadastradas as transacoes com os dados abaixo
		  |	{"id":"id1","tipo":"mercado1","valor":12.99,"descricao":"teste1","data":"2020-10-12T22:01:45.887971900","meio_de_pagamento":"debito","localizacao":"sp1"}	|
		  |	{"id":"id2","tipo":"mercado2","valor":13.99,"descricao":"teste2","data":"2020-10-13T22:01:45.887971900","meio_de_pagamento":"debito","localizacao":"sp2"}	|
		  |	{"id":"id3","tipo":"mercado3","valor":14.99,"descricao":"teste3","data":"2020-10-14T22:01:45.887971900","meio_de_pagamento":"credito","localizacao":"sp3"}	|
		  |	{"id":"id4","tipo":"mercado4","valor":15.99,"descricao":"teste4","data":"2020-10-15T22:01:45.887971900","meio_de_pagamento":"credito","localizacao":"sp4"}	|
		  |	{"id":"id5","tipo":"mercado5","valor":16.99,"descricao":"teste5","data":"2020-10-16T22:01:45.887971900","meio_de_pagamento":"debito","localizacao":"sp5"}	|
		  |	{"id":"id6","tipo":"mercado6","valor":11.99,"descricao":"teste6","data":"2020-10-17T22:01:45.887971900","meio_de_pagamento":"debito","localizacao":"sp6"}	|
		Quando for solicitado a consulta de transacoes com os dados abaixo
		  |	since		|	16/10/2020	|
		  |	quantity	|	2			|
		  |	page		|				|
		Então deve ser retornado as transacoes com os dados abaixo
		  |	{"id":"id5","tipo":"mercado5","valor":16.99,"descricao":"teste5","data":"2020-10-16T22:01:45.887972","meio_de_pagamento":"debito","localizacao":"sp5"}	|
		  |	{"id":"id6","tipo":"mercado6","valor":11.99,"descricao":"teste6","data":"2020-10-17T22:01:45.887972","meio_de_pagamento":"debito","localizacao":"sp6"}	|
		E o status retornado da chamada deve ser 200

	Cenario: Consultar lista de transacoes com sucesso por data, quantitdade e pagina
		Dado que um usuario tenha sido cadastrado com os dados abaixo anteriormente
		  |	email				|	teste@gmail.com	|	
		  |	password			|	1234			|	
		  |	last_name			|	Doe				|	
		  |	first_name			|	John			|
		  |	device_id			|	id1				|
		E que este usuario ja realizou o login com os dados abaixo
		  |	email				|	teste@gmail.com	|	
		  |	password			|	1234			|	
		  |	device_id			|	id1				|
		E que foram cadastradas as transacoes com os dados abaixo
		  |	{"id":"id1","tipo":"mercado1","valor":12.99,"descricao":"teste1","data":"2020-10-12T22:01:45.887971900","meio_de_pagamento":"debito","localizacao":"sp1"}	|
		  |	{"id":"id2","tipo":"mercado2","valor":13.99,"descricao":"teste2","data":"2020-10-13T22:01:45.887971900","meio_de_pagamento":"debito","localizacao":"sp2"}	|
		  |	{"id":"id3","tipo":"mercado3","valor":14.99,"descricao":"teste3","data":"2020-10-14T22:01:45.887971900","meio_de_pagamento":"credito","localizacao":"sp3"}	|
		  |	{"id":"id4","tipo":"mercado4","valor":15.99,"descricao":"teste4","data":"2020-10-15T22:01:45.887971900","meio_de_pagamento":"credito","localizacao":"sp4"}	|
		  |	{"id":"id5","tipo":"mercado5","valor":16.99,"descricao":"teste5","data":"2020-10-16T22:01:45.887971900","meio_de_pagamento":"debito","localizacao":"sp5"}	|
		  |	{"id":"id6","tipo":"mercado6","valor":11.99,"descricao":"teste6","data":"2020-10-17T22:01:45.887971900","meio_de_pagamento":"debito","localizacao":"sp6"}	|
		Quando for solicitado a consulta de transacoes com os dados abaixo
		  |	since		|	16/10/2020	|
		  |	quantity	|	1			|
		  |	page		|	1			|
		Então deve ser retornado as transacoes com os dados abaixo
		  |	{"id":"id6","tipo":"mercado6","valor":11.99,"descricao":"teste6","data":"2020-10-17T22:01:45.887972","meio_de_pagamento":"debito","localizacao":"sp6"}	|
		E o status retornado da chamada deve ser 200

	Cenario: Consultar lista de transacoes com erro data invalida
		Dado que um usuario tenha sido cadastrado com os dados abaixo anteriormente
		  |	email				|	teste@gmail.com	|	
		  |	password			|	1234			|	
		  |	last_name			|	Doe				|	
		  |	first_name			|	John			|
		  |	device_id			|	id1				|
		E que este usuario ja realizou o login com os dados abaixo
		  |	email				|	teste@gmail.com	|	
		  |	password			|	1234			|	
		  |	device_id			|	id1				|
		E que foram cadastradas as transacoes com os dados abaixo
		  |	{"id":"id1","tipo":"mercado1","valor":12.99,"descricao":"teste1","data":"2020-10-12T22:01:45.887971900","meio_de_pagamento":"debito","localizacao":"sp1"}	|
		  |	{"id":"id2","tipo":"mercado2","valor":13.99,"descricao":"teste2","data":"2020-10-13T22:01:45.887971900","meio_de_pagamento":"debito","localizacao":"sp2"}	|
		  |	{"id":"id3","tipo":"mercado3","valor":14.99,"descricao":"teste3","data":"2020-10-14T22:01:45.887971900","meio_de_pagamento":"credito","localizacao":"sp3"}	|
		  |	{"id":"id4","tipo":"mercado4","valor":15.99,"descricao":"teste4","data":"2020-10-15T22:01:45.887971900","meio_de_pagamento":"credito","localizacao":"sp4"}	|
		  |	{"id":"id5","tipo":"mercado5","valor":16.99,"descricao":"teste5","data":"2020-10-16T22:01:45.887971900","meio_de_pagamento":"debito","localizacao":"sp5"}	|
		  |	{"id":"id6","tipo":"mercado6","valor":11.99,"descricao":"teste6","data":"2020-10-17T22:01:45.887971900","meio_de_pagamento":"debito","localizacao":"sp6"}	|
		Quando for solicitado a consulta de transacoes com os dados abaixo
		  |	since		|	16-10-2020	|
		Então deve ser retornado uma resposta de erro com a seguinte mensagem "Not a valid date (try using dd/MM/yyyy)"
		E o status retornado da chamada deve ser 500

