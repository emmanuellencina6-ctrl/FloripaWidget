# FLORIPA — Widget Android

Proyecto Android Studio para un widget nativo de cuenta regresiva a Florianópolis.

## Fecha objetivo
23 de enero de 2027 a las 00:00, horario de Argentina (America/Argentina/Buenos_Aires).

## Qué incluye
- Widget de pantalla de inicio con diseño tropical y paneles translúcidos.
- Días, horas, minutos y segundos calculados desde la hora actual.
- Imagen de fondo incluida localmente; no requiere conexión a Internet.
- Actualización programada cada 60 segundos como mejor esfuerzo.

## Importante sobre la actualización
Android limita la frecuencia de actualización de widgets para ahorrar batería. El proyecto programa una alarma de 60 segundos, pero el sistema puede retrasarla, especialmente en modo ahorro/Doze. En Android 12 o superior, la actualización exacta depende del permiso especial de alarmas exactas (la app intenta abrir la pantalla para concederlo) y de las políticas del fabricante. Por eso, los segundos se recalculan correctamente en cada actualización, pero no se garantiza que la pantalla cambie cada segundo.

## Abrir y compilar
1. Abrí esta carpeta desde Android Studio (File > Open).
2. Esperá a que Gradle sincronice y descargue las dependencias/plugins necesarios.
3. Conectá el teléfono y elegí Run, o usá Build > Build APK(s).
4. Instalá la app.
5. En el Redmi: mantené presionada la pantalla de inicio > Widgets > Floripa Cuenta Regresiva.
6. Agregá el widget y ajustá su tamaño.

## Nota
Este entregable es el proyecto fuente completo. No incluye un APK precompilado ni firmado. La compilación final se realiza desde Android Studio.


## Compilar el APK automáticamente en GitHub (sin Android Studio)
1. Creá un repositorio nuevo en https://github.com/new (por ejemplo, `FloripaWidget`).
2. Descomprimí este ZIP en tu computadora. Es importante subir el CONTENIDO de la carpeta, incluyendo la carpeta oculta `.github`.
3. En el repositorio de GitHub, elegí **Add file > Upload files**, arrastrá todos los archivos y carpetas del proyecto y confirmá con **Commit changes**.
4. Abrí la pestaña **Actions**. Se ejecutará el flujo **Build FLORIPA Android widget**.
5. Cuando termine correctamente, abrí esa ejecución y descargá el artefacto **FLORIPA-widget-debug-apk**. Dentro estará `app-debug.apk`.
6. Pasá el APK al teléfono e instalalo. Android puede pedir permiso para instalar aplicaciones de esa fuente.

También podés iniciar una compilación manual desde **Actions > Build FLORIPA Android widget > Run workflow**.

El APK generado es de depuración (debug), apto para instalar y probar en tu propio teléfono; no es una versión de publicación para Play Store.
