import "../../styles/components/buttons.css";

function ConfigBtn({ onConfig }) {
    return (
        <div className="config-btn">
            <button className="config-link" type="button" onClick={onConfig}>Configurações</button>
        </div>
    )
}

export default ConfigBtn;