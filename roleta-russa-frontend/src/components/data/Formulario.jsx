import "../../styles/components/form.css";
import Input from "./Input";
import BtnAzul from "../BTNs/BtnAzul";

function Formulario({ tipo, onSwitch, onSubmit }) {
  const handleSubmit = (event) => {
    event.preventDefault();
    if (onSubmit) {
      onSubmit();
    }
  };

  if (tipo === "login") {
    return (
      <form className="form" onSubmit={handleSubmit}>
        <Input tipoInput="text" placeholder="Usuário" />
        <Input tipoInput="password" placeholder="Senha" />
        <BtnAzul placeholder="Entrar" type="submit" />
        <p>
          Não tem uma conta?{" "}
          <button type="button" className="link-button" onClick={onSwitch}>
            Cadastre-se
          </button>
        </p>
      </form>
    );
  } else if (tipo === "cadastro") {
    return (
      <form className="form" onSubmit={handleSubmit}>
        <Input tipoInput="text" placeholder="Usuário" />
        <Input tipoInput="email" placeholder="E-mail" />
        <Input tipoInput="password" placeholder="Senha" />
        <BtnAzul placeholder="Cadastrar" type="submit" />
        <p>
          Já tem uma conta?{" "}
          <button type="button" className="link-button" onClick={onSwitch}>
            Faça login
          </button>
        </p>
      </form>
    );
  }
}
export default Formulario;
