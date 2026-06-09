import "../../styles/components/buttons.css";
import PropTypes from "prop-types";

function BtnAzul({ placeholder, type = "button", onClick }) {
  return (
    <button className="btnAzul" type={type} onClick={onClick}>
      {placeholder}
    </button>
  );
}

BtnAzul.propTypes = {
  placeholder: PropTypes.string.isRequired,
  type: PropTypes.string,
  onClick: PropTypes.func.isRequired,
};

export default BtnAzul;
