# Pharmacy Microservices

Este projeto contém múltiplos módulos para simular um fluxo de pedidos, estoque, pagamentos e emissão de nota fiscal.

## Módulos

- **order**: Recebe pedidos e envia para verificação de estoque e pagamento.
- **stock**: Verifica disponibilidade de produtos em estoque.
- **payment**: Processa pagamentos de pedidos.
- **invoice**: Emite notas fiscais após confirmação de pagamento.
- **rabbitmq**: Mensageria para comunicação entre os microsserviços.

---

## Rodando com Docker Compose

1. Clone o projeto.

```bash
git clone https://github.com/pedro-scarelli/pharmacy.git
```
2. No diretório raiz, execute:

```bash
docker compose up --build
```
 Isso irá subir todos os serviços (rabbitmq, order, stock, payment, invoice) e configurar a rede entre eles.

### Testando a API

Você pode criar um pedido usando curl:
```bash
curl --location 'http://localhost:8081/order' \
--header 'Content-Type: application/json' \
--data '{
    "id": "486a3d51-2a3a-456c-a52f-69721dbaf641",
    "product": "teste",
    "quantity": 1,
    "price": 20.2
}'
```

Após enviar o pedido, os microserviços irão se comunicar via RabbitMQ para processar estoque, pagamento e emissão de nota fiscal.

Toda a configuraćão do RabbitMQ é criada automaticamente pelo arquivo
[definitions.json](definitions.json)

Pedro Scarelli, João Fissmer, Caio Abraao, Arthur Utpadel e Kevin Schops
