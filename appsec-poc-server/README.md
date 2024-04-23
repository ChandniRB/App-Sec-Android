# App-Sec-Android
Server Application for

## Security measures implemented
1. Secure storage of API key using `EncryptedSharedPreferences`
2. Device and App Integrity Checks using [Play Integrity API](https://developer.android.com/google/play/integrity/overview)
3. Secure communication using SSL Pinning

## Prerequisites
1. Java version 17
2. Maven version 3.8.8

## Setup

Set environment variables:
1. `APPSEC_CONFIG=<path to config.json>`
1. `DB_IP`
1. `DB_PORT`
1. `DB_NAME_NR`
1. `DB_NAME_NR_RO`
1. `DB_USER`
1. `DB_PASSWORD`
1. `HOST`


## Run

1. `mvn clean install`

2. `java -jar target/appSecPoc.war`

3. `docker build -t appSecPoc:latest .`

4. `docker run --network host --env DB_IP=<DB_IP> --env DB_PORT=<DB_PORT> --env DB_NAME_NR=<DB_NAME> --env DB_USER=<DB_USERNAME> --env DB_PASSWORD=<DB_PASSWORD> --env HOST=<HOST> -p 8443:8443 appSecPoc:latest .`

