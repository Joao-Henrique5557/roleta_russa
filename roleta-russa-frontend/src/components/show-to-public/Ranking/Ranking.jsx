import { useEffect } from "react";
import styles from "./Ranking.module.css";
import jogadores from "../../../constants/ranking_teste";

function Ranking() {
  useEffect(() => {}, []);
  return (
    <div className={styles.ranking}>
      <h1>Ranking Global</h1>
      <ol className={styles.list}>
        {jogadores.map((jogador, index) => (
          <li key={index} className={styles.item}>
            {jogador.nome} {jogador.pontos}pt
          </li>
        ))}
      </ol>
    </div>
  );
}

export default Ranking;
