import { useState, useCallback, useEffect } from "react";
import styles from "./game.module.css";

// ---- Lógica do revólver (espelha Revolve.java + Rodada.java) ----
function gerarBalas(dificuldade) {
  let quantBalas = Math.floor(Math.random() * 5) + 2; // 2–6
  let quantVerdadeiras;

  if (dificuldade === "facil") {
    quantVerdadeiras = 1;
    if (quantBalas >= 3) quantBalas = 3;
  } else if (dificuldade === "medio") {
    quantVerdadeiras = 2;
    if (quantBalas <= 4 && quantBalas < 6) quantBalas += 1;
  } else {
    quantVerdadeiras = 3;
    if (quantBalas <= 3 || quantBalas < 6) quantBalas += 1;
  }

  const balas = new Array(quantBalas).fill(false);
  let colocadas = 0;
  while (colocadas < quantVerdadeiras) {
    const pos = Math.floor(Math.random() * quantBalas);
    if (!balas[pos]) {
      balas[pos] = true;
      colocadas++;
    }
  }
  return { balas, quantVerdadeiras };
}

function estadoInicial(dificuldade) {
  const { balas, quantVerdadeiras } = gerarBalas(dificuldade);
  return {
    fase: "jogando", // "jogando" | "resultado"
    dificuldade,
    rodada: 1,
    vezDe: "jogador", // "jogador" | "bot"
    jogador: { vidas: 2, alive: true },
    bot: { vidas: 2, alive: true },
    balas,
    posAtual: 0,
    quantVerdadeiras,
    log: [
      `🔫 Rodada 1 iniciada — ${balas.length} câmaras, ${quantVerdadeiras} bala(s) real(is).`,
    ],
    esperandoBot: false,
  };
}

function recarregar(estado) {
  const { balas, quantVerdadeiras } = gerarBalas(estado.dificuldade);
  return {
    ...estado,
    balas,
    posAtual: 0,
    quantVerdadeiras,
    rodada: estado.rodada + 1,
    log: [
      ...estado.log,
      `🔁 Revólver recarregado — Rodada ${estado.rodada + 1} | ${balas.length} câmaras, ${quantVerdadeiras} bala(s) real(is).`,
    ],
  };
}

// Retorna o novo estado após um disparo
function atirar(estado, alvo) {
  const { balas, posAtual } = estado;
  const isVerdadeira = balas[posAtual];
  const novoPos = posAtual + 1;

  let novoJogador = { ...estado.jogador };
  let novoBot = { ...estado.bot };
  let logEntry;
  let mudaVez = false;

  if (alvo === "self") {
    const quemAtira = estado.vezDe === "jogador" ? "Você" : "Bot";
    if (isVerdadeira) {
      logEntry = `💥 ${quemAtira} atirou em si mesmo — bala REAL! -1 vida.`;
      if (estado.vezDe === "jogador") novoJogador.vidas -= 1;
      else novoBot.vidas -= 1;
      // quem acertou em si mesmo mantém a vez (regra do jogo original)
    } else {
      logEntry = `💨 ${quemAtira} atirou em si mesmo — bala falsa. Mantém a vez.`;
    }
  } else {
    const quemAtira = estado.vezDe === "jogador" ? "Você" : "Bot";
    const alvoNome = estado.vezDe === "jogador" ? "Bot" : "Você";
    if (isVerdadeira) {
      logEntry = `💥 ${quemAtira} atirou no ${alvoNome} — bala REAL! -1 vida.`;
      if (estado.vezDe === "jogador") novoBot.vidas -= 1;
      else novoJogador.vidas -= 1;
    } else {
      logEntry = `💨 ${quemAtira} atirou no ${alvoNome} — bala falsa.`;
    }
    mudaVez = true;
  }

  // Checar morte
  if (novoJogador.vidas <= 0) novoJogador.alive = false;
  if (novoBot.vidas <= 0) novoBot.alive = false;

  let novoEstado = {
    ...estado,
    jogador: novoJogador,
    bot: novoBot,
    posAtual: novoPos,
    log: [...estado.log, logEntry],
    vezDe: mudaVez
      ? estado.vezDe === "jogador"
        ? "bot"
        : "jogador"
      : estado.vezDe,
  };

  // Alguém morreu?
  if (!novoJogador.alive || !novoBot.alive) {
    return { ...novoEstado, fase: "resultado" };
  }

  // Revólver vazio?
  if (novoPos >= balas.length) {
    novoEstado = recarregar(novoEstado);
  }

  return novoEstado;
}

// ---- Componente ----
export default function SingleplayerGame({ onBack, onConfig }) {
  const [estado, setEstado] = useState(null);

  const iniciar = useCallback((dificuldade) => {
    setEstado(estadoInicial(dificuldade));
  }, []);

  const agir = useCallback((alvo) => {
    setEstado((prev) => {
      if (!prev || prev.fase !== "jogando" || prev.vezDe !== "jogador")
        return prev;
      let novo = atirar(prev, alvo);

      // Se após jogador é vez do bot e ainda está jogando, agendar bot
      if (novo.fase === "jogando" && novo.vezDe === "bot") {
        novo = { ...novo, esperandoBot: true };
      }
      return novo;
    });
  }, []);

  // Bot age com delay
  useEffect(() => {
    if (!estado || !estado.esperandoBot || estado.fase !== "jogando") return;

    const timer = setTimeout(() => {
      setEstado((prev) => {
        if (!prev || !prev.esperandoBot || prev.fase !== "jogando") {
          return prev;
        }

        const restantes = prev.balas.length - prev.posAtual;

        const verdadeirasRestantes = prev.balas
          .slice(prev.posAtual)
          .filter(Boolean).length;

        const probVerdadeira =
          restantes > 0 ? verdadeirasRestantes / restantes : 0;

        const botAlvo = probVerdadeira < 0.4 ? "self" : "opponent";

        const novo = atirar(prev, botAlvo);

        return {
          ...novo,
          esperandoBot: false,
        };
      });
    }, 1200);

    return () => clearTimeout(timer);
  }, [estado]);

  // ---- SETUP ----
  if (!estado) {
    return (
      <div className={styles.pagePanel}>
        <div className={styles.pageHeader}>
          <div>
            <h1>Singleplayer</h1>
            <p>Enfrente o bot em uma partida de Roleta Russa.</p>
          </div>
          <div className={styles.pageActions}>
            <button className={styles.secondaryButton} onClick={onConfig}>
              Configurações
            </button>
            <button className={styles.primaryButton} onClick={onBack}>
              Voltar
            </button>
          </div>
        </div>
        <div className={styles.gameArea}>
          <div className={styles.setupArea}>
            <p className={styles.setupTitle}>Escolha a dificuldade</p>
            <div className={styles.difficultyCards}>
              <button
                className={`${styles.diffCard} ${styles.diffFacil}`}
                onClick={() => iniciar("facil")}
              >
                <span className={styles.diffLabel}>Fácil</span>
                <span className={styles.diffDesc}>
                  1 bala real, até 3 câmaras
                </span>
              </button>
              <button
                className={`${styles.diffCard} ${styles.diffMedio}`}
                onClick={() => iniciar("medio")}
              >
                <span className={styles.diffLabel}>Médio</span>
                <span className={styles.diffDesc}>
                  2 balas reais, 5+ câmaras
                </span>
              </button>
              <button
                className={`${styles.diffCard} ${styles.diffDificil}`}
                onClick={() => iniciar("dificil")}
              >
                <span className={styles.diffLabel}>Difícil</span>
                <span className={styles.diffDesc}>
                  3 balas reais, 6+ câmaras
                </span>
              </button>
            </div>
          </div>
        </div>
      </div>
    );
  }

  const {
    balas,
    posAtual,
    jogador,
    bot,
    vezDe,
    log,
    rodada,
    fase,
    esperandoBot,
  } = estado;
  const podeAgir = fase === "jogando" && vezDe === "jogador" && !esperandoBot;
  const venceu = !bot.alive;

  // ---- RESULTADO ----
  if (fase === "resultado") {
    return (
      <div className={styles.pagePanel}>
        <div className={styles.resultOverlay}>
          <div className={styles.resultCard}>
            <p
              className={`${styles.resultTitle} ${venceu ? styles.resultWin : styles.resultLose}`}
            >
              {venceu ? "🏆 Você Venceu!" : "💀 Você Perdeu!"}
            </p>
            <div className={styles.resultStats}>
              <div className={styles.resultStat}>
                <span>Sua vida</span>
                <strong>{jogador.vidas}</strong>
              </div>
              <div className={styles.resultStat}>
                <span>Rodadas</span>
                <strong>{rodada}</strong>
              </div>
              <div className={styles.resultStat}>
                <span>Bot</span>
                <strong>{bot.vidas}</strong>
              </div>
            </div>
            <div style={{ display: "flex", gap: 12 }}>
              <button
                className={styles.primaryButton}
                onClick={() => setEstado(null)}
              >
                Jogar de novo
              </button>
              <button className={styles.secondaryButton} onClick={onBack}>
                Menu
              </button>
            </div>
          </div>
        </div>
      </div>
    );
  }

  // ---- JOGO ----
  return (
    <div className={styles.pagePanel}>
      <div className={styles.pageHeader}>
        <div>
          <h1>Singleplayer</h1>
          <p>
            Rodada {rodada} — Dificuldade: {estado.dificuldade}
          </p>
        </div>
        <div className={styles.pageActions}>
          <button className={styles.secondaryButton} onClick={onConfig}>
            Configurações
          </button>
          <button className={styles.secondaryButton} onClick={onBack}>
            Sair
          </button>
        </div>
      </div>

      <div className={styles.gameArea}>
        {/* Status dos jogadores */}
        <div className={styles.statusBar}>
          <div
            className={`${styles.statusCard} ${jogador.vidas <= 1 ? styles.statusCardDanger : ""}`}
          >
            <span>Você</span>
            <strong>
              {"❤️".repeat(jogador.vidas)}
              {"🖤".repeat(Math.max(0, 2 - jogador.vidas))}
            </strong>
          </div>
          <div className={styles.statusCard}>
            <span>Câmara</span>
            <strong>
              {posAtual + 1} / {balas.length}
            </strong>
          </div>
          <div
            className={`${styles.statusCard} ${bot.vidas <= 1 ? styles.statusCardSuccess : ""}`}
          >
            <span>Bot</span>
            <strong>
              {"❤️".repeat(bot.vidas)}
              {"🖤".repeat(Math.max(0, 2 - bot.vidas))}
            </strong>
          </div>
        </div>

        {/* Revólver visual */}
        <div className={styles.revolverSection}>
          <p className={styles.revolverTitle}>🔫 Revólver</p>
          <div className={styles.chambers}>
            {balas.map((bala, i) => {
              let cls = styles.chamber;
              if (i < posAtual) cls += " " + styles.chamberFired;
              else if (i === posAtual) cls += " " + styles.chamberCurrent;
              return <div key={i} className={cls} title={`Câmara ${i + 1}`} />;
            })}
          </div>
          <p className={styles.revolverInfo}>
            {estado.quantVerdadeiras} bala(s) real(is) escondida(s) em{" "}
            {balas.length} câmara(s)
          </p>
        </div>

        {/* Vez de quem */}
        <div className={styles.turnInfo}>
          <h2>{vezDe === "jogador" ? "🎯 Sua vez" : "🤖 Vez do Bot"}</h2>
          <p>
            {esperandoBot
              ? "O bot está pensando..."
              : vezDe === "jogador"
                ? "Escolha seu alvo"
                : ""}
          </p>
        </div>

        {/* Ações */}
        <div className={styles.actions}>
          <button
            className={`${styles.actionBtn} ${styles.actionSelf}`}
            onClick={() => agir("self")}
            disabled={!podeAgir}
          >
            <span className={styles.actionIcon}>🎰</span>
            <span>Atirar em mim</span>
            <span className={styles.actionLabel}>Mantém a vez se falsa</span>
          </button>
          <button
            className={`${styles.actionBtn} ${styles.actionOpponent}`}
            onClick={() => agir("opponent")}
            disabled={!podeAgir}
          >
            <span className={styles.actionIcon}>💀</span>
            <span>Atirar no Bot</span>
            <span className={styles.actionLabel}>Passa a vez</span>
          </button>
        </div>

        {/* Log */}
        <div className={styles.log}>
          {[...log].reverse().map((entry, i) => {
            let cls = styles.logEntry;
            if (entry.includes("REAL")) cls += " " + styles.logEntryHit;
            else if (entry.includes("falsa")) cls += " " + styles.logEntryMiss;
            else if (entry.includes("🔁") || entry.includes("Rodada"))
              cls += " " + styles.logEntrySystem;
            return (
              <p key={i} className={cls}>
                {entry}
              </p>
            );
          })}
        </div>
      </div>
    </div>
  );
}
