const AUTH_API = "http://localhost:8080/api/auth";

// Se já estiver logado, vai direto para a tela principal
if (localStorage.getItem("usuarioLogado")) {
  window.location.href = "index.html";
}

const tabLogin = document.getElementById("tabLogin");
const tabRegister = document.getElementById("tabRegister");
const formLogin = document.getElementById("formLogin");
const formRegister = document.getElementById("formRegister");
const authAlert = document.getElementById("authAlert");

// Alternar entre abas
tabLogin.addEventListener("click", () => {
  tabLogin.classList.add("active");
  tabRegister.classList.remove("active");
  formLogin.style.display = "block";
  formRegister.style.display = "none";
  esconderAlerta();
});

tabRegister.addEventListener("click", () => {
  tabRegister.classList.add("active");
  tabLogin.classList.remove("active");
  formRegister.style.display = "block";
  formLogin.style.display = "none";
  esconderAlerta();
});

function mostrarAlerta(msg, tipo = "erro") {
  authAlert.textContent = msg;
  authAlert.className = `auth-alert ${tipo}`;
  authAlert.style.display = "block";
}

function esconderAlerta() {
  authAlert.style.display = "none";
}

// 1. Processar Login
formLogin.addEventListener("submit", async (e) => {
  e.preventDefault();
  const email = document.getElementById("loginEmail").value.trim();
  const senha = document.getElementById("loginSenha").value.trim();

  try {
    const res = await fetch(`${AUTH_API}/login`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ email, senha })
    });

    const data = await res.json();

    if (!res.ok) {
      mostrarAlerta(data.mensagem || "Erro ao fazer login.", "erro");
      return;
    }

    // Salva o usuário na sessão do navegador e redireciona
    localStorage.setItem("usuarioLogado", JSON.stringify(data));
    window.location.href = "index.html";
  } catch (err) {
    mostrarAlerta("Não foi possível conectar ao servidor Java.", "erro");
  }
});

// 2. Processar Cadastro
formRegister.addEventListener("submit", async (e) => {
  e.preventDefault();
  const nome = document.getElementById("regNome").value.trim();
  const email = document.getElementById("regEmail").value.trim();
  const senha = document.getElementById("regSenha").value.trim();

  try {
    const res = await fetch(`${AUTH_API}/registro`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ nome, email, senha })
    });

    const data = await res.json();

    if (!res.ok) {
      mostrarAlerta(data.mensagem || "Erro ao cadastrar.", "erro");
      return;
    }

    mostrarAlerta("Cadastro realizado com sucesso! Faça login.", "sucesso");
    setTimeout(() => {
      tabLogin.click();
      document.getElementById("loginEmail").value = email;
    }, 1500);
  } catch (err) {
    mostrarAlerta("Não foi possível conectar ao servidor Java.", "erro");
  }
});