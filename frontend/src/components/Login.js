import React, { useState } from 'react';
import { loginUser } from '../services/authService';

const Login = ({ onLogin }) => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            const response = await loginUser(username, password);
            if (response.token) {
                onLogin(); // Om inloggningen lyckas, kör onLogin-funktionen
            }
        } catch (err) {
            setError('Fel användarnamn eller lösenord!');
        }
    };

    return (
        <div>
            <h2>Logga in</h2>
            {error && <p>{error}</p>}
            <form onSubmit={handleSubmit}>
                <div>
                    <label>Användarnamn:</label>
                    <input
                        type="text"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                    />
                </div>
                <div>
                    <label>Lösenord:</label>
                    <input
                        type="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                    />
                </div>
                <button type="submit">Logga in</button>
            </form>
        </div>
    );
};

export default Login;