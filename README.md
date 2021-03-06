SocIO
=====

* Author:    Timm Heuss (Timm.Heuss AT web.de)
* Website:   <http://heussd.github.com/socio/>
* GitHub:    <https://github.com/Heussd/socio/>


This software called SocIO implements the basic ideas of the concept [Social Resource Promotion](http://users.fbihome.de/~Heuss/srp/srp_heuss.pdf>) and utilizes semantic, REST and XMPP technologies in Java.


What does it do?
----------------
SocIO allows you to...

* assign tags to webpages and store them transparently in your personal RDF store.
* connect and exchange taggings with other Jabber-IDs (try <socio@jabber.ccc.de>).
* confirm tags that others gave to webpages.
* display related webpages, based on the tags assigned to the current page.
* follow [tags](http://localhost:8080/socio/rest/activity?tag=University), [users](http://localhost:8080/socio/rest/activity?user=socio@jabber.ccc.de) or your [entire peer activity](http://localhost:8080/socio/rest/activity) via RSS.


Requirements
------------

* A Jabber account. A dedicated account is suggested.
* For maximum confort: [Chromium](http://www.chromium.org/), [Google Chrome](http://www.google.de/chrome) or [Mozilla Firefox](http://www.mozilla.org/en-US/firefox/features/) browser (included HTML pages require some minor hacking to work with other compliant browsers, see notice below).


Build mantra
-------------
1. `git clone https://github.com/heussd/socio.git`
2. `mvn package`
3. Runnable JAR-file located at `target/socio-x.x.x-jar-with-dependencies.jar`
4. See folder `browser` for browser-integration


Update mantra
-------------
1. `git pull`
2. `mvn package`
3. Runnable JAR-file located at `target/socio-x.x.x-jar-with-dependencies.jar`
4. See folder `browser` for browser-integration

Usage
-----

*First run*: Execute the commandline `java -jar socio-x.x.x-jar-with-dependencies.jar`

Usual runs: Double click.


What do you think?
------------------

Feel free to mail the author your feedback: Timm.Heuss AT web.de.
