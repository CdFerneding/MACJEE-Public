# MACJEE
URL Übersicht:    
  "/auth" --> Authorising, wie Login/Registrierung  
  "/" --> "home": die Seite, die einen Überblick über MACJEE verschafft    
  "/dashboard" --> Angemeldete Nutzer haben (je nach Rolle) hier einen Überblick über die Funktionen, die sie nutzen können.
## zum Starten:  
In IDE Intellij unter:  
File -> New -> Project from Version Control...  
Dort dann den Github link angeben.
## zur Datenbank  
Datenbankinformationen sind bereits in der application.properties enthalten, es muss nichts verändert werden.  
Wenn es Probleme geben solle (und eine neue Datenbank benutzt wird) müssen die Rollen ein weiteres Mal in der Datenbank definiert werden:  
id, role:  
- 1, ROLE_CUSTOMER  
- 2, ROLE_COMPANY  
- 3, ROLE_USER  
- 4, ROLE_ADMIN  
## zu den Skills:
Die Skills wurden zu Beginn auf Englisch in der Datenbank gespeichert. Erst später haben wird die Webseiten-Sprache auf Deutsch festgelegt.  
Allerdings werden nun noch bei einigen Kunden die Skills auf Englisch angezeigt, da diese vor der Änderung definiert wurden.