import "../../styles/components/buttons.css";
import PropTypes from "prop-types";

function ConfigBtn({ onConfig }) {
  return (
    <div className="config-btn">
      <button className="config-link" type="button" onClick={onConfig}>
        Configurações
      </button>
    </div>
  );
}

ConfigBtn.propTypes = {
  onConfig: PropTypes.func.isRequired,
};

export default ConfigBtn;
