const express = require('express');
const app = express();
const bodyParser = require('body-parser');
const cookieParser = require('cookie-parser');
const cors = require('cors');
const helmet = require('helmet');
const http = require('http');
const httpProxy = require('express-http-proxy');
const jwt = require('jsonwebtoken');
const logger = require('morgan');
const { emitWarning } = require('process');
require("dotenv-safe").config();

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(cors());
app.use(helmet());
app.use(logger('dev'));

// Proxy
const authServiceProxy = httpProxy('http://localhost:8081');
const clienteServiceProxy = httpProxy('http://localhost:8082');
const sagaClienteUsuarioServiceProxy = httpProxy('http://localhost:8080');

//ENDPOINTS

// LOGIN
app.post('/Auth/login', (req, res, next) => {
    authServiceProxy(req, res, next);
});

//CLIENTE
// Buscar Cliente pelo email
app.get('/clientes/:email', (req, res, next) => {
    clienteServiceProxy(req, res, next);
});








// // Cadastrar cliente
// app.post('/sagaClienteUsuario', (req, res, next) => {
//     sagaClienteUsuarioServiceProxy(req, res, next);
// });





// // 0.2 - Endpoint para Logout
// app.post('/logout', function (req, res) {
//     res.json({ auth: false, token: null });
// });

// // 1 - Endpoints para ms-usuario (CRUD Usuario)
// const usuarioServiceProxy = httpProxy('http://localhost:3001');
// // 1.1 - Endpoint para cadastrar Usuário
// app.post('/auth', (req, res, next) => {
//     usuarioServiceProxy(req, res, next);
// });
// // 1.2 - Endpoint para recuperar Senha
// app.post('/auth/recuperar', (req, res, next) => {
//     usuarioServiceProxy(req, res, next);
// });
// // 1.3 - Endpoint para buscar Usuário por ID
// app.get('/auth/id/:id', verifyJWT, (req, res, next) => {
//     usuarioServiceProxy(req, res, next);
// });

// });
// // 1.5 - Endpoint para buscar Usuário por CPF
// app.get('/auth/cpf/:cpf', verifyJWT, (req, res, next) => {
//     usuarioServiceProxy(req, res, next);
// });

// // 1.6 - Endpoint para atualizar Usuário por CPF
// app.put('/auth/:cpf', verifyJWT, async (req, res, next) => {
//     usuarioServiceProxy(req, res, next);
// });

// // 1.7 - Endpoint para deletar Usuário por ID
// app.delete('/auth/id/:id', verifyJWT, (req, res, next) => {
//     usuarioServiceProxy(req, res, next);
// });
// // 1.8 - Endpoint para deletar Usuário por Login
// app.delete('/auth/login/:login', verifyJWT, (req, res, next) => {
//     usuarioServiceProxy(req, res, next);
// });
// // 1.9 - Endpoint para deletar Usuário por CPF
// app.delete('/auth/cpf/:cpf', verifyJWT, (req, res, next) => {
//     usuarioServiceProxy(req, res, next);
// });

// // 2 - Endpoints para ms-mensagens (CRUD Avaliação)
// const mensagemServiceProxy = httpProxy('http://localhost:3002');

// // 2.1 - Endpoint para registrar Avaliação
// app.post('/quest', verifyJWT, (req, res, next) => {
//     mensagemServiceProxy(req, res, next);
// });
// // 2.2 - Endpoint para listar todas Avaliações
// app.get('/quest', verifyJWT, (req, res, next) => {
//     mensagemServiceProxy(req, res, next);
// });
// // 2.3 - Endpoint para buscar Avaliação por ID (da avaliação)
// app.get('/quest/id/:id', verifyJWT, (req, res, next) => {
//     mensagemServiceProxy(req, res, next);
// });
// // 2.4 - Endpoint para buscar Avaliação por CPF
// app.get('/quest/cpf/:cpf', verifyJWT, (req, res, next) => {
//     mensagemServiceProxy(req, res, next);
// });
// // 2.5 - Endpoint para atualizar Avaliação por ID (da avaliação)
// app.put('/quest/:id', verifyJWT, (req, res, next) => {
//     mensagemServiceProxy(req, res, next);
// });

// // 3 - Endpoints para ms-mensagens (CRUD Favoritos)
// // 3.1 - Endpoint para cadastrar Endereço Favorito
// app.post('/favoritos', verifyJWT, (req, res, next) => {
//     mensagemServiceProxy(req, res, next);
// });
// // 3.2 - Endpoint para listar Endereço Favorito pelo seu ID
// app.get('/favoritos/id/:id', verifyJWT, (req, res, next) => {
//     mensagemServiceProxy(req, res, next);
// });
// // 3.3 - Endpoint para listar Endereços Favoritos pelo CPF do Usuario
// app.get('/favoritos/cpf/:cpf', verifyJWT, (req, res, next) => {
//     mensagemServiceProxy(req, res, next);
// });
// // 3.4 - Endpoint para listar Endereço Favorito pelo ID Google
// app.get('/favoritos/idGoogle/:idGoogle', verifyJWT, (req, res, next) => {
//     mensagemServiceProxy(req, res, next);
// });
// // 3.5 - Endpoint para atualizar Endereço Favorito pelo seu ID
// app.put('/favoritos/:id', verifyJWT, (req, res, next) => {
//     mensagemServiceProxy(req, res, next);
// });
// // 3.6 - Endpoint para excluir Endereço Favorito pelo seu ID
// app.delete('/favoritos/id/:id', verifyJWT, (req, res, next) => {
//     mensagemServiceProxy(req, res, next);
// });
// // 3.7 - Endpoint para excluir Endereços Favoritos pelo CPF do Usuario
// app.delete('/favoritos/cpf/:cpf', verifyJWT, (req, res, next) => {
//     mensagemServiceProxy(req, res, next);
// });
// // 3.8 - Endpoint para excluir Endereço Favorito pelo ID Google
// app.delete('/favoritos/idGoogle/:idGoogle', verifyJWT, (req, res, next) => {
//     mensagemServiceProxy(req, res, next);
// });

// // 4  - Endpoint para ms-parking (Consultar Vagas de Estacionamento e suas Probabilidades)
// const parkingServiceProxy = httpProxy('http://localhost:5000');
// // 4.1 - Endpoint que envia um 'location(lng, lat)' no body e 
// // recebe um JSON com uma lista de poligonos/vagas mais proximos e suas informacoes
// app.post('/parking', verifyJWT, (req, res, next) => {
//     parkingServiceProxy(req, res, next);
// });

// Cria o servidor na porta 3000
const server = http.createServer(app);
server.listen(3000, () => {
    console.log('Servidor rodando na porta 3000');
});