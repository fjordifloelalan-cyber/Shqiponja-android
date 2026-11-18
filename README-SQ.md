# SHQIPONJA — aplikacion Android (prototip)

Ky është projekti fillestar Android Studio për aplikacionin SHQIPONJA, me pamje kuqezi dhe ndërfaqe në shqip.

## Çfarë përfshihet
- Ekrani kryesor me listën e bisedave shembull
- Ekran bisede me mesazhe lokale (vetëm në pajisje)
- Skedat Telefonatat dhe Cilësimet
- Emri dhe identiteti vizual SHQIPONJA

## E rëndësishme
Ky version është prototip i ndërfaqes. Mesazhet nuk dërgohen te persona të tjerë dhe nuk ka ende regjistrim, server, njoftime, telefonata reale ose enkriptim. Për këto nevojitet backend, konfigurim i sigurt dhe testim.

## Si të hapet
1. Instalo Android Studio në kompjuter.
2. Zgjidh **Open** dhe hap këtë dosje.
3. Lejo Gradle të shkarkojë varësitë.
4. Lidhe telefonin Android me USB (me USB debugging aktiv) ose përdor emulator.
5. Shtyp **Run** për ta provuar.

## Si të krijohet APK
Në Android Studio: **Build → Build Bundle(s) / APK(s) → Build APK(s)**.
APK-ja e debug gjendet zakonisht te `app/build/outputs/apk/debug/app-debug.apk`.

Për ta kthyer në aplikacion real, hapi tjetër është të lidhim autentikimin dhe mesazhet me një backend (p.sh. Firebase), të konfigurojmë rregullat e sigurisë dhe të testojmë dërgimin/pritjen në dy telefona.
