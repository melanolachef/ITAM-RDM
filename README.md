# ITAM - Sistema de Gestão de Ativos de TI (Rei dos Motores)

## Sobre o Projeto

O ITAM (IT Asset Management) é uma solução corporativa desenvolvida para modernizar e centralizar o controle de infraestrutura de TI da empresa Rei dos Motores. O sistema substitui controles manuais e descentralizados por uma aplicação web robusta, focada na integridade dos dados, rastreabilidade patrimonial e suporte à tomada de decisão gerencial.

A aplicação gerencia o ciclo de vida completo dos ativos, desde a aquisição, passando por manutenções e transferências de responsabilidade, até o descarte final, integrando dados financeiros e operacionais.

## Funcionalidades Principais

O sistema foi modularizado para atender a diferentes vertentes da gestão de TI:

### 1. Gestão de Hardware e Ativos
* **Inventário Centralizado:** Cadastro detalhado de equipamentos (Notebooks, Desktops, Monitores, Impressoras e Celulares).
* **Rastreabilidade:** Vínculo direto entre equipamentos e colaboradores responsáveis. O sistema mantém um histórico de auditoria (logs) registrando todas as movimentações e trocas de titularidade.
* **Gestão de Ciclo de Vida:** Controle de status operacionais (Disponível, Em Uso, Manutenção, Descarte).
* **Identificação Física:** Geração automática de etiquetas QR Code para cada ativo cadastrado, facilitando a auditoria física.

### 2. Inteligência Financeira e Manutenção
* **Cálculo de Depreciação:** O sistema calcula automaticamente o valor atual do ativo baseando-se na data de compra e valor original, permitindo uma visão real do patrimônio tecnológico.
* **Histórico de Manutenções:** Módulo dedicado ao registro de ordens de serviço, fornecedores e custos de reparo, permitindo análise de gastos por equipamento.

### 3. Gestão de Telefonia Corporativa
* **Controle de Linhas:** Gestão de linhas móveis vinculadas a colaboradores.
* **Alertas de Vencimento:** Sistema inteligente que alerta visualmente sobre datas de recarga (planos pré-pagos) ou vencimento de faturas (planos pós-pagos).

### 4. Gestão de Softwares e Acessórios
* **Licenciamento:** Controle de licenças de software, chaves de ativação e datas de expiração para evitar inconformidades legais.
* **Estoque de Periféricos:** Controle quantitativo de itens de consumo rápido e baixo valor (mouses, teclados, cabos), com indicadores de estoque mínimo.

### 5. Painel Gerencial (Dashboard)
* Visualização gráfica e métricas em tempo real sobre a saúde do parque tecnológico, incluindo distribuição por tipo de equipamento e status de disponibilidade.

### 6. Segurança e Controle de Acesso
* Autenticação segura via banco de dados.
* Hierarquia de perfis de usuário (Administrador e Usuário Comum), garantindo que apenas pessoal autorizado possa alterar dados sensíveis.

## Tecnologias Utilizadas

O projeto foi desenvolvido seguindo padrões de arquitetura MVC (Model-View-Controller) e boas práticas de engenharia de software.

**Backend e Infraestrutura**
* **Java 17:** Linguagem base do projeto.
* **Spring Boot 3:** Framework para agilidade na configuração e desenvolvimento.
* **Spring Data JPA:** Camada de persistência e abstração de banco de dados.
* **Spring Security:** Gestão de autenticação, autorização e criptografia de senhas.
* **MySQL:** Banco de dados relacional.

**Frontend e Interface**
* **Thymeleaf:** Motor de templates para renderização dinâmica de páginas no servidor (Server-Side Rendering).
* **Bootstrap 5:** Framework CSS para estilização responsiva e componentes de interface.
* **Chart.js:** Biblioteca JavaScript para renderização dos gráficos do dashboard.

**Ferramentas de Desenvolvimento**
* **Maven:** Gerenciamento de dependências e build.
* **Git/GitHub:** Versionamento de código.
