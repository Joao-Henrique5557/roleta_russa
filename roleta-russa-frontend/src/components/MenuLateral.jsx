import BotaoMenu from "./BTNs/BotaoMenu";
import "../styles/components/menuLateral.css";

function MenuLateral() {
    return (
        <div className="menu-lateral">

            <BotaoMenu texto="Criar servidor local na minha máquina" />
            <BotaoMenu texto="Jogar contra IA (Em breve)" />
            <BotaoMenu texto="Jogar contra jogador presencial" />
            <BotaoMenu texto="Gerenciar conta" />

        </div>
    );
}

export default MenuLateral;