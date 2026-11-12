# BEMI 💚
## Sobre o projeto
O projeto BEMI foi desenvolvido para auxiliar idosos que moram sozinhos, oferecendo autonomia, acessibilidade e segurança em seu cotidiano. O isolamento social, a dificuldade na gestão de documentos e consultas, que frequentemente resultam em idas perdidas, e a crescente exposição a golpes representam riscos cotidianos que impactam diretamente a qualidade de vida e a tranquilidade dessas pessoas e de seus familiares.

Através de uma interface acessível e de recursos de apoio, o BEMI organiza documentos, gerencia consultas, emite avisos importantes e mantém o contato familiar a apenas um toque de distância. Seu objetivo é transformar a tecnologia em cuidado prático, simplificando a rotina, incentivando a prevenção contra fraudes e aproximando gerações.

## Tema da Global Solution — CitizenTech

O BEMI está alinhado ao desafio **CitizenTech**, proposto pela FIAP na Global Solution 2026.2, que busca utilizar a tecnologia para resolver problemas reais e melhorar a qualidade de vida das pessoas. O BEMI transforma necessidades do cotidiano em uma solução tecnológica acessível e centrada no cidadão.

## Fluxo de navegação: O aplicativo foi organizado para oferecer uma navegação simples e intuitiva, considerando as necessidades de pessoas idosas.
<img width="390" height="305" alt="image" src="https://github.com/user-attachments/assets/937d5347-90fe-48d8-b72f-bc82d81e1268" />

## Splash Screen: 
apresenta a identidade visual do BEMI e verifica se o usuário já visualizou a introdução ou possui uma sessão ativa.
<p align="center">
  <img src="https://github.com/user-attachments/assets/401ffea7-fbc5-4e10-a73d-5632d03cfbd7" width="220" alt="Splash Screen">
</p>

## Introdução: 
apresenta a proposta do aplicativo e seus principais benefícios por meio de telas explicativas.
<p align="center">
  <img src="https://github.com/user-attachments/assets/8151efd0-d727-494d-9a9a-ce936d3e900e" width="180" alt="Introdução 1">
  <img src="https://github.com/user-attachments/assets/748bef46-203c-41a1-ad49-c8b189739476" width="180" alt="Introdução 2">
  <img src="https://github.com/user-attachments/assets/33ae06c6-a076-4c86-9b35-842ddfca483a" width="180" alt="Introdução 3">
</p>


## Tela de acesso: 
permite que o usuário entre em sua conta, realize um cadastro ou continue como visitante.
<p align="center">
  <img width="200" alt="acesso" src="https://github.com/user-attachments/assets/d9939d56-8e6e-42bb-baa1-f56cf1419929" />
</p>
## Login e cadastro: 
possibilitam criar uma conta e acessar o aplicativo com seus dados.
<p align="center">
  <img src="https://github.com/user-attachments/assets/ab628877-af06-4d91-a1c0-6bf2eeba6fc8" width="200" alt="Login">
  <img src="https://github.com/user-attachments/assets/5e80bffb-82f1-4b97-b849-6ad52dfe1e1d" width="200" alt="Cadastro">
</p>

## Tela inicial
Apresenta os principais recursos do BEMI de forma organizada e acessível, permitindo que o usuário encontre facilmente as funcionalidades disponíveis.
<p align="center">
  <img width="200" alt="Tela inicial como visitante" src="https://github.com/user-attachments/assets/3c2ad5c9-899f-4f6d-96f4-9ab650a2508a" />
  <img width="200" alt="Tela inicial com login" src="https://github.com/user-attachments/assets/a02eee2b-5334-4e7d-b658-7faee8bb58c5" />
</p>

## Documentos:
permite consultar documentos importantes, verificar seus status e visualizar suas imagens. São vistos com login
<p align="center">
  <img src="https://github.com/user-attachments/assets/d42232d8-feb6-435a-a7eb-14887fdc9f77" width="200" alt="Documentos">
  <img src="https://github.com/user-attachments/assets/a92bcdba-b76c-4aa8-8849-9c11bbbae58d" width="200" alt="Visualização de documento">
</p>

## Consultas: 
reúne informações relacionadas aos compromissos de saúde do usuário.
<p align="center">
  <img width="200" alt="consultas" src="https://github.com/user-attachments/assets/417367a1-dd1c-4902-9fd4-58d5699ab11b" />
</p>

## Avisos: 
apresenta lembretes e informações importantes para ajudar o usuário a manter sua rotina organizada.
<p align="center">
  <img width="200" alt="avisos" src="https://github.com/user-attachments/assets/724964b0-e485-48a8-b7cb-6af8ef638102" />
</p>

## Emergência: 
disponibiliza contatos familiares para facilitar a comunicação em situações em que o usuário precisa de apoio.
<p align="center">
 <img width="200" alt="contatos" src="https://github.com/user-attachments/assets/ab4bb1fa-9fb9-44af-8670-a2817c8674a1" />
</p>


## Minha conta: 
permite visualizar as informações da conta, acessar as opções de autenticação e encerrar a sessão.
<p align="center">
  <img src="https://github.com/user-attachments/assets/ecf642bb-3160-47cc-969c-d5a530647ad5" width="200" alt="Minha conta com login">
  <img src="https://github.com/user-attachments/assets/b622614e-dc71-4d94-a74c-1253065a4ba7" width="200" alt="Minha conta sem login">
</p>

## API utilizada e armazenamento de dados

Para o BEMI, foi criada uma API REST utilizando a plataforma **JSONBin.io**, responsável por armazenar e disponibilizar os dados de documentos e consultas apresentados no aplicativo.

A integração foi desenvolvida com **Retrofit**, responsável pelas requisições à API, e **Gson**, utilizado para converter os dados recebidos em JSON para os modelos Kotlin.

Além dos dados disponibilizados pela API, o aplicativo utiliza armazenamento local por meio do **Room**, responsável por armazenar as informações dos usuários e contatos de emergência. O **SharedPreferences** é utilizado para salvar preferências, como a conclusão da introdução e o estado da sessão do usuário.

Dessa forma, o BEMI combina dados de uma API REST com persistência local, permitindo organizar as informações e manter dados importantes disponíveis no dispositivo.

## Explicação da arquitetura

O BEMI foi desenvolvido com uma organização em camadas, separando as responsabilidades para facilitar a manutenção, a reutilização de componentes e a evolução do aplicativo.

- **Data:** responsável pelo acesso e gerenciamento dos dados. Inclui a comunicação com a API REST, o banco de dados local Room e as preferências armazenadas com SharedPreferences.
- **Domain:** reúne os modelos de dados utilizados pelo aplicativo e as regras relacionadas ao funcionamento da aplicação, como a geração de avisos.
- **UI:** concentra as telas e os componentes visuais desenvolvidos com Jetpack Compose. Os ViewModels ficam responsáveis por gerenciar os estados e as informações apresentados na interface.
- **Navigation:** organiza as rotas e controla a navegação entre as telas do aplicativo.

Essa estrutura permite separar a interface das regras e do acesso aos dados, tornando o projeto mais organizado e facilitando futuras alterações e implementações.

## Desenvolvedoras
- Isabella Constance Silva Gonçalves - 559219
- Daniela Grego Pantaleão - 555808
