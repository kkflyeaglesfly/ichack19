const ROLES = {
  ADMIN: "ADMIN",
  USER: "USER",
  BANNED: "BANNED"
}

var users = [{
  userId: 0,
  password: "password",
  role: ROLES.ADMIN,
  email: "sam@cogni.com",
  name: "Sam Liem"
}];

var messages = [{
  text: "Welcome!",
  user: users[0].userId,
  createdAt: Date.now(),
  channel: 0
}];

function signIn(email, password) {
  var userId = -1;
  var user = user.filter((u) => u.email == email && u.password == password);
  if (user.length == 1) {
    return user.userId;
  }
  return userId;
}

function existsUser(email) {
  return users.some((u) => u.email == email);
}

function getUserWithId(userId) {
  return users.filter((u) => u.userId == userId)[0];
}

function createUser(email, name) {
  var user = null;
  if (!existsUser(email)) {
    var user = {
      role: ROLES.USER,
      email: email,
      name: name,
      userId: users.length
    };
    users.push(user);
  }
  return user;
}

function insertMessage(messageData) {
  messages.push(messageData);
}

function getMessagesByChannel(channel) {
  return messages.filter((m) => m.channel == channel).sort((a, b) => {
    return a.createdAt < b.createdAt;
  });
}

module.exports = {
  signIn,
  existsUser,
  getUserWithId,
  createUser,
  insertMessage,
  getMessagesByChannel
};