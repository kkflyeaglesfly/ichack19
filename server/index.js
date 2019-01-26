var express = require('express');
var http = require('http')
var db = require('./db.js');
var app = express();
var server = http.Server(app);
var socketio = require('socket.io');
var websocket = socketio(server);
server.listen(4000, () => console.log('listening on *:4000'));

// Mapping objects to easily map sockets and users.
var clients = {};
var users = {};

// This represents a unique chatroom.
// For this example purpose, there is only one chatroom;
var chatId = 0;

websocket.on('connection', (socket) => {
  clients[socket.id] = socket;
  socket.on('userJoined', (email, name) => onUserJoined(email, name, socket));
  socket.on('message', (message) => onMessageReceived(message, socket));
});

// Event listeners.
// When a user joins the chatroom.
function onUserJoined(email, name, socket) {
  var user;
  if (!db.existsUser(email)) {
    user = db.createUser(email, name);
    socket.emit('userJoined', user);
  } else {
    user = db.getUser(email);
  }
  users[socket.id] = user;
  _sendExistingMessages(socket);
}

// When a user sends a message in the chatroom.
function onMessageReceived(text, senderSocket) {
  var user = users[senderSocket.id];
  // Safety check.
  if (!user) return;

  _sendAndSaveMessage({
    text: text,
    user: user,
    createdAt: Date.now(),
    channel: 0
  }, senderSocket);
}

// Helper functions.
// Send the pre-existing messages to the user that just joined.
function _sendExistingMessages(socket) {
  sendExistingMessagesByChannel(socket, 0);
}

function sendExistingMessagesByChannel(socket, channel = 0) {
  var messages = db.getMessagesByChannel(channel);
  if (!messages.length) return;
  socket.emit('message', messages);
}

// Save the message to the db and send all sockets but the sender.
function _sendAndSaveMessage(message, socket) {
  var messageData = {
    text: message.text,
    user: message.user,
    createdAt: message.createdAt,
    channel: 0
  };

  db.insertMessage(messageData);
  websocket.emit('message', [messageData]);
}