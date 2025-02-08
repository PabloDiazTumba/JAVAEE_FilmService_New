import axios from "axios";

const API_URL = "http://localhost:8080/api/auth/";

export const login = async (username, password) => {
    try {
        const response = await axios.post(API_URL + "login", {
            username,
            password,
        });

        console.log("Login Response:", response.data);

        if (response.data.token) {
            localStorage.setItem("token", response.data.token);
            return response.data;
        }
    } catch (error) {
        console.error("Login failed:", error.response?.data || error.message);
    }
};

// Logout-funktion
export const logout = () => {
    localStorage.removeItem("token");  // Ta bort token från localStorage
    console.log("User logged out");
};