// --- VERIFICAÇÃO DE AUTENTICAÇÃO ---
const usuarioLogado = JSON.parse(localStorage.getItem("usuarioLogado"));

if (!usuarioLogado) {
  window.location.href = "login.html";
} else {
  const userGreeting = document.getElementById("userGreeting");
  if (userGreeting) {
    userGreeting.textContent = `Olá, ${usuarioLogado.nome.split(" ")[0]}!`;
  }
}

// Botão de Logout
const btnLogout = document.getElementById("btnLogout");
if (btnLogout) {
  btnLogout.addEventListener("click", () => {
    localStorage.removeItem("usuarioLogado");
    window.location.href = "login.html";
  });
}

const API_URL = "http://localhost:8080/api/academias";

const searchInput = document.getElementById("searchInput");
const searchBtn = document.getElementById("searchBtn");
const priceFilter = document.getElementById("priceFilter");
const priceValue = document.getElementById("priceValue");
const cardsGrid = document.getElementById("cardsGrid");
const navBusca = document.getElementById("navBusca");

// Elementos do Modal
const btnOpenModal = document.getElementById("btnOpenModal");
const btnCloseModal = document.getElementById("btnCloseModal");
const modalCadastrar = document.getElementById("modalCadastrar");
const formCadastroAcademia = document.getElementById("formCadastroAcademia");

// Foco no campo de busca
if (navBusca) {
  navBusca.addEventListener("click", () => {
    setTimeout(() => {
      searchInput.focus();
    }, 300);
  });
}

// Atualiza o texto do filtro de preço e recarrega
priceFilter.addEventListener("input", (e) => {
  priceValue.textContent = e.target.value;
  carregarAcademias();
});

// Eventos de Busca
searchBtn.addEventListener("click", carregarAcademias);
searchInput.addEventListener("keypress", (e) => {
  if (e.key === "Enter") carregarAcademias();
});

// Abrir e fechar Modal
btnOpenModal.addEventListener("click", () => {
  modalCadastrar.style.display = "flex";
});

btnCloseModal.addEventListener("click", () => {
  modalCadastrar.style.display = "none";
});

window.addEventListener("click", (e) => {
  if (e.target === modalCadastrar) {
    modalCadastrar.style.display = "none";
  }
});

// --- BUSCA NA API ---
async function carregarAcademias() {
  const termo = searchInput.value.trim();
  const precoMax = parseFloat(priceFilter.value);

  let url = `${API_URL}/busca?precoMax=${precoMax}`;
  if (termo) {
    url += `&termo=${encodeURIComponent(termo)}`;
  }

  try {
    const response = await fetch(url);
    if (!response.ok) throw new Error("Erro ao consultar a API Java");
    
    const academias = await response.json();
    renderizarCards(academias, precoMax);
  } catch (error) {
    console.error(error);
    cardsGrid.innerHTML = `<p style="color: #ef4444; grid-column: 1/-1;">Não foi possível carregar as academias. Verifique se o back-end Java está rodando na porta 8080.</p>`;
  }
}

// --- RENDERIZAÇÃO DOS CARDS ---
function renderizarCards(academias, precoMax) {
  cardsGrid.innerHTML = "";

  if (!academias || academias.length === 0) {
    cardsGrid.innerHTML = "<p style='grid-column: 1/-1; color: var(--text-muted);'>Nenhuma academia encontrada com os critérios informados.</p>";
    return;
  }

  let totalCardsRenderizados = 0;

  academias.forEach((academia) => {
    if (!academia.planos || academia.planos.length === 0) return;

    // Filtra para exibir apenas os planos dentro do preço máximo selecionado
    const planosValidos = academia.planos.filter(
      (plano) => Number(plano.precoMensal) <= Number(precoMax)
    );

    planosValidos.forEach((plano) => {
      totalCardsRenderizados++;
      const card = document.createElement("div");
      card.className = `card-plano ${plano.destaque ? "destaque" : ""}`;

      const tagDestaque = plano.destaque 
        ? `<div class="badge-vantagem">O mais vantajoso</div>` 
        : "";

      const valorFormatado = Number(plano.precoMensal).toFixed(2).replace(".", ",");
      const urlGoogleMaps = `https://www.google.com/maps/search/?api=1&query=${encodeURIComponent(academia.nome + " " + academia.bairro + " " + academia.cidade)}`;

      card.innerHTML = `
        ${tagDestaque}
        <h4>${plano.nomePlano}</h4>
        <p class="academia-origem"><i class="fa-solid fa-dumbbell"></i> ${academia.nome} - ${academia.bairro}, ${academia.cidade}</p>
        
        <div class="card-preco">
          <span class="label-partir">A partir de</span>
          <div class="valor">R$ ${valorFormatado} <span style="font-size: 0.9rem; font-weight: normal;">/mês</span></div>
          <small>${plano.fidelidade || "Sem fidelidade"}</small>
        </div>

        <ul class="beneficios-list">
          <li><i class="fa-solid fa-circle-check" style="color: #22c55e;"></i> ${plano.beneficios || "Acesso à musculação"}</li>
          <li><i class="fa-solid fa-star" style="color: #eab308;"></i> Avaliação: ${academia.notaAvaliacao || 5.0} / 5.0</li>
        </ul>

        <button class="card-btn" onclick="window.open('${urlGoogleMaps}', '_blank')">Ver Academia</button>
      `;

      cardsGrid.appendChild(card);
    });
  });

  if (totalCardsRenderizados === 0) {
    cardsGrid.innerHTML = "<p style='grid-column: 1/-1; color: var(--text-muted);'>Nenhum plano encontrado abaixo dessa faixa de preço.</p>";
  }
}

// --- CADASTRO DE NOVA ACADEMIA VIA MODAL ---
if (formCadastroAcademia) {
  formCadastroAcademia.addEventListener("submit", async (e) => {
    e.preventDefault();

    const nome = document.getElementById("cadNome").value;
    const cidade = document.getElementById("cadCidade").value;
    const bairro = document.getElementById("cadBairro").value;
    const planoNome = document.getElementById("cadPlanoNome").value;
    const preco = parseFloat(document.getElementById("cadPreco").value);

    const novaAcademia = {
      nome: nome,
      cidade: cidade,
      bairro: bairro,
      endereco: `${bairro}, ${cidade}`,
      notaAvaliacao: 5.0,
      planos: [
        {
          nomePlano: planoNome,
          precoMensal: preco,
          fidelidade: "Sem fidelidade",
          beneficios: "Acesso completo aos equipamentos e aulas",
          destaque: false
        }
      ]
    };

    try {
      const response = await fetch(API_URL, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(novaAcademia),
      });

      if (!response.ok) {
        throw new Error(`Erro na API: ${response.status}`);
      }

      alert("Academia cadastrada com sucesso!");
      formCadastroAcademia.reset();
      modalCadastrar.style.display = "none";

      // Atualiza os dados na tela em tempo real
      carregarAcademias();
    } catch (err) {
      console.error(err);
      alert("Erro ao cadastrar academia. Certifique-se de que o backend está ativo.");
    }
  });
}

// Carga inicial
document.addEventListener("DOMContentLoaded", carregarAcademias);