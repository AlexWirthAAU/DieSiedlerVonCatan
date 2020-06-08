
[![Build Status](https://travis-ci.com/AlexWirthAAU/DieSiedlerVonCatan.svg?branch=master)](https://travis-ci.com/AlexWirthAAU/DieSiedlerVonCatan)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=DieSiedlerVonCatan&metric=alert_status)](https://sonarcloud.io/dashboard?id=DieSiedlerVonCatan)

<h1 align="center">Die Sielder von Catan</h1>

<p align="center"><img src="https://twoaveragegamers.com/wp-content/uploads/2018/10/catanBanner.jpg" height="200"></p>


## Spielablauf:
Zu Beginn loggt sich der User mit seinem Namen ein. Dann wird er in eine Lobby weitergeleitet, wo er nach Mitspielern suchen kann. Er kann zwischen 1 - 3 Mitspielern auswählen und das Spiel starten. Ist dies passiert, kommen alle gewählten Spieler zu einer Farbauswahl. Haben alle ihre Spielfarbe gewählt, kann das Hauptspiel gestartet werden. Es beginnt jener Spieler, der zuerst die App gestartet hat. Zu Beginn kann jeder Spieler „kostenlos“ zwei Siedlungen und Straßen bauen. Basierend auf den Landschaften, an denen er Siedlungen gebaut hat, erhält jeder Spieler seine Startrohstoffe. Dann beginnt der normale Spielablauf: Zu Beginn muss der Spieler würfeln, indem er das Handy schüttelt. Je nach Augenzahl werden die Rohstoffe verteilt und Spieler kommt zu einer Auswahl bei der aus folgenden Optionen wählen kann: 

  * Siedlung bauen 
  * Straße bauen 
  * Stadt bauen 
  * Handeln 
  * Banktausch 
  * Hafentausch 
  * Karte kaufen 
  * Karte ausspielen 
  * Baukosten ansehen 
  * Inventar ansehen 
  * Weiter 

\
Es darf jeweils nur eine Aktion pro Spielzug gewählt werden. Wird eine 7 gewürfelt, werden keine Rohstoffe verteilt und der Spieler darf den Räuber versetzten. Befindet sich der Räuber auf der Landschaft mit der gewürfelten Zahl gibt es ebenso keine Rohstoffe. Für jede Siedlung gibt es 2, für jeder Stadt 3 Siegpunkte. Eine Siegpunktkarte bringt einen Siegpunkt. Für die größte Rittermacht gibt es 2 Siegpunkte. Hat ein Spieler 10 Siegpunkte erreicht, ist das Spiel beendet und die Spieler kommen wieder in die Lobby. 

## Regelanpassungen:
* nur eine Aktion pro Spielzug erlaubt
* es gibt nur Spezialhäfen
* beim Versetzen des Räubers werden den Besitzern der angrenzenden Siedlungen/Städte keine Rohstoffe gestohlen
* Es ist auch möglich zu zweit zu spielen
* die größte Rittermacht erhält man bereits ab einem Ritter

**@author Alex Wirth**
* Gameboard
* Bauen
* Würfeln & Ressourcenverteilung

**@author Fabien Schaffenrath**
* Netzwerk
* Spielstart (edit)
* Räuber
* Schummeln

**@author Christina Senger**
* Netzwerk (edit)
* Spielstart
* Handeln mit Mitspielern, mit der Bank und über Häfen
* Entwicklungskarten
* Größte Rittermacht

### Gameboard
Das Spielbrett besteht aus 19 Feldern, die einer bestimmten Ressource zugeteilt sind und einen Würfelwert zwischen 2-12 haben. An jedem Knotenpunkt der Felder können Siedlungen gebaut werden, die mit Straßen verbunden sind. Einige Knotenpunkte sind Hafensiedlungen. An ihnen ist ein weißes Schiff zu erkennen. 

### Bauen
Entscheidet sich der Spieler eine Siedlung zu bauen, so werden ihm alle möglichen Knotenpunkte in rot markiert. Klickt er auf einen dieser markierten Punkte, wird der Punkt besiedelt und in seiner Spielerfarbe eingefärbt. Eine Siedlung kann nur an einer Straße des Spielers gebaut werden sofern die benachbarten Kreuzungen noch nicht besiedelt sind.
Straßen können entweder an andere bereits existierende Straßen des Spielers oder an eine seiner Siedlungen gebaut werden. Auch hier werden die möglichen Kanten zunächst rot eingefärbt. Baut der Spieler eine Straße, wird sie in seiner Spielfarbe eingefärbt.
Siedlungen können im Verlauf des Spieles zu Städten umgebaut werden. Städte werden als schwarze Quadrate mit Punkten der Spielerfarbe dargestellt.

### Würfeln & Rohstoffverteilung
Wenn ein Spieler an der Reihe ist, wird er aufgefordert zu würfeln. Dies passiert durch Schütteln seines Smartphones. Eine zufallsgenerierte Zahl wird dem Spieler angezeigt.
Je nach Zahl werden Rohstoffe an jene Spieler verteilt, die Siedlungen bzw. Städte an den betroffenen Feldern haben. Siedlungen bringen eine Einheit des Rohstoffs, Städte zwei. Wird die Zahl 7 gewürfelt startet die Aktion Räuber.

### Räuber 
Der Räuber belegt zu jeder Zeit eines der Rohstofffelder auf dem Spielfeld. Zu Beginn des Spiels befindet er sich am Spielfeld "Wüste". Würfelt ein Spieler die Zahl 7, muss er den Räuber auf ein anderes Spielfeld versetzen. 
Alle Spieler, die Siedlungen oder Städte besitzen, die an das Feld mit dem Räuber angrenzen, bekommen keine Rohstoffe beim Würfeln der entsprechenden Zahl.

### Schummeln
Ein Spieler kann zu jeder Zeit versuchen, einem anderen Spieler einen Rohstoff zu stehlen. Dabei wählt er einen Spieler und danach einen Rohstoff aus. Der zu bestehlende Spieler hat während seines nächsten Zuges die Möglichkeit, diesen Diebstahlsversuch aufzudecken. Dabei muss er das Diebstahlsicon berühren und raten, welche seiner Rohstoffe gestohlen wird. Sollte der Spieler richtig raten, so darf er versuchen, einen Rohstoff von seinem Dieb zurückzustehlen. Außerdem muss der Dieb seinen nächsten Zug aussetzen. Ratet der Spieler falsch, so wird ihm am Ende seines Spielzuges der Rohstoff gestohlen.
Gleichzeitig kann nur 1 Diebstahl an einem Spieler stattfinden.

### Netzwerk
Die grundsätzliche Kommunikation über das Netzwerk erfolgt durch Command Identifier gesendet als Strings. Zusätzlich gibt es ein GameSession Objekt, dass alle Daten zu einem Spiel speichert und bei Änderungen ebenfalls übertragen wird.
Server:
Der Server verfügt über eine Endlossschleife, die auf neue Verbindungen reagiert. Wird eine neue Verbindung erkannt, so wird ein seperater Listener Thread gestartet, der auf Nachrichten dieser Verbindung reagiert. Bei jeder Nachricht wird überprüft, welche Aktion gewünscht ist und ob dafür alle nötigen Informationen/Vorraussetzungen erfüllt sind. Falsl ja wird ein entsprechender Logikthread aufgerufen, der die Aktion letztendlich ausführt und gegebenfalls Nachrichten an Clients zurücksendet. Wird kein Logikthread gestartet, so wird in der Grundverbindung eine Fehlermeldung zurückgesendet.
Client:
Sobald der Client den Wilkommensbildschirm verlässt, ist im Hintergrund eine Verbindung zum Server aufgebaut. Ein ListenerThread wartet dauerhaft auf neue Nachrichten des Servers. Selbst sendet der Client Nachrichten über einen seperaten Thread an den Server. Wird eine Nachricht vom Server erhalten, so werden entsprechende lokale Daten aktualisiert und der derzeitige Handler aufgerufen. Der Handler führt dann die Interface Operationen aus, die der Nachricht folgen sollen.

### Handeln
Ein Spieler kann mit der Bank 4:1 handeln, sofern er von mindestens einem Rohstoff 4 oder mehr hat. Dazu wählt er den gewünschten und gebotenen Rohstoff aus und schließt den Handel ab. 
Besitzt er eine Siedlung er Stadt an einem Hafen, kann er auch den Hafentausch als Aktion wählen. Dieser funktioniert äquivalent zum Banktausch, allerdings wird im Verhältnis 3:1 getauscht. Im Gegensatz zum Originalspiel gibt es keine allgemeinen, sondern nur Spezialhäfen (Rohstoff des Hafens ist jener der angrenzenden Landschaft).  Der gewünschte Rohstoff kann beliebig gewählt werden, der gebotene muss einem Hafen im eigenen Besitz entsprechen.
Besitzt ein Spieler mindestens einen Rohstoff, kann er mit seinen Mitspielern handeln. Er kann die Rohstoffanzahl variieren und auch mehrere Rohstoffe auswählen. Das Handelsangebot wird an alle Mitspieler gesendet, die genug Rohstoffe haben. Diese können das Angebot annehmen oder ablehnen. Hat kein Mitspieler genug Rohstoffe oder alle lehnen ab, kann kein Handel durchgeführt werden. Andernfalls „gewinnt“ jener Spieler, der zuerst angenommen hat. Die Handelspartner bekommen eine Bestätigung.

### Entwicklungskarten
Insgesamt gibt es 26 Entwicklungskarten im Spiel. Als erstes werden Ritterkarte, Straßenbaukarte und Siegpunktkarte verkauft. Die restlichen Karten werden zufällig gekauft. Nach dem Ausspielen sind die Karten aus dem Spiel. Insgesamt gibt es fünf verschiedene Karten:
* Ritterkarte: der Spieler darf den Räuber versetzen. Grenzen an die neue Position des Räubers Siedlungen oder Städte anderer Spieler, so wird einem dieser Spieler ein zufälliger Rohstoff gestohlen.
* Straßenbaukarte: der Spieler darf zwei Straßen bauen 
* Erfindungskarte: der Spieler wählt einen Rohstoff, von dem er zwei erhält
* Monopolkarte: der Spieler wählt einen Rohstoff, von dem er die Vorräte aller anderen Spieler erhält
* Siegpunktkarte: der Spieler erhält bei Kauf einen Siegpunkt

### Größte Rittermacht
Die größte Rittermacht besitzt jener Spieler mit den meisten aktiven Ritterkarten. Wurde eine Ritterkarte gekauft, wird überprüft, ob der Spieler nun die größte Rittermacht besitzt. Ist dies der Fall, erhält er einen Siegpunkt. Wird die Ritterkarte ausgespielt, wird wieder geprüft und gegebenenfalls erhält ein anderer Spieler den Siegpunkt.
