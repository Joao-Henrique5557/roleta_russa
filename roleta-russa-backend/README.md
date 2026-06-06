# Internet e redes: ligando java a conceitos de rede.

- InetAddress: representa o endereço IP(IPV4 ou IPV6).
- NetworkInterface: interface de rede.

Conceito importante: 
A server socket, implemented in Java as the ServerSocket class, is a specialized network endpoint that listens for incoming client connection requests on a specific port.  Unlike a standard socket that facilitates two-way communication, the server socket’s primary role is passive: it binds to a local IP address and port, waits for clients to initiate contact, and then accepts those requests. 

Upon accepting a connection, the server socket typically creates a new, distinct Socket instance dedicated to that specific client.  This allows the server to continue listening on the original port for additional incoming connections while the newly created socket handles the bidirectional data stream with the connected client.  This architecture enables a single server process to manage multiple concurrent clients by assigning each a unique communication channel.

- SocketServer: Um soquete de servidor (ou server socket) é um ponto de extremidade de comunicação em redes de computadores que atua como o lado passivo e receptor em uma arquitetura cliente-servidor. 

- Socket: especie de canal de comunicação com o servidor.

- Host: destino.

- Porta: canal de entrada do servidor.

- BufferedReader: Lê dados de entrada do socket de forma eficiente. No jogo, ele escuta as mensagens enviadas pela rede.

- PrintWriter: Envia strings e dados formatados pelo socket. O parâmetro autoFlush garante o envio imediato.

- Bidirecional: O Socket fornece um InputStream para leitura e um OutputStream para escrita simultânea.

- ClientHandler: Classe que implementa Runnable para gerenciar a lógica individual.

- Broadcasting: comunicação em grupo.

Ciclo de vida da conexão:
1. Escuta
2. Conexão
3. Troca (I/O via Streams(Protocolo))
4. Encerramento

Requisitos:
- Fila de espera: 2 em 2
- Timeout: ações automáticas (AFK)
- Encapsulamento de lógica: apenas o servidor controla o jogo
- Sincronização: evitar conflitos de dados em múltiplas threads