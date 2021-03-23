# language: pt
@RegistrarNovoUsuarioTeste
Funcionalidade: Teste de cadastro de novo usuario

	O sistema deve cadastrar de forma correta seguindo as seguintes restricoes:
	1-) quando for cadastrado com sucesso, deve ser retornado um token com o secret do usuario contido no payload
	2-) quanto for solicitada a criacao de um usuario que ja existe, deve ser retornado um erro
	3-) caso algum dos campos necessarios nao esteja presente, deve ser retornado um erro

	Cenario: Cadastro com sucesso
		Dado que o usario digitou os seguintes dados
		  |	email		|	teste@gmail.com	|	
		  |	password	|	1234			|	
		  |	last_name	|	Doe				|	
		  |	first_name	|	John			|
		E que o dispositivo utilizado pelo usuario tenha gerado um id de dispositivo aleatorio
		Quando for solicitado o cadastramento
		Então deve ser retornado um token de dados
		E o status retornado deve ser 200
		E o token deve conter os campos abaixo preenchidos dentro do payload
			|	secret	|
			|	device	|
		E o objeto "device" deve ter o campo "verification_code" preenchido
		
	Cenario: Erro de usuario ja cadastrado
		Dado que o usario digitou os seguintes dados
		  |	email		|	teste@gmail.com	|	
		  |	password	|	1234			|	
		  |	last_name	|	Doe				|	
		  |	first_name	|	John			|
		E que o dispositivo utilizado pelo usuario tenha gerado um id de dispositivo aleatorio
		E que ja exista um usuario cadastrado com os mesmos dados
		Quando for solicitado o cadastramento
		Então deve ser retornado uma excessao com a mensagem "User teste@gmail.com already exists"
		E o status retornado deve ser 500	
		
	Cenario: Erro de senha em branco
		Dado que o usario digitou os seguintes dados
		  |	email		|	teste@gmail.com	|	
		  |	password	|					|	
		  |	last_name	|	Doe				|	
		  |	first_name	|	John			|
		E que o dispositivo utilizado pelo usuario tenha gerado um id de dispositivo aleatorio
		Quando for solicitado o cadastramento
		Então deve ser retornado uma excessao com a mensagem "Password can't be null or empty"
		E o status retornado deve ser 500	
		
	Cenario: Erro de email em branco
		Dado que o usario digitou os seguintes dados
		  |	email		|					|	
		  |	password	|	1234			|	
		  |	last_name	|	Doe				|	
		  |	first_name	|	John			|
		E que o dispositivo utilizado pelo usuario tenha gerado um id de dispositivo aleatorio
		Quando for solicitado o cadastramento
		Então deve ser retornado uma excessao com a mensagem "Email can't be null or empty"
		E o status retornado deve ser 500	
		
	Cenario: Erro de nome em branco
		Dado que o usario digitou os seguintes dados
		  |	email		|	teste@gmail.com	|	
		  |	password	|	1234			|	
		  |	last_name	|	Doe				|	
		  |	first_name	|					|
		E que o dispositivo utilizado pelo usuario tenha gerado um id de dispositivo aleatorio
		Quando for solicitado o cadastramento
		Então deve ser retornado uma excessao com a mensagem "First name can't be null or empty"
		E o status retornado deve ser 500	
		
	Cenario: Erro de sobrenome em branco
		Dado que o usario digitou os seguintes dados
		  |	email		|	teste@gmail.com	|	
		  |	password	|	1234			|	
		  |	last_name	|					|	
		  |	first_name	|	John			|
		E que o dispositivo utilizado pelo usuario tenha gerado um id de dispositivo aleatorio
		Quando for solicitado o cadastramento
		Então deve ser retornado uma excessao com a mensagem "Last name can't be null or empty"
		E o status retornado deve ser 500	
		
	Cenario: Erro de senha nula
		Dado que o usario digitou os seguintes dados
		  |	email		|	teste@gmail.com	|	
		  |	last_name	|	Doe				|	
		  |	first_name	|	John			|
		E que o dispositivo utilizado pelo usuario tenha gerado um id de dispositivo aleatorio
		Quando for solicitado o cadastramento
		Então deve ser retornado uma excessao com a mensagem "Password can't be null or empty"
		E o status retornado deve ser 500	
		
	Cenario: Erro de email nulo
		Dado que o usario digitou os seguintes dados
		  |	password	|	1234			|	
		  |	last_name	|	Doe				|	
		  |	first_name	|	John			|
		E que o dispositivo utilizado pelo usuario tenha gerado um id de dispositivo aleatorio
		Quando for solicitado o cadastramento
		Então deve ser retornado uma excessao com a mensagem "Email can't be null or empty"
		E o status retornado deve ser 500	
		
	Cenario: Erro de nome nulo
		Dado que o usario digitou os seguintes dados
		  |	email		|	teste@gmail.com	|	
		  |	password	|	1234			|	
		  |	last_name	|	Doe				|	
		E que o dispositivo utilizado pelo usuario tenha gerado um id de dispositivo aleatorio
		Quando for solicitado o cadastramento
		Então deve ser retornado uma excessao com a mensagem "First name can't be null or empty"
		E o status retornado deve ser 500	
		
	Cenario: Erro de sobrenome nulo
		Dado que o usario digitou os seguintes dados
		  |	email		|	teste@gmail.com	|	
		  |	password	|	1234			|	
		  |	first_name	|	John			|
		E que o dispositivo utilizado pelo usuario tenha gerado um id de dispositivo aleatorio
		Quando for solicitado o cadastramento
		Então deve ser retornado uma excessao com a mensagem "Last name can't be null or empty"
		E o status retornado deve ser 500	
		
		