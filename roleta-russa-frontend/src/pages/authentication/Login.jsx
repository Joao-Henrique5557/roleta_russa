import Formulario from "../../components/data/Formulario/Formulario";
import ConfigBtn from "../../components/BTNs/ConfigBtn/ConfigBtn";
import PropTypes from "prop-types";
import styles from "./login.module.css";

function AutenticationLogin({ onConfig, onSignup, onHome, urlAPI}) {
  return (
    <div className={styles.loginContainer}>
      <ConfigBtn onConfig={onConfig} />
      <Formulario tipo="login" onSwitch={onSignup} onSubmit={onHome} urlAPI={urlAPI}/>
    </div>
  );
}

AutenticationLogin.propTypes = {
  onConfig: PropTypes.func.isRequired,
  onSignup: PropTypes.func.isRequired,
  onHome: PropTypes.func.isRequired,
};
export default AutenticationLogin;
