
En el directorio diagrams/ se encuentran los diagramas relacionados.
En el directorio postman/ se encuentra el proyecto postman para probar los endpoints de la API.

Instrucciones para la ejecución del proyecto:

1-. clonar el repositorio.
git clone 

2-. Movernos dentro de la raiz del proyecto.
cd user-rest-api

3-. Desde la raiz (paso 2), construir el proyecto.
gradle build

4-. Ejecutar.
java -jar build/libs/user-rest-api-0.0.1-SNAPSHOT.jar


El proyecto se expone por el puerto 8081 y tiene dos endpoints principales:
	
	http://localhost:8081/user-rest-api/token
		Tipo POST.
		Necesita como entrada un objeto JSON como sigue:
		{
			"email" : "admin@admin.com",
			"password" : "Admin123"
		}
	Nota: Este usuario se carga al iniciar el proyecto. Con fines de poder obtener el token inicial.
	
	http://localhost:8081/user-rest-api/v1/user
		Tipo POST.
		Necesita como entrada un objeto JSON como sigue:
		{
			"name": "juan Perez", 
			"email": "juan@perez.org", 
			"password": "Algo123", 
			"phones": [ 
						{ 
							"number": "1234567", 
							"citycode": "1",
							"countrycode": "57" 
						}
					] 
		}
		
		Nota: Las validaciones sobre el input seran las siguientes:
				- name es requerido y no debe estar vacío.
				- email es requerido y debe cumplir formarto (aaaa@aaaaa.aa)
				- password es requerido y debe cumplir con: 1 mayuscula exacta, 2 numeros exactos y el resto minusculas.
				- Recuerde tambien que debe incluir el token, obtenido desde el endpoint /token.
				    Este debe ir como cabecera "Authorization" : "Bearer {aquiElToken}".
				    Si utiliza el proyecto Postman, solo bastaría con remplazar el token en la pestaña Athorization.
				    
				    
				    
				    	
		