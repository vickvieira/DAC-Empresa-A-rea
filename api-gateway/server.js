const express = require('express');
const app = express();
const bodyParser = require('body-parser');
const cookieParser = require('cookie-parser');
const cors = require('cors');
const helmet = require('helmet');
const http = require('http');
const httpProxy = require('express-http-proxy');
const logger = require('morgan');

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(cors());
app.use(helmet());
app.use(logger('dev'));

//Configurações de portas (back-end\.env):
// AUTH_PORT=8081
// CLIENTE_PORT=8082
// SAGACLIENTEUSUARIO_PORT=8080
// SAGARESERVACLIENTE_PORT=8083
// RESERVA_COMMAND_PORT=8084
// RESERVA_QUERY_PORT=8085
// FUNCIONARIO_PORT=8086
// VOOS_PORT=8087

// PROXY
const authServiceProxy = httpProxy('http://localhost:8081');
const sagaClienteUsuarioServiceProxy = httpProxy('http://localhost:8080');
const clienteServiceProxy = httpProxy('http://localhost:8082');
const sagaReservaClienteProxy = httpProxy('http://localhost:8083');
const reservaCommandProxy = httpProxy('http://localhost:8084');
const reservaQueryProxy = httpProxy('http://localhost:8085');
const funcionarioProxy = httpProxy('http://localhost:8086');
const voosProxy = httpProxy('http://localhost:8087');


// ENDPOINTS

// 1. AUTH
// 1.1 FAZER LOGIN          
app.post('/Auth/login', (req, res, next) => {       //Endpoint definido no back em Auth > LoginController.java 
    authServiceProxy(req, res, next);
});
// 1.2 LOGOUT
app.post('/Auth/logut', function (req, res) {
    //Implementar método em back-end\Auth\src\main\java\controller\LoginController.java
    res.json({ auth: false, token: null });
});


// 2. SAGA CLIENTE USUÁRIO
// 2.1 Cadastrar cliente            
app.post('/sagaClienteUsuario', (req, res, next) => {   //Endpoint definido no back em SagaClienteUsuario > SagaController.java 
    sagaClienteUsuarioServiceProxy(req, res, next);
});
// 2.2 Buscar cliente por email
app.get('/sagaClienteUsuario/:email', (req, res, next) => {
    //Implementar método em back-end\SagaClienteUsuario\src\main\java\controller\SagaController.java
    sagaClienteUsuarioServiceProxy(req, res, next);
});
// 2.3 Atualizar cliente por email
app.put('/sagaClienteUsuario/:email', (req, res, next) => {      //Implementar método em back-end\SagaClienteUsuario\src\main\java\controller\SagaController.java
    sagaClienteUsuarioServiceProxy(req, res, next);
});
// 2.4 Excluir cliente por email
app.delete('/sagaClienteUsuario/:email', (req, res, next) => {     //Implementar método em back-end\SagaClienteUsuario\src\main\java\controller\SagaController.java
    sagaClienteUsuarioServiceProxy(req, res, next);
});

//Outros endpoints

// Cria o servidor na porta 3001
const server = http.createServer(app);
server.listen(3001, () => {
    console.log('Servidor rodando na porta 3001');
});
