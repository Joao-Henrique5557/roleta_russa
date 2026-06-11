import "../styles/pages/config.css";
import PropTypes from "prop-types";

function ConfigPage({
  volume,
  currentTrack,
  tracks,
  onVolumeChange,
  onTrackChange,
  onBack,
}) {
  return (
    <div className="page-panel">
      <div className="page-header">
        <div>
          <h1>Configurações</h1>
          <p>Altere volume e música de fundo para a experiência do jogo.</p>
        </div>
        <button className="primary-button" onClick={onBack}>
          Voltar
        </button>
      </div>

      <div className="config-card">
        <div className="config-section">
          <h2>Volume da música</h2>
          <input
            type="range"
            min="0"
            max="1"
            step="0.01"
            value={volume}
            onChange={(event) => onVolumeChange(Number(event.target.value))}
          />
          <span>{Math.round(volume * 100)}%</span>
        </div>

        <div className="config-section">
          <h2>Trocar música</h2>
          <div className="track-list">
            {tracks.map((track) => (
              <button
                key={track.id}
                type="button"
                className={`track-button ${track.id === currentTrack ? "active" : ""}`}
                onClick={() => onTrackChange(track.id)}
              >
                {track.label}
              </button>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
}

ConfigPage.propTypes = {
  volume: PropTypes.number.isRequired,
  currentTrack: PropTypes.string.isRequired,
  tracks: PropTypes.arrayOf(
    PropTypes.shape({
      id: PropTypes.number.isRequired,
      label: PropTypes.string.isRequired,
    }),
  ).isRequired,
  onVolumeChange: PropTypes.func.isRequired,
  onTrackChange: PropTypes.func.isRequired,
  onBack: PropTypes.func.isRequired,
};
export default ConfigPage;
