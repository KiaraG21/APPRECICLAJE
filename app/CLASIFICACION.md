# Clasificación — estado de integración

Código Android: `src/main/java/com/example/appecolim/clasificacion/`.

Incluye menú con búsqueda, siete categorías, dos imágenes locales por carrusel, avance cada 4,5 segundos, deslizamiento manual, cantidad con dos decimales, controles de 0,50 kg, confirmación, guardado SQLite y pantalla de éxito. Tras el éxito vuelve a Clasificación a los 2,5 segundos. Los intervalos y el paso son decisiones de implementación, no especificaciones de Figma.

Los SVG del usuario se conservan en `design/clasificacion/iconos-usuario`. Según la última indicación, `bateria.svg` se usa en Pilas y Baterías; la lata de Metales procede del recurso original exportado de Figma. El ZIP aportó las seis capas del icono de jardín. Los recursos Android renderizados están en `src/main/res/drawable-nodpi`, junto a las 14 imágenes generadas. Inter incluye su licencia en `design/clasificacion`.

## Inicio y login

El salto temporal a Clasificación se ha retirado de MainActivity y `clasificacionPreview=false`. MainActivity conserva el comportamiento de `master` (abrir RegistroActivity). No se ha incorporado splash-android ni se ha inventado un login: `origin/master` en `1fc6bb8` no contiene esas pantallas.

Cuando exista el login, guardar la sesión mediante el EmpleadoDAO existente y abrir:

```java
startActivity(new Intent(this, com.example.appecolim.clasificacion.ClasificacionActivity.class));
```

En modo normal se obtiene el empleado de `empleado_local`, se inserta en `residuo_local` con estado `pendiente`, y solo se muestra éxito tras confirmar la inserción. Sin empleado no se guarda. La sincronización de pendientes con el servidor no está implementada por este módulo.

El soporte de pruebas permanece desactivado. La constante de compilación `CLASIFICACION_PREVIEW` es siempre false en release. Si se reactivara para pruebas, también se debe pasar el extra `clasificacion_preview=true`; las inserciones usarían únicamente `ecoreg_clasificacion_pruebas.db`.

## Verificación y pendientes

Se añadieron pruebas unitarias de precisión, separadores decimales y entradas inválidas. No hay dispositivo Android conectado ni un AVD configurado, por lo que aún falta comprobar visualmente las pantallas y los gestos en un dispositivo. El login queda pendiente de su publicación.

El límite de Figma impidió extraer el check exacto de éxito y los iconos negros de los detalles de Metales y Punzocortante: esos detalles reutilizan los iconos de sus categorías, y éxito usa un check tipográfico. No se afirma una igualdad visual completa con Figma hasta sustituir esos tres recursos y verificar en Android.

No crear commit ni hacer push hasta que el usuario lo indique expresamente.

Verificación final: assembleDebug, testDebugUnitTest y lintDebug finalizaron correctamente. Lint mantiene advertencias; no se han suprimido con una baseline. Fecha de registro compatible con FechaUtils de master.
