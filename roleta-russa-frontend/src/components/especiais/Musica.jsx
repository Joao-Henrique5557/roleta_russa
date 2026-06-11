import { useEffect, useRef } from "react";
import "../styles/components/misc.css";
import PropTypes from "prop-types";

Musica.propTypes = {
  src: PropTypes.string.isRequired,
  volume: PropTypes.number
};

function Musica({ src, volume = 0.7 }) {
  const audioRef = useRef(null);

  useEffect(() => {
    const audio = audioRef.current;
    if (!audio) return;
    audio.volume = volume;
  }, [volume]);

  useEffect(() => {
    const audio = audioRef.current;
    if (!audio) return;

    const playAudio = () => {
      audio.volume = volume;
      audio.play().catch(() => {
        const enableAudio = () => {
          audio.play();
          document.removeEventListener("click", enableAudio);
          document.removeEventListener("touchstart", enableAudio);
        };
        document.addEventListener("click", enableAudio);
        document.addEventListener("touchstart", enableAudio);
      });
    };

    audio.pause();
    audio.load();

    if (audio.readyState >= 2) {
      playAudio();
    } else {
      audio.addEventListener("canplay", playAudio);
    }

    return () => {
      audio.removeEventListener("canplay", playAudio);
    };
  }, [src, volume]);

  return (
    <audio ref={audioRef} loop hidden>
      <source src={src} type="audio/mpeg" />
      Seu navegador não suporta áudio.
    </audio>
  );
}

export default Musica;
