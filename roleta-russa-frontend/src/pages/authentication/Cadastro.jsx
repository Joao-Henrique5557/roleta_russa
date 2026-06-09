import Formulario from "../../components/Formulario";

function AutenticationCadastro({ onConfig, onLogin }) {
    return (
        <div className="login-container border">
            <div className="form-toolbar">
                <button className="config-link" type="button" onClick={onConfig}>Configurações</button>
                <button className="config-link" type="button" onClick={onLogin}>Voltar ao Login</button>
            </div>
            <Formulario tipo="cadastro" onSwitch={onLogin} />
        </div>
    )
}

export default AutenticationCadastro;