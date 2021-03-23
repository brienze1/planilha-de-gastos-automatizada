# language: pt
@LoginTeste
Funcionalidade: Teste de login de usuario

	O sistema deve realizar o login de forma correta seguindo as seguintes restricoes:
	1-) quando for realizado o login com sucesso, deve ser retornado um token de acesso com os dados do usuario e data expiracao preenchidos
	2-) quanto for solicitada o login de um usuario que nao existe, deve ser retornado um erro
	2-) quanto for solicitada o login e algum dado nao vier preenchido, deve ser retornado um erro
	2-) quanto for solicitada o login e a senha nao bater, deve ser retornado um erro
	2-) quanto for solicitada o login e o dispositivo nao estiver cadastrado para este usuario, deve ser retornado um erro

	Cenario: Login com sucesso
		Dado que o dispositivo do usuario informou os seguintes dados no login
		  |	email		|	teste@gmail.com	|	
		  |	password	|	1234			|	
		  |	device_id	|	id1				|	
		E que ja exista um usuario cadastrado com os dados abaixo
		  |	email		|	teste@gmail.com	|	
		  |	password	|	1234			|	
		  |	last_name	|	Doe				|	
		  |	first_name	|	John			|
		  |	device_id	|	id1				|
		Quando for solicitado o login
		Então deve ser retornado um token de acesso
		E o status que foi retornado deve ser 200
		E o token de acesso deve conter os campos abaixo preenchidos dentro do payload
			|	name		|
			|	device_id	|
			|	user_id		|
		E o token deve conter uma data de expiracao valida

	Cenario: Login com sucesso, dispositivo nao cadastrado
		Dado que o dispositivo do usuario informou os seguintes dados no login
		  |	email		|	teste@gmail.com	|	
		  |	password	|	1234			|	
		  |	device_id	|	id2				|	
		E que ja exista um usuario cadastrado com os dados abaixo
		  |	email		|	teste@gmail.com	|	
		  |	password	|	1234			|	
		  |	last_name	|	Doe				|	
		  |	first_name	|	John			|
		  |	device_id	|	id1				|
		Quando for solicitado o login
		Então deve ser retornado um token de acesso
		E o status que foi retornado deve ser 200
		E o token de acesso deve conter os campos abaixo preenchidos dentro do payload
			|	name		|
			|	device_id	|
			|	user_id		|
		E o token deve conter uma data de expiracao valida

	Cenario: Login com erro de senha incorreta
		Dado que o dispositivo do usuario informou os seguintes dados no login
		  |	email		|	teste@gmail.com	|	
		  |	password	|	1235			|	
		  |	device_id	|	id1				|	
	  E que ja exista um usuario cadastrado com os dados abaixo
		  |	email		|	teste@gmail.com	|	
		  |	password	|	1234			|	
		  |	last_name	|	Doe				|	
		  |	first_name	|	John			|
		  |	device_id	|	id1				|
		Quando for solicitado o login
		Então deve ser retornado uma exception com a mensagem "Password does not match"
		E o status que foi retornado deve ser 500

	Cenario: Login com erro de usuario nao encontrado
		Dado que o dispositivo do usuario informou os seguintes dados no login
		  |	email		|	teste@gmail.com	|	
		  |	password	|	1234			|	
		  |	device_id	|	id1				|	
		Quando for solicitado o login
		Então deve ser retornado uma exception com a mensagem "User not found"
		E o status que foi retornado deve ser 500

	Cenario: Login com erro de email vazio
		Dado que o dispositivo do usuario informou os seguintes dados no login
		  |	email		|					|	
		  |	password	|	1234			|	
		  |	device_id	|	id1				|	
		E que ja exista um usuario cadastrado com os dados abaixo
		  |	email		|	teste@gmail.com	|	
		  |	password	|	1234			|	
		  |	last_name	|	Doe				|	
		  |	first_name	|	John			|
		  |	device_id	|	id1				|
		Quando for solicitado o login
		Então deve ser retornado uma exception com a mensagem "Email can't be null or blank"
		E o status que foi retornado deve ser 500

	Cenario: Login com erro de email nulo
		Dado que o dispositivo do usuario informou os seguintes dados no login
		  |	password	|	1234			|	
		  |	device_id	|	id1				|	
		E que ja exista um usuario cadastrado com os dados abaixo
		  |	email		|	teste@gmail.com	|	
		  |	password	|	1234			|	
		  |	last_name	|	Doe				|	
		  |	first_name	|	John			|
		  |	device_id	|	id1				|
		Quando for solicitado o login
		Então deve ser retornado uma exception com a mensagem "Email can't be null or blank"
		E o status que foi retornado deve ser 500

	Cenario: Login com erro de device_id vazio
		Dado que o dispositivo do usuario informou os seguintes dados no login
		  |	email		|	teste@gmail.com	|	
		  |	password	|	1234			|	
		  |	device_id	|					|	
		E que ja exista um usuario cadastrado com os dados abaixo
		  |	email		|	teste@gmail.com	|	
		  |	password	|	1234			|	
		  |	last_name	|	Doe				|	
		  |	first_name	|	John			|
		  |	device_id	|	id1				|
		Quando for solicitado o login
		Então deve ser retornado uma exception com a mensagem "DeviceId can't be null or blank"
		E o status que foi retornado deve ser 500

	Cenario: Login com erro de device_id nulo
		Dado que o dispositivo do usuario informou os seguintes dados no login
		  |	email		|	teste@gmail.com	|	
		  |	password	|	1234			|	
		E que ja exista um usuario cadastrado com os dados abaixo
		  |	email		|	teste@gmail.com	|	
		  |	password	|	1234			|	
		  |	last_name	|	Doe				|	
		  |	first_name	|	John			|
		  |	device_id	|	id1				|
		Quando for solicitado o login
		Então deve ser retornado uma exception com a mensagem "DeviceId can't be null or blank"
		E o status que foi retornado deve ser 500
		
	Cenario: Login com erro de senha vazia
		Dado que o dispositivo do usuario informou os seguintes dados no login
		  |	email		|	teste@gmail.com	|	
		  |	password	|					|	
		  |	device_id	|	id1				|	
		E que ja exista um usuario cadastrado com os dados abaixo
		  |	email		|	teste@gmail.com	|	
		  |	password	|	1234			|	
		  |	last_name	|	Doe				|	
		  |	first_name	|	John			|
		  |	device_id	|	id1				|
		Quando for solicitado o login
		Então deve ser retornado uma exception com a mensagem "Password can't be null or blank"
		E o status que foi retornado deve ser 500
		
	Cenario: Login com erro de senha nula
		Dado que o dispositivo do usuario informou os seguintes dados no login
		  |	email		|	teste@gmail.com	|	
		  |	device_id	|	id1				|	
		E que ja exista um usuario cadastrado com os dados abaixo
		  |	email		|	teste@gmail.com	|	
		  |	password	|	1234			|	
		  |	last_name	|	Doe				|	
		  |	first_name	|	John			|
		  |	device_id	|	id1				|
		Quando for solicitado o login
		Então deve ser retornado uma exception com a mensagem "Password can't be null or blank"
		E o status que foi retornado deve ser 500
