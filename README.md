# Play Your List — MS-AV-01

Sistema de playlists para ambientes com HPWM, dividido em 4 microsserviços Spring Boot.

## Serviços e portas

| Serviço       | Porta | Banco (H2 em memória) |
|---------------|-------|------------------------|
| `musicas`     | 8081  | `musicasdb`            |
| `playlists`   | 8082  | `playlistsdb`          |
| `reproducoes` | 8083  | `reproducoesdb`        |
| `api`         | 8084  | -                      |

O `api` depende dos outros três (usa Open Feign pra chamá-los), então **suba `musicas`, `playlists` e `reproducoes` antes**.

## Como rodar

Em 4 terminais separados, dentro de cada pasta:

```bash
mvn spring-boot:run
```

Requer JDK 17 instalada (não só JRE) e `JAVA_HOME` apontando pra ela.

Console do H2 de cada serviço: `http://localhost:<porta>/h2-console` (JDBC URL: `jdbc:h2:mem:<nome>db`, user `sa`, senha em branco).

## Endpoints principais

**musicas** (`:8081`)
- `POST /musicas`, `GET /musicas`, `GET /musicas/{id}`, `PUT /musicas/{id}`, `DELETE /musicas/{id}`

**playlists** (`:8082`)
- `POST /playlists`, `GET /playlists`, `GET /playlists/{id}`, `PUT /playlists/{id}`, `DELETE /playlists/{id}`
- `POST /playlists/{id}/musicas/{musicaId}`, `DELETE /playlists/{id}/musicas/{musicaId}`, `GET /playlists/{id}/musicas`

**reproducoes** (`:8083`)
- `POST /reproducao` (body: `{"playlistId": 1}`)
- `GET /reproducao/{playlistid}`, `GET /reproducao/total/{playlistid}`

**api** (`:8084`, orquestra via Open Feign)
- `POST /api/adicionar/{playlistId}/musicas/{musicaId}` — valida música e playlist antes de vincular
- `PUT /api/executar/{playlistId}` — valida a playlist e registra a reprodução

## Observações

- Cada microsserviço tem seu próprio banco H2 em memória (independente), por isso a FK de `playlist_musicas` para `musicas` do enunciado foi removida — a validação de existência da música é feita pelo `api` via Feign, não por constraint de banco.
- O enunciado menciona `POST /statistic` para o endpoint `PUT /api/executar/{playlistId}`, mas só existe `POST /reproducao` especificado no serviço `reproducoes`. Implementado chamando `/reproducao`.
