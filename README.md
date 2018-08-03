*URL SGBD (pour le serveur) :* 	

	app/server/src/main/resources/application.properties
	
*URL serveur (pour le client) :*

	app/client/src/config.ts
	app/client/src/app/app.module.ts (whitelist)

**Lancement serveur :**

	~RateMyManager/app/server$		./mvnw spring-boot:run
	
**Lancement application client :**

	~RateMyManager/app/client$		ionic serve
	
**Deploiement android :**

	~RateMyManager/app/client$		...
