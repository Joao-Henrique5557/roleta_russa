import styles from "./CardNovidade.module.css";

function CardNovidade({ titulo, descricao, autor, versao, tipo, data, ativo }) {
  return (
    <div className={styles.card}>
      <h3>{titulo}</h3>
      <p>{descricao}</p>
      <p>{autor}</p>
      <p>{versao}</p>
      <p>{tipo}</p>
      <p>{data}</p>
      <p>{ativo}</p>
    </div>
  );
}

export default CardNovidade;
