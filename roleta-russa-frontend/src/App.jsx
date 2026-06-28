import { useState } from "react";

import Home from "./pages/Home/Home";
import AutenticationLogin from "./pages/authentication/Login";
import AutenticationCadastro from "./pages/authentication/Cadastro";
import SingleplayerGame from "./pages/game/SingleplayerGame";
import MultiplayerLobby from "./pages/game/MultiplayerLobby";
import ConfigPage from "./pages/ConfigPage/ConfigPage";
import Musica from "./components/especiais/Musica/Musica";
import musicTracks from "./constants/musicTracks";

function App() {
  const [view, setView] = useState("login");
  const [returnView, setReturnView] = useState("login");
  const [trackId, setTrackId] = useState(4);
  const [volume, setVolume] = useState(0.5);
  const [urlAPI] = useState("https://roleta-russa-api.onrender.com");

  const currentTrack =
    musicTracks.find((t) => t.id === trackId) ?? musicTracks[0];

  const openConfig = (nextView) => {
    setReturnView(nextView ?? view);
    setView("config");
  };

  const navigateTo = (target) => {
    setView(target);
    if (target !== "config") setReturnView(target);
  };

  return (
    <div className="app-shell">
      <Musica src={currentTrack.src} volume={volume} />
      {view === "login" && (
        <AutenticationLogin
          onConfig={() => openConfig("login")}
          onSignup={() => navigateTo("signup")}
          onHome={() => navigateTo("home")}
          urlAPI={urlAPI}
        />
      )}
      {view === "signup" && (
        <AutenticationCadastro
          onConfig={() => openConfig("signup")}
          onLogin={() => navigateTo("login")}
          urlAPI={urlAPI}
        />
      )}
      {view === "home" && (
        <Home
          onConfig={() => openConfig("home")}
          onSingleplayer={() => navigateTo("singleplayer")}
          onMultiplayer={() => navigateTo("multiplayer")}
          onLogout={() => navigateTo("login")}
          urlAPI={urlAPI}
        />
      )}
      {view === "singleplayer" && (
        <SingleplayerGame
          onBack={() => navigateTo("home")}
          onConfig={() => openConfig("singleplayer")}
        />
      )}
      {view === "multiplayer" && (
        <MultiplayerLobby
          onBack={() => navigateTo("home")}
          onConfig={() => openConfig("multiplayer")}
          onCreateRoom={(room) =>
            alert(`Sala "${room}" criada! (back-end pendente)`)
          }
          onJoinRoom={(room) =>
            alert(`Entrando na sala ${room}... (back-end pendente)`)
          }
        />
      )}
      {view === "config" && (
        <ConfigPage
          volume={volume}
          currentTrack={currentTrack.id}
          tracks={musicTracks}
          onVolumeChange={setVolume}
          onTrackChange={setTrackId}
          onBack={() => navigateTo(returnView)}
        />
      )}
    </div>
  );
}

export default App;
