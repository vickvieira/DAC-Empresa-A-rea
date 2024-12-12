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
const reservaQueryProxy = httpProxy('http://localhost:8085');
const voosProxy = httpProxy('http://localhost:8087');


// ENDPOINTS

///////// MS-AUTH /////////
// FAZER LOGIN          
app.post('/Auth/login', (req, res, next) => {       //Endpoint definido no back em Auth > LoginController
    authServiceProxy(req, res, next);
});


//////////. MS-CLIENTE /////////

// BUSCAR TODOS OS CLIENTES
app.get('/clientes', (req, res, next) => {   //Endpoint definido no back em Clientes > ClienteController
    clienteServiceProxy(req, res, next);
});

//BUSCAR CLIENTE POR ID
app.get('/clientes/:id', (req, res, next) => {   //Endpoint definido no back em Clientes > ClienteController
    clienteServiceProxy(req, res, next);
});




///////// VOOS /////////
//BUSCAR TODOS OS VOOS
app.get('/voos/todos', (req, res, next) => {   //Endpoint definido no back em Voos > VoosController
    voosProxy(req, res, next);
});

//BUSCAR VOO POR CODIGO
app.get('/voos/getVooByCodigo/:codigoVoo', (req, res, next) => {   //Endpoint definido no back em Voos > VoosController
    voosProxy(req, res, next);
});

// BUSCAR VOO POR ORIGEM E DESTINO
app.get('/voos', (req, res, next) => {   // Endpoint definido no back em Voos > VoosController
    const { aeroportoOrigem, aeroportoDestino } = req.query;

    if (!aeroportoOrigem || !aeroportoDestino) {
        return res.status(400).json({ error: "Os parâmetros 'aeroportoOrigem' e 'aeroportoDestino' são obrigatórios." });
    }

    voosProxy(req, res, next);
});


///////// SAGA CLIENTE USUÁRIO /////////

// Cadastrar cliente            
app.post('/sagaClienteUsuario', (req, res, next) => {   //Endpoint definido no back em SagaClienteUsuario > SagaController 
    sagaClienteUsuarioServiceProxy(req, res, next);
});


//////// SAGA RESERVA CLIENTE ////////

// CADASTRAR RESERVA
app.post('/sagaReservaCliente', (req, res, next) => {   //Endpoint definido no back em SagaReserva|Cliente > SagaController 
    sagaReservaClienteProxy(req, res, next);
});


//CANCELAR RESERVA
app.post('/sagaReservaCliente/cancelarReserva/:reserva', (req, res, next) => {   //Endpoint definido no back em SagaReserva|Cliente > SagaController 
    sagaReservaClienteProxy(req, res, next);
});

// RESERVA QUERY
//CONSULTAR RESERVA
app.get('reservaquery/getReserva/:codigoReserva', (req, res, next) => {   //Endpoint definido no back em ReservaQuery > ReservaController 
    reservaQueryProxy(req, res, next);
});

// Cria o servidor na porta 3001
const server = http.createServer(app);
server.listen(3001, () => {
    console.log('Servidor rodando na porta 3001');
});