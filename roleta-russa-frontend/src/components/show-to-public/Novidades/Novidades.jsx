import styles from "./Novidades.module.css";
import CardNovidade from "../CardNovidade/CardNovidade";
// import novidades from "../../../constants/novidades_exemplo";
import { useEffect, useState } from "react";
import axios from "axios";

function Novidades({ urlAPI }) {
  const [novidades, setNovidades] = useState([]);
  const [loading, setLoanding] = useState(false);
  const [timeoutError, setTimeoutError] = useState(false);

  useEffect(() => {
    const listarNovidades = async () => {
      try {
        setLoanding(true);
        const response = await axios.get(`${urlAPI}/ListarNovidades`, {timeout: 5000});
        setNovidades(response.data);
      } catch (error) {
        if(error.code === "ECONNABORTED"){
          setTimeoutError(true);
        } else{
          alert("erro ao buscar novidades");
        console.error("erro: " + error);
        }
      } finally {
        setLoanding(false);
      }
    };
    if (urlAPI) {
      listarNovidades();
    }
  }, [urlAPI]);

  return (
    <div className={styles.novidades}>
      <h1>Novidades</h1>
      {loading && <p>Carregando...</p>}
      {timeoutError&& <p>Carregamento lento, tente novamente.</p>}
      {novidades.length == 0 ? (
        <p>Nenhuma novidade encontrada.</p>
      ) : (
        novidades.map((novidade, index) => (
          <CardNovidade key={index} {...novidade} />
        ))
      )}
    </div>
  );
}

export default Novidades;
