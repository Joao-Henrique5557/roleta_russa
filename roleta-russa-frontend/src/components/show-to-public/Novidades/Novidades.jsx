import styles from "./Novidades.module.css";
import CardNovidade from "../CardNovidade/CardNovidade";
import novidades from "../../../constants/novidades_exemplo";
import { useEffect } from "react";

function Novidades() {
  useEffect(() => {}, []);
  return (
    <div className={styles.novidades}>
      <h1>Novidades</h1>
      {novidades.map((novidade, index) => (
        <CardNovidade key={index} {...novidade} />
      ))}
    </div>
  );
}

export default Novidades;
