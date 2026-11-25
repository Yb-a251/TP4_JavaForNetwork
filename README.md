# 1 . TCP SERVER 
## Step 1 

We created the TCPServer class with 2 constructors, we can use either the default port (8080), or choose a port. 

![Test with default port and port 9000](/images/step1.png)

## Step 2 - TCP Server Implementation

We added a ServerSocket to accept TCP connections to the launch() method.

The server receives messages line by line using UTF-8 and sends them back with the client's IP address.

When the client disconnects, the server closes the socket and waits for a new connection. 

## Step 3 - Testing with Netcat

We tested the TCP server using Netcat to connect on localhost.
The message were sent back to the client's IP address, 
and when we disconnect the client, the server waits for a new connection.


![TCP server test with Netcat](/images/step3.png)

## Step 4 - Real client

We created the TCPClient class to establish a connection
with the server using its hostname and port.

The client reads messages from the keyboard, sends them
to the server in UTF-8 and displays the received responses.

![TCP client](/images/step4.png)

## Step 5 - Error Handling

We added some exception handling to display clear error messages
when : 
-   the hostname is unknown, 
-   the server is not running, or
-   the connection is lost.

We also check if readLine() returns null to detect when
the server closes the connection.

The client detects the disconnection during the next exchange
with the server.

![Error tests](images/step5.png)

## Step 6 - Client and Server Tests

### a) Message exchange

We sent several messages and the server correctly returned
each message with the client's IP address.

### b) Character encoding

With UTF-8 on both sides, accented and Chinese characters
were displayed correctly.

![UTF-8 test](images/step6_utf8.png)

With ISO-8859-1 on the client and UTF-8 on the server,
some special characters were not displayed correctly.

This shows why the client and the server should use
the same encoding.

![Encoding test](images/step6_encoding.png)

## Step 7 - Two Clients

The second client can establish a TCP connection, even if
the server has not called accept() for it yet.

BUt it won't receive a response because the server
is waiting for messages from the first client.

![Two connected clients](images/step7_before1.png)
![Two connected clients](images/step7_before2.png)


When the first client disconnects, the server accepts the
second client and processes the message that was buffered

![Second client receiving a response](images/step7_after1.png)
![Second client receiving a response](images/step7_after2.png)
Ths server doesn't manage clients simultaneously 

## Step 8 - Client Disconnection

After stopping the client with Ctrl+C, readLine() returned null
and the server closed the client socket.

The ss command shows two connections in TIME-WAIT
and no connection in CLOSE-WAIT.

TIME-WAIT allows old TCP segments to disappear and the
last ACK to be sent again if necessary.

We also managed to reconnect to the server.

![Client disconnection](images/step8.png)

## Step 9 - Connection Setup

### TCP

The TCP connection starts with a three-way handshake
(SYN, SYN-ACK, ACK).

![Three-way handshake](images/step_handshake.png)

We captured 7 TCP packets: 3 for the connection setup,
2 for data transmission and 2 for acknowledgments.

![TCP communication](images/step9_tcp.png)

### UDP

We captured 3 UDP datagrams. Unlike TCP, UDP does not
require a connection setup or transport-layer acknowledgments.

![UDP communication](images/step9_udp.png)

### Comparison

TCP exchanges additional packets to establish the connection,
acknowledge data and close the connection. UDP has less
overhead because it is connectionless.

## Step 10 - Stream versus Messages

### Small messages

The client sends three small messages using three write() calls.
The server can receive all 14 bytes in a single read() call.

![Small messages](images/step10_small.png)

### Large message

The client sends 10000 bytes using a single write() call.
The server needs several read() calls because the buffer
size is limited to 1024 bytes.

![Large message](images/step10_large.png)

Unlike UDP, TCP does not preserve message boundaries.
Using println() and readLine() in our previous implementation
allows us to separate messages using line breaks.


## Step 11 - Errors

TCP reports an error when the server is not running.

With UDP, the message is sent but no response is received.

After adding connect(), the UDP client detects the ICMP
Port Unreachable error.

TCP can detect when the other side closes the connection,
while UDP cannot automatically detect a disconnection.

![TCP and UDP errors](images/step11.png)

## Step 12 - TCP vs UDP

### a) Reliability mechanisms

In TP2, we implemented sequence numbers, acknowledgments,
retransmissions and duplicate detection ourselves.
With TCP, these mechanisms are handled automatically.

### b) UDP applications

UDP is useful for online gaming, video calls and live streaming,
where low latency is more important than receiving every packet.