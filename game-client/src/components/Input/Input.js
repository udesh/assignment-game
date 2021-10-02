import React from 'react'
import Button from '@material-ui/core/Button';

const Input = ({ onSendMessage }) => {
    
    let onStart = () => {
        onSendMessage("START");
    }
    let onMinusOnePress = () => {
        onSendMessage("-1");
    }
    let onZeroPress = () => {
        onSendMessage("0");
    }
    let onOnePress = () => {
        onSendMessage("1");
    }

    return (
        <div className="message-input">
           
            <Button variant="contained" color="primary" onClick={onStart}>
                Start
            </Button>
            <Button variant="contained" color="primary" onClick={onMinusOnePress}>
                -1
            </Button>
            <Button variant="contained" color="primary" onClick={onZeroPress}>
                0
            </Button>
            <Button variant="contained" color="primary" onClick={onOnePress}>
                1
            </Button>
        </div>
    );
}


export default Input
