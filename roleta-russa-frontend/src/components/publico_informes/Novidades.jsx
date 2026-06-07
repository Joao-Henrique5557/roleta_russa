import CardNovidade from "./CardNovidade";
import "../../styles/components/novidades.css";

function Novidades() {
    return (
        <div className="novidades">

            <h1>Novidades:</h1>

            <CardNovidade />
            <CardNovidade />
            <CardNovidade />

        </div>
    );
}

export default Novidades;