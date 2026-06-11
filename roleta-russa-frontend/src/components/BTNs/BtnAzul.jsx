import "../../styles/components/buttons.css";

function BtnAzul({ placeholder, type = "button", onClick }) {
  return (
    <button className="btnAzul" type={type} onClick={onClick}>
      {placeholder}
    </button>
  );
}

export default BtnAzul;
