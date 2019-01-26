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

websocket.on('connection', (socket) => {
  clients[socket.id] = socket;
  socket.on('login', (email, password) => onUserSignIn(email, password, socket));
  socket.on('userJoined', (userId) => onUserJoined(userId, socket));
  socket.on('message', (message, channel) => onMessageReceived(message, channel, socket));
});

function onUserSignIn(email, password, socket) {
  var userId = signIn(email, password);
  socket.emit('login success', userId);
}

// Event listeners.
// When a user joins the chatroom.
function onUserJoined(userId, socket) {
  users[socket.id] = db.getUserWithId(userId);
  _sendExistingMessages(socket);
}

// When a user sends a message in the chatroom.
function onMessageReceived(text, channel, senderSocket) {
  var user = users[senderSocket.id];
  // Safety check.
  if (!user) return;

  _sendAndSaveMessage({
    text: text,
    user: user.userId,
    createdAt: Date.now(),
    channel: channel
  }, senderSocket);
}

// Helper functions.
// Send the pre-existing messages to the user that just joined.
function _sendExistingMessages(socket) {
  sendExistingMessagesByChannel(0, socket);
}

function sendExistingMessagesByChannel(channel = 0, socket) {
  var messages = db.getMessagesByChannel(channel);
  if (!messages.length) return;
  socket.emit('message', messages);
}

// Save the message to the db and send all sockets but the sender.
function _sendAndSaveMessage(message, socket) {
  db.insertMessage(message);
  websocket.emit('message', [message]);
}