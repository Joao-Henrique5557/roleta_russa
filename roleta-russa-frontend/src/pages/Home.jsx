import "../styles/pages/home.css";

import MenuLateral from "../components/MenuLateral";
import Ranking from "../components/Ranking";
import Novidades from "../components/publico_informes/Novidades";
import Footer from "../components/publico_informes/Footer";
import ScrollIndicator from "../components/ScrollIndicator";
import Musica from "../components/Musica";

function Home() {
    return (
        <div className="home">
            <div className="conteudo-principal">
                <MenuLateral />
                <Ranking />
                <Novidades />
                <ScrollIndicator />
            </div>

            <Footer />
            <Musica src="/audio/L'amour Toujours - Gigi D'Agostino (Piano Cover).mp3" />
        </div>
    );
}

export default Home;