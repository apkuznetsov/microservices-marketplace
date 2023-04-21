'use strict';

const usernamePage = document.querySelector('#username-page');
const chatPage = document.querySelector('#chat-page');
const usernameForm = document.querySelector('#usernameForm');
const messageForm = document.querySelector('#messageForm');
const messageInput = document.querySelector('#message');
const messageArea = document.querySelector('#messageArea');
const connectingElement = document.querySelector('.connecting');

let stompClient = null;
let username = null;

const colors = [
  '#2196F3', '#32c787', '#00BCD4', '#ff5652',
  '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];

function connect(event) {
  username = document.querySelector('#name').value.trim();

  if (username) {
    const socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, onConnected, onError);
  }

  event.preventDefault();
}

function onConnected() {
  stompClient.subscribe('/user/queue/reply', onPersonalMessageReceived)
  stompClient.send("/app/chat.enter", {},
      JSON.stringify({
        sender: 'anonymous',
        type: 'JOIN',
        content: username
      })
  );
}

function onError(error) {
  connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
  connectingElement.style.color = 'red';
}

function onPersonalMessageReceived(payload) {
  const message = JSON.parse(payload.body);

  if (message.type === 'LOGIN_ACCEPTED') {
    showChat();
    showServerMessageInChat(message.content, 'event-message');
    stompClient.subscribe('/topic/public', onMessageReceived);
  } else if (message.type === 'LOGIN_DECLINED') {
    stompClient.disconnect();
  } else if (message.type === 'LOGIN_REQUIRED') {
    showServerMessageInChat(message.content, 'error-message');
  } else if (message.type === 'COMMAND_ERROR') {
    showServerMessageInChat(message.content, 'error-message');
  } else if (message.type === 'COMMAND_RESULT') {
    showServerMessageInChat(message.content, 'command-result');
  }
}

function showChat() {
  usernamePage.classList.add('hidden');
  chatPage.classList.remove('hidden');
  connectingElement.classList.add('hidden');
}

function showServerMessageInChat(content, style) {
  const messageElement = document.createElement('li');
  messageElement.classList.add(style);

  const textElement = document.createElement('p');
  const messageText = document.createTextNode(content);

  textElement.appendChild(messageText);
  messageElement.appendChild(textElement);
  messageArea.appendChild(messageElement);

  messageArea.scrollTop = messageArea.scrollHeight;
}

function sendMessage(event) {
  const content = messageInput.value.trim();
  if (content && stompClient) {
    const message = {
      sender: username,
      content: messageInput.value,
      type: 'CHAT'
    };
    if (content.startsWith("/")) {
      stompClient.send("/app/chat.command", {}, JSON.stringify(message));
      messageInput.value = '';
    } else {
      stompClient.send("/app/chat.send", {}, JSON.stringify(message));
      messageInput.value = '';
    }
  }
  event.preventDefault();
}

function onMessageReceived(payload) {
  const message = JSON.parse(payload.body);
  const messageElement = document.createElement('li');

  if (message.type === 'JOIN') {
    messageElement.classList.add('event-message');
  } else if (message.type === 'LEAVE') {
    messageElement.classList.add('event-message');
  } else {
    messageElement.classList.add('chat-message');

    const avatarElement = document.createElement('i');
    const avatarText = document.createTextNode(message.sender[0]);
    avatarElement.appendChild(avatarText);
    avatarElement.style['background-color'] = getAvatarColor(message.sender);

    messageElement.appendChild(avatarElement);

    const usernameElement = document.createElement('span');
    const usernameText = document.createTextNode(message.sender);
    usernameElement.appendChild(usernameText);

    const timeElement = document.createElement('span');
    const timeText = document.createTextNode(getCurrentTimeString());
    timeElement.appendChild(timeText);
    timeElement.classList.add('time-element');

    messageElement.appendChild(usernameElement);
    messageElement.appendChild(timeElement);
  }

  const textElement = document.createElement('p');
  const messageText = document.createTextNode(message.content);

  textElement.appendChild(messageText);

  messageElement.appendChild(textElement);

  messageArea.appendChild(messageElement);
  messageArea.scrollTop = messageArea.scrollHeight;
}

function getCurrentTimeString() {
  const today = new Date();
  return withLeadingZero(today.getHours()) + ":" +
      withLeadingZero(today.getMinutes()) + ":" +
      withLeadingZero(today.getSeconds());
}

function withLeadingZero(number) {
  if (number < 10) {
    return "0" + number;
  } else {
    return number;
  }
}

function getAvatarColor(messageSender) {
  let hash = 0;
  for (let i = 0; i < messageSender.length; i++) {
    hash = 31 * hash + messageSender.charCodeAt(i);
  }
  const index = Math.abs(hash % colors.length);
  return colors[index];
}

usernameForm.addEventListener('submit', connect, true);
messageForm.addEventListener('submit', sendMessage, true);
