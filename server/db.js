var db = function(){
    //users: [{userid, name, email}[],
    //messages: [{text, user, createdAt, chatId}]
    var userList = {};
    var messageList = {};

};

function getUserWithId(userId) {
    return db.users[userId];
}

function insertUser(user){
     if (!getUserWithId()){
        db.userList.push(user);
        user._id = users.length;
    }
}

function insertMessage(messageData) {
    db.messageList.push(messageData);
}
export {getUserWithId, insertMessage, insertUser}
