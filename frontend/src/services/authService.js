import axiosInstance from "../axiosInstance";

// Registrering av användare
export const registerUser = async (username, password) => {
    try {
        const response = await axiosInstance.post('/api/auth/register', {
            username,
            password,
        });
        return response.data;
    } catch (error) {
        console.error('Registration failed:', error);
        throw error;
    }
};

// Inloggning av användare
export const loginUser = async (username, password) => {
    try {
        const response = await axiosInstance.post('/api/auth/login', null, {
            params: {
                username,
                password,
            }
        });
        // Spara JWT-token i localStorage
        const token = response.data.token;
        localStorage.setItem('token', token);
        return response.data;
    } catch (error) {
        console.error('Login failed:', error);
        throw error;
    }
};

// Logout
export const logoutUser = () => {
    localStorage.removeItem('token');
};