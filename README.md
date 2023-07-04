# MACJEE
URL Übersicht:    
  "/auth" --> Authorising, wie Login/Registrierung  
  "/" --> "home": die Seite, die einen Überblick über MACJEE verschafft    
  "/dashboard" --> Angemeldete Nutzer haben (je nach Rolle) hier einen Überblick über die Funktionen, die sie nutzen können.
## zum Starten:  
In IDE Intellij unter:  
File -> New -> Project from Version Control...  
dort dann den Github link angeben, wo sie diese README gefunden haben.
## zur Datenbank  
Datenbankinformationen sind bereits in der application.properties enthalten, es muss nichts veränder werden.  
Wenn es Probleme geben solle (und eine neue Datenbank benutzt wird) müssen die Rollen ein weiteres mal in der Datenbank definiert werden:  
id, role:  
- 1, ROLE_CUSTOMER  
- 2, ROLE_COMPANY  
- 3, ROLE_USER  
- 4, ROLE_ADMIN  