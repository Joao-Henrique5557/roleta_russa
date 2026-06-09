function BotaoMenu({ texto, onClick }) {
    return (
        <button className="botao-menu" type="button" onClick={onClick}>
            {texto}
        </button>
    );
}

export default BotaoMenu;