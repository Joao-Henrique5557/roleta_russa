import Formulario from "../../components/Formulario";
import PropTypes from "prop-types";

AutenticationCadastro.propTypes = {
  onConfig: PropTypes.func.isRequired,
  onLogin: PropTypes.func.isRequired,
};

function AutenticationCadastro({ onConfig, onLogin }) {
  return (
    <div className="login-container border">
      <div className="form-toolbar">
        <button className="config-link" type="button" onClick={onConfig}>
          Configurações
        </button>
        <button className="config-link" type="button" onClick={onLogin}>
          Voltar ao Login
        </button>
      </div>
      <Formulario tipo="cadastro" onSwitch={onLogin} />
    </div>
  );
}

export default AutenticationCadastro;
