import React, { useState, useEffect } from "react";
import Login from './components/Login'; // Se till att du har den här raden
import './App.css';

function App() {
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  useEffect(() => {
    console.log("Checking localStorage for token...");
    const token = localStorage.getItem("token");
    if (token) {
      console.log("User is logged in.");
      setIsLoggedIn(true);
    } else {
      console.log("No token found, user is not logged in.");
    }
  }, []);

  const handleLogout = () => {
    console.log("Logging out...");
    localStorage.removeItem("token");
    setIsLoggedIn(false);
  };

  return (
      <div className="App">
        <h1>Welcome to the App</h1>
        {isLoggedIn ? (
            <div>
              <p>Logged in!</p>
              <button onClick={handleLogout}>Logout</button>
            </div>
        ) : (
            <div>
              <p>Please log in</p>
              <Login />
            </div>
        )}
      </div>
  );
}

export default App;