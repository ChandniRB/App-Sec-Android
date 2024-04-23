# App-Sec-Android
Application for demonstrating security features in Android

## Security measures implemented
1. Secure storage of API key using `EncryptedSharedPreferences`
2. Device and App Integrity Checks using [Play Integrity API](https://developer.android.com/google/play/integrity/overview)
3. Secure communication using SSL Pinning

## Tech stack
1. Spring-boot for server ([appsec-poc-server](https://github.com/ChandniRB/App-Sec-Android/tree/master/appsec-poc-server)) 
2. Kotlin for Android app ([client](https://github.com/ChandniRB/App-Sec-Android/tree/master/client))
