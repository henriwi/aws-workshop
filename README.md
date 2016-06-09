# Workshop om Amazon Web Services

## Slides

Slides på http://tiny.cc/aws-kurs-slides.

## Oppgave 1 – Logg inn

- https://aws-kurs.signin.aws.amazon.com/console
- Finn brukernavnet ditt på Confluence (lenke på slides)
- Bytt _region_ i AWS til den som står oppført ved brukeren din på Confluece

## Oppgave 2 – Sett opp en applikasjon på Elastic Beanstalk

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

## Oppgave 3 – Deploy eksempelapplikasjonen manuelt

- Last ned vår ferdige eksempelapplikasjon her [aws-workshop-ingen-db.jar](https://github.com/henriwi/aws-workshop/raw/ingen-db/app/dist/aws-workshop-ingen-db.jar?raw=true)
- Deploy denne til miljøet ditt på Beanstalk
- Gå på URL-en til applikasjonen og sjekk at den kjører


## Oppgave 4 – Sett opp Relational Database Service (RDS)

- Dette gjør du under _Configuration_ -> _Data tier_
- Velg et vilkårlig brukernavn og passord (f.eks. `admin`/`qwerty1234`). Passordet blir automatisk håndtert i Beanstalk, du kommer derfor ikke til å bruke det senere.
- La resten stå som default og klikk apply
- Last ned ny versjon av vår eksempelapplikasjon med databasekonfigurasjon: [aws-workshop.jar](https://github.com/henriwi/aws-workshop/blob/master/app/dist/aws-workshop.jar?raw=true)
- Deploy denne til miljøet ditt på Beanstalk

På tide med en kaffepause! Det tar ca 10 minutter før RDS-databasen er opprettet.

Når databasen er opprettet går du inn på applikasjonen din og prøver å lage noen todo-oppføringer. Refresh siden, og se at oppføringene har blitt lagret i databasen.


## Oppgave 5 – Oppsett av auto scaling

Konfigurer opp autoskalering for applikasjonen din. Dette gjør du under _Configuration_ -> _Auto scaling_.

Skaleringen skal settes opp slik at det legges til én instans om antall requester i løpet av 1 minutter overstiger 10. Du skal nedskalere med én instans om antall requester i løpet av et minutt er under 5.

Velg en scaling cooldown på 60 sekunder.

## Oppgave 6 – Test av auto scaling

### Skalere opp
Åpne applikasjonen din nok ganger til at du overstiger 10 requester i løpet av 1 minutt. Vent følg med under _"Events"_ og vent på beskjed om at en instans er lagt til. Dette kan du også se under _"Health"_.

### Skalere ned
På grunn av hvordan Amazons overvåkning fungerer, vil ikke applikasjonen nedskalere av seg selv om den har null trafikk (spør oss om detaljer!).

Du må derfor sende noen requests til applikasjonen (men under 5 i løpet av et minutt) for å trigge nedskalering.

## Oppgave 7 – Sett opp alarmer for opp- og nedskalering

Man kan sette opp alarmer i Elastic Beanstalk som varsler deg om visse hendelser i applikasjonen.

Sett opp alarmer som sender deg e-post når terskelverdiene for opp- og nedskalering brytes. Dette gjør du under _Monitoring_. Når alarmene er definert dukker de opp under _Alarms_.

## Oppgave 8

S3 er Amazons tjeneste for å hoste filer. I denne oppgaven skal dere laste opp et bilde i S3 og se at dette vises i applikasjonen.

- Opprett en S3-bucket i region Frankfurt med navnet `aws-kurs-gruppe{gruppenummer}`, f.eks. `aws-kurs-gruppe02`.
- Last opp et bilde. Bildet må ha filnavnet `bilde{gruppenummer}.jpg`
- Gå til `{applikasjonsurl}/bilder.html` og se om bildet ditt vises

## Oppgave 9
Amazon tilbyr SDK-er for flere språk mot tjenestene sine. Vi skal nå lage et lite Java-program som laster opp og henter ut filer til S3-bucketen som vi laget i Oppgave 8

- Velg brukernavnet ditt øverst til høyre i Amazon Console og velg Security Credentials
- Velg Users og finn og velg brukeren din i liste
- Velg User Actions -> Manage Access Key
- Velg Create Access Key og kopier verdiene som vises og lagre disse så du har de til senere
- Opprett filen ```.aws/credentials``` med følgende innhold
```
[default]
aws_access_key_id = <fyll inn>
aws_secret_access_key = <fyll inn>
```
- Åpne applikasjonen ved å åpne IntelliJ -> Open -> Velg pom.xml
- Åpne klassen ```S3.java```. 

Klassen inneholder startkode for å integrere mot S3. Oppgaven går ut på å gjøre følgende:

1. Last opp en fil til S3-bucket og verifiser at den har blitt lastet opp ved å se i Amazon Console
2. Hent ned alle filer i din bucket og skriv disse ut
3. Slett en fil fra bucketen

Relevante ressurser: TODO

## Applikasjon
Applikasjonen har følgende endepunkter:

- ```/``` : Viser hostname
- ```/todo.html``` : Viser en enkel frontend for listing og opprettelse av TODOs
- ```/api/todo``` : REST API-et
