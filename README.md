# Workshop om Amazon Web Services

## Slides

Slides på http://tiny.cc/aws-kurs-slides.

## Oppgave 1 – Logg inn

- https://aws-kurs.signin.aws.amazon.com/console
- Finn brukernavnet ditt på Confluence (lenke på slides)
- Bytt _region_ i AWS til den som står oppført ved brukeren din på Confluece

## Oppgave 2 – Sett opp en webserver på Amazons tjeneste _Elastic Beanstalk_

Vi starter med å opprette en _application_ på Elastic Beanstalk, med tilhørende _environment_ for å kjøre Java-applikasjoner.

- Velg _Elastic Beanstalk_ under _Services_ i toppmenyen
- Klikk _Create new application_, gi applikasjonen din et navn og klikk _Create_.
- Lag et nytt _environment_ ved å klikke _Create one now_ i den grå boksen midt på siden.
   - Velg _Web server environment_
   - Velg _Java_ under _Platform_
   - Trykk på _Configure more options_ (grå knapp nederst)
   - Velg _Highly available_ under _Configuration presets_ øverst på siden
   - Klikk _Create environment_
   - Det kommer opp et sort konsollvindu med tittelen _Creating \<appnavn\>-env_.

Nå opprettes et miljø for deg. Miljøet består blant annet av en webserver som er ferdig konfigurert for å kjøre Java-webapplikasjoner. 

#### _Ressurser_:
- [Amazons "Getting started"-guide for Elastic Beanstalk](http://docs.aws.amazon.com/elasticbeanstalk/latest/dg/GettingStarted.html#GettingStarted.Walkthrough.CreateApp)

## Oppgave 3 – Deploy eksempelapplikasjonen manuelt

Webserveren er nå klar, men foreløpig går den på tomgang uten noen kjørende applikasjon. I denne oppgaven skal vi deploye en enkel Java-applikasjon til serveren.

- Last ned vår ferdige eksempelapplikasjon her: [aws-workshop-ingen-db.jar](https://github.com/henriwi/aws-workshop/raw/ingen-db/app/dist/aws-workshop-ingen-db.jar?raw=true)
- Deploy denne til miljøet ditt på Beanstalk
- Gå på URL-en til applikasjonen og sjekk at applikasjonen kjører

#### Ressurser
- [Amazons "Getting started"-guide for Elastic Beanstalk](http://docs.aws.amazon.com/elasticbeanstalk/latest/dg/GettingStarted.html#GettingStarted.Walkthrough.DeployApp)


## Oppgave 4 – Sett opp Relational Database Service (RDS)

I denne oppgaven skal vi deploye en ny versjon av applikasjonen. Denne har funksjonalitet for å opprette todo-items i en database, og for at den skal fungere må vi legge til en databaseserver i miljøet vårt. Til dette skal vi bruke Amazons Relational Database Service.

- Gå til _Configuration_ -> _Data tier_ for å sette opp RDS.
- Velg et vilkårlig brukernavn og passord (f.eks. `admin`/`qwerty1234`). Passordet blir automatisk håndtert i Beanstalk, du kommer derfor ikke til å bruke det senere.
- La resten stå som default og klikk apply
- På tide med en kaffepause! Det tar ca 10 minutter før RDS-databasen er opprettet.
- Last ned ny versjon av vår eksempelapplikasjon med databasekonfigurasjon: [aws-workshop.jar](https://github.com/henriwi/aws-workshop/blob/master/app/dist/aws-workshop.jar?raw=true)
- Deploy denne til miljøet ditt på Beanstalk

Når databasen er opprettet går du inn på `{applikasjonsurl}/todo.html` og prøver å lage noen todo-oppføringer. Refresh siden, og se at oppføringene har blitt lagret i databasen.

#### Ressurser


## Oppgave 5 – Oppsett av auto scaling

Konfigurer opp autoskalering for applikasjonen din. Dette gjør du under _Configuration_ -> _Auto scaling_.

Skaleringen skal settes opp slik at det legges til én instans om antall requester i løpet av 1 minutter overstiger 10. Du skal nedskalere med én instans om antall requester i løpet av et minutt er under 5.

Velg en scaling cooldown på 60 sekunder.

#### Ressurser

## Oppgave 6 – Test av auto scaling

### Skalere opp
Åpne applikasjonen din nok ganger til at du overstiger 10 requester i løpet av 1 minutt. Vent følg med under _"Events"_ og vent på beskjed om at en instans er lagt til. Dette kan du også se under _"Health"_.

### Skalere ned
På grunn av hvordan Amazons overvåkning fungerer, vil ikke applikasjonen nedskalere av seg selv om den har null trafikk (spør oss om detaljer!).

Du må derfor sende noen requests til applikasjonen (men under 5 i løpet av et minutt) for å trigge nedskalering.

## Oppgave 7 – Sett opp alarmer for opp- og nedskalering

Man kan sette opp alarmer i Elastic Beanstalk som varsler deg om visse hendelser i applikasjonen.

Sett opp alarmer som sender deg e-post når terskelverdiene for opp- og nedskalering brytes. Dette gjør du under _Monitoring_. Når alarmene er definert dukker de opp under _Alarms_.

#### Ressurser

## Oppgave 8

S3 er Amazons tjeneste for å hoste statiske filer. I denne oppgaven skal dere laste opp et bilde i S3 og se at dette vises i applikasjonen.

- Opprett en S3-bucket i region Frankfurt med navnet `aws-kurs-gruppe{gruppenummer}`, f.eks. `aws-kurs-gruppe02`.
- Last opp et bilde. Bildet må ha filnavnet `bilde.jpg`.
- Gå til `{applikasjonsurl}/bilder.html` og se om bildet ditt vises.

#### Ressurser


## Oppgave 9
Amazon tilbyr SDK-er for flere språk mot tjenestene sine. Vi skal nå lage et lite Java-program som laster opp og henter ut filer til S3-bucketen vi laget i Oppgave 8. I denne oppgaven kjører vi Java-koden lokalt på egen maskin, og implementerer kode som kommuniserer med Amazons API-er. 

### Del 1: Sett opp tilgangsnøkler for AWS
Først må vi sette opp tilgangsnøkler for AWS på maskinen vår, slik at vi får tilgang til Amazons API-er når vi kjører koden lokalt.

- Velg brukernavnet ditt øverst til høyre i AWS Console og velg Security Credentials
- Velg Users og finn og velg brukeren din i listen
- Velg User Actions -> Manage Access Key
- Velg Create Access Key, kopier verdiene som vises og lagre disse så du har dem til senere
- Opprett filen `~/.aws/credentials` med følgende innhold
```
[default]
aws_access_key_id = <fyll inn>
aws_secret_access_key = <fyll inn>
```

### Del 2: Implementere løsning
- Åpne applikasjonen ved å åpne IntelliJ -> Open -> Velg pom.xml
- Åpne klassen `S3.java`.

Klassen inneholder startkode for å integrere mot S3. Oppgaven går ut på å skrive Java-kode for å gjøre følgende:

1. Last opp en fil til en S3-bucket og verifiser at den har blitt lastet opp ved å se i Amazon Console
2. Hent ned alle filer i din bucket og skriv ut filnavnene
3. Slett en fil fra bucketen

Relevante ressurser:

- [Eksempelkode på GitHub](https://github.com/awslabs/aws-java-sample/blob/master/src/main/java/com/amazonaws/samples/S3Sample.java)

## Applikasjon
Applikasjonen har følgende endepunkter:

- ```/``` : Viser hostname
- ```/todo.html``` : Viser en enkel frontend for listing og opprettelse av TODOs
- ```/bilder.html``` : Enkel HTML-side for bruk i oppgave 8
- ```/api/todo``` : REST API-et
