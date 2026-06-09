import Formulario from "../../components/Formulario";
import ConfigBtn from "../../components/BTNs/Config-btn";

import "../../styles/pages/login.css";

function AutenticationLogin({ onConfig, onSignup, onHome }) {
    return (
        <div className="login-container">
            <ConfigBtn onConfig={onConfig} />
            <Formulario tipo="login" onSwitch={onSignup} onSubmit={onHome} />
        </div>
    )
}

export default AutenticationLogin;