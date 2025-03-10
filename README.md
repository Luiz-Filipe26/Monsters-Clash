
# MONSTER CLASH

Monster Clash é um jogo mobile de cartas baseado em turnos, onde dois jogadores recebem um conjunto inicial de cartas e, a cada turno, novas cartas são adicionadas à mão. As cartas podem ser do tipo monstro ou equipamento, cada uma com atributos como custo de mana, ataque e defesa. O objetivo do jogo é reduzir os pontos de vida do oponente a zero antes que ele faça o mesmo com você.

## Instruções para executar o aplicativo

Para compilar e executar a aplicação, é necessário abri-la no Android Studio.
## Objetivo

Esta aplicação foi desenvolvida como trabalho final da disciplina de Programação Mobile, aplicando todos os conceitos abordados ao longo do semestre.

## Funcionalidades Implementadas

- Ao iniciar a aplicação, as cartas são geradas com seus respectivos atributos, como nome, descrição, ataque, defesa e custo. Em seguida, elas são transformadas em imagens e armazenadas no banco de dados local SQLite, juntamente com os seus atributos, para serem utilizadas posteriormente na exibição. Durante esse processo, uma tela de loading é exibida até que o carregamento do banco seja concluído.

- No início da partida, cada jogador recebe 7 cartas, que podem ser do tipo monstro ou equipamento.

- Foi implementada uma mão de cartas interativa, utilizando Live Data, que permite que o jogador selecione cartas deslizando o dedo. Ao fazer isso, a carta é destacada e seus detalhes são exibidos.

- O jogador pode arrastar uma carta para o tabuleiro, removendo-a da mão e posicionando-a no campo de batalha.

- Antes de posicionar a carta no tabuleiro, é possível escolher o modo de posicionamento: ataque ou defesa.
## Futuras Implementacoes

- Após o carregamento, o jogador entrará automaticamente em uma sala junto com o oponente.

- Quando o jogador finalizar sua jogada, o turno será passado para o oponente por meio de notificações no Firebase. Essa notificação conterá o ID do jogador e os detalhes da carta posicionada.
## Autores

- [Luiz Felipe](https://github.com/Luiz-Filipe26)
- [Lucas da Costa Miranda](https://github.com/lucascostamr)

