import { useEffect, useRef } from 'react';

function Musica({src}) {
  const audioRef = useRef(null);

  useEffect(() => {
    const audio = audioRef.current;
    
    // Tenta tocar automaticamente (pode ser bloqueado)
    const playAudio = () => {
      if (audio) {
        audio.play().catch(() => {
          // Se bloqueado, aguarda primeiro clique
          const enableAudio = () => {
            audio.play();
            document.removeEventListener('click', enableAudio);
            document.removeEventListener('touchstart', enableAudio);
          };
          document.addEventListener('click', enableAudio);
          document.addEventListener('touchstart', enableAudio);
        });
      }
    };

    // Aguarda o áudio estar pronto
    if (audio.readyState >= 2) {
      playAudio();
    } else {
      audio.addEventListener('canplay', playAudio);
    }

    return () => {
      audio.removeEventListener('canplay', playAudio);
    };
  }, []);

  return (
    <audio ref={audioRef} loop hidden>
      <source src={src} type="audio/mpeg" />
      Seu navegador não suporta áudio.
    </audio>
  );
}

export default Musica;