let baseUrl = 'http://localhost:8081';
let livrosCache = [];

function alternarTema() {
  const atual = document.documentElement.getAttribute('data-theme');
  const novo = atual === 'dark' ? 'light' : 'dark';
  document.documentElement.setAttribute('data-theme', novo);
  document.getElementById('botaoTema').textContent = novo === 'dark' ? 'Tema claro' : 'Tema escuro';
  localStorage.setItem('apilivro_tema', novo);
}

function trocarPagina(id) {
  document.querySelectorAll('.page').forEach(p => p.classList.remove('ativo'));
  document.getElementById(id).classList.add('ativo');
  window.scrollTo(0, 0);
}

function estaLogado() {
  return localStorage.getItem('apilivro_token') !== null;
}

async function fazerLogin() {
  const login = document.getElementById('campoUsuario').value.trim();
  const senha = document.getElementById('campoSenha').value.trim();
  const erro = document.getElementById('erroLogin');

  if (login.length === 0 || senha.length === 0) {
    erro.textContent = 'Preencha usuário e senha.';
    erro.style.display = 'block';
    return;
  }

  try {
    const resp = await fetch(baseUrl + '/auth/login', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ login: login, senha: senha })
    });

    if (!resp.ok) {
      erro.textContent = 'Usuário ou senha inválidos.';
      erro.style.display = 'block';
      return;
    }

    const dados = await resp.json();
    localStorage.setItem('apilivro_token', dados.token);
    localStorage.setItem('apilivro_nome_usuario', dados.nome);
    erro.style.display = 'none';
    irParaHome();
  } catch (e) {
    erro.textContent = 'Não foi possível conectar à API em ' + baseUrl + '.';
    erro.style.display = 'block';
  }
}

async function fazerRegistro() {
  const nome = document.getElementById('campoNomeRegistro').value.trim();
  const login = document.getElementById('campoUsuarioRegistro').value.trim();
  const senha = document.getElementById('campoSenhaRegistro').value.trim();
  const erro = document.getElementById('erroRegistro');

  if (nome.length === 0 || login.length === 0 || senha.length === 0) {
    erro.textContent = 'Preencha todos os campos.';
    erro.style.display = 'block';
    return;
  }

  try {
    const resp = await fetch(baseUrl + '/auth/registrar', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ nome: nome, login: login, senha: senha })
    });

    if (!resp.ok) {
      const corpo = await resp.json().catch(() => null);
      erro.textContent = (corpo && corpo.mensagem) ? corpo.mensagem : 'Não foi possível criar a conta. O usuário já pode existir.';
      erro.style.display = 'block';
      return;
    }

    const dados = await resp.json();
    localStorage.setItem('apilivro_token', dados.token);
    localStorage.setItem('apilivro_nome_usuario', dados.nome);
    erro.style.display = 'none';
    irParaHome();
  } catch (e) {
    erro.textContent = 'Não foi possível conectar à API em ' + baseUrl + '.';
    erro.style.display = 'block';
  }
}

function irParaHome() {
  if (!estaLogado()) {
    trocarPagina('pageLogin');
    return;
  }
  const nome = localStorage.getItem('apilivro_nome_usuario') || 'leitor';
  document.getElementById('boasVindas').textContent = 'Seja bem-vindo, ' + nome + '!';
  trocarPagina('pageHome');
  carregarLivros();
}

function sair() {
  localStorage.removeItem('apilivro_token');
  localStorage.removeItem('apilivro_nome_usuario');
  irParaHome();
}

async function carregarLivros() {
  const c1 = document.getElementById('listaRecentes');
  const c2 = document.getElementById('listaAvaliados');
  c1.innerHTML = '<div class="estado-vazio">Carregando...</div>';
  c2.innerHTML = '';

  try {
    const resp = await fetch(baseUrl + '/livros');
    if (!resp.ok) throw new Error('status ' + resp.status);
    livrosCache = await resp.json();

    preencherFiltroGenero();
    renderizarListas();
  } catch (e) {
    c1.innerHTML = '<div class="estado-erro">Não foi possível conectar à API em ' + baseUrl + '. Clique em "API" no topo para ajustar o endereço.</div>';
  }
}

function preencherFiltroGenero() {
  const select = document.getElementById('filtroGenero');
  const generos = [...new Set(livrosCache.map(l => l.genero))].sort();
  select.innerHTML = '<option value="">Todos os gêneros</option>' +
    generos.map(g => `<option value="${g}">${g}</option>`).join('');
}

function renderizarListas() {
  const busca = document.getElementById('campoBusca').value.toLowerCase();
  const genero = document.getElementById('filtroGenero').value;

  const filtrados = livrosCache.filter(l =>
    l.titulo.toLowerCase().includes(busca) &&
    (genero === '' || l.genero === genero)
  );

  const c1 = document.getElementById('listaRecentes');
  const c2 = document.getElementById('listaAvaliados');

  if (filtrados.length === 0) {
    c1.innerHTML = '<div class="estado-vazio">Nenhum livro encontrado.</div>';
    c2.innerHTML = '';
    return;
  }

  c1.innerHTML = '';
  filtrados.forEach(l => c1.appendChild(criarCard(l)));

  c2.innerHTML = '';
  [...filtrados].reverse().forEach(l => c2.appendChild(criarCard(l)));
}

function criarCard(livro) {
  const card = document.createElement('div');
  card.className = 'livro-card';
  card.onclick = () => mostrarDetalhe(livro);

  const img = document.createElement('img');
  img.className = 'livro-capa';
  img.src = livro.urlCapa && livro.urlCapa.length > 0 ? livro.urlCapa : placeholderCapa();
  img.onerror = () => { img.src = placeholderCapa(); };

  const info = document.createElement('div');
  info.className = 'livro-info';
  info.innerHTML =
    '<p class="livro-titulo">' + livro.titulo + '</p>' +
    '<p class="livro-autor">' + livro.nomeAutora + '</p>';

  card.appendChild(img);
  card.appendChild(info);
  return card;
}

function placeholderCapa() {
  return 'data:image/svg+xml;utf8,' + encodeURIComponent(
    '<svg xmlns="http://www.w3.org/2000/svg" width="200" height="300"><rect width="100%" height="100%" fill="#cfd9ea"/><text x="50%" y="50%" fill="#5c7290" font-size="13" text-anchor="middle" dy=".3em">sem capa</text></svg>'
  );
}

function mostrarDetalhe(livro) {
  document.getElementById('detalheCapa').src = livro.urlCapa && livro.urlCapa.length > 0 ? livro.urlCapa : placeholderCapa();
  document.getElementById('detalheNome').textContent = livro.titulo;
  document.getElementById('detalheAutor').textContent = 'por ' + livro.nomeAutora;
  document.getElementById('detalheSinopse').textContent = livro.sinopse && livro.sinopse.length > 0 ? livro.sinopse : 'Sinopse ainda não cadastrada para este livro.';
  document.getElementById('detalheTags').innerHTML =
    '<span class="tag">' + livro.genero + '</span>' +
    '<span class="tag">' + livro.anoPublicacao + '</span>' +
    '<span class="tag">' + livro.nomeEditora + '</span>';

  const sugestoes = document.getElementById('listaSugestoes');
  sugestoes.innerHTML = '';
  livrosCache.filter(l => l.id !== livro.id).slice(0, 6).forEach(l => sugestoes.appendChild(criarCard(l)));

  trocarPagina('pageDetalhe');
}

const temaSalvo = localStorage.getItem('apilivro_tema') || 'light';
document.documentElement.setAttribute('data-theme', temaSalvo);
document.getElementById('botaoTema').textContent = temaSalvo === 'dark' ? 'Tema claro' : 'Tema escuro';

irParaHome();
