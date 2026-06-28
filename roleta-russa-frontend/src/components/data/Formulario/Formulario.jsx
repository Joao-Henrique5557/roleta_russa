import styles from "./Formulario.module.css";
import Input from "../Imput/Input";
import BtnAzul from "../../BTNs/BtnAzul/BtnAzul";
import { useState, useRef, useEffect } from "react";
import axios from "axios";

function Formulario({ tipo, onSwitch, onSubmit, urlAPI }) {
  const [usuario, setUsuario] = useState("");
  const [email, setEmail] = useState("");
  const [senha, setSenha] = useState("");
  const usuarioRef = useRef(null);
  const emailRef = useRef(null);
  const senhaRef = useRef(null);
  const [error, setError] = useState(null);
  const [loading, setLoanding] = useState(false);

  function isOk() {
    if (!usuario) {
      // campo usuario vazio?
      usuarioRef.current?.focus();
      alert("preencha o campo usuario");
      setError("campo vazio");
      return false;
    }
    if (tipo === "cadastro" && !email) {
      // campo email de cadastro vazio?
      emailRef.current?.focus();
      alert("preencha o campo email");
      setError("campo vazio");
      return false;
    }
    if (!senha) {
      // campo senha vazio?
      senhaRef.current?.focus();
      alert("preencha o campo senha");
      setError("campo vazio");
      return false;
    }
    return true;
  }

  const handleSubmit = async (event) => {
    event.preventDefault(); // Não recarrega a página
    if (!isOk()) return; // Validação local de campos vazios

    const params = new URLSearchParams();
    params.append("usuario", usuario);
    params.append("senha", senha);

    setLoanding(true);
    if (tipo === "cadastro") {
      // --- FLUXO DE CADASTRO ---
      try {
        const paramsCadastro = new URLSearchParams();
        paramsCadastro.append("nome", usuario);
        paramsCadastro.append("email", email);
        paramsCadastro.append("senha", senha);

        const response = await axios.post(
          `${urlAPI}/CadastrarServlet`,
          paramsCadastro,
          { timeout: 5000 },
        );

        if (response.status === 200 || response.status === 201) {
          alert("Cadastro realizado com sucesso!");
          onSwitch(); // Muda automaticamente para a tela de Login
        }
      } catch (error) {
        if (error.code === "ECONNABORTED") {
          setError("Carregamento lento, tente novamente.");
          console.error("timeout executado");
        } else {
          console.error(error);
          setError("Erro ao realizar cadastro. Tente outro e-mail.");
        }
      } finally {
        setLoanding(false);
      }
    } else if (tipo === "login") {
      try {
        // Faz um POST enviando apenas as credenciais digitadas
        const response = await axios.post(
          `${urlAPI}/AutenticarServlet`,
          params,
        );

        if (response.status === 200) {
          const dadosUsuarioLogado = response.data;
          alert(`Bem-vindo de volta, ${dadosUsuarioLogado.nome}!`);

          localStorage.setItem("usuario", JSON.stringify(dadosUsuarioLogado));
          console.log("usuario armazenado: " + localStorage.getItem("usuario"))
          onSubmit(); // Avança para a tela Home do app
        }
      } catch (error) {
        console.error(error);
        if (error.response && error.response.status === 401) {
          setError("Usuário ou senha incorretos.");
        } else {
          setError("Erro ao conectar com o servidor.");
        }
      } finally {
        setLoanding(false);
      }
    }
  };

  useEffect(() => {
    if(localStorage.getItem("usuario")){
      onSubmit();
    }
  });

  return (
    <form className={styles.form} onSubmit={handleSubmit}>
      {error && loading ? <p>{loading}</p> : ""}
      {/* se tiver erro e estiver carregando */}
      {error && loading == false ? <p>{error}</p> : ""}
      {/* se tiver erro e não estiver carregando */}
      {error == null && loading ? <p>Carregando...</p> : ""}
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
