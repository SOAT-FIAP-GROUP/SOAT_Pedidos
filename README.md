# Tech Challenge - Fase 4

---

## Integrantes do grupo:

- Jose Augusto dos Santos- RM 361650
- Nathalia Matielo Rodrigues- RM 363100
- Rogerio Inacio Silva Junior- RM 364104
- Vanessa Moreira Wendling - RM 362741

---

## 📦 Evidência da cobertura de testes SonarCloud

![pedido-evidencia-sonar.png](pedido-evidencia-sonar.png)

---

## 📦 Funcionalidades Entregues na Fase 4

- Implementação de API Gateway
- Implementação de autenticação via Cognito
- Implementação de infra com Kubernets com terraform
- Criação de repositorio de infra com terraform para os bancos de dados SQL/NOSQL
- Branches Main/Master protegidas
- Deploy automatizado via CI/CD
- Criação de microserviços: Pedido, Produtos, Pagamento e Produção
- Comunicação entre serviços via chamada direta
- Implementação de um caminho de teste com BDD
- Cobertura de testes superior a 80%

---
## 💡 Solução Proposta

Foi desenvolvido um sistema de autoatendimento para fast food, que:

- Permite que o cliente faça pedidos diretamente via interface, podendo se identificar por CPF, cadastrar-se ou permanecer anônimo.


- O cliente pode montar o combo em etapas opcionais: Lanche, Acompanhamento e Bebida, com exibição clara de nome, descrição e preço.


- Possui integração com Mercado Pago via QRCode para pagamento.


- Exibe para o cliente um monitor de acompanhamento do pedido, com status atualizados em tempo real: Recebido, Em preparação, Pronto e Finalizado.


- Notifica o cliente quando o pedido estiver pronto para retirada.


- Permite ao estabelecimento gerenciar clientes, produtos e acompanhar os pedidos em andamento.



---

### Requisitos contemplados

- Escalabilidade e alta disponibilidade com Kubernetes.
- Segurança e gerenciamento de configuração via Secrets e ConfigMaps.
- Visibilidade e controle total via painel administrativo.

---


##  Modelagem de dados

![pedido-banco.png](pedido-banco.png)

---

## 🎥 Vídeo Demonstrativo

Assista ao vídeo com demonstração do funcionamento da aplicação e da arquitetura: https://youtu.be/hqAwc3LQJfs


---

## ⚙️ Tecnologias Utilizadas

- Java 17
- Spring Boot
- Kubernetes 
- Amazon Cognito
- API Gateway
- MariaDB (Banco de dados)
- Amazon RDS
- Terraform
- Github Actions

---

## 🚀 Como Executar Localmente

1. Instale JDK 17 e Maven.
2. Clone o repositório:
    ```bash
    git clone https://github.com/SOAT-FIAP-GROUP/SOAT_Pedidos.git
    cd SOAT_Pedidos
    ```
3. Crie o banco de dados RDS via terraform

4. Execute a aplicação via Maven:
    ```bash
    mvn spring-boot:run
    ```
5. Acesse a documentação Swagger:
    ```
    http://localhost:8082/swagger-ui/index.html
    ```
## 🚀 Como Executar via Kubernetes
1. Instalar Kubernetes com Minikube, ou
2. Instalar Docker Desktop e ativar Kubernetes
   - Se estiver usando **Minikube** habilite o metrics-server (necessário para HPA funcionar):
    ```bash
    minikube addons enable metrics-server
    ```
   - Aplique os manifetos YAML:
    ```bash
    kubectl apply -f k8s/
    ```
   - **Se estiver usando Minikube:**
    ```bash
    minikube service pedido-service
    ```

   Esse comando deve abrir automaticamente uma aba no navegador com a URL.  
   Acesse `.../swagger-ui/index.html` no final da URL para ver a documentação dos endpoints.

   - **Se estiver usando Docker Desktop:**

   Acesse diretamente no navegador:

   - http://localhost:30000/
   - http://localhost:30000/swagger-ui/index.html

   Neles você poderá visualizar a documentação interativa (OpenAPI/Swagger) dos endpoints disponíveis.

   - Endpoints para Health Checks:
      - Liveness Probe:
     ```bash
     /actuator/health/liveness
     ```
      - Readiness Probe:
     ```bash
     /actuator/health/readiness
     ```

---


## 📚 Endpoints e Exemplos


#### 🔍📚 Collection API (Postman)

Para ter acesso aos Endpoints e exemplos faça o download da collection e importe na sua IDE de preferência:
[Collection API](https://drive.google.com/uc?export=download&id=1xp52ZV3tcdlxPq5wG7C6tpEA4O6jXKvB)

---

### 🧾 Pedido

#### ➕ Cadastrar novo pedido

**POST** `/api/pedido`

**Body:**
```json
{
  "idUsuario": 1,
  "itens": [
    {
      "produtoId": 1,
      "quantidade": 2
    }
  ]
}
```

**Resposta:**
```json
{
  "id": 2,
  "idUsuario": 1,
  "status": "RECEBIDO",
  "valorTotal": 31.80,
  "dataHoraSolicitacao": "2025-08-03T17:26:30.8292112",
  "tempoTotalPreparo": "00:20:00",
  "itens": [
    {
      "id": 2,
      "pedidoId": 2,
      "produtoId": 1,
      "quantidade": 2,
      "precoUnitario": 15.90,
      "precoTotal": 31.80
    }
  ]
}
```

#### 📄 Buscar pedidos por status

**GET** `/api/pedido?status=RECEBIDO`

**Resposta:**
```json
[
  {
    "id": 2,
    "idUsuario": 1,
    "status": "RECEBIDO",
    "valorTotal": 31.80,
    "dataHoraSolicitacao": "2025-08-03T17:26:30",
    "tempoTotalPreparo": "00:20:00",
    "itens": [
      {
        "id": 2,
        "pedidoId": 2,
        "produtoId": 1,
        "quantidade": 2,
        "precoUnitario": 15.90,
        "precoTotal": 31.80
      }
    ]
  }
]
```

#### 📄 Buscar pedidos por codigo

**GET** `/api/pedido/buscar/:codigoPedido`

**Resposta:**
```json
{
  "id": 1,
  "idUsuario": 1,
  "status": "EM_PREPARACAO",
  "valorTotal": 31.80,
  "dataHoraSolicitacao": "2025-08-03T17:10:53",
  "tempoTotalPreparo": "00:20:00",
  "itens": [
    {
      "id": 1,
      "pedidoId": 1,
      "produtoId": 1,
      "quantidade": 2,
      "precoUnitario": 15.90,
      "precoTotal": 31.80
    }
  ]
}
```

#### 🔄 Alterar status do pedido

**PUT** `/api/pedido/status/:codigo?status=EM_PREPARACAO`

**Resposta:**
```json
{
  "id": 1,
  "idUsuario": 1,
  "status": "EM_PREPARACAO",
  "valorTotal": 31.80,
  "dataHoraSolicitacao": "2025-08-03T17:10:53",
  "tempoTotalPreparo": "00:20:00",
  "itens": [
    {
      "id": 1,
      "pedidoId": 1,
      "produtoId": 1,
      "quantidade": 2,
      "precoUnitario": 15.90,
      "precoTotal": 31.80
    }
  ]
}
```

#### 📄 Lista de pedidos ordenada

**GET** `/api/pedido/listarPedidos`

**Resposta:**
```json
[
    {
        "id": 3,
        "idUsuario": 1,
        "status": "PRONTO",
        "valorTotal": 31.80,
        "dataHoraSolicitacao": "2025-08-05T20:39:12",
        "tempoTotalPreparo": "00:20:00",
        "itens": [
            {
                "id": 3,
                "pedidoId": 3,
                "produtoId": 1,
                "quantidade": 2,
                "precoUnitario": 15.90,
                "precoTotal": 31.80
            }
        ]
    }
]
````

#### ❌ Remover pedido da fila de preparo

**DELETE** `/api/pedido/remover/fila/:codigoPedido`

**Resposta:** `204 No Content`

---


