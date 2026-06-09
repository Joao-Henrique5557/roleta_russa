import BotaoMenu from "./BTNs/BotaoMenu";
import "../styles/components/navigation.css";

function MenuLateral({ onSingleplayer, onMultiplayer }) {
    return (
        <div className="menu-lateral">
            <BotaoMenu texto="Jogar contra boot" onClick={onSingleplayer} />
            <BotaoMenu texto="Multiplayer: entrar em sala" onClick={onMultiplayer} />
        </div>
    );
}

export default MenuLateral;