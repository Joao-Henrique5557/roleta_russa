import { useEffect, useState } from "react";
import styles from "./Ranking.module.css";
import axios from "axios";

function Ranking({ urlAPI }) {
  const [jogadores, setJogadores] = useState([]);
  const [loading, setLoanding] = useState(false);
  const [timeoutError, setTimeoutError] = useState(false);
  useEffect(() => {
    const listarUsuarios = async () => {
      try {
        setLoanding(true);
        // CORREÇÃO 2: try/catch movido para dentro da função assíncrona
        const response = await axios.get(`${urlAPI}/ListarUsuarios`, {
          timeout: 5000,
        });
        setJogadores(response.data);
      } catch (error) {
        if (error.code === "ECONNABORTED") {
          setTimeoutError(true);
        } else {
          alert("Erro ao buscar dados do ranking");
          console.error("Error ao listar usuários: ", error);
        }
      } finally {
        setLoanding(false);
      }
    };

    if (urlAPI) {
      listarUsuarios();
    }
  }, [urlAPI]);

  return (
    <div className={styles.ranking}>
      <h1>Ranking Global</h1>
      {loading && <p>Carregando</p>}
      {timeoutError && <p>Carregamento lento, tente novamente.</p>}
      {jogadores.length == 0 ? (
        <p>Nenhum jogador encontrado.</p>
      ) : (
        <ol className={styles.list}>
          {jogadores.map((jogador, index) => (
            <li key={jogador.id || index} className={styles.item}>
              <span className={styles.posicao}>{index + 1}°</span>
              <span className={styles.nome}>{jogador.nome}</span>
              <span className={styles.pontos}>{jogador.pontos} pt</span>
            </li>
          ))}
        </ol>
      )}
    </div>
  );
}

export default Ranking;
