# language: pt
@AutoLoginTeste
Funcionalidade: Teste de auto login de usuario

	O sistema deve realizar o auto login de forma correta seguindo as seguintes restricoes:
	1-) para poder realizar o auto login devem ser respeitadas as seguintes regras
		1.1-) O usuario deve ter sido cadastrado nesse dispostivo ou ter sido feito ao menos 1x o login dele
		1.2-) primeiro o dispositivo deve ter sido validado com seu codigo de acesso
		1.3-) segundo o auto-login deve estar habilitado para o usuario
	2-) quando for realizado o auto login com sucesso, deve ser retornado um token de acesso com os dados do usuario e data expiracao preenchidos
	3-) quando for solicitado o auto login, e o auto login nao estiver habilitado para este usuario, deve ser retornado um erro
	4-) quanto for solicitada o auto login de um usuario que nao existe, deve ser retornado um erro
	5-) quanto for solicitada o auto login e o device_id ou o email nao vier preenchido, deve ser retornado um erro
	6-) quanto for solicitada o auto login e o dispositivo nao estiver verificado, deve ser retornado um erro
	7-) quanto for solicitada o auto login e o dispositivo nao estiver cadastrado para este usuario, deve ser retornado um erro

	Cenario: Auto Login com sucesso
		Dado que o dispositivo do usuario informou os seguintes dados no auto login
		  |	email		|	teste@gmail.com	|	
		  |	device_id	|	id1				|	
		E que um usuario foi cadastrado com os dados abaixo anteriormente
		  |	email		|	teste@gmail.com	|	
		  |	password	|	1234			|	
		  |	last_name	|	Doe				|	
		  |	first_name	|	John			|
		  |	device_id	|	id1				|
		E que o dispositivo "id1" ja foi verificado
		E que o auto login foi setado como "true" nas configuracoes desse usuario
		Quando for solicitado o auto login
		Então deve ser retornado o token de acesso
		E o status da chamada retornado deve ser 200
		E o token de acesso recebido deve conter os campos abaixo preenchidos dentro do payload
			|	name		|
			|	device_id	|
			|	user_id		|
		E deve conter uma data de expiracao valida

	Cenario: Auto Login com erro, configuracao nao habilitada
		Dado que o dispositivo do usuario informou os seguintes dados no auto login
		  |	email		|	teste@gmail.com	|	
		  |	device_id	|	id1				|	
		E que um usuario foi cadastrado com os dados abaixo anteriormente
		  |	email		|	teste@gmail.com	|	
		  |	password	|	1234			|	
		  |	last_name	|	Doe				|	
		  |	first_name	|	John			|
		  |	device_id	|	id1				|
		E que o dispositivo "id1" ja foi verificado
		E que o auto login foi setado como "false" nas configuracoes desse usuario
		Quando for solicitado o auto login
		Então deve ser retornado uma exception com a seguinte mensagem "User configuration doesn't allow auto-login"
		E o status da chamada retornado deve ser 500

	Cenario: Auto Login com erro, dispositivo nao verificado
		Dado que o dispositivo do usuario informou os seguintes dados no auto login
		  |	email		|	teste@gmail.com	|	
		  |	device_id	|	id1				|	
		E que um usuario foi cadastrado com os dados abaixo anteriormente
		  |	email		|	teste@gmail.com	|	
		  |	password	|	1234			|	
		  |	last_name	|	Doe				|	
		  |	first_name	|	John			|
		  |	device_id	|	id1				|
		E que o auto login foi setado como "true" nas configuracoes desse usuario
		Quando for solicitado o auto login
		Então deve ser retornado uma exception com a seguinte mensagem "Device not verified"
		E o status da chamada retornado deve ser 500

	Cenario: Auto Login com erro, dispositivo nao cadastrado
		Dado que o dispositivo do usuario informou os seguintes dados no auto login
		  |	email		|	teste@gmail.com	|	
		  |	device_id	|	id2				|	
		E que um usuario foi cadastrado com os dados abaixo anteriormente
		  |	email		|	teste@gmail.com	|	
		  |	password	|	1234			|	
		  |	last_name	|	Doe				|	
		  |	first_name	|	John			|
		  |	device_id	|	id1				|
		E que o auto login foi setado como "true" nas configuracoes desse usuario
		Quando for solicitado o auto login
		Então deve ser retornado uma exception com a seguinte mensagem "Unknown device"
		E o status da chamada retornado deve ser 500

	Cenario: Auto Login com erro de usuario nao encontrado
		Dado que o dispositivo do usuario informou os seguintes dados no auto login
		  |	email		|	teste@gmail.com	|	
		  |	device_id	|	id1				|	
		Quando for solicitado o auto login
		Então deve ser retornado uma exception com a seguinte mensagem "User not found"
		E o status da chamada retornado deve ser 500

	Cenario: Auto Login com erro de email vazio
		Dado que o dispositivo do usuario informou os seguintes dados no auto login
		  |	email		|					|	
		  |	device_id	|	id1				|	
		Quando for solicitado o auto login
		Então deve ser retornado uma exception com a seguinte mensagem "Email can't be null or blank"
		E o status da chamada retornado deve ser 500

	Cenario: Auto Login com erro de email nulo
		Dado que o dispositivo do usuario informou os seguintes dados no auto login
		  |	device_id	|	id1				|	
		Quando for solicitado o auto login
		Então deve ser retornado uma exception com a seguinte mensagem "Email can't be null or blank"
		E o status da chamada retornado deve ser 500

	Cenario: Auto Login com erro de device_id vazio
		Dado que o dispositivo do usuario informou os seguintes dados no auto login
		  |	email		|	teste@gmail.com	|
		  |	device_id	|					|	
		Quando for solicitado o auto login
		Então deve ser retornado uma exception com a seguinte mensagem "DeviceId can't be null or blank"
		E o status da chamada retornado deve ser 500

	Cenario: Auto Login com erro de device_id nulo
		Dado que o dispositivo do usuario informou os seguintes dados no auto login
		  |	email		|	teste@gmail.com	|
		Quando for solicitado o auto login
		Então deve ser retornado uma exception com a seguinte mensagem "DeviceId can't be null or blank"
		E o status da chamada retornado deve ser 500
		