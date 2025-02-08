import axios from 'axios';

// Skapa en Axios-instans
const axiosInstance = axios.create({
    baseURL: 'http://localhost:8080', // Din backend-URL
    timeout: 10000,
});

// Hantera JWT-token som finns i cookie eller localStorage
axiosInstance.interceptors.request.use((config) => {
    const token = localStorage.getItem('token'); // Hämtar token från localStorage

    if (token) {
        config.headers['Authorization'] = `Bearer ${token}`; // Lägg till token i headern
    }

    return config;
}, (error) => {
    return Promise.reject(error);
});

export default axiosInstance;