import "../../styles/components/cards.css";
import jogadores from "../../constants/ranking_teste";
function Ranking() {
  

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
