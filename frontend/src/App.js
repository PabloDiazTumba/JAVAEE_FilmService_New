import React, { useEffect, useState } from 'react';
import Login from './components/Login';
import { logoutUser } from './services/authService';
import axiosInstance from './axiosInstance';

const App = () => {
  const [isAuthenticated, setIsAuthenticated] = useState(false);

  // Kontrollera om användaren är autentiserad vid sidladdning
  useEffect(() => {
    const token = localStorage.getItem('token');
    if (token) {
      setIsAuthenticated(true);
    }
  }, []);

  // Hantera utloggning
  const handleLogout = () => {
    logoutUser();
    setIsAuthenticated(false);
  };

  // Om användaren är inloggad, visa en skyddad sida
  const ProtectedPage = () => {
    const [data, setData] = useState(null);

    useEffect(() => {
      const fetchData = async () => {
        try {
          const response = await axiosInstance.get('/api/protected'); // Byt till din skyddade endpoint
          setData(response.data);
        } catch (error) {
          console.error(error);
        }
      };

      fetchData();
    }, []);

    return (
        <div>
          <h1>Skyddad sida</h1>
          <p>{data ? data.message : 'Laddar data...'}</p>
          <button onClick={handleLogout}>Logga ut</button>
        </div>
    );
  };

  return (
      <div>
        <h1>Välkommen till din app</h1>
        {isAuthenticated ? (
            <ProtectedPage />
        ) : (
            <Login onLogin={() => setIsAuthenticated(true)} />
        )}
      </div>
  );
};

export default App;