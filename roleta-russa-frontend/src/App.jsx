import { useState } from "react";

import Home from "./pages/Home";
import AutenticationLogin from "./pages/authentication/Login";
import AutenticationCadastro from "./pages/authentication/Cadastro";
import SingleplayerGame from "./pages/game/SingleplayerGame";
import MultiplayerLobby from "./pages/game/MultiplayerLobby";
import ConfigPage from "./pages/ConfigPage";
import Musica from "./components/especiais/Musica";
import { musicTracks } from "./constants/musicTracks";

import "./styles/global/index.css";

function App() {
  const [view, setView] = useState("login"); // view = "login"
  const [returnView, setReturnView] = useState("login"); // returnView é a tela para onde o usuário volta depois de sair da configuração. Inicialmente, é "login"
  const [trackId, setTrackId] = useState(1); // musica inicial = gigi
  const [volume, setVolume] = useState(0.5); // volume = 0.5

  const currentTrack =
    musicTracks.find((track) => track.id === trackId) ?? musicTracks[0];
  // para todos os tracks de musicTracks, encontre o track que tem o id igual ao trackId. Se não encontrar, use musicTracks[0] (primeira música da lista)

  const openConfig = (nextView) => {
    setReturnView(nextView ?? view);
    setView("config");
  }; // abrir a tela de configuração, guardando a tela atual para voltar depois

  const navigateTo = (target) => {
    setView(target); // mudar a tela para a tela de destino
    if (target !== "config") {
      setReturnView(target);
    } // se a tela de destino não for a configuração, atualize o returnView para a tela de destino
  };

  return (
    <div className="app-shell">
      <Musica src={currentTrack.src} volume={volume} />{" "}
      {/* componente de música, recebe a src da música atual e o volume */}
      {/* se view == login? */}
      {view === "login" && (
        <AutenticationLogin
          onConfig={() => openConfig("login")}
          onSignup={() => navigateTo("signup")} // para ir para a tela de cadastro
          onHome={() => navigateTo("home")}
        />
      )}
      {/* se view == cadastro? */}
      {view === "signup" && (
        <AutenticationCadastro
          onConfig={() => openConfig("signup")}
          onLogin={() => navigateTo("login")}
        />
      )}
      {/* se view == home? */}
      {view === "home" && (
        <Home
          onConfig={() => openConfig("home")}
          onSingleplayer={() => navigateTo("singleplayer")}
          onMultiplayer={() => navigateTo("multiplayer")}
          onLogout={() => navigateTo("login")}
        />
      )}
      {/* se view == singleplayer? */}
      {view === "singleplayer" && (
        <SingleplayerGame
          onBack={() => navigateTo("home")}
          onConfig={() => openConfig("singleplayer")}
        />
      )}
      {/* se view == multiplayer? */}
      {view === "multiplayer" && (
        <MultiplayerLobby
          onBack={() => navigateTo("home")}
          onConfig={() => openConfig("multiplayer")}
          onCreateRoom={() =>
            alert("Sala criada!\nAgora ela aparece na lista.")
          }
          onJoinRoom={(room) => alert(`Entrando na sala ${room}`)}
        />
      )}
      {/* se view == config? */}
      {view === "config" && (
        <ConfigPage
          volume={volume}
          currentTrack={currentTrack.id}
          tracks={musicTracks}
          onVolumeChange={(value) => setVolume(value)}
          onTrackChange={(track) => setTrackId(track)}
          onBack={() => navigateTo(returnView)}
        />
      )}
    </div>
  );
}

export default App;
