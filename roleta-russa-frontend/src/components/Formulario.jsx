import "../styles/components/formulario.css";
import Input from "./Input";
import BtnAzul from "./BtnAzul";

function Formulario({ tipo }) {
    if (tipo === "login") {
        return (
            <form className="form" action="entrar" method="post">
                <Input tipoInput="text" placeholder="Usuário" />
                <Input tipoInput="password" placeholder="Senha" />
                <BtnAzul placeholder="Entrar" />
                <p>Não tem uma conta? <a href="irTelaCadastro">Cadastre-se</a></p> 
            </form>
        )
    } else if (tipo === "cadastro") {
        return (
            <form className="form" action="cadastrar" method="post">
                <Input tipoInput="text" placeholder="Usuário" />
                <Input tipoInput="email" placeholder="E-mail" />
                <Input tipoInput="password" placeholder="Senha" />
                <BtnAzul placeholder="Cadastrar" />
                <p>Já tem uma conta? <a href="irTelaLogin">Faça login</a></p> 
            </form>
        )
    }
}
export default Formulario;