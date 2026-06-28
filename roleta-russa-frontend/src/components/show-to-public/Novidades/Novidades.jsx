import styles from "./Novidades.module.css";
import CardNovidade from "../CardNovidade/CardNovidade";
// import novidades from "../../../constants/novidades_exemplo";
import { useEffect, useState } from "react";
import axios from "axios";

function Novidades({ urlAPI }) {
  const [novidades, setNovidades] = useState([]);
  const [loading, setLoanding] = useState(false);

  useEffect(() => {
    const listarNovidades = async () => {
      try {
        setLoanding(true);
        const response = await axios.get(`${urlAPI}/ListarNovidades`);
        setNovidades(response.data);
      } catch (error) {
        alert("erro ao buscar novidades");
        console.error("erro: " + error);
      } finally{
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
      {loading&& <p>Carregando...</p>}
      {novidades.map((novidade, index) => (
        <CardNovidade key={index} {...novidade} />
      ))}
    </div>
  );
}

export default Novidades;
