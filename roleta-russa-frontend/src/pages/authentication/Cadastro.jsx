import Formulario from "../../components/data/Formulario";
import ConfigBtn from "../../components/BTNs/ConfigBtn";
import PropTypes from "prop-types";
import "../../styles/pages/login.css";

function AutenticationCadastro({ onConfig, onLogin }) {
  return (
    <div className="login-container">
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
