Project:&nbsp;&nbsp;Environment photos  
Repo:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;https://github.com/of78ojax/wahlzeit/  
Tag:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;adap-cw03  
Diff:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;https://github.com/of78ojax/wahlzeit/compare/adap-cw02...of78ojax:adap-cw03


## Programmierung
Wie gewünscht habe ich die Klassen `Photo, PhotoManger, PhotoFactory` um die jeweilige Spezialisierung erweitert. 
### EnvironmentPhoto
Ich habe mich für Umgebungsfotos entschieden und möchte hier alle mögliche Natur, aber auch die Möglichkeit lassen urbane Bilder einzufügen.  
Die Implementierung stützt sich zunächst in großen Teilen auf die der `Photo`-Klasse. D.h. ich habe versucht wo es ging die Methoden der Oberklasse aufzurufen, um die Standard/gemeinsamen Datenstrukturen zu nutzen. 
Die `writeOn(), readFrom()`-Methoden wickeln den Großteil der Abarbeitung über die Fotoklasse ab und ich musste nur noch die neu eingeführte Kategorisierung in die unterschiedlichen Umgebung in der Unterklasse hinzufügen.  
### EnvironmentPhotoFactory & EnvironmentPhotoFactory
Hier sieht die Sache sehr ähnlich aus. Wie im Forum vorgeschlagen habe ich in der Oberklasse die statische Instanz jeweils mit einer Spezialisierung vor initialisiert. So können die ``getInstanz())`` weiter benutzt werden, geben aber die Spezialisierung zurück.
In der Spezialisierung ist wenig dazu gekommen, da die Hauptaufgaben ja bereits durch die Oberklasse implementiert ist. Ich habe hauptsächlich dafür gesorgt, dass die Rückgaben bzw. verwalteten Fotos auch der Spezialisierung der Fotos entsprechen. Da zum erstellen und verwalten die Instanzen vorinitialisiert sind, kann dies auch übergreifend angenommen werden und die getter wandeln die Rückgabe aus der Oberklasse nur um.  
Entsprechend sorgt die Instanz der Factory dafür, dass auch nur Environment Fotos erstellt werden.
## Fragen
### Warum erweitern statt ersetzen?
Zum einen weil die Aufgabe es so verlangt, zum anderen weil die Abstraktion die Übersicht vereinfacht.
Fotos haben allgemeine Daten, die in der Oberklasse zusammengefasst werden. Die Spezialisierung führt für sich Eigenschaften hinzu die, nicht zu allgemeinen Fotos passen. Die Kapselung sorgt für eine räumliche Trennung der Informationen und lassen sich dadurch leichter finden bzw. die Bedeutung erkennen.  
Der Polymorphismus erzeugt zusätzlich die Möglichkeit die Unterklassen leicht auszutauschen bzw. Implementierungen zu testen. Würde man die Klasse ersetzen müsste man alles rückgängig machen / vollständig neu aufbauen => großer Aufwand.  
Ähnlich zum vorangegangen muss mit der Spezialisierung weniger angepasst werden. Funktionen, die die ``Photo``-Klasse nutzen können dies auch weiterhin tun, die Spezialisierung lässt sich dort einfach weiter verwenden.
### Welche Test 
Die Test untersuchen zunächst die korrekte Funktionsweise der einzelnen Funktionen und Konstruktoren. Also ob einfach nachvollziehbare Werte das gewünschte Ergebnis bzw. Funktionalität liefern. Zusätzlich werden Default Parameter abgefragt, um unerwartet Anfangszustände zu verhindern.  
Dann soll natürlich die korrekte Funktionsweise / Typisierung durch die Spezialisierung der Management bzw. Factory-Klassen getestet werden. Also werden nach und nach einige Photos erzeugt und abgefragt, ob der richtige Typ zurück gegeben wird.


