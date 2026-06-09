import "../styles/pages/game.css";

const rooms = [
  { id: "Sala Cremosa", players: "1/6", theme: "Vermelho" },
  { id: "Sala Fumaça", players: "2/6", theme: "Azul" },
  { id: "Sala Negra", players: "3/6", theme: "Preto" },
];

function MultiplayerLobby({ onBack, onConfig, onCreateRoom, onJoinRoom }) {
  return (
    <div className="page-panel">
      <div className="page-header">
        <div>
          <h1>Multiplayer</h1>
          <p>Entre em uma sala existente ou crie uma nova partida no estilo Among Us.</p>
        </div>
        <div className="page-actions">
          <button className="secondary-button" onClick={onConfig}>Configurações</button>
          <button className="primary-button" onClick={onBack}>Voltar</button>
        </div>
      </div>

      <div className="game-card">
        <div className="game-card-header">Salas disponíveis</div>
        <div className="room-list">
          {rooms.map((room) => (
            <div key={room.id} className="room-item">
              <div>
                <strong>{room.id}</strong>
                <span>{room.players} • {room.theme}</span>
              </div>
              <button className="room-button" onClick={() => onJoinRoom(room.id)}>
                Entrar
              </button>
            </div>
          ))}
        </div>
        <div className="room-footer">
          <p>Não encontrou uma sala? Crie a sua própria partida.</p>
          <button className="primary-button" onClick={onCreateRoom}>Criar nova sala</button>
        </div>
      </div>
    </div>
  );
}

export default MultiplayerLobby;
