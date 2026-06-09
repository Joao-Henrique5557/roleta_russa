import BotaoMenu from "./BTNs/BotaoMenu";
import "../styles/components/navigation.css";
import PropTypes from "prop-types";

function MenuLateral({ onSingleplayer, onMultiplayer }) {
  return (
    <div className="menu-lateral">
      <BotaoMenu texto="Jogar contra boot" onClick={onSingleplayer} />
      <BotaoMenu texto="Multiplayer: entrar em sala" onClick={onMultiplayer} />
    </div>
  );
}

MenuLateral.propTypes = {
  onSingleplayer: PropTypes.func.isRequired,
  onMultiplayer: PropTypes.func.isRequired,
};

export default MenuLateral;
