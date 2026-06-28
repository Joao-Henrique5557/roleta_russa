import { useEffect, useState } from "react";
import styles from "./Ranking.module.css";
import axios from "axios";

function Ranking({ urlAPI }) {
  // CORREÇÃO 1: Inicializa como array vazio para evitar quebras no primeiro render
  const [jogadores, setJogadores] = useState([]);

  useEffect(() => {
    const listarUsuarios = async () => {
      try {
        // CORREÇÃO 2: try/catch movido para dentro da função assíncrona
        const response = await axios.get(`${urlAPI}/ListarUsuarios`);
        setJogadores(response.data);
      } catch (error) {
        alert("Erro ao buscar dados do ranking");
        console.error("Error ao listar usuários: ", error);
      }
    };

    if (urlAPI) {
      listarUsuarios();
    }
  }, [urlAPI]);

  return (
    <div className={styles.ranking}>
      <h1>Ranking Global</h1>
      <ol className={styles.list}>
        {jogadores.map((jogador, index) => (
          <li key={jogador.id || index} className={styles.item}>
            <span className={styles.posicao}>{index + 1}°</span>
            <span className={styles.nome}>{jogador.nome}</span>
            <span className={styles.pontos}>{jogador.pontos} pt</span>
          </li>
        ))}
      </ol>
    </div>
  );
}

export default Ranking;
