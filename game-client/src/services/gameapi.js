import Axios from "axios";

const api = Axios.create({
    baseURL: '/api/',
});

const gameAPI = {
    getMessages: (groupId) => {
        console.log('Calling get messages from API');
        return api.get(`messages/${groupId}`);
    },

    sendMessage: (username, text, id) => {
        let msg = {
            sender: username,
            command: text,
            gameId:id
        }
        return api.post(`send`, msg);
    },

    sendLoginMessage: (username, playmode) => {
        let msg = {
            name: username,
            playMode: playmode
        }
        return api.post(`login`, msg);
    }
}


export default gameAPI;
