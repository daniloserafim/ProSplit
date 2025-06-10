# ProSplit

**ProSplit** é uma aplicação para facilitar a divisão de contas entre colegas, considerando descontos, taxas e entregas proporcionais aos itens pedidos por cada pessoa.

---

## 🐳 Como rodar com Docker

### Pré-requisitos

- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/)

### Passos

1. Clone este repositório:
   ```bash
   git clone https://github.com/seu-usuario/prosplit.git
   cd prosplit
   ```

2. Execute o projeto com Docker Compose:
   ```bash
   docker-compose up --build
   ```

3. Acesse a aplicação:
   - Frontend: http://localhost:3000
   - Backend (API): http://localhost:8080

4. Para parar a aplicação:
   ```bash
   docker-compose down
   ```

## 📦 Build automático

O Dockerfile de cada serviço cuida automaticamente das dependências e build da aplicação. Basta executar o docker-compose para ter o ambiente pronto, sem necessidade de instalar Java, Maven ou Node localmente.

## ✨ Contribuições

Contribuições são bem-vindas! Sinta-se à vontade para abrir issues ou pull requests.

**ProSplit — Facilitando dividir contas e fortalecer amizades!**
