export interface ExtratoMilhas {
    //criei esse model separadado de milhas, só pra nao dar conflito com outras coisas
    id?: string;
    clienteId: string;
    dataHora: string;
    saldo: number;
    operacao: number;
    //mais coisa

}
