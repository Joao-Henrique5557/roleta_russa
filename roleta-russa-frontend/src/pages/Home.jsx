import "../styles/pages/home.css";

import MenuLateral from "../components/MenuLateral";
import Ranking from "../components/show-to-public/Ranking";
import Novidades from "../components/show-to-public/Novidades";
import Footer from "../components/show-to-public/Footer";
import ScrollIndicator from "../components/ScrollIndicator";
import PropTypes from "prop-types";
import ConfigBtn from "../components/BTNs/ConfigBtn";

Home.propTypes = {
  onConfig: PropTypes.func.isRequired,
  onSingleplayer: PropTypes.func.isRequired,
  onMultiplayer: PropTypes.func.isRequired,
  onLogout: PropTypes.func.isRequired,
};

function Home({ onConfig, onSingleplayer, onMultiplayer, onLogout }) {
  return (
    <div className="home">
      <ConfigBtn onConfig={onConfig} />
      <span className="logout-btn material-symbols-outlined btnLogout" onClick={onLogout}>
        logout
      </span>
      <div className="conteudo-principal">
        <MenuLateral
          onSingleplayer={onSingleplayer}
          onMultiplayer={onMultiplayer}
        />
        <Ranking />
        <Novidades />
        <ScrollIndicator />
      </div>

      <Footer />
    </div>
  );
}

export default Home;
