import "../../styles/components/form.css";
import PropTypes from "prop-types";

function Input({ tipoInput, placeholder }) {
  return (
    <div className="input">
      <p>{placeholder} </p>
      <input type={tipoInput} />
    </div>
  );
}

Input.propTypes = {
  tipoInput: PropTypes.string.isRequired,
  placeholder: PropTypes.string.isRequired,
};

export default Input;
