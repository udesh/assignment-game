import React, { useState } from 'react';
import SockJsClient from 'react-stomp';
import './App.css';
import Input from './components/Input/Input';
import LoginForm from './components/Login';
import Messages from './components/Messages/Messages';
import gameAPI from './services/gameapi';
import { randomColor } from './utils/common';


const SOCKET_URL = 'http://localhost:8080/ws-game/';

const App = () => {
  const [messages, setMessages] = useState([])
  const [user, setUser] = useState(null)
  const [gameId, setGame] = useState(null)

  let onConnected = () => {
  }

  let onMessageReceived = (msg) => {
    setMessages(messages.concat(msg));
    setGame(msg.gameId);
  }

  let onSendMessage = (msgText) => {
    gameAPI.sendMessage(user.username, msgText, gameId).then(res => {
    }).catch(err => {
      console.log('Error Occured while sending message to api' );
    })
  }

  let handleLoginSubmit = (username) => {
    setUser({
      username: username,
      color: randomColor()
    })

  }

  return (
    <div className="App">
      {!!user ?
        (
          <>
            <SockJsClient
              url={SOCKET_URL}
              topics={['/topic/group']}
              onConnect={onConnected}
              onDisconnect={console.log("Disconnected!")}
              onMessage={msg => onMessageReceived(msg)}
              debug={false}
            />
            <Messages
              messages={messages}
              currentUser={user}
            />
            <Input onSendMessage={onSendMessage} />
          </>
        ) :
        <LoginForm onSubmit={handleLoginSubmit} />
      }
    </div>
  )
}

export default App;
