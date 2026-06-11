import Formulario from "../../components/data/Formulario";
import ConfigBtn from "../../components/BTNs/Config-btn";
import PropTypes from "prop-types";
import "../../styles/pages/login.css";

function AutenticationLogin({ onConfig, onSignup, onHome }) {
  return (
    <div className="login-container">
      <ConfigBtn onConfig={onConfig} />
      <Formulario tipo="login" onSwitch={onSignup} onSubmit={onHome} />
    </div>
  );
}

AutenticationLogin.propTypes = {
  onConfig: PropTypes.func.isRequired,
  onSignup: PropTypes.func.isRequired,
  onHome: PropTypes.func.isRequired,
};

export default AutenticationLogin;
