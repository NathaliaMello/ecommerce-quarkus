# ecommerce-quarkus

Ecossistema completo de microserviços construído com Quarkus, Kafka e Kubernetes.
Projeto de estudo com foco em arquitetura profissional, observabilidade e resiliência.

## Arquitetura
```
Cliente 
└── API Gateway (8080)
├── user-service (8081) ── PostgreSQL (5432)
├── order-service (8082) ── PostgreSQL (5433)
│     └── publica eventos ──► Kafka (9092)
└── notification-service (8083)
└── consome eventos ◄── Kafka (9092)
```

## Serviços

| Serviço              | Tecnologia                   | Porta| Responsabilidade                   |
|----------------------|------------------------------|------|------------------------------------|
| api-gateway          | Quarkus                      | 8080 | Ponto de entrada único, roteamento |
| user-service         | Quarkus + PostgreSQL         | 8081 | Gestão de usuários                 |
| order-service        | Quarkus + PostgreSQL + Kafka | 8082 | Gestão de pedidos                  |
| notification-service | Quarkus + Kafka              | 8083 | Notificações assíncronas           |

## Infraestrutura

Toda a infraestrutura Docker roda em servidor dedicado na rede local.

| Serviço | Porta |
|---|---|
| PostgreSQL (users) | 5432 |
| PostgreSQL (orders) | 5433 |
| Kafka | 9092 |
| Kafka UI | 8090 |

Os arquivos Docker estão em `infra/`.

**Subindo tudo localmente:**
```powershell
cd infra
docker-compose up -d
```

**Subindo em servidor remoto:**
```powershell
cd infra
$env:KAFKA_HOST="192.XXX.XX.XXX"
docker-compose up -d
```

**Subindo só o Kafka (desenvolvimento rápido):**
```powershell
cd infra
docker-compose -f docker-compose.local.yml up -d
```

## Como rodar localmente

### Pré-requisitos
- Java 21
- Maven 3.9+
- Docker (para infraestrutura)

### Subindo a infraestrutura
```powershell
# Na máquina de infraestrutura
docker-compose up -d
```

### Subindo os serviços
```powershell
# Em terminais separados
cd user-service && ./mvnw quarkus:dev
cd order-service && ./mvnw quarkus:dev
cd api-gateway && ./mvnw quarkus:dev
cd notification-service && ./mvnw quarkus:dev
```

### Testando
Acessa `http://localhost:8080/q/swagger-ui` para explorar os endpoints via Gateway.

## Conceitos implementados

- API Gateway com roteamento e validação de headers
- Circuit Breaker com fallback semântico
- Idempotência via Idempotency-Key header
- Mensageria assíncrona com Kafka
- Comunicação resiliente entre microserviços
- Banco de dados por serviço (Database per Service pattern)

## Roadmap
- [x] Fase 1 — Fundação Quarkus: user-service com CRUD, JPA, tratamento de erros
- [x] Fase 2 — Arquitetura de microserviços: order-service, API Gateway, Circuit Breaker e idempotência
- [x] Fase 3 — Mensageria assíncrona: Kafka, eventos e notification-service
- [x] Fase 4 — Observabilidade (OpenTelemetry, Jaeger, Grafana)
- [x] Fase 5 — Segurança (Keycloak, JWT, RBAC)
- [x] Fase 6 — DevOps Parte 1 (Dockerfile otimizado, .gitignore, proteção de secrets)
- [x] Fase 7 — IA Generativa (Groq, Llama 3, geração dinâmica de notificações)
- [ ] Fase 8 — DevOps Parte 2 (Kubernetes, CI/CD)
- [x] Fase 9A - Frontend (Angular 19)
- [x] Fase 9B - Frontend (Native Federation - Arquitetura MFE)
- [x] Fase 9C - Frontend (Keycloak + OIDC no Shell)
- [ ] Fase 10 - Testes (Karma/ Jest + Cypress)
