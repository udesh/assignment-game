import React, { useState } from 'react';
import gameAPI from '../../services/gameapi';
import TextField from '@material-ui/core/TextField';
import Button from '@material-ui/core/Button';
import { Checkbox } from '@material-ui/core';

const LoginForm = ({ onSubmit }) => {

    const [username, setUsername] = useState("");
    const [auto, setAuto] = useState(true);
    const [manual, setManual] = useState(false);

    let handleUserNameChange = event => setUsername(event.target.value);
    let handleAutoChange = () => { setAuto(!auto); setManual(auto)}
    let handleManualChange = () => { setManual(!manual); setAuto(manual);}

    let handleSubmit = () => {
        var playmode = "AUTO";
        if (auto) {
            playmode = "AUTO"
        } else {
            playmode = "MANUAL"
        }
        gameAPI.sendLoginMessage(username, playmode).then(res => {
            console.log('Sent', res);
          }).catch(err => {
            console.log('Error Occured while sending message to api');
          })
        onSubmit(username);
    }

    return (
        <div>
            <TextField
                label="Type your username"
                placeholder="Username"
                onChange={handleUserNameChange}
                margin="normal"
                onKeyPress={event => {
                    if (event.key === 'Enter') {
                        handleSubmit();
                    }
                }}
            />
            <br />
            <Button variant="contained" color="primary" onClick={handleSubmit} >
                Login
             </Button>
             
             <br />
             <div>
             <label>Auto</label>
             </div>
             <Checkbox checked={auto} onChange={handleAutoChange} ></Checkbox>
             <div>
             <br/>
             <label>Manual</label>
             </div>
             <Checkbox checked={manual} onChange={handleManualChange} ></Checkbox>

        </div>
    )
}

export default LoginForm
