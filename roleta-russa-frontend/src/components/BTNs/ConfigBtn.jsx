import "../../styles/components/buttons.css";
import PropTypes from "prop-types";
function ConfigBtn({ onConfig }) {
  return (
    <div className="config-btn">
      <span className="material-symbols-outlined" onClick={onConfig}>
        settings
      </span>
    </div>
  );
}

ConfigBtn.propTypes = {
  onConfig: PropTypes.func.isRequired,
};

export default ConfigBtn;
