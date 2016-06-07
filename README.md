# Workshop om Amazon Web Services

### Slides

Slides på http://tiny.cc/aws-kurs-slides.

### Oppgave 1 – Logg inn

- https://aws-kurs.signin.aws.amazon.com/console
- Finn brukernavnet ditt på Confluence (lenke på slides)
- Bytt _region_ i AWS til den som står oppført ved brukeren din på Confluece

### Oppgave 2 – Sett opp en applikasjon på Elastic Beanstalk

- Velg _Elastic Beanstalk_ under _Services_ i toppmenyen
- Klikk _Create new application_, gi applikasjonen din et navn og klikk _Create_.
- Lag et nytt _environment_ ved å klikke _Create one now_ i den grå boksen midt på siden.
   - Velg _Web server environment_
   - Velg _Java_ under _Platform_
   - Trykk på _Configure more options_ (grå knapp nederst)
   - Velg _Highly available_ under _Configuration presets_ øverst på siden
   - Klikk _Create environment_
   - Det kommer opp et sort konsollvindu med tittelen _Creating \<appnavn\>-env_. 
   
Nå opprettes miljøet ditt for deg. Dette består blant annet av en EC2-instans og en lastbalanserer. Vi forklarer straks på tavla hva dette betyr. 

### Oppgave 3 – Deploy eksempelapplikasjonen manuelt

- Last ned vår ferdige eksempelapplikasjon fra [GitHub](https://github.com/henriwi/aws-workshop/blob/master/app/dist/aws-workshop-1.0-SNAPSHOT.jar?raw=true)
- Deploy denne til miljøet ditt på Beanstalk
- Gå på URL-en til applikasjonen og sjekk at den kjører


### Oppgave 4 – Sett opp Relational Database Service (RDS)

- Dette gjør du under _Configuration_ -> _Data tier_
- Velg et vilkårlig brukernavn og passord (f.eks. `admin`/`qwerty1234`), det spiller ingen rolle hva
- La resten stå som default

Gå på FYLL UT URL HER og se at data hentes og lagres.


### Oppgave 5 – Oppsett av auto scaling

Konfigurer opp autoskalering for applikasjonen din. Dette gjør du under _Configuration_ -> _Auto scaling_.

Skaleringen skal settes opp slik at det legges til én instans om antall requester i løpet av 1 minutter overstiger 10. Du skal nedskalere med én instans om antall requester i løpet av et minutt er under 5.

Velg en scaling cooldown på 60 sekunder.

### Oppgave 6 – Test av auto scaling

#### Skalere opp
Åpne applikasjonen din nok ganger til at du overstiger 10 requester i løpet av 1 minutt. Vent følg med under _"Events"_ og vent på beskjed om at en instans er lagt til. Dette kan du også se under _"Health"_. 

#### Skalere ned
På grunn av hvordan Amazons overvåkning fungerer, vil ikke applikasjonen nedskalere av seg selv om den har null trafikk (spør oss om detaljer!).

Du må derfor sende noen requests til applikasjonen (men under 5 i løpet av et minutt) for å trigge nedskalering. 

### Oppgave 7 – Sett opp alarmer for opp- og nedskalering

Man kan sette opp alarmer i Elastic Beanstalk som varsler deg om visse hendelser i applikasjonen.

Sett opp alarmer som sender deg e-post når terskelverdiene for opp- og nedskalering brytes. Dette gjør du under _Monitoring_. Når alarmene er definert dukker de opp under _Alarms_.