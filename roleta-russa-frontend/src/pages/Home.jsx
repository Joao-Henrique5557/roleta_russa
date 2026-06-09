import "../styles/pages/home.css";

import MenuLateral from "../components/MenuLateral";
import Ranking from "../components/Ranking";
import Novidades from "../components/publico_informes/Novidades";
import Footer from "../components/publico_informes/Footer";
import ScrollIndicator from "../components/ScrollIndicator";

function Home({ onConfig, onSingleplayer, onMultiplayer, onLogout }) {
    return (
        <div className="home">
            <div className="">
                <button className="config-link" type="button" onClick={onConfig}>Configurações</button>
                <button className="config-link" type="button" onClick={onLogout}>Sair</button>
            </div>

            <div className="conteudo-principal">
                <MenuLateral onSingleplayer={onSingleplayer} onMultiplayer={onMultiplayer} />
                <Ranking />
                <Novidades />
                <ScrollIndicator />
            </div>

            <Footer />
        </div>
    );
}

export default Home;