# Workshop om Amazon Web Services

### Oppgave 1: Sett opp en applikasjon på Elastic Beanstalk

- Velg _Elastic Beanstalk_ under _Services_ i toppmenyen
- Klikk _Create new application_, gi applikasjonen din et navn, og klikk _Create_.
- Lag et nytt _environment_ ved å klikke _Create one now_ i den grå boksen midt på siden.
   - Velg _Web server environment_
   - Velg _Java_ under _Platform_
   - La resten stå som default og klikk _Create environment_
   - Det kommer opp et sort konsollvindu med tittelen _Creating <appnavn>-env_. Vent noen minutter mens opprettelsen av environmentet fullføres.

#### Alternativt: Sett opp med scaling når man lager environmentet i første omgang
- ...
- Velg _Java_ under _Platform_
- Trykk på _Configure more options_ (grå knapp nederst)
- Under _Scaling_ -> _Modify_
  - Environment type: `Load balancing, auto scaling`
  - Save
- Create environment

Dette vil ta ca 5 minutter


### Installer `eb`-klienten til Elastic Beanstalk

`brew install aws-elasticbeanstalk`

Dette er antakelig ikke noe vi trenger. Slett i så fall punktet.


### Deploy eksempelapplikasjonen manuelt
`mvn clean install`

_Upload and deploy_ -> Velg `hello-world-1.0-SNAPSHOT.jar`, last opp.

Gå på URL og se at den svarer.

### Auto scaling

Configuration -> Scaling, settings -> Environment type -> _Load balancing, auto scaling_ -> Save

Her kan det passe å ta en teorisesjon på tavla

#### Sett opp auto scaling-regler

Under _Auto scaling_:

- Scaling cooldown: 15

Under _Scaling trigger_:

- Trigger measurement: `RequestCount`
- Trigger statistic: `Sum`
- Unit of measurement: `Count`
- Measurement period: `1`
- Breach duration: `1`
- Upper threshold: `1`
- Upper breach scale increment: `1`
- Lower threshold: `0`
- Lower breach scale increment: `-1`

### Sett opp RDS

App dashboard

Configuration -> Data Tier (nederst) -> _"create a new RDS database"

User/pass: `admin`/`qwerty1234`. Velg defaults ellers. Klikk _Apply_.

Dette tar ca 10 minutter. Kaffepause.








