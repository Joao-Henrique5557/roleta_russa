import "../styles/components/cards.css";

function Ranking() {
  const jogadores = Array(15).fill({
    nome: "pedro",
    pontos: 1200,
  });

  return (
    <div className="ranking">
      <h1>Ranking Global</h1>

      <ol>
        {jogadores.map((jogador, index) => (
          <li key={index}>
            {jogador.nome} {jogador.pontos}pt
          </li>
        ))}
      </ol>
    </div>
  );
}

export default Ranking;
