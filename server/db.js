const ROLES = {
  ADMIN: "ADMIN",
  USER: "USER",
  BANNED: "BANNED"
}

var users = [{
  role: ROLES.ADMIN,
  email: "sam@cogni.com",
  name: "Sam Liem"
}];

var messages = [{
  text: "message text 1",
  user: {
    role: ROLES.ADMIN,
    email: "sam@cogni.com",
    name: "Sam Liem"
  },
  createdAt: Date.now(),
  channel: 0
}];

function existsUser(email) {
  return users.some((u) => u.email == email);
}

function getUser(email) {
  return users.filter((u) => u.email == email)[0];
}

function createUser(email, name) {
  var user = null;
  if (!existsUser(email)) {
    var user = {
      role: ROLES.USER,
      email: email,
      name: name,
    };
    users.push(user);
  }
  return user;
}

function insertMessage(messageData) {
  messages.push(messageData);
  console.log(messages);
}

function getMessagesByChannel(channel) {
  return messages.filter((m) => m.channel == channel).sort((a, b) => {
    return a.createdAt < b.createdAt;
  });
}

module.exports = {
  getUser,
  existsUser,
  createUser,
  insertMessage,
  getMessagesByChannel
};