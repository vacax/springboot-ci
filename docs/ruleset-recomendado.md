# Ruleset recomendado para la rama protegida

Los gates del pipeline solo **bloquean** si la rama exige que pasen. Sin un ruleset (o branch protection),
un PR con checks en rojo se puede fusionar igual.

## Reglas

| Regla | Valor | Por qué |
| --- | --- | --- |
| Require a pull request before merging | 1 aprobación, descartar aprobaciones con commits nuevos | Nadie publica sin revisión |
| Require review from Code Owners | Sí | `.github/`, `Dockerfile` y archivos de IA tienen dueño (ver `CODEOWNERS`) |
| Require status checks to pass | Ver lista abajo, *strict* (rama actualizada) | Convierte los workflows en gates |
| Require code scanning results | CodeQL: *High or higher* | Segunda barrera para SAST, nativa de GitHub |
| Block force pushes | Sí | Protege el historial |
| Restrict deletions | Sí | |
| Require signed commits | Opcional (recomendado) | Integridad del autor |
| Require linear history | Opcional | Historial auditable |

Checks requeridos (nombre del job tal como aparece en el PR):

- `Build y pruebas (JDK 21)`
- `gitleaks`
- `Analizar (java-kotlin)` y `Analizar (actions)`
- `Revisión de dependencias`
- `Build, escaneo y SBOM`
- `ZAP baseline`
- `zizmor` (solo se ejecuta si el PR toca `.github/`; si se marca como requerido, configurar el workflow sin filtro `paths`)

## Aplicarlo con la API

Guarda este JSON como `ruleset.json` y ejecuta
`gh api -X POST repos/<owner>/<repo>/rulesets --input ruleset.json`:

```json
{
  "name": "proteger-rama-principal",
  "target": "branch",
  "enforcement": "active",
  "conditions": { "ref_name": { "include": ["~DEFAULT_BRANCH"], "exclude": [] } },
  "rules": [
    { "type": "deletion" },
    { "type": "non_fast_forward" },
    { "type": "pull_request", "parameters": {
        "required_approving_review_count": 1,
        "dismiss_stale_reviews_on_push": true,
        "require_code_owner_review": true,
        "require_last_push_approval": true,
        "required_review_thread_resolution": true } },
    { "type": "required_status_checks", "parameters": {
        "strict_required_status_checks_policy": true,
        "required_status_checks": [
          { "context": "Build y pruebas (JDK 21)" },
          { "context": "gitleaks" },
          { "context": "Analizar (java-kotlin)" },
          { "context": "Analizar (actions)" },
          { "context": "Revisión de dependencias" },
          { "context": "Build, escaneo y SBOM" },
          { "context": "ZAP baseline" } ] } },
    { "type": "code_scanning", "parameters": {
        "code_scanning_tools": [
          { "tool": "CodeQL", "security_alerts_threshold": "high_or_higher", "alerts_threshold": "errors" } ] } }
  ]
}
```

> En un repositorio personal con un solo mantenedor, la aprobación obligatoria impide fusionar tus propios PR.
> Para el laboratorio puedes dejar `required_approving_review_count` en 0 y conservar los checks.

## Configuración complementaria (Settings > Advanced Security)

- Dependency graph, Dependabot alerts y Dependabot security updates: activados.
- Secret Protection: secret scanning y **push protection** activados.
- Private vulnerability reporting: activado (lo usa `SECURITY.md`).
- Code scanning: lo configura `codeql.yml` (no activar además el *default setup*, entran en conflicto).

## Settings > Actions > General

- *Workflow permissions*: **Read repository contents and packages permissions** (por defecto de solo lectura).
- Desmarcar *Allow GitHub Actions to create and approve pull requests*.
- *Fork pull request workflows from outside collaborators*: **Require approval for all external contributors**.
