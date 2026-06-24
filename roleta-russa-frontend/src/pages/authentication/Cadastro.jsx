import Formulario from "../../components/data/Formulario/Formulario";
import ConfigBtn from "../../components/BTNs/ConfigBtn/ConfigBtn";
import PropTypes from "prop-types";
import styles from "./login.module.css";

function AutenticationCadastro({ onConfig, onLogin }) {
  return (
    <div className={styles.loginContainer}>
      <ConfigBtn onConfig={onConfig} />
      <Formulario tipo="cadastro" onSwitch={onLogin} onSubmit={onLogin} />
    </div>
  );
}

AutenticationCadastro.propTypes = {
  onConfig: PropTypes.func.isRequired,
  onLogin: PropTypes.func.isRequired,
};
export default AutenticationCadastro;
