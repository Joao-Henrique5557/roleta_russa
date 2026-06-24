import styles from "./CardNovidade.module.css";

function CardNovidade({ titulo, descricao }) {
  return (
    <div className={styles.card}>
      <h3>{titulo}</h3>
      <p>{descricao}</p>
    </div>
  );
}

export default CardNovidade;
