import "../styles/pages/game.css";

function SingleplayerGame({ onBack, onConfig }) {
  return (
    <div className="page-panel">
      <div className="page-header">
        <div>
          <h1>Singleplayer</h1>
          <p>Jogue contra o boot em um duelo rápido.</p>
        </div>
        <div className="page-actions">
          <button className="secondary-button" onClick={onConfig}>Configurações</button>
          <button className="primary-button" onClick={onBack}>Voltar</button>
        </div>
      </div>

      <div className="game-card">
        <div className="game-card-header">Jogar contra bot</div>
        <div className="game-card-body">
          <p>Prepare sua arma e dispare contra o boot adversário. Esta sala simula um cenário de combate rápido.</p>
          <div className="game-metrics">
            <div>
              <span>Vida</span>
              <strong>100%</strong>
            </div>
            <div>
              <span>Arma</span>
              <strong>Revólver 44</strong>
            </div>
            <div>
              <span>Missões</span>
              <strong>1</strong>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default SingleplayerGame;
