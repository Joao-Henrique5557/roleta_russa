import "../styles/components/input.css";

function Input({tipoInput, placeholder}) {
    return (
        <div className="input">
            <p>{placeholder} </p>
            <input type={tipoInput} />
        </div>
    )
}

export default Input;