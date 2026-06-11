import { useState } from "react";

import Home from "./pages/Home";
import AutenticationLogin from "./pages/authentication/Login";
import AutenticationCadastro from "./pages/authentication/Cadastro";
import SingleplayerGame from "./pages/game/SingleplayerGame";
import MultiplayerLobby from "./pages/game/MultiplayerLobby";
import ConfigPage from "./pages/ConfigPage";
import Musica from "./components/especiais/Musica";

import "./styles/global/index.css";

// Lista de músicas disponíveis
const musicTracks = [
  {
    id: 1,
    label: "L'amour Toujours",
    src: "/audio/L'amour Toujours - Gigi D'Agostino (Piano Cover).mp3",
  },
  {
    id: 2,
    label: "FNAF cover",
    src: "/audio/fnafcover.mp3",
  },
];

function App() {
  const [view, setView] = useState("login"); // view = "login"
  const [returnView, setReturnView] = useState("login");
  const [trackId, setTrackId] = useState(1); // musica inicial = gigi
  const [volume, setVolume] = useState(0.5); // volume = 0.5

  //   Array.find()
  //   Ele retorna o primeiro elemento que satisfaz a condição.

  const currentTrack =
    musicTracks.find((track) => track.id === trackId) ?? musicTracks[0];
  // para todos os tracks de musicTracks, encontre o track que tem o id igual ao trackId. Se não encontrar, use musicTracks[0] (primeira música da lista)

  const openConfig = (nextView) => {
    setReturnView(nextView ?? view);
    setView("config");
  };

  const navigateTo = (target) => {
    setView(target);
    if (target !== "config") {
      setReturnView(target);
    }
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
