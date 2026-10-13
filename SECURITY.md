# Política de seguridad

Este repositorio es material docente de la asignatura **MCW-621 Ciberseguridad en la Nube** (PUCMM / UCJC).
Aun así, se gestiona como un proyecto real: los reportes de seguridad se atienden con el proceso descrito aquí.

## Versiones soportadas

| Versión | Rama / tag | Soporte |
| --- | --- | --- |
| 1.x | `devsecops-referencia`, tags `v1-*` | ✅ Recibe correcciones de seguridad |
| 0.x | `master`, tags `v0.0.x`, `v0-baseline` | ❌ Sin soporte. Contiene vulnerabilidades **intencionales** con fines didácticos |

## Cómo reportar una vulnerabilidad

**No abras un issue público.** Usa uno de estos canales privados:

1. **Preferido:** el reporte privado de GitHub. Ve a la pestaña *Security* del repositorio y pulsa
   *Report a vulnerability* (<https://github.com/vacax/springboot-ci/security/advisories/new>).
2. Alternativo: correo a `ca.camacho@ce.pucmm.edu.do` con el asunto `[SEGURIDAD] springboot-ci`.

Incluye la versión o commit afectado, los pasos para reproducir, el impacto que observaste y, si la tienes,
una propuesta de corrección.

## Qué puedes esperar

| Etapa | Plazo objetivo |
| --- | --- |
| Acuse de recibo | 3 días hábiles |
| Evaluación inicial (aceptado / rechazado, con severidad CVSS) | 10 días hábiles |
| Corrección de severidad crítica o alta | 30 días |
| Corrección de severidad media o baja | Próxima versión planificada |

Si el reporte se acepta, se publica un *GitHub Security Advisory* con el crédito a quien lo reportó (si lo desea).
Si se rechaza, se explica el motivo.

## Fuera de alcance

- Las vulnerabilidades intencionales de `master`, `v0-baseline` y de las ramas `demo/*`. Están documentadas en el README.
- Las credenciales de las ramas `demo/*`: son **ficticias** y no dan acceso a ningún servicio.
