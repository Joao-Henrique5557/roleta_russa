import styles from "./Formulario.module.css";
import Input from "../Imput/Input";
import BtnAzul from "../../BTNs/BtnAzul/BtnAzul";
import { useState, useRef } from "react";

function Formulario({ tipo, onSwitch, onSubmit }) {
  const [usuario, setUsuario] = useState("");
  const [email, setEmail] = useState("");
  const [senha, setSenha] = useState("");
  const usuarioRef = useRef(null);
  const emailRef = useRef(null);
  const senhaRef = useRef(null);

  function isOk() {
    if (!usuario) {
      usuarioRef.current?.focus();
      return false;
    }
    if (tipo === "cadastro" && !email) {
      emailRef.current?.focus();
      return false;
    }
    if (!senha) {
      senhaRef.current?.focus();
      return false;
    }
    return true;
  }

  const handleSubmit = (event) => {
    event.preventDefault();
    if (!isOk()) return;
    if (onSubmit) onSubmit({ usuario, email, senha });
  };

  return (
    <form className={styles.form} onSubmit={handleSubmit}>
      <Input
        ref={usuarioRef}
        tipoInput="text"
        placeholder="Usuário"
        value={usuario}
        onChange={(e) => setUsuario(e.target.value)}
        name="inputUsuario"
      />
      {tipo === "cadastro" && (
        <Input
          ref={emailRef}
          tipoInput="email"
          placeholder="E-mail"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          name="inputEmail"
        />
      )}
      <Input
        ref={senhaRef}
        tipoInput="password"
        placeholder="Senha"
        value={senha}
        onChange={(e) => setSenha(e.target.value)}
        name="inputSenha"
      />
      <BtnAzul
        placeholder={tipo === "login" ? "Entrar" : "Cadastrar"}
        type="submit"
      />
      <p>
        {tipo === "login" ? "Não tem uma conta? " : "Já tem uma conta? "}
        <button type="button" className={styles.linkButton} onClick={onSwitch}>
          {tipo === "login" ? "Cadastre-se" : "Faça login"}
        </button>
      </p>
    </form>
  );
}

export default Formulario;
